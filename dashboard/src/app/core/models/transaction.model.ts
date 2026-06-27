export type TransactionType = 'income' | 'expense';

export interface TransactionRequest {
  id_conta: string;
  id_categoria: string;
  valor: number;
  tipo: TransactionType;
  descricao: string;
  ocorreu_em: string;
}

export interface TransactionResponse {
  id: string;
  valor: number;
  tipo: TransactionType;
  descricao: string;
  ocorreu_em: string;
  id_conta: string;
  nome_conta: string;
  id_categoria: string;
  nome_categoria: string;
  data_criacao: string;
  data_atualizacao: string;
}
