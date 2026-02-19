<script setup lang="ts">
import type { Quote } from '../services/quotesApi'

defineProps<{
  quotes: Quote[]
}>()

defineEmits<{
  (e: 'clearAll'): void
  (e: 'deleteOne', quoteId: number): void
}>()
</script>

<template>
  <section class="flex-1 flex flex-col min-w-0 bg-slate-50 dark:bg-slate-950 transition-colors">
    <div class="p-6 sm:p-10 flex justify-between items-center gap-4">
      <div class="flex flex-row items-center gap-4 min-w-0">
        <h2 class="text-3xl font-bold">Historique</h2>
        <button
          v-if="quotes.length > 0"
          @click="$emit('clearAll')"
          class="text-[10px] font-bold text-red-500 hover:text-white hover:bg-red-500 border border-red-500/30 px-3 py-1 rounded-lg transition-all cursor-pointer"
        >
          RÉINITIALISER
        </button>
      </div>
      <span class="text-slate-400 dark:text-slate-500 font-mono text-sm tracking-tighter whitespace-nowrap">
        {{ quotes.length }} SIMULATION{{ quotes.length > 1 ? 'S' : '' }}
      </span>
    </div>

    <div class="flex-1 overflow-y-auto px-6 sm:px-10 pb-10 custom-scrollbar">
      <table class="w-full text-left border-collapse">
        <thead class="sticky top-0 bg-slate-50 dark:bg-slate-950 z-10 transition-colors">
          <tr class="text-[10px] font-bold text-slate-400 dark:text-slate-500 border-b border-slate-200 dark:border-slate-800">
            <th class="py-4">CONDUCTEUR</th>
            <th class="py-4">VÉHICULE</th>
            <th class="py-4">RISQUE</th>
            <th class="py-4 text-right">PRIME /mois</th>
          </tr>
        </thead>

        <tbody class="divide-y divide-slate-100 dark:divide-slate-800">
          <tr
            v-for="quote in quotes"
            :key="quote.quoteId"
            class="hover:bg-white dark:hover:bg-slate-900 transition-colors group"
          >
            <td class="py-6 font-bold text-lg dark:text-slate-200">
              <div class="flex items-center gap-3">
                <button
                  @click="$emit('deleteOne', quote.quoteId)"
                  class="p-2 text-slate-300 hover:text-red-500 hover:bg-red-50 dark:hover:bg-red-500/10 rounded-xl transition-all opacity-70 group-hover:opacity-100 cursor-pointer hover:rotate-12 hover:scale-110 active:scale-90"
                  aria-label="Supprimer le devis"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                    />
                  </svg>
                </button>
                {{ quote.customerName }}
              </div>
            </td>
            <td class="py-6 text-slate-500 dark:text-slate-400 font-medium">{{ quote.vehicleType }}</td>
            <td class="py-6">
              <span
                :class="[
                  'px-3 py-1 rounded-full text-[10px] font-black border transition-all',
                  quote.riskLevel === 'HIGH'
                    ? 'bg-red-500/10 text-red-500 border-red-500/20'
                    : 'bg-emerald-500/10 text-emerald-500 border-emerald-500/20'
                ]"
              >
                {{ quote.riskLevel }}
              </span>
            </td>
            <td class="py-6 text-right font-black text-2xl text-blue-600 dark:text-blue-400 italic">
              {{ quote.monthlyPremium }}€
            </td>
          </tr>
        </tbody>
      </table>

      <div v-if="quotes.length === 0" class="flex flex-col items-center justify-center h-64 text-slate-300 dark:text-slate-700">
        <p class="text-lg font-medium">Aucun historique disponible</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 6px;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #e2e8f0;
  border-radius: 9999px;
}
:deep(.dark) .custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #1e293b;
}
</style>

