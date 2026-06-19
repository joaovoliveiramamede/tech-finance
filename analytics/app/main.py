from datetime import datetime
from typing import Optional
from uuid import UUID

from fastapi import FastAPI, HTTPException, Query

from app.schemas import FinancialAnalysisRequest, FinancialAnalysisResponse, TimePeriod
from app.services.finance_math import FinancialMathService
from app.services.mysql_transactions import MySQLTransactionRepository

app = FastAPI(
    title="TechFinance Analytics",
    version="1.0.0"
)

service = FinancialMathService()
transaction_repository = MySQLTransactionRepository()


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "techfinance-analytics"
    }


@app.post("/analytics/financial", response_model=FinancialAnalysisResponse)
def analyze_financial_data(request: FinancialAnalysisRequest):
    return service.analyze(request)


@app.get("/analytics/accounts/{account_id}/financial", response_model=FinancialAnalysisResponse)
def analyze_account_financial_data(
    account_id: UUID,
    start: Optional[datetime] = Query(default=None),
    end: Optional[datetime] = Query(default=None),
    period: TimePeriod = Query(default=TimePeriod.MONTH),
    forecastPeriods: int = Query(default=3, ge=0, le=36)
):
    try:
        transactions = transaction_repository.find_by_account(account_id, start, end)
    except RuntimeError as exc:
        raise HTTPException(status_code=500, detail=str(exc)) from exc
    except Exception as exc:
        raise HTTPException(
            status_code=502,
            detail=f"Erro ao consultar transacoes no MySQL: {exc}"
        ) from exc

    return service.analyze(
        FinancialAnalysisRequest(
            transactions=transactions,
            period=period,
            forecastPeriods=forecastPeriods
        )
    )
