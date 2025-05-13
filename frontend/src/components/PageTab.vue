<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{
    title: string
    tabs: {
        label: string
        content: any
    }[]
    activeTab?: number  // 외부에서 전달받는 선택된 탭 인덱스 (선택사항)
}>()

const emit = defineEmits(['update:activeTab'])

const openTab = ref(props.activeTab ?? 0)

// 외부에서 activeTab이 바뀌면 내부도 업데이트
watch(() => props.activeTab, (newVal) => {
    if (typeof newVal === 'number') {
        openTab.value = newVal
    }
})


// 탭 클릭 시 내부 상태 + 부모로 이벤트 전달
const setTab = (index: number) => {
    openTab.value = index
    emit('update:activeTab', index)
}

const activeClasses = 'bg-red-600 text-white rounded-t px-4 py-2 font-semibold transition-colors'
const inactiveClasses = 'bg-white text-red-600 border rounded-t px-4 py-2 font-semibold hover:bg-red-50 transition-colors'
</script>

<template>
    <div class="my-10 mx-auto max-w-screen-lg">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">{{ title }}</h2>

        <ul class="flex border-b">
            <li v-for="(tab, index) in tabs" :key="index" :class="{ '-mb-px': openTab === index }" class="mr-1">
                <button @click="setTab(index)" :class="openTab === index ? activeClasses : inactiveClasses">
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
