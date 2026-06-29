from datetime import datetime
from typing import Optional
from uuid import UUID

from fastapi import FastAPI, HTTPException, Query
from fastapi.responses import Response

from app.schemas import (
    FinancialAnalysisRequest,
    FinancialAnalysisResponse,
    ProfitChartResponse,
    TimePeriod,
)
from app.services.chart_service import ProfitChartService
from app.services.finance_math import FinancialMathService
from app.services.mysql_transactions import MySQLTransactionRepository

app = FastAPI(
    title="TechFinance Analytics",
    version="1.0.0"
)

service = FinancialMathService()
chart_service = ProfitChartService(service)
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
    request = _load_account_request(account_id, start, end, period, forecastPeriods)
    return service.analyze(request)


@app.post("/analytics/financial/profit-chart", response_model=ProfitChartResponse)
def profit_chart_from_transactions(request: FinancialAnalysisRequest):
    return chart_service.build_chart(request)


@app.get("/analytics/accounts/{account_id}/profit-chart", response_model=ProfitChartResponse)
def profit_chart_for_account(
    account_id: UUID,
    start: Optional[datetime] = Query(default=None),
    end: Optional[datetime] = Query(default=None),
    period: TimePeriod = Query(default=TimePeriod.MONTH),
):
    request = _load_account_request(account_id, start, end, period, forecastPeriods=0)
    return chart_service.build_chart(request)


@app.get("/analytics/accounts/{account_id}/profit-chart.png")
def profit_chart_png_for_account(
    account_id: UUID,
    start: Optional[datetime] = Query(default=None),
    end: Optional[datetime] = Query(default=None),
    period: TimePeriod = Query(default=TimePeriod.MONTH),
):
    request = _load_account_request(account_id, start, end, period, forecastPeriods=0)
    png_bytes = chart_service.build_chart_png(request)
    return Response(content=png_bytes, media_type="image/png")


def _load_account_request(
    account_id: UUID,
    start: Optional[datetime],
    end: Optional[datetime],
    period: TimePeriod,
    forecastPeriods: int,
) -> FinancialAnalysisRequest:
    try:
        transactions = transaction_repository.find_by_account(account_id, start, end)
    except RuntimeError as exc:
        raise HTTPException(status_code=500, detail=str(exc)) from exc
    except Exception as exc:
        raise HTTPException(
            status_code=502,
            detail=f"Erro ao consultar transacoes no MySQL: {exc}"
        ) from exc

    return FinancialAnalysisRequest(
        transactions=transactions,
        period=period,
        forecastPeriods=forecastPeriods,
    )
