import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

export interface TableColumn<T> {
  key: string;
  header: string;
  value: (item: T) => string;
}

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="overflow-hidden rounded-[10px] border border-[#e4e8ef] bg-white">
      <div class="overflow-x-auto">
        <table class="min-w-full text-left text-sm">
          <thead class="border-b border-[#e4e8ef] bg-[#fafbfc]">
            <tr>
              <th
                *ngFor="let column of columns"
                class="px-4 py-3 font-semibold text-[#172033]"
              >
                {{ column.header }}
              </th>
            </tr>
          </thead>
          <tbody>
            <tr *ngIf="data.length === 0">
              <td
                [attr.colspan]="columns.length"
                class="px-4 py-8 text-center text-[#8a94a6]"
              >
                {{ emptyMessage }}
              </td>
            </tr>
            <tr
              *ngFor="let row of data"
              class="border-b border-[#e4e8ef] last:border-b-0"
            >
              <td
                *ngFor="let column of columns"
                class="px-4 py-3 text-[#697386]"
              >
                {{ getValue(column, row) }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `,
})
export class DataTableComponent<T extends { id: string }> {
  @Input() columns: TableColumn<T>[] = [];
  @Input() data: T[] = [];
  @Input() emptyMessage = 'Nenhum registro encontrado.';

  getValue(column: TableColumn<T>, row: T): string {
    return column.value(row);
  }
}
