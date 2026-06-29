import { apiClient } from '@/core/http/api-client';
import {
  TransactionRequest,
  TransactionResponse,
} from '@/core/models/transaction.model';

class TransactionApiService {
  findAll(): Promise<TransactionResponse[]> {
    return apiClient.getList<TransactionResponse>('/transaction');
  }

  create(request: TransactionRequest): Promise<TransactionResponse> {
    return apiClient.post<TransactionResponse>('/transaction/create', request);
  }
}

export const transactionApiService = new TransactionApiService();
