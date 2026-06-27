import { API_BASE_URL } from '../../config/api'
import type { ErrorResponse } from '../../types/error'
import { getToken } from '../session/sessionManager'
import { ApiException } from './apiException'

class ApiClient {
  private readonly baseUrl = API_BASE_URL

  async get<T>(path: string): Promise<T> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'GET',
      headers: this.buildHeaders(true),
    })

    return this.parseJson<T>(response)
  }

  async getList<T>(path: string): Promise<T[]> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'GET',
      headers: this.buildHeaders(true),
    })

    return this.parseJson<T[]>(response)
  }

  async postPublic<T>(path: string, body: unknown): Promise<T> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'POST',
      headers: this.buildHeaders(false),
      body: JSON.stringify(body),
    })

    return this.parseJson<T>(response)
  }

  async post<T>(path: string, body: unknown): Promise<T> {
    const response = await fetch(`${this.baseUrl}${path}`, {
      method: 'POST',
      headers: this.buildHeaders(true),
      body: JSON.stringify(body),
    })

    return this.parseJson<T>(response)
  }

  private buildHeaders(authenticated: boolean): HeadersInit {
    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
    }

    if (authenticated) {
      const token = getToken()

      if (token) {
        headers.Authorization = `Bearer ${token}`
      }
    }

    return headers
  }

  private async parseJson<T>(response: Response): Promise<T> {
    const body = await response.text()

    if (!response.ok) {
      throw this.buildException(body, response.status)
    }

    if (!body) {
      return undefined as T
    }

    return JSON.parse(body) as T
  }

  private buildException(body: string, statusCode: number): ApiException {
    try {
      const error = JSON.parse(body) as ErrorResponse
      return new ApiException(error.mensagem, statusCode)
    } catch {
      return new ApiException(`erro na API | status=${statusCode}`, statusCode)
    }
  }
}

export const apiClient = new ApiClient()
