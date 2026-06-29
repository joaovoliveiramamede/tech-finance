import React, {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from 'react';

import { LoginRequest, RegisterRequest } from '@/core/models/auth.model';
import { authService } from '@/core/services/auth.service';
import { sessionManager } from '@/core/session/session-manager';

interface AuthContextValue {
  isAuthenticated: boolean;
  isLoading: boolean;
  username: string | null;
  name: string | null;
  login: (request: LoginRequest) => Promise<void>;
  register: (request: RegisterRequest) => Promise<void>;
  logout: () => Promise<void>;
  refreshSession: () => Promise<void>;
}

const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState<string | null>(null);
  const [name, setName] = useState<string | null>(null);

  const syncFromSession = useCallback(() => {
    const session = sessionManager.getSession();
    setIsAuthenticated(sessionManager.isAuthenticated());
    setUsername(session?.username ?? null);
    setName(session?.name ?? null);
  }, []);

  const refreshSession = useCallback(async () => {
    await sessionManager.load();
    syncFromSession();
  }, [syncFromSession]);

  useEffect(() => {
    refreshSession().finally(() => setIsLoading(false));
  }, [refreshSession]);

  const login = useCallback(
    async (request: LoginRequest) => {
      await authService.login(request);
      syncFromSession();
    },
    [syncFromSession],
  );

  const register = useCallback(
    async (request: RegisterRequest) => {
      await authService.register(request);
      syncFromSession();
    },
    [syncFromSession],
  );

  const logout = useCallback(async () => {
    await authService.logout();
    syncFromSession();
  }, [syncFromSession]);

  const value = useMemo(
    () => ({
      isAuthenticated,
      isLoading,
      username,
      name,
      login,
      register,
      logout,
      refreshSession,
    }),
    [isAuthenticated, isLoading, username, name, login, register, logout, refreshSession],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextValue {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }

  return context;
}
