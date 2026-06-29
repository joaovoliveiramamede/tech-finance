import { apiClient } from '@/core/http/api-client';
import { AuthResponse, LoginRequest, RegisterRequest } from '@/core/models/auth.model';
import { sessionManager } from '@/core/session/session-manager';
import { userApiService } from './user-api.service';

class AuthService {
  async register(request: RegisterRequest): Promise<void> {
    const response = await apiClient.postPublic<AuthResponse>('/auth/register', request);
    await this.establishSession(response);
  }

  async login(request: LoginRequest): Promise<void> {
    const response = await apiClient.postPublic<AuthResponse>('/auth/login', request);
    await this.establishSession(response);
  }

  async logout(): Promise<void> {
    await sessionManager.clearSession();
  }

  private async establishSession(response: AuthResponse): Promise<void> {
    await sessionManager.setSession({
      token: response.token,
      username: response.usuario,
      name: response.nome,
      userId: '',
    });

    const user = await userApiService.findByUsername(response.usuario);

    await sessionManager.setSession({
      token: response.token,
      username: response.usuario,
      name: response.nome,
      userId: user.id,
    });
  }
}

export const authService = new AuthService();
