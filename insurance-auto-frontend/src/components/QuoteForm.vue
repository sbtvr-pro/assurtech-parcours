<script setup lang="ts">
import { computed, ref, watch } from 'vue'

type QuoteFormModel = {
  name: string
  car: string
  age: number | null
  claims: number
}

const props = defineProps<{
  modelValue: QuoteFormModel
  loading: boolean
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: QuoteFormModel): void
  (e: 'submit-form'): void
}>()

const local = ref<QuoteFormModel>(props.modelValue)

// Quand le parent remplace complètement l'objet (ex: reset formulaire)
watch(
  () => props.modelValue,
  (v) => {
    if (v !== local.value) {
      local.value = v
    }
  }
)

// Quand l'utilisateur tape dans le formulaire
watch(
  local,
  (v) => {
    if (v !== props.modelValue) {
      emit('update:modelValue', v)
    }
  },
  { deep: true }
)

const canSubmit = computed(() => !props.loading)
</script>

<template>
  <section
    class="w-full lg:w-[35%] p-6 sm:p-10 flex flex-col border-b lg:border-b-0 lg:border-r border-slate-200 dark:border-slate-800 bg-white/50 dark:bg-slate-900/50 transition-colors"
  >
    <div class="max-w-xl mx-auto w-full">
      <h2 class="text-3xl font-bold mb-8">Nouveau Devis</h2>

      <form @submit.prevent="emit('submit-form')" class="space-y-8">
        <div class="space-y-2">
          <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Nom du Client</label>
          <input
            v-model="local.name"
            type="text"
            class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl shadow-sm outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all"
          />
        </div>
<br>  
        <div class="grid grid-cols-2 gap-6">
          <div class="space-y-2">
            <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Âge</label>
            <input
              v-model.number="local.age"
              type="number"
              min="15"
              max="90"
              class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all"
            />
          </div>
          <div class="space-y-2">
            <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Sinistres</label>
            <input
              v-model.number="local.claims"
              type="number"
              min="0"
              class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all"
            />
          </div>
        </div>
<br>
        <div class="space-y-2">
          <label class="text-[10px] font-bold uppercase tracking-widest text-slate-400 ml-1">Véhicule</label>
          <select
            v-model="local.car"
            class="w-full p-4 bg-slate-50 dark:bg-slate-800 border-none rounded-2xl outline-none appearance-none cursor-pointer dark:text-white transition-all"
          >
            <option value="" disabled>Choisir un véhicule</option>
            <option value="BERLINE" class="dark:bg-slate-800">Berline</option>
            <option value="SUV" class="dark:bg-slate-800">SUV</option>
            <option value="SPORT" class="dark:bg-slate-800">Sport</option>
          </select>
        </div>
        <br>
        <br>
        <br>
        
        <button
          type="submit"
          :disabled="!canSubmit"
          class="w-full py-6 bg-blue-600 hover:bg-blue-500 text-white rounded-2xl font-black text-xl transition-all shadow-xl shadow-blue-500/20 active:scale-95 cursor-pointer disabled:cursor-not-allowed disabled:bg-blue-300 disabled:shadow-none"
        >
          Calculer
        </button>
      </form>
    </div>
  </section>
</template>

