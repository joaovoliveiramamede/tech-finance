import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthStateService } from '../../../core/services/auth-state.service';
import { resolveErrorMessage } from '../../../core/utils/formatters';
import { normalize, validateRegister } from '../../../core/utils/validation';
import { AuthLayoutComponent } from '../../../shared/components/auth-layout/auth-layout.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, AuthLayoutComponent],
  template: `
    <app-auth-layout
      title="Criar conta"
      subtitle="Comece a organizar suas finanças"
    >
      <form class="space-y-4" (ngSubmit)="submit()">
        <input
          type="text"
          placeholder="Nome"
          [(ngModel)]="name"
          name="name"
          class="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="text"
          placeholder="Usuário"
          [(ngModel)]="username"
          name="username"
          class="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <input
          type="password"
          placeholder="Senha"
          [(ngModel)]="password"
          name="password"
          class="w-full rounded-xl border border-slate-200 px-3 py-3 text-slate-900 outline-none focus:border-blue-500"
        />

        <p *ngIf="error" class="text-sm text-red-600">{{ error }}</p>

        <button
          type="submit"
          [disabled]="loading"
          class="w-full cursor-pointer rounded-xl bg-blue-600 py-3 font-bold text-white transition-colors hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {{ loading ? 'Registrando...' : 'Registrar' }}
        </button>

        <a
          routerLink="/login"
          class="block w-full text-center text-blue-600 transition-colors hover:text-blue-700"
        >
          Já tenho conta
        </a>
      </form>
    </app-auth-layout>
  `,
})
export class RegisterComponent {
  private readonly auth = inject(AuthStateService);
  private readonly router = inject(Router);

  name = '';
  username = '';
  password = '';
  error: string | null = null;
  loading = false;

  submit(): void {
    this.error = null;

    const normalizedName = normalize(this.name);
    const normalizedUsername = normalize(this.username);
    const validationError = validateRegister(
      normalizedName,
      normalizedUsername,
      this.password,
    );

    if (validationError) {
      this.error = validationError;
      return;
    }

    this.loading = true;

    this.auth
      .register({
        nome: normalizedName,
        usuario: normalizedUsername,
        senha: this.password,
        papel: 'USER',
      })
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: () => this.router.navigateByUrl('/criar-conta'),
        error: (err) =>
          (this.error = resolveErrorMessage(err, 'Erro ao registrar usuário.')),
      });
  }
}
