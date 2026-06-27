import { Injectable } from '@angular/core';
import { Observable, switchMap, map } from 'rxjs';
import { ApiClient } from '../http/api-client.service';
import {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
} from '../models/auth.model';
import { SessionManager } from '../session/session-manager.service';
import { UserApiService } from './user-api.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(
    private readonly api: ApiClient,
    private readonly session: SessionManager,
    private readonly userApi: UserApiService,
  ) {}

  register(request: RegisterRequest): Observable<void> {
    return this.api
      .postPublic<AuthResponse>('/auth/register', request)
      .pipe(switchMap((response) => this.establishSession(response)));
  }

  login(request: LoginRequest): Observable<void> {
    return this.api
      .postPublic<AuthResponse>('/auth/login', request)
      .pipe(switchMap((response) => this.establishSession(response)));
  }

  logout(): void {
    this.session.clearSession();
  }

  private establishSession(response: AuthResponse): Observable<void> {
    this.session.setSession({
      token: response.token,
      username: response.usuario,
      name: response.nome,
      userId: '',
    });

    return this.userApi.findByUsername(response.usuario).pipe(
      map((user) => {
        this.session.setSession({
          token: response.token,
          username: response.usuario,
          name: response.nome,
          userId: user.id,
        });
      }),
    );
  }
}
