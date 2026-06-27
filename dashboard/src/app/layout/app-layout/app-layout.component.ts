import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthStateService } from '../../core/services/auth-state.service';

const NAV_ITEMS = [
  { to: '/', label: 'Dashboard', exact: true },
  { to: '/contas', label: 'Contas', exact: false },
  { to: '/transacoes', label: 'Transações', exact: false },
  { to: '/categorias', label: 'Categorias', exact: false },
];

const PAGE_TITLES: Record<string, string> = {
  '/': 'Dashboard',
  '/contas': 'Contas',
  '/transacoes': 'Transações',
  '/categorias': 'Categorias',
};

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="flex min-h-svh bg-[#f5f7fb]">
      <aside
        class="flex w-[248px] min-w-[248px] max-w-[248px] flex-col justify-between bg-[#172033] px-4 py-6"
      >
        <div class="space-y-7">
          <div class="px-2">
            <h1 class="text-xl font-bold text-white">TechFinance</h1>
            <p class="text-xs text-[#9da8ba]">Controle financeiro pessoal</p>
          </div>

          <nav class="space-y-2">
            <a
              *ngFor="let item of navItems"
              [routerLink]="item.to"
              routerLinkActive="bg-[#2f6fed] font-medium text-white"
              [routerLinkActiveOptions]="{ exact: item.exact }"
              class="block w-full rounded-lg px-3.5 py-3 text-left text-sm text-[#c7d0df] transition-colors hover:bg-[#22304a] hover:text-white"
            >
              {{ item.label }}
            </a>
          </nav>
        </div>

        <div class="space-y-1 rounded-[10px] bg-[#202c43] p-3">
          <p class="font-bold text-white">{{ displayName }}</p>
          <p class="text-xs text-[#9da8ba]">Usuário logado</p>
          <button
            type="button"
            (click)="logout()"
            class="mt-1 cursor-pointer text-sm text-[#c7d0df] transition-colors hover:text-white"
          >
            Sair
          </button>
        </div>
      </aside>

      <div class="flex min-w-0 flex-1 flex-col">
        <header
          class="flex h-16 items-center border-b border-[#e4e8ef] bg-white px-6"
        >
          <h2 class="text-lg font-bold text-[#172033]">{{ pageTitle }}</h2>
        </header>

        <main class="flex-1 overflow-auto p-6">
          <router-outlet (activate)="onActivate()"></router-outlet>
        </main>
      </div>
    </div>
  `,
})
export class AppLayoutComponent {
  private readonly auth = inject(AuthStateService);
  private readonly router = inject(Router);

  readonly navItems = NAV_ITEMS;
  pageTitle = 'Dashboard';

  get displayName(): string {
    return this.auth.name ?? this.auth.username ?? 'Usuário';
  }

  logout(): void {
    this.auth.logout();
    void this.router.navigateByUrl('/login');
  }

  onActivate(): void {
    const path = window.location.pathname;
    this.pageTitle = PAGE_TITLES[path] ?? 'Dashboard';
  }
}
