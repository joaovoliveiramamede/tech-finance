export interface CategoryRequest {
  nome: string
  descricao: string
}

export interface CategoryResponse {
  id: string
  nome: string
  descricao: string
  data_criacao: string
  data_atualizacao: string
}
