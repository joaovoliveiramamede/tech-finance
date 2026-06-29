import base64
from decimal import Decimal
from io import BytesIO
from typing import Optional

import matplotlib

matplotlib.use("Agg")

import matplotlib.pyplot as plt
import pandas as pd

from app.schemas import FinancialAnalysisRequest, PeriodMetric, ProfitChartPoint, ProfitChartResponse, TimePeriod
from app.services.finance_math import FinancialMathService


class ProfitChartService:
    def __init__(self, finance_service: Optional[FinancialMathService] = None) -> None:
        self._finance_service = finance_service or FinancialMathService()

    def build_chart(self, request: FinancialAnalysisRequest) -> ProfitChartResponse:
        transactions = sorted(request.transactions, key=lambda item: item.occurredAt)
        period_metrics = self._finance_service.build_period_metrics(
            transactions,
            request.period,
        )

        df = self._build_dataframe(period_metrics)
        series = self._to_series(df)
        image_base64 = self._render_chart(df, request.period)

        return ProfitChartResponse(
            period=request.period,
            series=series,
            imageBase64=image_base64,
        )

    def build_chart_png(self, request: FinancialAnalysisRequest) -> bytes:
        transactions = sorted(request.transactions, key=lambda item: item.occurredAt)
        period_metrics = self._finance_service.build_period_metrics(
            transactions,
            request.period,
        )
        df = self._build_dataframe(period_metrics)
        return base64.b64decode(self._render_chart(df, request.period))

    def _build_dataframe(self, period_metrics: list[PeriodMetric]) -> pd.DataFrame:
        if not period_metrics:
            return pd.DataFrame(columns=["t", "R", "C", "L"])

        df = pd.DataFrame(
            {
                "t": [metric.period for metric in period_metrics],
                "R": [float(metric.income) for metric in period_metrics],
                "C": [float(metric.expense) for metric in period_metrics],
            }
        )

        df["L"] = df["R"] - df["C"]
        return df

    def _to_series(self, df: pd.DataFrame) -> list[ProfitChartPoint]:
        if df.empty:
            return []

        return [
            ProfitChartPoint(
                period=str(row.t),
                revenue=Decimal(str(row.R)).quantize(Decimal("0.01")),
                cost=Decimal(str(row.C)).quantize(Decimal("0.01")),
                profit=Decimal(str(row.L)).quantize(Decimal("0.01")),
            )
            for row in df.itertuples(index=False)
        ]

    def _render_chart(self, df: pd.DataFrame, period: TimePeriod) -> str:
        figure, axis = plt.subplots(figsize=(10, 5), dpi=120)

        if df.empty:
            axis.text(
                0.5,
                0.5,
                "Sem dados para o período selecionado",
                ha="center",
                va="center",
                fontsize=12,
                color="#697386",
            )
            axis.set_axis_off()
        else:
            x = df["t"]
            axis.plot(x, df["R"], marker="o", linewidth=2, color="#0f8f5f", label="R(t) — Receita")
            axis.plot(x, df["C"], marker="o", linewidth=2, color="#c2413d", label="C(t) — Custo")
            axis.plot(x, df["L"], marker="o", linewidth=2.5, color="#2f6fed", label="L(t) = R(t) - C(t)")
            axis.axhline(0, color="#e4e8ef", linewidth=1)
            axis.fill_between(
                range(len(x)),
                df["L"],
                0,
                where=df["L"] >= 0,
                alpha=0.12,
                color="#0f8f5f",
                interpolate=True,
            )
            axis.fill_between(
                range(len(x)),
                df["L"],
                0,
                where=df["L"] < 0,
                alpha=0.12,
                color="#c2413d",
                interpolate=True,
            )
            axis.set_xticks(range(len(x)))
            axis.set_xticklabels(x, rotation=30, ha="right")
            axis.set_ylabel("Valor (R$)")
            axis.legend(loc="best", frameon=False)
            axis.grid(axis="y", linestyle="--", alpha=0.35)
            axis.spines[["top", "right"]].set_visible(False)

        period_label = {
            TimePeriod.DAY: "por dia",
            TimePeriod.MONTH: "por mês",
            TimePeriod.YEAR: "por ano",
        }[period]
        axis.set_title(f"Lucro L(t) = R(t) - C(t) ({period_label})", fontsize=13, fontweight="bold")

        figure.tight_layout()
        buffer = BytesIO()
        figure.savefig(buffer, format="png", bbox_inches="tight")
        plt.close(figure)
        buffer.seek(0)
        return base64.b64encode(buffer.read()).decode("ascii")
