import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div
      *ngIf="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4"
    >
      <div
        class="w-full max-w-md rounded-[10px] bg-white shadow-xl"
        role="dialog"
        aria-modal="true"
        aria-labelledby="modal-title"
      >
        <div class="border-b border-[#e4e8ef] px-5 py-4">
          <h2 id="modal-title" class="text-lg font-bold text-[#172033]">
            {{ title }}
          </h2>
        </div>

        <div class="px-5 py-4">
          <ng-content></ng-content>
        </div>

        <div
          class="flex justify-end gap-2 border-t border-[#e4e8ef] px-5 py-4"
        >
          <ng-content select="[modal-footer]"></ng-content>
        </div>
      </div>
    </div>
  `,
})
export class ModalComponent {
  @Input() open = false;
  @Input() title!: string;
  @Output() closed = new EventEmitter<void>();

  @HostListener('document:keydown.escape')
  onEscape(): void {
    if (this.open) {
      this.closed.emit();
    }
  }
}

@Component({
  selector: 'app-form-field',
  standalone: true,
  template: `
    <label class="block space-y-1.5">
      <span class="text-sm font-medium text-[#172033]">{{ label }}</span>
      <ng-content></ng-content>
    </label>
  `,
})
export class FormFieldComponent {
  @Input() label!: string;
}

export const FIELD_CLASS =
  'w-full rounded-lg border border-[#e4e8ef] px-3 py-2.5 text-sm text-[#172033] outline-none focus:border-[#2f6fed]';

@Component({
  selector: 'app-primary-button',
  standalone: true,
  template: `
    <button
      [type]="type"
      [attr.form]="formId || null"
      [disabled]="disabled"
      (click)="clicked.emit()"
      class="cursor-pointer rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf] disabled:cursor-not-allowed disabled:opacity-70"
    >
      <ng-content></ng-content>
    </button>
  `,
})
export class PrimaryButtonComponent {
  @Input() type: 'button' | 'submit' = 'button';
  @Input() formId = '';
  @Input() disabled = false;
  @Output() clicked = new EventEmitter<void>();
}

@Component({
  selector: 'app-secondary-button',
  standalone: true,
  template: `
    <button
      type="button"
      [disabled]="disabled"
      (click)="clicked.emit()"
      class="cursor-pointer rounded-lg border border-[#e4e8ef] px-4 py-2.5 text-sm font-medium text-[#697386] transition-colors hover:bg-[#f5f7fb] disabled:cursor-not-allowed disabled:opacity-70"
    >
      <ng-content></ng-content>
    </button>
  `,
})
export class SecondaryButtonComponent {
  @Input() disabled = false;
  @Output() clicked = new EventEmitter<void>();
}
