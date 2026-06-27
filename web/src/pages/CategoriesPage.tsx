import { type FormEvent, useCallback, useEffect, useState } from 'react'
import { DataTable } from '../components/ui/DataTable'
import {
  FormField,
  Modal,
  PrimaryButton,
  SecondaryButton,
  fieldClassName,
} from '../components/ui/Modal'
import { PageHeader } from '../components/ui/PageHeader'
import { categoryApiService } from '../services/categoryApiService'
import type { CategoryResponse } from '../types/category'
import { formatDateTime, resolveErrorMessage } from '../utils/formatters'

export function CategoriesPage() {
  const [categories, setCategories] = useState<CategoryResponse[]>([])
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)
  const [modalOpen, setModalOpen] = useState(false)
  const [creating, setCreating] = useState(false)
  const [name, setName] = useState('')
  const [description, setDescription] = useState('')

  const loadCategories = useCallback(async () => {
    setLoading(true)

    try {
      const data = await categoryApiService.findAll()
      setCategories(data)
      setError(null)
    } catch (loadError) {
      setError(resolveErrorMessage(loadError, 'Erro ao processar categoria.'))
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    loadCategories()
  }, [loadCategories])

  function openModal() {
    setName('')
    setDescription('')
    setModalOpen(true)
  }

  async function handleCreate(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const trimmedName = name.trim()

    if (!trimmedName) {
      setError('Informe o nome da categoria.')
      return
    }

    setCreating(true)

    try {
      await categoryApiService.create({
        nome: trimmedName,
        descricao: description.trim(),
      })
      setModalOpen(false)
      setError(null)
      await loadCategories()
    } catch (createError) {
      setError(resolveErrorMessage(createError, 'Erro ao processar categoria.'))
    } finally {
      setCreating(false)
    }
  }

  return (
    <div className="space-y-5">
      <PageHeader
        title="Categorias"
        subtitle="Classificação das suas movimentações"
        action={
          <PrimaryButton onClick={openModal}>Nova categoria</PrimaryButton>
        }
      />

      {error && <p className="text-sm text-red-600">{error}</p>}

      {loading ? (
        <p className="text-sm text-[#8a94a6]">Carregando...</p>
      ) : (
        <DataTable
          data={categories}
          columns={[
            {
              key: 'nome',
              header: 'Nome',
              render: (category) => category.nome,
            },
            {
              key: 'descricao',
              header: 'Descrição',
              render: (category) => category.descricao || '-',
            },
            {
              key: 'criada',
              header: 'Criada em',
              render: (category) => formatDateTime(category.data_criacao),
            },
          ]}
        />
      )}

      <Modal
        open={modalOpen}
        title="Nova categoria"
        onClose={() => setModalOpen(false)}
        footer={
          <>
            <SecondaryButton onClick={() => setModalOpen(false)}>
              Cancelar
            </SecondaryButton>
            <PrimaryButton
              type="submit"
              form="create-category-form"
              disabled={creating}
            >
              {creating ? 'Salvando...' : 'Salvar'}
            </PrimaryButton>
          </>
        }
      >
        <form
          id="create-category-form"
          className="space-y-4"
          onSubmit={handleCreate}
        >
          <FormField label="Nome">
            <input
              type="text"
              value={name}
              onChange={(event) => setName(event.target.value)}
              placeholder="Nome"
              className={fieldClassName}
            />
          </FormField>

          <FormField label="Descrição">
            <input
              type="text"
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              placeholder="Descrição"
              className={fieldClassName}
            />
          </FormField>
        </form>
      </Modal>
    </div>
  )
}
