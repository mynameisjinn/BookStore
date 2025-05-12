<script setup>
import { RouterLink } from 'vue-router';
import { ref, computed } from 'vue'
import MenuSlider from './MenuSlider.vue'
import { useAuthStore } from '../stores/auth';

const authStore = useAuthStore()
const user = computed(() => authStore.user)

const showMenu = ref(false)


</script>

<template>
    <header class="bg-white text-gray-800 px-4 py-3 gap-4 mx-auto max-w-screen-lg">
        <div class="max-w-screen-xl mx-auto flex items-center gap-4">
            <!-- 로고와 검색창을 같은 줄에 두고 간격 좁힘 -->
            <div class="flex items-center gap-4 flex-1">
                <!-- 로고: 오른쪽으로 약간 이동 -->
                <RouterLink to="/" aria-label="Back to homepage" class="flex items-center mr-4">
                    <img src="/logo.svg" alt="Logo" class="w-20 h-auto" />
                </RouterLink>

                <!-- 검색창: 로고 옆에 붙게 -->
                <div class="relative w-full max-w-xl">
                    <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                        <button type="submit" title="Search" class="p-1 focus:outline-none focus:ring">
                            <img src="/images/search.svg" />
                        </button>
                    </span>
                    <input type="search" name="Search" placeholder="검색어를 입력하세요"
                        class="w-full py-3 pl-10 pr-4 text-base rounded-full border border-red-500 bg-gray-50 focus:outline-none focus:ring-2 focus:ring-red-300" />
                </div>
            </div>

            <!-- 메뉴 버튼 -->
            <button @click="showMenu = !showMenu" title="Open menu" type="button" class="p-2 lg:hidden">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                    class="w-6 h-6 text-gray-800">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                </svg>
            </button>
        </div>
    </header>

    <MenuSlider v-if="showMenu" />
</template>
  