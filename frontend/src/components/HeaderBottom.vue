<template>
    <div class="gap-4 mx-auto max-w-screen-lg bg-white border-t border-b border-gray-200 rounded-md shadow-sm">
        <!-- 탭 리스트 -->
        <div class="flex space-x-6 px-6 py-3 text-sm font-medium text-gray-600">
            <button v-for="(tab, index) in tabs" :key="tab.name" @click="activeTab = index" :class="[
                'transition-colors duration-200',
                activeTab === index
                    ? 'text-red-600 border-b-2 border-red-500 pb-2 font-semibold'
                    : 'text-gray-500 hover:text-red-400'
            ]">
                {{ tab.name }}
            </button>
        </div>

        <!-- 탭 콘텐츠 -->
        <transition name="fade">
            <div v-if="activeTab !== null && tabs[activeTab].items.length"
                class="bg-gray-50 px-6 py-4 border-t border-gray-200 rounded-b-md">
                <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                    <li v-for="item in tabs[activeTab].items" :key="item" class="hover:underline cursor-pointer">
                        {{ item }}
                    </li>
                </ul>
            </div>
        </transition>
    </div>
</template>
  
<script setup>
import { ref } from 'vue'

const tabs = [
    {
        name: '소설',
        items: ['한국소설', '영미소설', '추리/미스터리', 'SF', '로맨스']
    },
    {
        name: '자격증',
        items: ['정보처리기사', '전기기사', '한자능력검정', '한국사능력검정']
    },
    {
        name: '베스트',
        items: ['전체', '소설', '컴퓨터/IT']
    },
]

// 처음엔 아무 탭도 활성화되지 않음
const activeTab = ref(null)
</script>
  
<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
    transform: translateY(-5px);
}
</style>
