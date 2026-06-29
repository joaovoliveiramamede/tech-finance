export interface LoginRequest {
  usuario: string;
  senha: string;
}

export interface RegisterRequest {
  nome: string;
  usuario: string;
  senha: string;
  papel: string;
}

export interface AuthResponse {
  nome: string;
  usuario: string;
  papel: string;
  token: string;
}
