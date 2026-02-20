<script setup lang="ts">
import { onMounted, ref } from 'vue'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
import QuoteForm from './components/QuoteForm.vue'
import QuoteHistory from './components/QuoteHistory.vue'
import MessageModal from './components/modals/MessageModal.vue'
import ConfirmModal from './components/modals/ConfirmModal.vue'
import { createQuote, deleteAllQuotes, deleteQuoteById, getQuotes, checkServerHealth, type Quote } from './services/quotesApi'

const isDark = ref(false)
const loading = ref(false)
const serverWakingUp = ref(false)
const countdown = ref(60)
const quotes = ref<Quote[]>([])

const form = ref({
  name: '',
  car: '',
  age: null as number | null,
  claims: 0
})

const messageModal = ref({ show: false, title: '', message: '' })
const confirmModal = ref({
  show: false,
  title: '',
  message: '',
  onConfirm: null as null | (() => void | Promise<void>)
})

const toggleTheme = () => {
  isDark.value = !isDark.value
  const htmlClass = document.documentElement.classList

  if (isDark.value) {
    htmlClass.add('dark')
    localStorage.setItem('theme', 'dark')
  } else {
    htmlClass.remove('dark')
    localStorage.setItem('theme', 'light')
  }
}

const showMessage = (message: string, title = 'Information') => {
  messageModal.value = { show: true, title, message }
}

const showConfirm = (message: string, title: string, onConfirm: () => void | Promise<void>) => {
  confirmModal.value = { show: true, title, message, onConfirm }
}

const fetchQuotes = async () => {
  try {
    quotes.value = await getQuotes()
  } catch (error) {
    console.error('Erreur API:', error)
    if (!serverWakingUp.value) {
      showMessage('Impossible de charger les devis.', 'Erreur')
    }
  }
}

const startWakeUpLoop = async () => {
  serverWakingUp.value = true
  countdown.value = 60
  
  const countdownInterval = setInterval(() => {
    if (countdown.value > 0) countdown.value--
  }, 1000)

  return new Promise<void>((resolve) => {
    const checkInterval = setInterval(async () => {
      const isHealthy = await checkServerHealth()
      if (isHealthy || countdown.value <= 0) {
        clearInterval(checkInterval)
        clearInterval(countdownInterval)
        serverWakingUp.value = false
        resolve()
      }
    }, 3000)
  })
}

const createQuoteHandler = async () => {
  if (!form.value.name || !form.value.age || !form.value.car) {
    showMessage('Merci de remplir tous les champs obligatoires.', 'Champs requis')
    return
  }

  loading.value = true
  try {
    // Vérifier si le serveur est prêt
    const isHealthy = await checkServerHealth()
    if (!isHealthy) {
      await startWakeUpLoop()
    }

    await createQuote(form.value)
    await fetchQuotes()
    form.value = { name: '', car: '', age: null, claims: 0 }
  } catch (e: any) {
    console.error(e)
    showMessage(`Erreur : ${e?.message || 'Problème inconnu'}`, 'Erreur')
  } finally {
    loading.value = false
  }
}

const clearHistory = () => {
  showConfirm("Voulez-vous vraiment supprimer tout l'historique ?", "Supprimer l'historique", async () => {
    try {
      await deleteAllQuotes()
      quotes.value = []
    } catch (e) {
      console.error('Erreur de connexion :', e)
      showMessage("Erreur lors de la suppression de l'historique", 'Erreur')
    }
  })
}

const deleteOneQuote = (quoteId: number) => {
  showConfirm('Voulez-vous vraiment supprimer ce devis ?', 'Supprimer le devis', async () => {
    try {
      await deleteQuoteById(quoteId)
      quotes.value = quotes.value.filter((q) => q.quoteId !== quoteId)
    } catch (e) {
      console.error('Erreur de connexion :', e)
      showMessage('Erreur lors de la suppression du devis', 'Erreur')
    }
  })
}

const confirmAndClose = async () => {
  try {
    await confirmModal.value.onConfirm?.()
  } finally {
    confirmModal.value.show = false
  }
}

onMounted(() => {
  fetchQuotes() // Chargement initial silencieux (H2 in memory)
  if (localStorage.getItem('theme') === 'dark') {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
})
</script>

<template>
  <div
    class="min-h-screen w-screen bg-slate-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 transition-colors duration-500 font-sans antialiased"
  >
    <div class="min-h-screen w-full flex flex-col">
      <AppHeader :is-dark="isDark" @toggle-theme="toggleTheme" />

      <main class="flex-1 flex flex-col lg:flex-row min-h-0 overflow-y-auto relative">
        <!-- Bandeau de réveil du serveur (Déclenché au clic) -->
        <div 
          v-if="serverWakingUp"
          class="absolute inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4"
        >
          <div class="bg-white dark:bg-slate-900 p-8 rounded-3xl shadow-2xl border border-slate-200 dark:border-slate-800 flex flex-col items-center text-center space-y-6 max-w-md animate-in fade-in zoom-in duration-300">
            <div class="relative w-24 h-24">
              <div class="absolute inset-0 border-4 border-blue-600/20 rounded-full"></div>
              <div 
                class="absolute inset-0 border-4 border-blue-600 rounded-full border-t-transparent animate-spin"
              ></div>
              <div class="absolute inset-0 flex items-center justify-center font-bold text-2xl text-blue-600">
                {{ countdown }}s
              </div>
            </div>
            
            <div class="space-y-2">
              <p class="text-xl font-black text-slate-800 dark:text-white">Réveil du serveur en cours</p>
              <p class="text-slate-500 dark:text-slate-400 leading-relaxed">
                Render met ses services en veille après 15 min d'inactivité. 
                <span class="block mt-2 font-medium text-blue-600 dark:text-blue-400">Le calcul démarrera automatiquement dès que le serveur sera prêt.</span>
              </p>
            </div>

            <div class="w-full bg-slate-100 dark:bg-slate-800 h-2 rounded-full overflow-hidden">
              <div 
                class="bg-blue-600 h-full transition-all duration-1000 ease-linear"
                :style="{ width: `${(countdown / 60) * 100}%` }"
              ></div>
            </div>
          </div>
        </div>

        <QuoteForm v-model="form" :loading="loading" @submit-form="createQuoteHandler" />
        <QuoteHistory :quotes="quotes" @clear-all="clearHistory" @delete-one="deleteOneQuote" />
      </main>
      <AppFooter />
      

      <MessageModal
        :show="messageModal.show"
        :title="messageModal.title"
        :message="messageModal.message"
        @close="messageModal.show = false"
      />

      <ConfirmModal
        :show="confirmModal.show"
        :title="confirmModal.title"
        :message="confirmModal.message"
        @close="confirmModal.show = false"
        @confirm="confirmAndClose"
      />
    </div>
  </div>
</template>