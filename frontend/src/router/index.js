import { createWebHistory, createRouter } from 'vue-router'

import MainView from '../views/pages/MainView.vue'
import LoginView from '../views/account/LoginView.vue'
import RegisterView from '../views/account/RegisterView.vue'

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
        path: '/register',
        name: 'register',
        component: RegisterView,
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;