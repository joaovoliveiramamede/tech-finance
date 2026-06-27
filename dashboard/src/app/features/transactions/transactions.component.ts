import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin, finalize } from 'rxjs';
import { AccountApiService } from '../../core/services/account-api.service';
import { CategoryApiService } from '../../core/services/category-api.service';
import { TransactionApiService } from '../../core/services/transaction-api.service';
import { AccountResponse } from '../../core/models/account.model';
import { CategoryResponse } from '../../core/models/category.model';
import {
  TransactionResponse,
  TransactionType,
} from '../../core/models/transaction.model';
import {
  formatCurrency,
  formatDateTime,
  formatTransactionApiDate,
  formatTransactionType,
  resolveErrorMessage,
} from '../../core/utils/formatters';
import {
  DataTableComponent,
  TableColumn,
} from '../../shared/components/data-table/data-table.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import {
  FIELD_CLASS,
  FormFieldComponent,
  ModalComponent,
  PrimaryButtonComponent,
  SecondaryButtonComponent,
} from '../../shared/components/modal/modal.component';

const TRANSACTION_TYPES: { value: TransactionType; label: string }[] = [
  { value: 'income', label: 'Receita' },
  { value: 'expense', label: 'Despesa' },
];

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    PageHeaderComponent,
    DataTableComponent,
    ModalComponent,
    FormFieldComponent,
    PrimaryButtonComponent,
    SecondaryButtonComponent,
  ],
  template: `
    <div class="space-y-5">
      <app-page-header
        title="Transações"
        subtitle="Lançamentos de receitas e despesas"
      >
        <button
          actions
          type="button"
          (click)="openModal()"
          class="cursor-pointer rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
        >
          Nova transação
        </button>
      </app-page-header>

      <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>

      <p *ngIf="loading" class="text-sm text-[#8a94a6]">Carregando...</p>
      <app-data-table
        *ngIf="!loading"
        [columns]="columns"
        [data]="transactions"
      />

      <app-modal
        [open]="modalOpen"
        title="Nova transação"
        (closed)="modalOpen = false"
      >
        <form
          id="create-transaction-form"
          class="space-y-4"
          (ngSubmit)="create()"
        >
          <app-form-field label="Conta">
            <select
              [(ngModel)]="accountId"
              name="accountId"
              [class]="fieldClass"
            >
              <option *ngFor="let account of accounts" [value]="account.id">
                {{ account.nome }}
              </option>
            </select>
          </app-form-field>

          <app-form-field label="Categoria">
            <select
              [(ngModel)]="categoryId"
              name="categoryId"
              [class]="fieldClass"
            >
              <option *ngFor="let category of categories" [value]="category.id">
                {{ category.nome }}
              </option>
            </select>
          </app-form-field>

          <app-form-field label="Tipo">
            <select [(ngModel)]="type" name="type" [class]="fieldClass">
              <option
                *ngFor="let item of transactionTypes"
                [value]="item.value"
              >
                {{ item.label }}
              </option>
            </select>
          </app-form-field>

          <app-form-field label="Valor">
            <input
              type="text"
              inputmode="decimal"
              [(ngModel)]="amount"
              name="amount"
              placeholder="Valor"
              [class]="fieldClass"
            />
          </app-form-field>

          <app-form-field label="Descrição">
            <input
              type="text"
              [(ngModel)]="description"
              name="description"
              placeholder="Descrição"
              [class]="fieldClass"
            />
          </app-form-field>
        </form>

        <div modal-footer>
          <app-secondary-button (clicked)="modalOpen = false">
            Cancelar
          </app-secondary-button>
          <app-primary-button
            type="submit"
            formId="create-transaction-form"
            [disabled]="creating"
          >
            {{ creating ? 'Salvando...' : 'Salvar' }}
          </app-primary-button>
        </div>
      </app-modal>
    </div>
  `,
})
export class TransactionsComponent implements OnInit {
  private readonly transactionsApi = inject(TransactionApiService);
  private readonly accountsApi = inject(AccountApiService);
  private readonly categoriesApi = inject(CategoryApiService);

  accounts: AccountResponse[] = [];
  categories: CategoryResponse[] = [];
  transactions: TransactionResponse[] = [];
  accountNames = new Map<string, string>();
  categoryNames = new Map<string, string>();

  error: string | null = null;
  loading = true;
  modalOpen = false;
  creating = false;

  accountId = '';
  categoryId = '';
  type: TransactionType = 'income';
  amount = '';
  description = '';

  readonly fieldClass = FIELD_CLASS;
  readonly transactionTypes = TRANSACTION_TYPES;

  columns: TableColumn<TransactionResponse>[] = [
    {
      key: 'data',
      header: 'Data',
      value: (t) => formatDateTime(t.ocorreu_em),
    },
    {
      key: 'descricao',
      header: 'Descrição',
      value: (t) => t.descricao || '-',
    },
    {
      key: 'conta',
      header: 'Conta',
      value: (t) => this.resolveAccountName(t),
    },
    {
      key: 'categoria',
      header: 'Categoria',
      value: (t) => this.resolveCategoryName(t),
    },
    {
      key: 'tipo',
      header: 'Tipo',
      value: (t) => formatTransactionType(t.tipo),
    },
    {
      key: 'valor',
      header: 'Valor',
      value: (t) => formatCurrency(t.valor),
    },
  ];

  ngOnInit(): void {
    this.loadData();
  }

  openModal(): void {
    if (this.accounts.length === 0) {
      this.error = 'Cadastre uma conta antes de lançar transações.';
      return;
    }

    if (this.categories.length === 0) {
      this.error = 'Cadastre uma categoria antes de lançar transações.';
      return;
    }

    this.accountId = this.accounts[0]?.id ?? '';
    this.categoryId = this.categories[0]?.id ?? '';
    this.type = 'income';
    this.amount = '';
    this.description = '';
    this.modalOpen = true;
  }

  create(): void {
    if (!this.accountId) {
      this.error = 'Selecione uma conta.';
      return;
    }

    if (!this.categoryId) {
      this.error = 'Selecione uma categoria.';
      return;
    }

    const normalizedAmount = this.amount.trim().replace(',', '.');

    if (!normalizedAmount) {
      this.error = 'Informe o valor.';
      return;
    }

    const parsedAmount = Number(normalizedAmount);

    if (Number.isNaN(parsedAmount)) {
      this.error = 'Valor inválido.';
      return;
    }

    this.creating = true;

    this.transactionsApi
      .create({
        id_conta: this.accountId,
        id_categoria: this.categoryId,
        valor: parsedAmount,
        tipo: this.type,
        descricao: this.description.trim(),
        ocorreu_em: formatTransactionApiDate(),
      })
      .pipe(finalize(() => (this.creating = false)))
      .subscribe({
        next: () => {
          this.modalOpen = false;
          this.error = null;
          this.loadData();
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar transação.')),
      });
  }

  private loadData(): void {
    this.loading = true;

    forkJoin({
      accounts: this.accountsApi.findAll(),
      categories: this.categoriesApi.findAll(),
      transactions: this.transactionsApi.findAll(),
    })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: ({ accounts, categories, transactions }) => {
          this.accounts = accounts;
          this.categories = categories;
          this.transactions = transactions;
          this.accountNames = new Map(
            accounts.map((a) => [a.id, a.nome]),
          );
          this.categoryNames = new Map(
            categories.map((c) => [c.id, c.nome]),
          );
          this.error = null;
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar transação.')),
      });
  }

  private resolveAccountName(transaction: TransactionResponse): string {
    if (transaction.nome_conta?.trim()) {
      return transaction.nome_conta;
    }

    return this.accountNames.get(transaction.id_conta) ?? '-';
  }

  private resolveCategoryName(transaction: TransactionResponse): string {
    if (transaction.nome_categoria?.trim()) {
      return transaction.nome_categoria;
    }

    return this.categoryNames.get(transaction.id_categoria) ?? '-';
  }
}
