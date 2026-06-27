import { apiClient } from '../infrastructure/http/apiClient'
import type { UserResponse } from '../types/user'

class UserApiService {
  findByUsername(username: string): Promise<UserResponse> {
    return apiClient.get<UserResponse>(`/user/${username}`)
  }
}

export const userApiService = new UserApiService()
