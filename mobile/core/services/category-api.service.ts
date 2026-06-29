import { apiClient } from '@/core/http/api-client';
import {
  CategoryRequest,
  CategoryResponse,
} from '@/core/models/category.model';

class CategoryApiService {
  findAll(): Promise<CategoryResponse[]> {
    return apiClient.getList<CategoryResponse>('/category');
  }

  create(request: CategoryRequest): Promise<CategoryResponse> {
    return apiClient.post<CategoryResponse>('/category/create', request);
  }
}

export const categoryApiService = new CategoryApiService();
