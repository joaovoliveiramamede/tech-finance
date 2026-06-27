import { Routes } from '@angular/router';
import {
  authGuard,
  guestGuard,
  onboardingGuard,
} from './core/guards/auth.guards';
import { AppLayoutComponent } from './layout/app-layout/app-layout.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { CreateAccountComponent } from './features/auth/create-account/create-account.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { AccountsComponent } from './features/accounts/accounts.component';
import { TransactionsComponent } from './features/transactions/transactions.component';
import { CategoriesComponent } from './features/categories/categories.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [guestGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [guestGuard],
  },
  {
    path: 'criar-conta',
    component: CreateAccountComponent,
    canActivate: [authGuard],
  },
  {
    path: '',
    component: AppLayoutComponent,
    canActivate: [authGuard, onboardingGuard],
    children: [
      { path: '', component: DashboardComponent },
      { path: 'contas', component: AccountsComponent },
      { path: 'transacoes', component: TransactionsComponent },
      { path: 'categorias', component: CategoriesComponent },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
