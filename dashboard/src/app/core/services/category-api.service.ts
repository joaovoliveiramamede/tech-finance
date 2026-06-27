import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client.service';
import {
  CategoryRequest,
  CategoryResponse,
} from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class CategoryApiService {
  constructor(private readonly api: ApiClient) {}

  findAll(): Observable<CategoryResponse[]> {
    return this.api.getList<CategoryResponse>('/category');
  }

  create(request: CategoryRequest): Observable<CategoryResponse> {
    return this.api.post<CategoryResponse>('/category/create', request);
  }
}
