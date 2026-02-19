<script setup lang="ts">
defineProps<{
  show: boolean
  title: string
  message: string
}>()

defineEmits<{
  (e: 'close'): void
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
          aria-labelledby="modal-title-alert"
          aria-describedby="modal-desc-alert"
        >
          <div class="flex gap-4 p-6 border-l-4 border-blue-500 bg-blue-500/5 dark:bg-blue-500/10">
            <div class="shrink-0 w-10 h-10 rounded-xl bg-blue-500/20 dark:bg-blue-500/30 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-blue-600 dark:text-blue-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div class="min-w-0 flex-1">
              <h3 id="modal-title-alert" class="text-[10px] font-black uppercase tracking-widest text-blue-600 dark:text-blue-400 mb-1">
                {{ title }}
              </h3>
              <p id="modal-desc-alert" class="text-slate-700 dark:text-slate-200 text-base leading-relaxed">
                {{ message }}
              </p>
            </div>
          </div>
          <div class="px-6 pb-6 pt-2 flex justify-end">
            <button
              type="button"
              @click="$emit('close')"
              class="px-6 py-3 rounded-xl bg-blue-600 hover:bg-blue-500 text-white font-bold text-sm transition-all cursor-pointer active:scale-95"
            >
              OK
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

