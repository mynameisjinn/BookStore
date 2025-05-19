import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import './style.css'
import './tailwind.css'
import App from './App.vue'
import router from './router'
import { useAuthStore } from './stores/auth.js'
import { useMenuStore } from './stores/menu.js'
import { isTokenExpired } from './utils/jwt.js'

import './plugins/axios-error.js'
import Toast from 'vue-toastification'
import 'vue-toastification/dist/index.css'


const app = createApp(App);

const pinia = createPinia();


app.use(router);
app.use(pinia);

app.use(Toast, {
    position: 'top-right',
    timeout: 3000,
})


// pinia 사용 , localStorage 저장 (새로고침시 리셋방지)
const authStore = useAuthStore()
authStore.loadToken()
authStore.loadUser()
authStore.loadRole()

if (isTokenExpired(authStore.token)) {
    console.log("JWT 만료됨, 로그아웃 처리")
    authStore.logout()
    router.push('/login')
}

const menuStore = useMenuStore(pinia)
await menuStore.fetchMenu() // 앱 시작 시 메뉴 호출

app.mount('#app');
