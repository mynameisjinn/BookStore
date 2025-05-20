import {createRouter, createWebHistory} from 'vue-router'
import {useAuthStore} from '../stores/auth'

import MainView from '../views/pages/MainView.vue'
import LoginView from '../views/account/LoginView.vue'
import JoinView from '../views/account/JoinView.vue'
import MypageView from '../views/account/MypageView.vue'
import NovelView from '../views/pages/NovelView.vue'

import ServerErrorView from '../views/error/ServerErrorView.vue'
import NotFoundView from '../views/error/NotFoundView.vue'

import AdminLoginView from '../views/admin/AdminLoginView.vue'
import AdminMainView from '../views/admin/AdminMainView.vue'
import axios from "axios";
import BookRegisterView from "../views/admin/BookRegisterView.vue";

import AdminPageLayout from "../layouts/AdminPageLayout.vue";
import BookListView from "../views/admin/BookListView.vue";

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
        meta: { hideLayout: true }
    },
    {
        path: '/join',
        name: 'join',
        component: JoinView,
        meta: { hideLayout: true }
    },


    // Mypage

    {
        path: '/mypage',
        name: 'mypage',
        component: MypageView,
        meta: { requiresAuth: true,  requiresUser: true }, // 인증 필요 플래그
    },

    // 소설
    {
        path: '/category/novel/:catchAll(.*)*',
        name: 'novel',
        component: NovelView,
    },




    // 관리자 페이지 
    {
        path: '/login-admin',
        name: 'admin-login',
        component: AdminLoginView,
        // meta: { requiresAuth: true }
        meta: { hideLayout: true }
    },

    {
        path: '/admin',
        component: AdminPageLayout,
        meta: { requiresAdmin: true },
        children: [
            {
                path: '',
                name: 'admin-main',
                component: AdminMainView,
            },
            {
                path: 'book/register',
                name: 'admin-book-register',
                component: BookRegisterView
            },
            {
                path: 'book/list',
                name: 'admin-book-list',
                component: BookListView
            }
        ]
    },

    // Error 
    {
        path: '/server-error',
        component: ServerErrorView,
        meta: { hideLayout: true },
        beforeEnter: async (to, from, next) => {
            try {
                await axios.get('/api/test');
                // 서버 정상 -> 홈으로 리디렉션
                next('/');
            } catch (e) {
                // 서버 여전히 문제 -> 페이지 표시
                next();
            }
        }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFoundView',
        component: NotFoundView,
        meta: { hideLayout: true }
    }

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
        if (userRole !== 'ROLE_ADMIN') {
            // alert('관리자만 접근할 수 있습니다.')
            return next({ name: 'admin-login' })
        }
    }

    // 사용자자 권한 체크 
    if (to.meta.requiresUser) {
        if (userRole !== 'ROLE_USER') {
            alert('사용자 전용 페이지')
            return next({ name: 'admin-main' })
        }
    }


    next()
})

export default router;