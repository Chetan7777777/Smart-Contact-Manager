/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/**/*.{html,js}"],
  theme: {
    extend: {
      inset: { '80px': '80px', '100px': '100px'}
    },
  },
  plugins: [],
  darkMode:"selector"
}

