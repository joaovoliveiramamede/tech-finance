import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-auth-layout',
  standalone: true,
  template: `
    <div
      class="flex min-h-svh items-center justify-center bg-gradient-to-br from-slate-900 to-blue-800 px-4 py-10"
    >
      <div class="w-full max-w-md rounded-[22px] bg-white p-9 shadow-2xl">
        <div class="mb-6 space-y-1">
          <h1 class="text-3xl font-bold text-slate-900">{{ title }}</h1>
          <p class="text-slate-500">{{ subtitle }}</p>
        </div>
        <ng-content></ng-content>
      </div>
    </div>
  `,
})
export class AuthLayoutComponent {
  @Input() title!: string;
  @Input() subtitle!: string;
}
