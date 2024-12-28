module.exports = {
  content: [
    '../resources/templates/**/*.{html,js}'
  ],
  theme: {
      extend: {
        fontFamily: {
          poppins: ['Poppins', 'sans-serif'],
        },
        colors: {
          'custom-gray': '#D9D9D9',
          'custom-blue': '#006EC4',
          'custom-yellow': '#FEB000',
          'custom-red': '#E03137',
        },
        keyframes: {
          fadeInUp: {
            '0%': { opacity: '0', transform: 'scale(0.8)' },
            '25%': { opacity: '0.25', transform: 'scale(0.9)' },
            '50%': { opacity: '0.5', transform: 'scale(1.05)' },
            '75%': { opacity: '0.75', transform: 'scale(1.1)' },
            '100%': { opacity: '1', transform: 'scale(1)' },
          },
          fadeOutDown: {
            '0%': { opacity: '1', transform: 'scale(1)' },
            '25%': { opacity: '0.75', transform: 'scale(1.1)' },
            '50%': { opacity: '0.5', transform: 'scale(1.05)' },
            '75%': { opacity: '0.25', transform: 'scale(0.9)' },
            '100%': { opacity: '0', transform: 'scale(0.8)' },
          },
        },
        animation: {
          fadeInUp: 'fadeInUp 60s ease-out',  
          fadeOutDown: 'fadeOutDown 60s ease-in', 
        }
  
      }
    },
    plugins: [],
    }
