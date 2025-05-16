import { createWebHistory, createRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

import MainView from '../views/pages/MainView.vue'
import LoginView from '../views/account/LoginView.vue'
import JoinView from '../views/account/JoinView.vue'
import MypageView from '../views/account/MypageView.vue'
import NovelView from '../views/pages/NovelView.vue'

import ServerErrorView from '../views/pages/ServerErrorView.vue'

import AdminLoginView from '../views/admin/AdminLoginView.vue'
import AdminMainView from '../views/admin/AdminMainView.vue'
import ManageBooksView from '../views/admin/ManageBooksView.vue'

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

    // Error 
    { 
        path: '/server-error', 
        component: ServerErrorView 
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




    // 관리자 페이지 
    {
        path: '/admin/login',
        name: 'admin-login',
        component: AdminLoginView,
        // meta: { requiresAuth: true }
    },
    {
        path: '/admin',
        name: 'admin-main',
        component: AdminMainView,
        meta: { requiresAdmin: true }
    },
    {
        path: '/admin/books',
        name: 'admin-books',
        component: ManageBooksView,
        meta: { requiresAdmin: true }
    },

]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()
    const isAuthenticated = !!authStore.token

    const userRole = authStore.role

    // 인증이 필요한 페이지 접근시 
    if (to.meta.requiresAuth && !isAuthenticated) {
        next({ name: 'login' })
    }



    // 관리자 권한 체크 
    if (to.meta.requiresAdmin) {
        // if (!isAuthenticated) {
        //     alert('로그인이 필요합니다.')
        //     return next({ name: 'login' })
        // }

        if (userRole !== 'ROLE_ADMIN') {
            // alert('관리자만 접근할 수 있습니다.')
            return next({ name: 'admin-login' })
        }
    }


    next()
})

export default router;