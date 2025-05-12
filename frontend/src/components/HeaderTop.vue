<!-- src/components/HeaderTop.vue -->
<script setup>
import { RouterLink } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { computed } from 'vue'

const authStore = useAuthStore()
const user = computed(() => authStore.user)

const username = computed(() => {
    const email = user.value?.email
    return email ? email.split('@')[0] : ''
})

const logout = () => {
    authStore.logout()
}
</script>

<template>
    <div class="bg-red-600 text-white text-sm">
        <div class="max-w-screen-xl mx-auto px-4 py-2 flex justify-end items-center gap-4">
            <p v-if="user" class="hidden sm:block">안녕하세요, <span class="font-semibold">{{ username }}</span>님</p>
            <button v-if="user" type="submit" title="Mypage" class="p-1 focus:outline-none focus:ring">
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
