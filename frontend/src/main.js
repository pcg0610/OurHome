import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

/**
 * Vuetify
 * npm i vuetify
 * https://vuetifyjs.com/en/getting-started/installation/#existing-projects
 */
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

/**
 * Material Design Icons
 * npm install @mdi/font -D
 * https://vuetifyjs.com/en/features/icon-fonts/#material-design-icons
 */
import '@mdi/font/css/materialdesignicons.css'
import { aliases, mdi } from 'vuetify/iconsets/mdi'

const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
          mdi,
        },
    },
})

/**
 * Axios
 * npm install axios
 * https://axios-http.com/docs/intro
 */

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(vuetify)

app.mount('#app')
