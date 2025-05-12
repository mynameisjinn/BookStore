import { createApp } from 'vue'
import { createPinia } from 'pinia'
// import './style.css'
import './tailwind.css'
import App from './App.vue'
import router from './router'
import { useAuthStore } from './stores/auth.js'
import { isTokenExpired } from './utils/jwt.js'

const app = createApp(App);

const pinia = createPinia();


app.use(router);
app.use(pinia);

// pina 사용 , localStorage 저장 (새로고침시 리셋방지)
const authStore = useAuthStore()
authStore.loadToken()
authStore.loadUser()

if (isTokenExpired(authStore.token)) {
    console.log("JWT 만료됨, 로그아웃 처리")
    authStore.logout()
    // router.push('/login') 
}

app.mount('#app');
