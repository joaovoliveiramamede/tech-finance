from datetime import datetime
from decimal import Decimal
from enum import Enum
from typing import Optional
from uuid import UUID

from pydantic import BaseModel, Field


class TransactionType(str, Enum):
    INCOME = "INCOME"
    EXPENSE = "EXPENSE"


class TimePeriod(str, Enum):
    DAY = "DAY"
    MONTH = "MONTH"
    YEAR = "YEAR"


class CategoryDTO(BaseModel):
    id: Optional[UUID] = None
    name: Optional[str] = None
    description: Optional[str] = None


class AccountDTO(BaseModel):
    id: UUID
    balance: Decimal


class TransactionDTO(BaseModel):
    id: UUID
    amount: Decimal
    type: TransactionType
    description: Optional[str] = None
    occurredAt: datetime
    account: AccountDTO
    category: Optional[CategoryDTO] = None


class FinancialAnalysisRequest(BaseModel):
    transactions: list[TransactionDTO]
    period: TimePeriod = TimePeriod.MONTH
    forecastPeriods: int = Field(default=3, ge=0, le=36)


class CategorySummary(BaseModel):
    category: str
    total: Decimal


class PeriodMetric(BaseModel):
    period: str
    income: Decimal
    expense: Decimal
    profit: Decimal
    endingBalance: Decimal


class ForecastMetric(BaseModel):
    period: str
    expectedIncome: Decimal
    expectedExpense: Decimal
    expectedProfit: Decimal
    expectedBalance: Decimal
    probabilityPositiveBalance: Decimal


class FinancialStatistics(BaseModel):
    periodsAnalyzed: int
    averageIncomePerPeriod: Decimal
    averageExpensePerPeriod: Decimal
    averageProfitPerPeriod: Decimal
    minProfit: Decimal
    maxProfit: Decimal
    profitVolatility: Decimal
    positiveProfitProbability: Decimal
    positiveBalanceProbability: Decimal


class DecisionCase(BaseModel):
    scenario: str
    expectedProfit: Decimal
    expectedBalance: Decimal
    probabilityPositiveBalance: Decimal
    suggestedAction: str


class FinancialAnalysisResponse(BaseModel):
    totalIncome: Decimal
    totalExpense: Decimal
    balance: Decimal
    expenseRate: Decimal
    categoryExpenses: list[CategorySummary]
    period: TimePeriod = TimePeriod.MONTH
    periodMetrics: list[PeriodMetric] = Field(default_factory=list)
    forecast: list[ForecastMetric] = Field(default_factory=list)
    statistics: Optional[FinancialStatistics] = None
    decisionCases: list[DecisionCase] = Field(default_factory=list)


class AccountFinancialAnalysisQuery(BaseModel):
    accountId: UUID
    start: Optional[datetime] = None
    end: Optional[datetime] = None
    period: TimePeriod = TimePeriod.MONTH
    forecastPeriods: int = Field(default=3, ge=0, le=36)
