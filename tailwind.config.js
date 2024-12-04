/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.{html,js}', 
    './src/main/resources/static/**/*.html',         
    './src/**/*.{js,ts,jsx,tsx}'   
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

