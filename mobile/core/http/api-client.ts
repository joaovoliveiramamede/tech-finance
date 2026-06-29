import { environment } from '@/constants/environment';
import { ErrorResponse } from '@/core/models/error.model';
import { sessionManager } from '@/core/session/session-manager';
import { ApiException } from './api-exception';

class ApiClient {
  private readonly baseUrl = environment.apiBaseUrl;

  async get<T>(path: string): Promise<T> {
    return this.request<T>('GET', path, undefined, true);
  }

  async getList<T>(path: string): Promise<T[]> {
    return this.get<T[]>(path);
  }

  async postPublic<T>(path: string, body: unknown): Promise<T> {
    return this.request<T>('POST', path, body, false);
  }

  async post<T>(path: string, body: unknown): Promise<T> {
    return this.request<T>('POST', path, body, true);
  }

  private async request<T>(
    method: string,
    path: string,
    body: unknown | undefined,
    authenticated: boolean,
  ): Promise<T> {
    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
    };

    if (authenticated) {
      const token = sessionManager.getToken();
      if (token) {
        headers.Authorization = `Bearer ${token}`;
      }
    }

    const response = await fetch(`${this.baseUrl}${path}`, {
      method,
      headers,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });

    if (!response.ok) {
      throw await this.handleError(response);
    }

    if (response.status === 204) {
      return undefined as T;
    }

    return (await response.json()) as T;
  }

  private async handleError(response: Response): Promise<ApiException> {
    try {
      const body = (await response.json()) as ErrorResponse;
      if (body?.mensagem) {
        return new ApiException(body.mensagem, response.status);
      }
    } catch {
      // ignore parse errors
    }

    return new ApiException(`erro na API | status=${response.status}`, response.status);
  }
}

export const apiClient = new ApiClient();
