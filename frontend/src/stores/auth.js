// stores/auth.js
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: null,
        user: null, // 로그인한 유저 정보
    }),
    actions: {
        setToken(newToken) {
            this.token = newToken
            localStorage.setItem('token', newToken) // 새로고침 유지용
        },
        setUser(newUser) {
            this.user = newUser
            localStorage.setItem('user', JSON.stringify(newUser)) // 유저 정보도 저장
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
        logout() {
            this.token = null
            this.user = null
            localStorage.removeItem('token')
            localStorage.removeItem('user')
        }
    }
})
