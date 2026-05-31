from decimal import Decimal, ROUND_HALF_UP
from collections import defaultdict

from app.schemas import (
    FinancialAnalysisRequest,
    FinancialAnalysisResponse,
    CategorySummary,
    TransactionType,
)


class FinancialMathService:

    def analyze(self, request: FinancialAnalysisRequest) -> FinancialAnalysisResponse:
        transactions = request.transactions

        total_income = sum(
            (t.amount for t in transactions if t.type == TransactionType.INCOME),
            Decimal("0")
        )

        total_expense = sum(
            (t.amount for t in transactions if t.type == TransactionType.EXPENSE),
            Decimal("0")
        )

        balance = total_income - total_expense

        expense_rate = Decimal("0")

        if total_income > 0:
            expense_rate = (total_expense / total_income).quantize(
                Decimal("0.01"),
                rounding=ROUND_HALF_UP
            )

        category_totals = defaultdict(lambda: Decimal("0"))

        for transaction in transactions:
            if transaction.type == TransactionType.EXPENSE:
                category_name = (
                    transaction.category.name
                    if transaction.category and transaction.category.name
                    else "SEM_CATEGORIA"
                )

                category_totals[category_name] += transaction.amount

        category_expenses = [
            CategorySummary(
                category=category,
                total=total
            )
            for category, total in category_totals.items()
        ]

        return FinancialAnalysisResponse(
            totalIncome=total_income,
            totalExpense=total_expense,
            balance=balance,
            expenseRate=expense_rate,
            categoryExpenses=category_expenses
        )