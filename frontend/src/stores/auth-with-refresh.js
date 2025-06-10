import axios from "axios";
import {useMenuStore} from "./menu.js";
import {defineStore} from "pinia";
import {ref, computed} from "vue";
import {useLikeStore} from "./like.js";

export const useAuthStore2 = defineStore('auth', () => {
    const user = ref(null)
    const role = ref(null)

    // 로그인 여부 확인
    const isLoggedIn = computed(() => {
        return !!user.value
    })

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
    const logout = async () => {
        user.value = null
        role.value = null
        localStorage.removeItem('user')
        localStorage.removeItem('role')

        const menuStore = useMenuStore()
        menuStore.resetMenu()
        menuStore.fetchMenu()

        const likeStore = useLikeStore()
        likeStore.resetFavorite()

        // 서버에도 로그아웃 요청(Optional)
        try {
            await axios.post('/api/logout', {}, { withCredentials: true })
        } catch (e) {
            console.error('로그아웃 요청 실패', e)
        }
    }

    return {
        user,
        role,
        isLoggedIn,
        setUser,
        setRole,
        loadUser,
        loadRole,
        logout
    }
})
