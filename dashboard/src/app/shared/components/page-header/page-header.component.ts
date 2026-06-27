import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-page-header',
  standalone: true,
  template: `
    <div class="flex flex-wrap items-start justify-between gap-4">
      <div class="space-y-1">
        <h1 class="text-3xl font-bold text-[#172033]">{{ title }}</h1>
        <p class="text-sm text-[#697386]">{{ subtitle }}</p>
      </div>
      <ng-content select="[actions]"></ng-content>
    </div>
  `,
})
export class PageHeaderComponent {
  @Input() title!: string;
  @Input() subtitle!: string;
}
