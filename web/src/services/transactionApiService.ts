import { apiClient } from '../infrastructure/http/apiClient'
import type {
  TransactionRequest,
  TransactionResponse,
} from '../types/transaction'

class TransactionApiService {
  findAll(): Promise<TransactionResponse[]> {
    return apiClient.getList<TransactionResponse>('/transaction')
  }

  create(request: TransactionRequest): Promise<TransactionResponse> {
    return apiClient.post<TransactionResponse>('/transaction/create', request)
  }
}

export const transactionApiService = new TransactionApiService()
