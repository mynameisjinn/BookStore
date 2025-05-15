import { defineStore } from 'pinia'
import { isTokenExpired } from '../utils/jwt'
import { useMenuStore } from './menu'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: null,
        user: null,
        role: null,
    }),
    getters: {
        isLoggedIn: (state) => {
            return !!state.token && !isTokenExpired(state.token)
        }
    },
    actions: {
        setToken(newToken) {
            this.token = newToken
            localStorage.setItem('token', newToken) // 새로고침 유지용
        },
        setUser(newUser) {
            this.user = newUser
            localStorage.setItem('user', JSON.stringify(newUser))
        },
        setRole(newRole) {
            this.role = newRole
            localStorage.setItem('role', newRole)
        },
        loadToken() {
            const savedToken = localStorage.getItem('token')
            if (savedToken) this.token = savedToken
        },
        loadUser() {
            const savedUser = localStorage.getItem('user')
            if (savedUser && savedUser !== 'undefined') {
                this.user = JSON.parse(savedUser)
            }
        },
        loadRole() {
            const savedRole = localStorage.getItem('role')
            if (savedRole && savedRole !== 'undefined') {
                this.role = savedRole
            }
            return this.role
        },
        logout() {
            this.token = null
            this.user = null
            this.role = null
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            localStorage.removeItem('role')

            const menuStore = useMenuStore()
            menuStore.resetMenu()
            menuStore.fetchMenu() 
        }
    }
})
