export class ApiException extends Error {
  readonly statusCode: number

  constructor(message: string, statusCode: number) {
    super(message)
    this.name = 'ApiException'
    this.statusCode = statusCode
  }
}
