import { apiClient } from '../infrastructure/http/apiClient'
import { getSession } from '../infrastructure/session/sessionManager'
import type {
  AccountRequest,
  AccountResponse,
  AccountType,
} from '../types/account'

class AccountApiService {
  findAll(): Promise<AccountResponse[]> {
    return apiClient.getList<AccountResponse>('/account')
  }

  create(request: AccountRequest): Promise<AccountResponse> {
    return apiClient.post<AccountResponse>('/account/create', request)
  }

  createAccount(name: string, balance: number): Promise<AccountResponse> {
    return this.create(this.buildRequest(name, balance))
  }

  private buildRequest(name: string, balance: number): AccountRequest {
    const userId = getSession()?.userId

    if (!userId) {
      throw new Error('usuário não autenticado')
    }

    return {
      nome: name,
      valor: balance,
      tipo: 'CHECKING' as AccountType,
      id_usuario: userId,
    }
  }
}

export const accountApiService = new AccountApiService()
