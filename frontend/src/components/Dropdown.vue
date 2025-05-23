<template>
    <div class="mx-auto flex h-screen w-full items-center justify-center bg-gray-200 py-20">
        <div class="relative py-2">
            <div class="flex cursor-pointer items-center justify-between space-x-5 bg-white px-4" @click="toggleDropdown">
                <span class="menu-hover my-2 py-2 text-base font-medium text-black lg:mx-4">
                    {{ selectedDay || 'Choose Day' }}
                </span>
                <span>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="h-6 w-6">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 8.25l-7.5 7.5-7.5-7.5" />
                    </svg>
                </span>
            </div>

            <div v-show="isOpen" class="absolute z-50 mt-1 w-full flex-col bg-gray-100 py-1 px-4 text-gray-800 shadow-xl">
                <a v-for="(day, index) in days" :key="index"
                    class="my-2 block border-b border-gray-100 py-1 font-semibold text-gray-500 hover:text-black md:mx-2"
                    @click="selectDay(day)">
                    {{ day }}
                </a>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref } from 'vue'

const isOpen = ref(false)
const selectedDay = ref(null)
const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']

const toggleDropdown = () => {
    isOpen.value = !isOpen.value
}

const selectDay = (day) => {
    selectedDay.value = day
    isOpen.value = false
}
</script>
  