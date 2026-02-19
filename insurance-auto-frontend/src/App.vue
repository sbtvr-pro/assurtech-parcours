<script setup lang="ts">
import { onMounted, ref } from 'vue'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
import QuoteForm from './components/QuoteForm.vue'
import QuoteHistory from './components/QuoteHistory.vue'
import MessageModal from './components/modals/MessageModal.vue'
import ConfirmModal from './components/modals/ConfirmModal.vue'
import { createQuote, deleteAllQuotes, deleteQuoteById, getQuotes, type Quote } from './services/quotesApi'

const isDark = ref(false)
const loading = ref(false)
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
    showMessage('Impossible de charger les devis.', 'Erreur')
  }
}

const createQuoteHandler = async () => {
  if (!form.value.name || !form.value.age || !form.value.car) {
    showMessage('Merci de remplir tous les champs obligatoires.', 'Champs requis')
    return
  }

  loading.value = true
  try {
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
  fetchQuotes()
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

      <main class="flex-1 flex flex-col lg:flex-row min-h-0 overflow-y-auto">
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