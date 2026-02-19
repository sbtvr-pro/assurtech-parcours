<script setup lang="ts">
defineProps<{
  show: boolean
  title: string
  message: string
}>()

defineEmits<{
  (e: 'close'): void
  (e: 'confirm'): void
}>()
</script>

<template>
  <Teleport to="body">
    <Transition name="modal">
      <div
        v-if="show"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm"
        @click.self="$emit('close')"
      >
        <div
          class="modal-panel w-full max-w-md rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-700 bg-white dark:bg-slate-900 overflow-hidden"
          role="dialog"
          aria-modal="true"
          aria-labelledby="modal-title-confirm"
          aria-describedby="modal-desc-confirm"
        >
          <div class="flex gap-4 p-6 border-l-4 border-red-500 bg-red-500/5 dark:bg-red-500/10">
            <div class="shrink-0 w-10 h-10 rounded-xl bg-red-500/20 dark:bg-red-500/30 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-600 dark:text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
              </svg>
            </div>
            <div class="min-w-0 flex-1">
              <h3 id="modal-title-confirm" class="text-[10px] font-black uppercase tracking-widest text-red-600 dark:text-red-400 mb-1">
                {{ title }}
              </h3>
              <p id="modal-desc-confirm" class="text-slate-700 dark:text-slate-200 text-base leading-relaxed">
                {{ message }}
              </p>
            </div>
          </div>
          <div class="px-6 pb-6 pt-2 flex gap-3 justify-end">
            <button
              type="button"
              @click="$emit('close')"
              class="px-6 py-3 rounded-xl bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 font-bold text-sm hover:ring-2 hover:ring-slate-300 dark:hover:ring-slate-600 transition-all cursor-pointer"
            >
              Annuler
            </button>
            <button
              type="button"
              @click="$emit('confirm')"
              class="px-6 py-3 rounded-xl bg-red-500 hover:bg-red-600 text-white font-bold text-sm transition-all cursor-pointer active:scale-95"
            >
              Confirmer
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.2s ease;
}
.modal-enter-active :deep(.modal-panel),
.modal-leave-active :deep(.modal-panel) {
  transition: transform 0.2s ease;
}
.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
.modal-enter-from :deep(.modal-panel),
.modal-leave-to :deep(.modal-panel) {
  transform: scale(0.95);
}
</style>

