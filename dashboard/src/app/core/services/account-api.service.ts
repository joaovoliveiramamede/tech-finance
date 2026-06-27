import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client.service';
import {
  AccountRequest,
  AccountResponse,
  AccountType,
} from '../models/account.model';
import { SessionManager } from '../session/session-manager.service';

@Injectable({ providedIn: 'root' })
export class AccountApiService {
  constructor(
    private readonly api: ApiClient,
    private readonly session: SessionManager,
  ) {}

  findAll(): Observable<AccountResponse[]> {
    return this.api.getList<AccountResponse>('/account');
  }

  create(request: AccountRequest): Observable<AccountResponse> {
    return this.api.post<AccountResponse>('/account/create', request);
  }

  createAccount(name: string, balance: number): Observable<AccountResponse> {
    return this.create(this.buildRequest(name, balance));
  }

  private buildRequest(name: string, balance: number): AccountRequest {
    const userId = this.session.getSession()?.userId;

    if (!userId) {
      throw new Error('usuário não autenticado');
    }

    return {
      nome: name,
      valor: balance,
      tipo: 'CHECKING' as AccountType,
      id_usuario: userId,
    };
  }
}
