<script setup>
import { useRouter, RouterLink } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '../../stores/auth'
import { useMenuStore } from '../../stores/menu'
import SimpleFooter from '../../components/SimpleFooter.vue'
import { useToast } from 'vue-toastification'
import {useAuthStore2} from "../../stores/auth-with-refresh.js";

const toast = useToast();

const router = useRouter()

const email = ref('')
const password = ref('')

// const login = async () => {
//     const authStore = useAuthStore()
//     const menuStore = useMenuStore()
//
//     try {
//         const res = await axios.post('/api/login', {
//             email: email.value,
//             password: password.value
//         })
//
//         // JWT 토큰과 유저 정보를 store에 저장
//         authStore.setToken(res.data.token)
//         authStore.setUser(res.data.user)
//         authStore.setRole(res.data.role)
//
//         // console.log(authStore.user)
//
//         menuStore.resetMenu()
//         menuStore.fetchMenu()
//
//         if(res.data.role === 'ROLE_ADMIN'){
//             router.push('/admin')
//         } else {
//             router.push('/')
//         }
//
//         toast.success('로그인 성공!')
//     } catch (err) {
//         // console.error('로그인 실패', err)
//         // alert('아이디, 비밀번호를 확인해주세요')
//         toast.error('아이디, 비밀번호를 확인해주세요')
//         password.value = ''
//     }
// }

const login = async () => {
  const authStore = useAuthStore2()
  const menuStore = useMenuStore()

  try {
    const res = await axios.post('/api/login', {
      email: email.value,
      password: password.value
    }, {
      withCredentials: true // ✅ 쿠키 사용을 위해 반드시 필요
    })

    // JWT는 쿠키에 저장되므로 setToken은 제거
    authStore.setUser(res.data.user)
    authStore.setRole(res.data.role)

    menuStore.resetMenu()
    menuStore.fetchMenu()

    if (res.data.role === 'ROLE_ADMIN') {
      router.push('/admin')
    } else {
      router.push('/')
    }

    toast.success('로그인 성공!')
  } catch (err) {
    toast.error('아이디, 비밀번호를 확인해주세요')
    password.value = ''
  }
}


</script>

<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="flex flex-col max-w-md p-6 rounded-md sm:p-10 bg-gray-50 text-gray-800">
            <div class="mb-8 text-center">
                <RouterLink to="/" aria-label="Back to homepage" class="flex items-center p-2">
                    <img src="/logo.svg" alt="Logo" class="w-12 h-auto" /> 
                    <h1 class="my-3 text-4xl font-bold ml-5">LOGIN</h1> 
                </RouterLink>
                <!-- <p class="text-sm text-gray-600">Sign in to access your account</p> -->
            </div>
            <form @submit.prevent="login" class="space-y-12">
                <div class="space-y-4">
                    <div>
                        <!-- <label for="email" class="block mb-2 text-sm">Email address</label> -->
                        <input type="email" id="email" v-model="email" placeholder="nickname@example.com"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                    <div>
                        <div class="flex justify-between mb-2">
                            <!-- <label for="password" class="text-sm">Password</label> -->
                            <!-- <a rel="noopener noreferrer" href="#" class="text-xs hover:underline text-gray-600">Forgot password?</a> -->
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
                    <p class="px-6 text-sm text-center text-gray-600">가입후 다양한 서비스를 이용해보세요
                        <RouterLink to="/join" class="hover:underline text-red-600">회원가입</RouterLink>
                    </p>
                </div>
            </form>
        </div>
    </div>
    <SimpleFooter />
</template>
