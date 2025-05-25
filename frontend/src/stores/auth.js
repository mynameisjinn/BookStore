import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { isTokenExpired } from '../utils/jwt'
import { useMenuStore } from './menu'

export const useAuthStore = defineStore('auth', () => {
    // 상태 정의 (state)
    const token = ref(null)
    const user = ref(null)
    const role = ref(null)

    // 로그인 여부 확인 (getter)
    const isLoggedIn = computed(() => {
        return !!token.value && !isTokenExpired(token.value)
    })

    // 토큰 설정
    const setToken = (newToken) => {
        token.value = newToken
        localStorage.setItem('token', newToken)
    }

    // 유저 정보 설정
    const setUser = (newUser) => {
        user.value = newUser
        localStorage.setItem('user', JSON.stringify(newUser))
    }

    // 역할 설정
    const setRole = (newRole) => {
        role.value = newRole
        localStorage.setItem('role', newRole)
    }

    // 토큰 불러오기
    const loadToken = () => {
        const savedToken = localStorage.getItem('token')
        if (savedToken) token.value = savedToken
    }

    // 유저 정보 불러오기
    const loadUser = () => {
        const savedUser = localStorage.getItem('user')
        if (savedUser && savedUser !== 'undefined') {
            user.value = JSON.parse(savedUser)
        }
    }

    // 역할 불러오기
    const loadRole = () => {
        const savedRole = localStorage.getItem('role')
        if (savedRole && savedRole !== 'undefined') {
            role.value = savedRole
        }
        return role.value
    }

    // 로그아웃 처리
    const logout = () => {
        token.value = null
        user.value = null
        role.value = null
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        localStorage.removeItem('role')

        const menuStore = useMenuStore()
        menuStore.resetMenu()
        menuStore.fetchMenu()
    }

    return {
        token,
        user,
        role,
        isLoggedIn,
        setToken,
        setUser,
        setRole,
        loadToken,
        loadUser,
        loadRole,
        logout
    }
})

// import { defineStore } from 'pinia'
// import { isTokenExpired } from '../utils/jwt'
// import { useMenuStore } from './menu'
//
// export const useAuthStore = defineStore('auth', {
//     state: () => ({
//         token: null,
//         user: null,
//         role: null,
//     }),
//     getters: {
//         isLoggedIn: (state) => {
//             return !!state.token && !isTokenExpired(state.token)
//         }
//     },
//     actions: {
//         setToken(newToken) {
//             this.token = newToken
//             localStorage.setItem('token', newToken) // 새로고침 유지용
//         },
//         setUser(newUser) {
//             this.user = newUser
//             localStorage.setItem('user', JSON.stringify(newUser))
//         },
//         setRole(newRole) {
//             this.role = newRole
//             localStorage.setItem('role', newRole)
//         },
//         loadToken() {
//             const savedToken = localStorage.getItem('token')
//             if (savedToken) this.token = savedToken
//         },
//         loadUser() {
//             const savedUser = localStorage.getItem('user')
//             if (savedUser && savedUser !== 'undefined') {
//                 this.user = JSON.parse(savedUser)
//             }
//         },
//         loadRole() {
//             const savedRole = localStorage.getItem('role')
//             if (savedRole && savedRole !== 'undefined') {
//                 this.role = savedRole
//             }
//             return this.role
//         },
//         logout() {
//             this.token = null
//             this.user = null
//             this.role = null
//             localStorage.removeItem('token')
//             localStorage.removeItem('user')
//             localStorage.removeItem('role')
//
//             const menuStore = useMenuStore()
//             menuStore.resetMenu()
//             menuStore.fetchMenu()
//         }
//     }
// })
