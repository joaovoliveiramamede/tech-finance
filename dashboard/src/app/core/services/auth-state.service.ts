import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest } from '../models/auth.model';
import { SessionManager } from '../session/session-manager.service';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  constructor(
    private readonly auth: AuthService,
    private readonly session: SessionManager,
  ) {}

  get isAuthenticated(): boolean {
    return this.session.isAuthenticated();
  }

  get username(): string | null {
    return this.session.getSession()?.username ?? null;
  }

  get name(): string | null {
    return this.session.getSession()?.name ?? null;
  }

  login(request: LoginRequest): Observable<void> {
    return this.auth.login(request).pipe(tap(() => undefined));
  }

  register(request: RegisterRequest): Observable<void> {
    return this.auth.register(request).pipe(tap(() => undefined));
  }

  logout(): void {
    this.auth.logout();
  }
}
