import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AccountApiService } from '../../../core/services/account-api.service';
import { AuthStateService } from '../../../core/services/auth-state.service';
import { resolveErrorMessage } from '../../../core/utils/formatters';
import { AuthLayoutComponent } from '../../../shared/components/auth-layout/auth-layout.component';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, FormsModule, AuthLayoutComponent],
  template: `
    <app-auth-layout
      title="Primeira conta"
      [subtitle]="welcomeSubtitle"
    >
      <form class="space-y-4" (ngSubmit)="submit()">
        <input
          type="text"
          placeholder="Nome da conta"
          [(ngModel)]="accountName"
          name="accountName"
          class="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="text"
          inputmode="decimal"
          placeholder="Saldo inicial (ex: 1500.00)"
          [(ngModel)]="balance"
          name="balance"
          class="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>

        <button
          type="submit"
          [disabled]="loading"
          class="w-full cursor-pointer rounded-xl bg-blue-600 py-3 font-bold text-white transition-colors hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {{ loading ? 'Criando...' : 'Criar conta e continuar' }}
        </button>
      </form>
    </app-auth-layout>
  `,
})
export class CreateAccountComponent {
  private readonly accounts = inject(AccountApiService);
  private readonly auth = inject(AuthStateService);
  private readonly router = inject(Router);

  accountName = '';
  balance = '';
  error: string | null = null;
  loading = false;

  get welcomeSubtitle(): string {
    const name = this.auth.name?.trim() || 'Olá';
    return `${name}! Crie sua primeira conta para começar a usar o painel.`;
  }

  submit(): void {
    this.error = null;

    const trimmedName = this.accountName.trim();

    if (!trimmedName) {
      this.error = 'Informe o nome da conta.';
      return;
    }

    const normalizedBalance = this.balance.trim().replace(',', '.');

    if (!normalizedBalance) {
      this.error = 'Informe o saldo inicial.';
      return;
    }

    const parsedBalance = Number(normalizedBalance);

    if (Number.isNaN(parsedBalance)) {
      this.error = 'Saldo inválido.';
      return;
    }

    this.loading = true;

    this.accounts
      .createAccount(trimmedName, parsedBalance)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => this.router.navigateByUrl('/'),
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao criar conta.')),
      });
  }
}
