import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';
import { CategoryApiService } from '../../core/services/category-api.service';
import { CategoryResponse } from '../../core/models/category.model';
import {
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
  selector: 'app-categories',
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
        title="Categorias"
        subtitle="Classificação das suas movimentações"
      >
        <button
          actions
          type="button"
          (click)="openModal()"
          class="cursor-pointer rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf]"
        >
          Nova categoria
        </button>
      </app-page-header>

      <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>

      <p *ngIf="loading" class="text-sm text-[#8a94a6]">Carregando...</p>
      <app-data-table
        *ngIf="!loading"
        [columns]="columns"
        [data]="categories"
      />

      <app-modal
        [open]="modalOpen"
        title="Nova categoria"
        (closed)="modalOpen = false"
      >
        <form
          id="create-category-form"
          class="space-y-4"
          (ngSubmit)="create()"
        >
          <app-form-field label="Nome">
            <input
              type="text"
              [(ngModel)]="name"
              name="name"
              placeholder="Nome"
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
            formId="create-category-form"
            [disabled]="creating"
          >
            {{ creating ? 'Salvando...' : 'Salvar' }}
          </app-primary-button>
        </div>
      </app-modal>
    </div>
  `,
})
export class CategoriesComponent implements OnInit {
  private readonly categoriesApi = inject(CategoryApiService);

  categories: CategoryResponse[] = [];
  error: string | null = null;
  loading = true;
  modalOpen = false;
  creating = false;
  name = '';
  description = '';
  readonly fieldClass = FIELD_CLASS;

  columns: TableColumn<CategoryResponse>[] = [
    { key: 'nome', header: 'Nome', value: (c) => c.nome },
    {
      key: 'descricao',
      header: 'Descrição',
      value: (c) => c.descricao || '-',
    },
    {
      key: 'criada',
      header: 'Criada em',
      value: (c) => formatDateTime(c.data_criacao),
    },
  ];

  ngOnInit(): void {
    this.loadCategories();
  }

  openModal(): void {
    this.name = '';
    this.description = '';
    this.modalOpen = true;
  }

  create(): void {
    const trimmedName = this.name.trim();

    if (!trimmedName) {
      this.error = 'Informe o nome da categoria.';
      return;
    }

    this.creating = true;

    this.categoriesApi
      .create({ nome: trimmedName, descricao: this.description.trim() })
      .pipe(finalize(() => (this.creating = false)))
      .subscribe({
        next: () => {
          this.modalOpen = false;
          this.error = null;
          this.loadCategories();
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar categoria.')),
      });
  }

  private loadCategories(): void {
    this.loading = true;

    this.categoriesApi
      .findAll()
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (data) => {
          this.categories = data;
          this.error = null;
        },
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao processar categoria.')),
      });
  }
}
