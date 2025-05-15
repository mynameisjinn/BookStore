<script setup>
import { useRouter, RouterLink } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '../../stores/auth'
import { useMenuStore } from '../../stores/menu'
import SimpleFooter from '../../components/SimpleFooter.vue'


const router = useRouter()

const email = ref('')
const password = ref('')

const login = async () => {
    const authStore = useAuthStore()
    const menuStore = useMenuStore()
        
    try {
        const res = await axios.post('/api/login', {
            email: email.value,
            password: password.value
        })

        // JWT 토큰과 유저 정보를 store에 저장
        authStore.setToken(res.data.token)
        authStore.setUser(res.data.user)
        authStore.setRole(res.data.role)
        
        // console.log(authStore.user)

        menuStore.resetMenu()
        menuStore.fetchMenu() 

        router.push('/admin')
    } catch (err) {
        console.error('로그인 실패', err)
        alert('아이디, 비밀번호를 확인해주세요')
    }
}

</script>

<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="flex flex-col max-w-md p-6 rounded-md sm:p-10 bg-gray-50 text-gray-800">
            <div class="mb-8 text-center">
                <h1 class="my-3 text-2xl font-bold">관리자 권한이 필요한 페이지입니다</h1>
                <RouterLink to="/" aria-label="Back to homepage" class="flex items-center p-2">
                    <img src="/logo.svg" alt="Logo" class="w-12 h-auto" /> 
                    <h1 class="my-3 text-4xl font-bold ml-5">ADMIN LOGIN</h1> 
                </RouterLink>
            </div>
            <form @submit.prevent="login" class="space-y-12">
                <div class="space-y-4">
                    <div>
                        <input type="email" id="email" v-model="email" placeholder="nickname@example.com"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                    <div>
                        <div class="flex justify-between mb-2">
                        </div>
                        <input type="password" id="password" v-model="password" placeholder="*****"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                </div>
                <div class="space-y-2">
                    <div>
                        <button type="submit" class="w-full px-8 py-3 font-semibold rounded-md bg-red-600 text-gray-50">
                            로그인
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <SimpleFooter />
</template>
