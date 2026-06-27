import { Injectable } from '@angular/core';
import { Session } from './session.model';

const STORAGE_KEY = 'techfinance_session';

@Injectable({ providedIn: 'root' })
export class SessionManager {
  private session: Session | null = this.loadSession();

  getSession(): Session | null {
    return this.session;
  }

  setSession(value: Session): void {
    this.session = value;
    localStorage.setItem(STORAGE_KEY, JSON.stringify(value));
  }

  clearSession(): void {
    this.session = null;
    localStorage.removeItem(STORAGE_KEY);
  }

  isAuthenticated(): boolean {
    return Boolean(this.session?.token?.trim());
  }

  getToken(): string | null {
    return this.session?.token ?? null;
  }

  private loadSession(): Session | null {
    const raw = localStorage.getItem(STORAGE_KEY);

    if (!raw) {
      return null;
    }

    try {
      return JSON.parse(raw) as Session;
    } catch {
      return null;
    }
  }
}
