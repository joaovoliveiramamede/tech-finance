from datetime import datetime
from decimal import Decimal
from enum import Enum
from typing import Optional
from uuid import UUID

from pydantic import BaseModel


class TransactionType(str, Enum):
    INCOME = "INCOME"
    EXPENSE = "EXPENSE"


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


class CategorySummary(BaseModel):
    category: str
    total: Decimal


class FinancialAnalysisResponse(BaseModel):
    totalIncome: Decimal
    totalExpense: Decimal
    balance: Decimal
    expenseRate: Decimal
    categoryExpenses: list[CategorySummary]