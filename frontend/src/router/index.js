import { createWebHistory, createRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

import MainView from '../views/pages/MainView.vue'
import LoginView from '../views/account/LoginView.vue'
import JoinView from '../views/account/JoinView.vue'
import MypageView from '../views/account/MypageView.vue'
import NovelView from '../views/pages/NovelView.vue'

const routes = [
    {
        path: '/',
        name: 'main',
        component: MainView,
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView,
    },
    {
        path: '/join',
        name: 'join',
        component: JoinView,
    },

    // Mypage

    {
        path: '/mypage',
        name: 'mypage',
        component: MypageView,
        meta: { requiresAuth: true }, // 인증 필요 플래그
    },

    // 소설
    {
        path: '/category/novel',
        name: 'novel',
        component: NovelView,
    },

]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()
    const isAuthenticated = !!authStore.token

    if (to.meta.requiresAuth && !isAuthenticated) {
        // 인증이 필요한데 로그인 안 됨 → 로그인 페이지로
        next({ name: 'login' })
    } else {
        next()
    }
})

export default router;