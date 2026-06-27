import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client.service';
import { UserResponse } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserApiService {
  constructor(private readonly api: ApiClient) {}

  findByUsername(username: string): Observable<UserResponse> {
    return this.api.get<UserResponse>(`/user/${username}`);
  }
}
