<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { computed } from 'vue'
import axios from 'axios'
import { useToast } from 'vue-toastification'
import {useAuthStore2} from "../stores/auth-with-refresh.js";

const toast = useToast();

const router = useRouter()

// const authStore = useAuthStore()
const authStore = useAuthStore2()
const user = computed(() => authStore.user)

const username = computed(() => {
    const email = user.value?.email
    return email ? email.split('@')[0] : ''
})

const logout = () => {
    authStore.logout()
    router.push('/')
    toast.warning("로그아웃 되었습니다");
}

const mypage = async () => {
    // const authStore = useAuthStore()
    const authStore = useAuthStore2()
    // authStore.loadToken()

    try {
        const res = await axios.get('/api/mypage', {
            // headers: {
            //     Authorization: `Bearer ${authStore.token}`
            // }
          withCredentials: true
        })

        // 서버에서 받은 사용자 정보 출력
        console.log(res.data)
        alert(`${res.data.username}님, 마이페이지에 오신 것을 환영합니다!`)

        router.push('/mypage')

    } catch (error) {
        if (error.response?.status === 401) {
            // console.warn('인증 오류')
            router.push('/login')
        } else {
            // console.error('서버오류', error)
            alert('사용자만 접근가능한 메뉴\n사용자임에도 접근불가시 관리자 문의')
        }
    }
}


</script>

<template>
    <div class="bg-red-600 text-white text-sm">
        <div class="max-w-screen-xl mx-auto px-4 py-2 flex justify-end items-center gap-4">
            <p v-if="user" class="hidden sm:block">안녕하세요, <span class="font-semibold">{{ username }}</span>님</p>
            <button @click="mypage" type="button" title="mypage" class="p-1 focus:outline-none focus:ring">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                    stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                        d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                </svg>
            </button>
            <RouterLink v-if="!user" to="/join" class="hover:underline hover:opacity-90 transition duration-150">
                회원가입
            </RouterLink>

            <RouterLink v-if="!user" to="/login"
                class="bg-white text-red-600 font-semibold px-3 py-1 rounded hover:bg-gray-100 transition duration-150">
                로그인
            </RouterLink>

            <button v-if="user" @click="logout"
                class="bg-white text-red-600 font-semibold px-3 py-1 rounded hover:bg-gray-100 transition duration-150">
                로그아웃
            </button>
        </div>
    </div>
</template>
