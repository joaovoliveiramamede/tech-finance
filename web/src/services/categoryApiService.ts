import { apiClient } from '../infrastructure/http/apiClient'
import type { CategoryRequest, CategoryResponse } from '../types/category'

class CategoryApiService {
  findAll(): Promise<CategoryResponse[]> {
    return apiClient.getList<CategoryResponse>('/category')
  }

  create(request: CategoryRequest): Promise<CategoryResponse> {
    return apiClient.post<CategoryResponse>('/category/create', request)
  }
}

export const categoryApiService = new CategoryApiService()
