<template>
    <div class="gap-4 mx-auto max-w-screen-lg bg-white border-t border-b border-gray-200 rounded-md shadow-sm">
        <!-- 탭 리스트 -->
        <div class="flex space-x-6 px-6 py-3 text-sm font-medium text-gray-600">
            <button v-for="(tab, index) in tabs" :key="tab.name" @click="setActiveTab(index)" :class="[
                'transition-colors duration-200',
                activeTab === index
                    ? 'text-red-600 border-b-2 border-red-500 pb-2 font-semibold'
                    : 'text-gray-500 hover:text-red-400'
            ]">
                {{ tab.name }}
            </button>
        </div>

        <!-- 콘텐츠 -->
        <transition name="fade">
            <div v-if="activeTab !== null" class="bg-gray-50 px-6 py-4 border-t border-gray-200 rounded-b-md">
                <!-- 탭제목 + 전체보기 링크 -->
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-lg font-bold text-gray-800">
                        {{ tabs[activeTab].name }}
                    </h2>
                    <router-link :to="tabs[activeTab].link" class="text-sm text-red-500 hover:underline"
                        @click="resetActiveTab">
                        전체보기 →
                    </router-link>
                </div>

                <!-- 하위 카테고리 존재 시 -->
                <template v-if="tabs[activeTab].subCategories">
                    <div v-for="category in tabs[activeTab].subCategories" :key="category.title" class="mb-5">
                        <h3 class="text-gray-800 font-semibold mb-2">
                            {{ category.title }}
                        </h3>
                        <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                            <li v-for="item in category.items" :key="item" class="hover:underline cursor-pointer">
                                {{ item }}
                            </li>
                        </ul>
                    </div>
                </template>

                <!-- 일반 아이템만 있을 경우 -->
                <template v-else>
                    <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                        <li v-for="item in tabs[activeTab].items" :key="item" class="hover:underline cursor-pointer">
                            {{ item }}
                        </li>
                    </ul>
                </template>
            </div>
        </transition>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const tabs = [
    {
        name: '베스트',
        link: '/category/best',
        items: ['전체', '소설', '컴퓨터/IT']
    },
    {
        name: '소설',
        link: '/category/novel',
        subCategories: [
            {
                title: '장르별',
                items: ['로맨스', 'SF', '추리/미스터리']
            },
            {
                title: '국가별',
                items: ['한국소설', '영미소설']
            }
        ]
    },
    {
        name: '자격증',
        link: '/category/certificate',
        items: ['정보처리기사', '전기기사', '한자능력검정', '한국사능력검정']
    }
]

const activeTab = ref(null)

// 탭 클릭 시 활성화된 탭 설정
const setActiveTab = (index) => {
    activeTab.value = index
}

// 링크 클릭 시 activeTab 초기화
const resetActiveTab = () => {
    activeTab.value = null
}
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
