import { type ReactNode, useEffect } from 'react'

interface ModalProps {
  open: boolean
  title: string
  onClose: () => void
  children: ReactNode
  footer: ReactNode
}

export function Modal({ open, title, onClose, children, footer }: ModalProps) {
  useEffect(() => {
    if (!open) {
      return
    }

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === 'Escape') {
        onClose()
      }
    }

    window.addEventListener('keydown', handleKeyDown)
    return () => window.removeEventListener('keydown', handleKeyDown)
  }, [open, onClose])

  if (!open) {
    return null
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4">
      <div
        className="w-full max-w-md rounded-[10px] bg-white shadow-xl"
        role="dialog"
        aria-modal="true"
        aria-labelledby="modal-title"
      >
        <div className="border-b border-[#e4e8ef] px-5 py-4">
          <h2 id="modal-title" className="text-lg font-bold text-[#172033]">
            {title}
          </h2>
        </div>

        <div className="px-5 py-4">{children}</div>

        <div className="flex justify-end gap-2 border-t border-[#e4e8ef] px-5 py-4">
          {footer}
        </div>
      </div>
    </div>
  )
}

interface FormFieldProps {
  label: string
  children: ReactNode
}

export function FormField({ label, children }: FormFieldProps) {
  return (
    <label className="block space-y-1.5">
      <span className="text-sm font-medium text-[#172033]">{label}</span>
      {children}
    </label>
  )
}

export const fieldClassName =
  'w-full rounded-lg border border-[#e4e8ef] px-3 py-2.5 text-sm text-[#172033] outline-none focus:border-[#2f6fed]'

export function PrimaryButton({
  children,
  onClick,
  disabled,
  type = 'button',
  form,
}: {
  children: ReactNode
  onClick?: () => void
  disabled?: boolean
  type?: 'button' | 'submit'
  form?: string
}) {
  return (
    <button
      type={type}
      form={form}
      onClick={onClick}
      disabled={disabled}
      className="cursor-pointer rounded-lg bg-[#2f6fed] px-4 py-2.5 text-sm font-bold text-white transition-colors hover:bg-[#235fcf] disabled:cursor-not-allowed disabled:opacity-70"
    >
      {children}
    </button>
  )
}

export function SecondaryButton({
  children,
  onClick,
  disabled,
}: {
  children: ReactNode
  onClick?: () => void
  disabled?: boolean
}) {
  return (
    <button
      type="button"
      onClick={onClick}
      disabled={disabled}
      className="cursor-pointer rounded-lg border border-[#e4e8ef] px-4 py-2.5 text-sm font-medium text-[#697386] transition-colors hover:bg-[#f5f7fb] disabled:cursor-not-allowed disabled:opacity-70"
    >
      {children}
    </button>
  )
}
