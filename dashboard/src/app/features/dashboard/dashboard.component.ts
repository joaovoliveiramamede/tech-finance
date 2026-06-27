import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { AccountApiService } from '../../core/services/account-api.service';
import { CategoryApiService } from '../../core/services/category-api.service';
import { TransactionApiService } from '../../core/services/transaction-api.service';
import { AccountResponse } from '../../core/models/account.model';
import { CategoryResponse } from '../../core/models/category.model';
import { TransactionResponse } from '../../core/models/transaction.model';
import {
  formatCurrency,
  formatDateTime,
} from '../../core/utils/formatters';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="space-y-6">
      <div class="space-y-1">
        <h1 class="text-3xl font-bold text-[#172033]">Home</h1>
        <p class="text-sm text-[#697386]">Resumo geral das suas finanças</p>
      </div>

      <div class="grid gap-4 md:grid-cols-3">
        <div class="min-h-28 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
          <p class="text-sm text-[#697386]">Saldo total</p>
          <p class="mt-2 text-2xl font-bold text-[#172033]">
            {{ formatCurrency(totalBalance) }}
          </p>
        </div>
        <div class="min-h-28 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
          <p class="text-sm text-[#697386]">Receitas do mês</p>
          <p class="mt-2 text-2xl font-bold text-[#0f8f5f]">
            {{ formatCurrency(income) }}
          </p>
        </div>
        <div class="min-h-28 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
          <p class="text-sm text-[#697386]">Despesas do mês</p>
          <p class="mt-2 text-2xl font-bold text-[#c2413d]">
            {{ formatCurrency(expense) }}
          </p>
        </div>
      </div>

      <div class="grid gap-4 lg:grid-cols-2">
        <div class="min-h-56 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
          <h2 class="text-base font-bold text-[#172033]">Últimas transações</h2>
          <div class="mt-4">
            <p *ngIf="error" class="text-sm text-[#8a94a6]">{{ error }}</p>
            <p
              *ngIf="!error && lastTransactions.length === 0"
              class="text-sm text-[#8a94a6]"
            >
              Nenhuma transação registrada
            </p>
            <ul *ngIf="!error && lastTransactions.length > 0" class="space-y-3">
              <li
                *ngFor="let transaction of lastTransactions"
                class="text-sm leading-relaxed text-[#697386]"
              >
                {{ formatDateTime(transaction.ocorreu_em) }} •
                {{ transaction.descricao }} •
                {{ formatCurrency(transaction.valor) }}
              </li>
            </ul>
          </div>
        </div>

        <div class="min-h-56 rounded-[10px] border border-[#e4e8ef] bg-white p-5">
          <h2 class="text-base font-bold text-[#172033]">
            Categorias em destaque
          </h2>
          <div class="mt-4">
            <p
              *ngIf="topCategories.length === 0"
              class="text-sm text-[#8a94a6]"
            >
              Nenhuma categoria registrada
            </p>
            <ul *ngIf="topCategories.length > 0" class="space-y-2">
              <li
                *ngFor="let category of topCategories"
                class="text-sm text-[#697386]"
              >
                {{ category.nome }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class DashboardComponent implements OnInit {
  private readonly accountsApi = inject(AccountApiService);
  private readonly transactionsApi = inject(TransactionApiService);
  private readonly categoriesApi = inject(CategoryApiService);

  accounts: AccountResponse[] = [];
  transactions: TransactionResponse[] = [];
  categories: CategoryResponse[] = [];
  error: string | null = null;

  readonly formatCurrency = formatCurrency;
  readonly formatDateTime = formatDateTime;

  ngOnInit(): void {
    forkJoin({
      accounts: this.accountsApi.findAll(),
      transactions: this.transactionsApi.findAll(),
      categories: this.categoriesApi.findAll(),
    }).subscribe({
      next: ({ accounts, transactions, categories }) => {
        this.accounts = accounts;
        this.transactions = transactions;
        this.categories = categories;
        this.error = null;
      },
      error: () => {
        this.accounts = [];
        this.transactions = [];
        this.categories = [];
        this.error = 'Erro ao carregar dados.';
      },
    });
  }

  get totalBalance(): number {
    return this.accounts.reduce((sum, account) => sum + (account.saldo ?? 0), 0);
  }

  get income(): number {
    return this.transactions
      .filter((t) => t.tipo === 'income')
      .reduce((sum, t) => sum + (t.valor ?? 0), 0);
  }

  get expense(): number {
    return this.transactions
      .filter((t) => t.tipo === 'expense')
      .reduce((sum, t) => sum + (t.valor ?? 0), 0);
  }

  get lastTransactions(): TransactionResponse[] {
    return [...this.transactions]
      .sort((left, right) => {
        const leftDate = left.ocorreu_em
          ? new Date(left.ocorreu_em).getTime()
          : 0;
        const rightDate = right.ocorreu_em
          ? new Date(right.ocorreu_em).getTime()
          : 0;
        return rightDate - leftDate;
      })
      .slice(0, 5);
  }

  get topCategories(): CategoryResponse[] {
    return this.categories.slice(0, 5);
  }
}
