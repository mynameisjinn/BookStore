<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
    title: string
    tabs: {
        label: string
        content: any  // 컴포넌트 자체
    }[]
}>()

const openTab = ref(0)

const activeClasses = 'bg-red-600 text-white rounded-t px-4 py-2 font-semibold transition-colors'
const inactiveClasses = 'bg-white text-red-600 border rounded-t px-4 py-2 font-semibold hover:bg-red-50 transition-colors'
</script>

<template>
    <div class="my-10 mx-auto max-w-screen-lg">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">{{ title }}</h2>

        <ul class="flex border-b">
            <li v-for="(tab, index) in tabs" :key="index" :class="{ '-mb-px': openTab === index }" class="mr-1">
                <button @click="openTab = index" :class="openTab === index ? activeClasses : inactiveClasses">
                    {{ tab.label }}
                </button>
            </li>
        </ul>

        <div class="w-full mt-4">
            <div v-for="(tab, index) in tabs" :key="index" v-show="openTab === index">
                <component :is="tab.content" />
            </div>
        </div>
    </div>
</template>
