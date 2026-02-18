<script setup>
// 1. IMPORTS
// 'ref' est MAGIQUE. C'est ce qui rend une variable "vivante" (reactive).
// 'onMounted' est l'équivalent exact de ngOnInit() dans Angular.
import { ref, onMounted } from 'vue'

// 2. ÉTAT (STATE)
// En Angular : nom = '';
// En Vue     : const nom = ref('');
// ATTENTION : Pour lire/modifier la valeur dans le script, on utilise .value (ex: nom.value = 'Bob')
// Dans le HTML, pas besoin de .value.
const isDark = ref(false)

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

const loading = ref(false) // Pour afficher un spinner
const quotes = ref([])     // Notre liste de devis

// Notre objet formulaire (le DTO)
const form = ref({
  name: '',
  car: '', // Sera lié au select
  age: null,
  claims: 0
})

// 3. MÉTHODES (FONCTIONS)

// Fonction pour récupérer les devis (GET)
const fetchQuotes = async () => {
  try {
    // fetch est natif JS, pas besoin de HttpClientModule comme Angular
    const response = await fetch('http://localhost:8082/api/quotes')
    quotes.value = await response.json() // Hop, la vue se mettra à jour toute seule !
  } catch (error) {
    console.error("Erreur API:", error)
  }
}

// Fonction pour créer un devis (POST)
const createQuote = async () => {
  // Validation simple
  if (!form.value.name || !form.value.age || !form.value.car) {
    alert("Merci de remplir tous les champs obligatoires.")
    return
  }

  loading.value = true // Active le spinner
  
  try {
    const response = await fetch('http://localhost:8082/api/quotes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form.value) // Conversion Objet JS -> JSON
    })

    if (response.ok) {
      // Si succès (201 Created)
      await fetchQuotes() // On recharge la liste
      // On reset le formulaire
      form.value = { name: '', car: '', age: null, claims: 0 }
    } else {
      // Si erreur (ex: 400 Bad Request du Rule Engine)
      const errorData = await response.json()
      alert("Erreur : " + (errorData.error || "Problème inconnu"))
    }
  } catch (e) {
    console.error(e)
    alert("Impossible de contacter le serveur.")
  } finally {
    loading.value = false // Désactive le spinner quoi qu'il arrive
  }
}

const clearHistory = async () => {
  if (!confirm("Voulez-vous vraiment supprimer tout l'historique ?")) return

  try {
    // On appelle l'API avec la méthode DELETE
    const response = await fetch('http://localhost:8082/api/quotes', {
      method: 'DELETE'
    })

    if (response.ok) {
      quotes.value = [] // On vide la liste côté Front une fois que le Back a confirmé
    } else {
      alert("Erreur lors de la suppression de l'historique")
    }
  } catch (error) {
    console.error("Erreur de connexion :", error)
  }
}

const deleteOneQuote = async (quoteId) => {
  if (!confirm("Voulez-vous vraiment supprimer ce devis ?")) return

  try {
    const response = await fetch(`http://localhost:8082/api/quotes/${quoteId}`, {
      method: 'DELETE'
    })

    if (response.ok) {
      // On filtre la liste pour retirer le devis supprimé
      quotes.value = quotes.value.filter(q => q.quoteId !== quoteId)
    } else {
      alert("Erreur lors de la suppression du devis")
    }
  } catch (error) {
    console.error("Erreur de connexion :", error)
  }
}

// 4. LIFECYCLE HOOKS
// ngOnInit d'Angular
onMounted(() => {
  fetchQuotes()
  if (localStorage.getItem('theme') === 'dark') {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
})
</script>

<template>
  <div class="h-screen w-screen bg-slate-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 transition-colors duration-500 font-sans antialiased overflow-hidden">
    
    <div class="h-full w-full flex flex-col">
      
      <header class="w-full px-10 py-5 flex justify-between items-center shrink-0 border-b border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 transition-colors">
        <div>
          <h1 class="text-2xl font-black tracking-tighter uppercase">AssurTech<span class="text-blue-500">.</span></h1>
        </div>
        
        <button @click="toggleTheme" 
                class="px-4 py-2 rounded-xl bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 font-bold text-xs hover:ring-2 hover:ring-blue-500/50 transition-all cursor-pointer">
          {{ isDark ? '🌞 MODE CLAIR' : '🌙 MODE SOMBRE' }}
        </button>
      </header>

      <main class="flex-1 flex flex-col lg:flex-row min-h-0">
        
        <section class="w-full lg:w-[35%] p-10 flex flex-col border-r border-slate-200 dark:border-slate-800 bg-white/50 dark:bg-slate-900/50 transition-colors">
          <div class="max-w-xl mx-auto w-full">
            <h2 class="text-3xl font-bold mb-8">Nouveau Devis</h2>
            
            <form @submit.prevent="createQuote" class="space-y-8">
              <div class="space-y-2">
                <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Nom du Client</label>
                <input v-model="form.name" type="text"
                       class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl shadow-sm outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all" />
              </div>

              <div class="grid grid-cols-2 gap-6">
                <div class="space-y-2">
                  <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Âge</label>
                  <input v-model="form.age" type="number"  min="15" max="90"
                         class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all" />
                </div>
                <div class="space-y-2">
                  <label class="text-[10px] font-black uppercase tracking-widest text-slate-400">Sinistres</label>
                  <input v-model="form.claims" type="number" min="0"
                         class="w-full p-5 bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-2xl outline-none focus:ring-2 focus:ring-blue-500 dark:text-white transition-all" />
                </div>
              </div>
              <div class="space-y-2">
                <label class="text-[10px] font-bold uppercase tracking-widest text-slate-400 ml-1">Véhicule</label>
               <select v-model="form.car" 
                class="w-full p-4 bg-slate-50 dark:bg-slate-800 border-none rounded-2xl outline-none appearance-none cursor-pointer dark:text-white transition-all">
                <option value="" disabled selected>Choisir un véhicule</option>
                <option value="BERLINE" class="dark:bg-slate-800">Berline</option>
                <option value="SUV" class="dark:bg-slate-800">SUV</option>
                <option value="SPORT" class="dark:bg-slate-800">Sport</option>
              </select>
              </div>
<br>
              <button type="submit" 
                      class="w-full py-6 bg-blue-600 hover:bg-blue-500 text-white rounded-2xl font-black text-xl transition-all shadow-xl shadow-blue-500/20 active:scale-95 cursor-pointer disabled:cursor-not-allowed disabled:bg-blue-300 disabled:shadow-none">
                Calculer
              </button>
            </form>
          </div>
        </section>

        <section class="flex-1 flex flex-col min-w-0 bg-slate-50 dark:bg-slate-950 transition-colors">
          <div class="p-10 flex justify-between items-center">
           <div class="flex flex-row justify-around items-center gap-4">
            <h2 class="text-3xl font-bold">Historique</h2>
            <button @click="clearHistory" v-if="quotes.length > 0"
            class="text-[10px] font-bold text-red-500 hover:text-white hover:bg-red-500 border border-red-500/30 px-3 py-1 rounded-lg transition-all cursor-pointer">
      RÉINITIALISER
    </button></div>
            <span class="text-slate-400 dark:text-slate-500 font-mono text-sm tracking-tighter">{{ quotes.length }} SIMULATION{{ quotes.length > 1 ? 'S' : '' }}</span>
          </div>

          <div class="flex-1 overflow-y-auto px-10 pb-10 custom-scrollbar">
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
                <tr v-for="quote in quotes" :key="quote.quoteId" 
                    class="hover:bg-white dark:hover:bg-slate-900 transition-colors group">
                  <td class="py-6 font-bold text-lg dark:text-slate-200">
                    <div class="flex items-center gap-3">
                    <button @click="deleteOneQuote(quote.quoteId)" 
                    class="p-2 text-slate-300 hover:text-red-500 hover:bg-red-50 dark:hover:bg-red-500/10 rounded-xl transition-all opacity-70 group-hover:opacity-100 cursor-pointer hover:rotate-12 hover:scale-110 active:scale-90">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>{{ quote.customerName }}</div></td>
                  <td class="py-6 text-slate-500 dark:text-slate-400 font-medium">{{ quote.vehicleType }}</td>
                  <td class="py-6">
                    <span :class="['px-3 py-1 rounded-full text-[10px] font-black border transition-all', 
                      quote.riskLevel === 'HIGH' 
                        ? 'bg-red-500/10 text-red-500 border-red-500/20' 
                        : 'bg-emerald-500/10 text-emerald-500 border-emerald-500/20']">
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

      </main>
    </div>
  </div>
</template>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 6px;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
  /* On utilise du CSS standard pour éviter le bug de compilation */
  background-color: #e2e8f0; 
  border-radius: 9999px;
}
/* En mode sombre via une variable CSS si besoin */
:deep(.dark) .custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #1e293b;
}
</style>