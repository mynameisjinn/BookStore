import { createWebHistory, createRouter } from 'vue-router'

import MainView from '../views/pages/MainView.vue'
import LoginView from '../views/account/LoginView.vue'
import JoinView from '../views/account/JoinView.vue'

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

]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;