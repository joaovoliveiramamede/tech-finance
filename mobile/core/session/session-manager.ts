import { Platform } from 'react-native';
import * as SecureStore from 'expo-secure-store';
import { Session } from './session.model';

const STORAGE_KEY = 'techfinance_session';

class SessionManager {
  private session: Session | null = null;
  private loaded = false;

  async load(): Promise<Session | null> {
    if (this.loaded) {
      return this.session;
    }

    const raw = await this.readStorage();

    if (!raw) {
      this.loaded = true;
      return null;
    }

    try {
      this.session = JSON.parse(raw) as Session;
    } catch {
      this.session = null;
    }

    this.loaded = true;
    return this.session;
  }

  getSession(): Session | null {
    return this.session;
  }

  async setSession(value: Session): Promise<void> {
    this.session = value;
    this.loaded = true;
    await this.writeStorage(JSON.stringify(value));
  }

  async clearSession(): Promise<void> {
    this.session = null;
    this.loaded = true;
    await this.removeStorage();
  }

  isAuthenticated(): boolean {
    return Boolean(this.session?.token?.trim());
  }

  getToken(): string | null {
    return this.session?.token ?? null;
  }

  private async readStorage(): Promise<string | null> {
    if (Platform.OS === 'web') {
      return globalThis.localStorage?.getItem(STORAGE_KEY) ?? null;
    }

    return SecureStore.getItemAsync(STORAGE_KEY);
  }

  private async writeStorage(value: string): Promise<void> {
    if (Platform.OS === 'web') {
      globalThis.localStorage?.setItem(STORAGE_KEY, value);
      return;
    }

    await SecureStore.setItemAsync(STORAGE_KEY, value);
  }

  private async removeStorage(): Promise<void> {
    if (Platform.OS === 'web') {
      globalThis.localStorage?.removeItem(STORAGE_KEY);
      return;
    }

    await SecureStore.deleteItemAsync(STORAGE_KEY);
  }
}

export const sessionManager = new SessionManager();
