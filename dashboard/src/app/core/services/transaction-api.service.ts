import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client.service';
import {
  TransactionRequest,
  TransactionResponse,
} from '../models/transaction.model';

@Injectable({ providedIn: 'root' })
export class TransactionApiService {
  constructor(private readonly api: ApiClient) {}

  findAll(): Observable<TransactionResponse[]> {
    return this.api.getList<TransactionResponse>('/transaction');
  }

  create(request: TransactionRequest): Observable<TransactionResponse> {
    return this.api.post<TransactionResponse>('/transaction/create', request);
  }
}
