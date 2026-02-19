<script setup lang="ts">
defineProps<{
  isDark: boolean
}>()

defineEmits<{
  (e: 'toggleTheme'): void
}>()
</script>

<template>
  <header
    class="w-full px-6 sm:px-10 py-5 flex justify-between items-center shrink-0 border-b border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 transition-colors"
  >
    <div>
      <h1 class="text-2xl font-black tracking-tighter uppercase">
        AssurTech<span class="text-blue-500">.</span>
      </h1>
    </div>

    <button
      @click="$emit('toggleTheme')"
      class="px-4 py-2 rounded-xl bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 font-bold text-xs hover:ring-2 hover:ring-blue-500/50 transition-all cursor-pointer"
    >
      {{ isDark ? '🌞 MODE CLAIR' : '🌙 MODE SOMBRE' }}
    </button>
  </header>
</template>

