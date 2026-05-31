from fastapi import FastAPI

from app.schemas import FinancialAnalysisRequest, FinancialAnalysisResponse
from app.services.finance_math import FinancialMathService

app = FastAPI(
    title="TechFinance Analytics",
    version="1.0.0"
)

service = FinancialMathService()


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "techfinance-analytics"
    }


@app.post("/analytics/financial", response_model=FinancialAnalysisResponse)
def analyze_financial_data(request: FinancialAnalysisRequest):
    return service.analyze(request)