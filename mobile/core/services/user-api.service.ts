import { apiClient } from '@/core/http/api-client';
import { UserResponse } from '@/core/models/user.model';

class UserApiService {
  findByUsername(username: string): Promise<UserResponse> {
    return apiClient.get<UserResponse>(`/user/${username}`);
  }
}

export const userApiService = new UserApiService();
