from collections import defaultdict
from datetime import datetime, timedelta
from decimal import Decimal, ROUND_HALF_UP

from app.schemas import (
    CategorySummary,
    DecisionCase,
    FinancialAnalysisRequest,
    FinancialAnalysisResponse,
    FinancialStatistics,
    ForecastMetric,
    PeriodMetric,
    TimePeriod,
    TransactionType,
)


MONEY = Decimal("0.01")
PROBABILITY = Decimal("0.0001")


class FinancialMathService:

    def analyze(self, request: FinancialAnalysisRequest) -> FinancialAnalysisResponse:
        transactions = sorted(request.transactions, key=lambda item: item.occurredAt)

        total_income = sum(
            (t.amount for t in transactions if t.type == TransactionType.INCOME),
            Decimal("0")
        )
        total_expense = sum(
            (t.amount for t in transactions if t.type == TransactionType.EXPENSE),
            Decimal("0")
        )
        balance = self._money(total_income - total_expense)

        expense_rate = Decimal("0")
        if total_income > 0:
            expense_rate = (total_expense / total_income).quantize(
                Decimal("0.01"),
                rounding=ROUND_HALF_UP
            )

        category_expenses = self._category_expenses(transactions)
        period_metrics = self._build_period_metrics(transactions, request.period)
        statistics = self._build_statistics(period_metrics)
        current_account_balance = self._current_account_balance(transactions, balance)
        forecast = self._build_forecast(
            period_metrics,
            request.period,
            request.forecastPeriods,
            current_account_balance
        )

        return FinancialAnalysisResponse(
            totalIncome=self._money(total_income),
            totalExpense=self._money(total_expense),
            balance=balance,
            expenseRate=expense_rate,
            categoryExpenses=category_expenses,
            period=request.period,
            periodMetrics=period_metrics,
            forecast=forecast,
            statistics=statistics,
            decisionCases=self._build_decision_cases(statistics, current_account_balance)
        )

    def _category_expenses(self, transactions) -> list[CategorySummary]:
        category_totals: dict[str, Decimal] = defaultdict(lambda: Decimal("0"))

        for transaction in transactions:
            if transaction.type == TransactionType.EXPENSE:
                category_name = (
                    transaction.category.name
                    if transaction.category and transaction.category.name
                    else "SEM_CATEGORIA"
                )
                category_totals[category_name] += transaction.amount

        return [
            CategorySummary(category=category, total=self._money(total))
            for category, total in category_totals.items()
        ]

    def _build_period_metrics(self, transactions, period: TimePeriod) -> list[PeriodMetric]:
        grouped: dict[str, dict[str, Decimal]] = defaultdict(
            lambda: {"income": Decimal("0"), "expense": Decimal("0")}
        )

        for transaction in transactions:
            key = self._period_key(transaction.occurredAt, period)

            if transaction.type == TransactionType.INCOME:
                grouped[key]["income"] += transaction.amount
            else:
                grouped[key]["expense"] += transaction.amount

        running_balance = Decimal("0")
        metrics: list[PeriodMetric] = []

        for key in sorted(grouped):
            income = self._money(grouped[key]["income"])
            expense = self._money(grouped[key]["expense"])
            profit = self._money(income - expense)
            running_balance = self._money(running_balance + profit)

            metrics.append(
                PeriodMetric(
                    period=key,
                    income=income,
                    expense=expense,
                    profit=profit,
                    endingBalance=running_balance
                )
            )

        return metrics

    def _build_statistics(self, metrics: list[PeriodMetric]) -> FinancialStatistics:
        if not metrics:
            return FinancialStatistics(
                periodsAnalyzed=0,
                averageIncomePerPeriod=Decimal("0.00"),
                averageExpensePerPeriod=Decimal("0.00"),
                averageProfitPerPeriod=Decimal("0.00"),
                minProfit=Decimal("0.00"),
                maxProfit=Decimal("0.00"),
                profitVolatility=Decimal("0.00"),
                positiveProfitProbability=Decimal("0.0000"),
                positiveBalanceProbability=Decimal("0.0000")
            )

        count = Decimal(len(metrics))
        profits = [metric.profit for metric in metrics]
        average_income = sum((metric.income for metric in metrics), Decimal("0")) / count
        average_expense = sum((metric.expense for metric in metrics), Decimal("0")) / count
        average_profit = sum(profits, Decimal("0")) / count
        variance = sum(((profit - average_profit) ** 2 for profit in profits), Decimal("0")) / count
        volatility = Decimal(str(float(variance) ** 0.5))
        positive_profit_count = sum(1 for metric in metrics if metric.profit > 0)
        positive_balance_count = sum(1 for metric in metrics if metric.endingBalance > 0)

        return FinancialStatistics(
            periodsAnalyzed=len(metrics),
            averageIncomePerPeriod=self._money(average_income),
            averageExpensePerPeriod=self._money(average_expense),
            averageProfitPerPeriod=self._money(average_profit),
            minProfit=self._money(min(profits)),
            maxProfit=self._money(max(profits)),
            profitVolatility=self._money(volatility),
            positiveProfitProbability=self._probability(Decimal(positive_profit_count) / count),
            positiveBalanceProbability=self._probability(Decimal(positive_balance_count) / count)
        )

    def _build_forecast(
        self,
        metrics: list[PeriodMetric],
        period: TimePeriod,
        forecast_periods: int,
        current_balance: Decimal
    ) -> list[ForecastMetric]:
        if not metrics or forecast_periods <= 0:
            return []

        statistics = self._build_statistics(metrics)
        last_date = self._parse_period_start(metrics[-1].period, period)
        expected_balance = current_balance
        forecast: list[ForecastMetric] = []

        for index in range(1, forecast_periods + 1):
            forecast_period = self._period_key(
                self._add_period(last_date, period, index),
                period
            )
            expected_balance = self._money(expected_balance + statistics.averageProfitPerPeriod)
            probability = self._projected_balance_probability(
                expected_balance,
                statistics.averageProfitPerPeriod,
                statistics.profitVolatility,
                statistics.positiveBalanceProbability
            )

            forecast.append(
                ForecastMetric(
                    period=forecast_period,
                    expectedIncome=statistics.averageIncomePerPeriod,
                    expectedExpense=statistics.averageExpensePerPeriod,
                    expectedProfit=statistics.averageProfitPerPeriod,
                    expectedBalance=expected_balance,
                    probabilityPositiveBalance=probability
                )
            )

        return forecast

    def _build_decision_cases(
        self,
        statistics: FinancialStatistics,
        current_balance: Decimal
    ) -> list[DecisionCase]:
        scenarios = [
            ("CONSERVADOR", Decimal("0.90"), Decimal("1.10")),
            ("BASE", Decimal("1.00"), Decimal("1.00")),
            ("AGRESSIVO", Decimal("1.10"), Decimal("0.95")),
        ]
        cases: list[DecisionCase] = []

        for name, income_factor, expense_factor in scenarios:
            expected_income = statistics.averageIncomePerPeriod * income_factor
            expected_expense = statistics.averageExpensePerPeriod * expense_factor
            expected_profit = self._money(expected_income - expected_expense)
            expected_balance = self._money(current_balance + expected_profit)
            probability = self._projected_balance_probability(
                expected_balance,
                expected_profit,
                statistics.profitVolatility,
                statistics.positiveBalanceProbability
            )

            cases.append(
                DecisionCase(
                    scenario=name,
                    expectedProfit=expected_profit,
                    expectedBalance=expected_balance,
                    probabilityPositiveBalance=probability,
                    suggestedAction=self._suggest_action(expected_profit, probability)
                )
            )

        return cases

    def _projected_balance_probability(
        self,
        expected_balance: Decimal,
        expected_profit: Decimal,
        volatility: Decimal,
        historical_probability: Decimal
    ) -> Decimal:
        if expected_balance <= 0:
            return Decimal("0.0000")

        if volatility == 0:
            return Decimal("1.0000") if expected_profit >= 0 else historical_probability

        margin = expected_balance / (volatility + abs(expected_profit) + Decimal("0.01"))
        score = min(Decimal("1"), max(Decimal("0"), margin / Decimal("2")))
        return self._probability((score + historical_probability) / Decimal("2"))

    def _suggest_action(self, expected_profit: Decimal, probability: Decimal) -> str:
        if expected_profit > 0 and probability >= Decimal("0.70"):
            return "MANTER_E_EXPANDIR"
        if expected_profit >= 0 and probability >= Decimal("0.50"):
            return "MANTER_COM_CONTROLE"
        if expected_profit < 0 and probability >= Decimal("0.50"):
            return "REDUZIR_GASTOS"
        return "REVISAR_DECISAO"

    def _period_key(self, value: datetime, period: TimePeriod) -> str:
        if period == TimePeriod.DAY:
            return value.strftime("%Y-%m-%d")
        if period == TimePeriod.YEAR:
            return value.strftime("%Y")
        return value.strftime("%Y-%m")

    def _parse_period_start(self, value: str, period: TimePeriod) -> datetime:
        if period == TimePeriod.DAY:
            return datetime.strptime(value, "%Y-%m-%d")
        if period == TimePeriod.YEAR:
            return datetime.strptime(value, "%Y")
        return datetime.strptime(value, "%Y-%m")

    def _add_period(self, value: datetime, period: TimePeriod, amount: int) -> datetime:
        if period == TimePeriod.DAY:
            return value + timedelta(days=amount)
        if period == TimePeriod.YEAR:
            return value.replace(year=value.year + amount)

        month_index = value.month - 1 + amount
        year = value.year + month_index // 12
        month = month_index % 12 + 1
        return value.replace(year=year, month=month)

    def _current_account_balance(self, transactions, calculated_balance: Decimal) -> Decimal:
        if not transactions:
            return calculated_balance

        latest_transaction = max(transactions, key=lambda item: item.occurredAt)
        return self._money(latest_transaction.account.balance)

    def _money(self, value: Decimal) -> Decimal:
        return value.quantize(MONEY, rounding=ROUND_HALF_UP)

    def _probability(self, value: Decimal) -> Decimal:
        return min(Decimal("1"), max(Decimal("0"), value)).quantize(
            PROBABILITY,
            rounding=ROUND_HALF_UP
        )
