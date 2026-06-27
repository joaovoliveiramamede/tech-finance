import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { ErrorResponse } from '../models/error.model';
import { SessionManager } from '../session/session-manager.service';
import { ApiException } from './api-exception';

@Injectable({ providedIn: 'root' })
export class ApiClient {
  private readonly baseUrl = environment.apiBaseUrl;

  constructor(
    private readonly http: HttpClient,
    private readonly session: SessionManager,
  ) {}

  get<T>(path: string): Observable<T> {
    return this.http
      .get<T>(`${this.baseUrl}${path}`, { headers: this.buildHeaders(true) })
      .pipe(catchError((error) => this.handleError(error)));
  }

  getList<T>(path: string): Observable<T[]> {
    return this.get<T[]>(path);
  }

  postPublic<T>(path: string, body: unknown): Observable<T> {
    return this.http
      .post<T>(`${this.baseUrl}${path}`, body, {
        headers: this.buildHeaders(false),
      })
      .pipe(catchError((error) => this.handleError(error)));
  }

  post<T>(path: string, body: unknown): Observable<T> {
    return this.http
      .post<T>(`${this.baseUrl}${path}`, body, {
        headers: this.buildHeaders(true),
      })
      .pipe(catchError((error) => this.handleError(error)));
  }

  private buildHeaders(authenticated: boolean): HttpHeaders {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    if (authenticated) {
      const token = this.session.getToken();

      if (token) {
        headers = headers.set('Authorization', `Bearer ${token}`);
      }
    }

    return headers;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    const body = error.error;

    if (body && typeof body === 'object' && 'mensagem' in body) {
      return throwError(
        () => new ApiException((body as ErrorResponse).mensagem, error.status),
      );
    }

    return throwError(
      () => new ApiException(`erro na API | status=${error.status}`, error.status),
    );
  }
}
