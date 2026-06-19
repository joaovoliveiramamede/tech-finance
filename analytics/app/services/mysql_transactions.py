import os
from datetime import datetime
from decimal import Decimal
from typing import Optional
from uuid import UUID

from app.schemas import AccountDTO, CategoryDTO, TransactionDTO, TransactionType


class MySQLTransactionRepository:
    def __init__(self):
        self.host = os.getenv("MYSQL_HOST", "localhost")
        self.port = int(os.getenv("MYSQL_PORT", "3306"))
        self.database = os.getenv("MYSQL_DATABASE", "techfinance")
        self.user = os.getenv("MYSQL_USER", "root")
        self.password = os.getenv("MYSQL_PASSWORD", "root")

    def find_by_account(
        self,
        account_id: UUID,
        start: Optional[datetime] = None,
        end: Optional[datetime] = None
    ) -> list[TransactionDTO]:
        try:
            import mysql.connector
        except ModuleNotFoundError as exc:
            raise RuntimeError(
                "Dependencia mysql-connector-python nao instalada."
            ) from exc

        connection = mysql.connector.connect(
            host=self.host,
            port=self.port,
            database=self.database,
            user=self.user,
            password=self.password
        )

        try:
            cursor = connection.cursor(dictionary=True)
            try:
                cursor.execute(*self._build_query(account_id, start, end, True))
                rows = cursor.fetchall()
            except mysql.connector.Error:
                cursor.execute(*self._build_query(account_id, start, end, False))
                rows = cursor.fetchall()
        finally:
            connection.close()

        return [self._to_transaction(row) for row in rows]

    def _build_query(
        self,
        account_id: UUID,
        start: Optional[datetime],
        end: Optional[datetime],
        use_uuid_to_bin: bool
    ) -> tuple[str, list]:
        account_filter = (
            "(t.account_id = UUID_TO_BIN(%s) OR CAST(t.account_id AS CHAR) = %s)"
            if use_uuid_to_bin
            else "CAST(t.account_id AS CHAR) = %s"
        )
        sql = """
            SELECT
                t.id,
                t.valor AS amount,
                t.tipo_transacao AS transaction_type,
                t.descricao AS description,
                t.data_transacao AS occurred_at,
                a.id AS account_id,
                a.saldo AS account_balance,
                c.id AS category_id,
                c.nome AS category_name,
                c.descricao AS category_description
            FROM transacoes t
            INNER JOIN contas a ON a.id = t.account_id
            LEFT JOIN categorias c ON c.id = t.category_id
            WHERE {account_filter}
        """.format(account_filter=account_filter)
        params: list = [str(account_id), str(account_id)] if use_uuid_to_bin else [str(account_id)]

        if start:
            sql += " AND t.data_transacao >= %s"
            params.append(start)

        if end:
            sql += " AND t.data_transacao <= %s"
            params.append(end)

        sql += " ORDER BY t.data_transacao ASC"
        return sql, params

    def _to_transaction(self, row: dict) -> TransactionDTO:
        category = None
        if row.get("category_id"):
            category = CategoryDTO(
                id=self._to_uuid(row["category_id"]),
                name=row.get("category_name"),
                description=row.get("category_description")
            )

        return TransactionDTO(
            id=self._to_uuid(row["id"]),
            amount=Decimal(str(row["amount"])),
            type=self._to_transaction_type(row["transaction_type"]),
            description=row.get("description"),
            occurredAt=row["occurred_at"],
            account=AccountDTO(
                id=self._to_uuid(row["account_id"]),
                balance=Decimal(str(row["account_balance"]))
            ),
            category=category
        )

    def _to_uuid(self, value) -> UUID:
        if isinstance(value, UUID):
            return value

        if isinstance(value, bytes):
            return UUID(bytes=value)

        return UUID(str(value))

    def _to_transaction_type(self, value) -> TransactionType:
        normalized = str(value).upper()
        if normalized == "INCOME":
            return TransactionType.INCOME
        if normalized == "EXPENSE":
            return TransactionType.EXPENSE

        raise ValueError(f"Tipo de transacao invalido: {value}")
