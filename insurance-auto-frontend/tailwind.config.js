/** @type {import('tailwindcss').Config} */
export default {
  // On indique à Tailwind où chercher les classes CSS
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],

  darkMode: 'class', 
  theme: {
    extend: {},
  },
  plugins: [],
}