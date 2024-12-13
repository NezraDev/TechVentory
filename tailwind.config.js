/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.{html,js}', 
    './src/main/resources/static/**/*.html',         
    './src/**/*.{js,ts,jsx,tsx}'   
  ],
  theme: {
    extend: {
      fontFamily: {
        poppins: ['Poppins', 'sans-serif'],
      },
      colors: {
        'custom-gray': '#D9D9D9',
        'custom-blue': '#006EC4',
        
      }
    },
  },
  plugins: [],
}
