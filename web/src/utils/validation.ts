const MIN_NAME_LENGTH = 3
const MIN_PASSWORD_LENGTH = 6

export function normalize(value: string | null | undefined): string {
  return value?.trim() ?? ''
}

export function validateRegister(
  name: string,
  username: string,
  password: string,
): string | null {
  if (!normalize(name)) {
    return 'Informe seu nome.'
  }

  if (normalize(name).length < MIN_NAME_LENGTH) {
    return 'Nome inválido. Mínimo 3 caracteres.'
  }

  if (!normalize(username)) {
    return 'Informe o usuário.'
  }

  if (!normalize(password)) {
    return 'Informe sua senha.'
  }

  if (normalize(password).length < MIN_PASSWORD_LENGTH) {
    return 'Senha inválida. Mínimo 6 caracteres.'
  }

  return null
}
