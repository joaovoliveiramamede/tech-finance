import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';
import { AccountApiService } from '../../core/services/account-api.service';
import { AccountResponse } from '../../core/models/account.model';
import {
  formatCurrency,
  formatDateTime,
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

@Component({
  selector: 'app-accounts',
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
      <app-page-header title="Contas" subtitle="Carteiras, bancos e saldos">
        <button
          actions
          type="button"
          (click)="openModal()"
          class="cursor-pointer rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
        >
          Nova conta
        </button>
      </app-page-header>

      <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>

      <p *ngIf="loading" class="text-sm text-[#8a94a6]">Carregando...</p>
      <app-data-table
        *ngIf="!loading"
        [columns]="columns"
        [data]="accounts"
      />

      <app-modal
        [open]="modalOpen"
        title="Nova conta"
        (closed)="modalOpen = false"
      >
        <form id="create-account-form" class="space-y-4" (ngSubmit)="create()">
          <app-form-field label="Nome">
            <input
              type="text"
              [(ngModel)]="name"
              name="name"
              placeholder="Nome da conta"
              [class]="fieldClass"
            />
          </app-form-field>

          <app-form-field label="Saldo">
            <input
              type="text"
              inputmode="decimal"
              [(ngModel)]="balance"
              name="balance"
              placeholder="Saldo inicial"
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
            formId="create-account-form"
            [disabled]="creating"
          >
            {{ creating ? 'Salvando...' : 'Salvar' }}
          </app-primary-button>
        </div>
      </app-modal>
    </div>
  `,
})
export class AccountsComponent implements OnInit {
  private readonly accountsApi = inject(AccountApiService);

  accounts: AccountResponse[] = [];
  error: string | null = null;
  loading = true;
  modalOpen = false;
  creating = false;
  name = '';
  balance = '';
  readonly fieldClass = FIELD_CLASS;

  columns: TableColumn<AccountResponse>[] = [
    { key: 'nome', header: 'Nome', value: (a) => a.nome },
    { key: 'tipo', header: 'Tipo', value: (a) => a.tipo ?? '-' },
    {
      key: 'saldo',
      header: 'Saldo',
      value: (a) => formatCurrency(a.saldo),
    },
    {
      key: 'criada',
      header: 'Criada em',
      value: (a) => formatDateTime(a.data_criacao),
    },
    {
      key: 'atualizada',
      header: 'Atualizada em',
      value: (a) => formatDateTime(a.data_atualizacao),
    },
  ];

  ngOnInit(): void {
    this.loadAccounts();
  }

  openModal(): void {
    this.name = '';
    this.balance = '';
    this.modalOpen = true;
  }

  create(): void {
    const trimmedName = this.name.trim();

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

    this.creating = true;

    this.accountsApi
      .createAccount(trimmedName, parsedBalance)
      .pipe(finalize(() => (this.creating = false)))
      .subscribe({
        next: () => {
          this.modalOpen = false;
          this.error = null;
          this.loadAccounts();
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar conta.')),
      });
  }

  private loadAccounts(): void {
    this.loading = true;

    this.accountsApi
      .findAll()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (data) => {
          this.accounts = data;
          this.error = null;
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar conta.')),
      });
  }
}
