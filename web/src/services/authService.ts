import { apiClient } from '../infrastructure/http/apiClient'
import type { LoginRequest, RegisterRequest, AuthResponse } from '../types/auth'
import {
  clearSession,
  getSession,
  isAuthenticated,
  setSession,
} from '../infrastructure/session/sessionManager'
import { userApiService } from './userApiService'

class AuthService {
  async register(request: RegisterRequest): Promise<void> {
    const response = await apiClient.postPublic<AuthResponse>(
      '/auth/register',
      request,
    )

    await this.establishSession(response)
  }

  async login(request: LoginRequest): Promise<void> {
    const response = await apiClient.postPublic<AuthResponse>(
      '/auth/login',
      request,
    )

    await this.establishSession(response)
  }

  logout(): void {
    clearSession()
  }

  isAuthenticated(): boolean {
    return isAuthenticated()
  }

  getUsername(): string | null {
    return getSession()?.username ?? null
  }

  getName(): string | null {
    return getSession()?.name ?? null
  }

  getToken(): string | null {
    return getSession()?.token ?? null
  }

  private async establishSession(response: AuthResponse): Promise<void> {
    setSession({
      token: response.token,
      username: response.usuario,
      name: response.nome,
      userId: '',
    })

    const user = await userApiService.findByUsername(response.usuario)

    setSession({
      token: response.token,
      username: response.usuario,
      name: response.nome,
      userId: user.id,
    })
  }
}

export const authService = new AuthService()
