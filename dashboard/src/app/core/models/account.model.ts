export type AccountType = 'CHECKING';

export interface AccountRequest {
  nome: string;
  valor: number;
  tipo: AccountType;
  id_usuario: string;
}

export interface AccountResponse {
  id: string;
  nome: string;
  saldo: number;
  tipo: AccountType;
  id_usuario: string;
  data_criacao: string;
  data_atualizacao: string;
}
