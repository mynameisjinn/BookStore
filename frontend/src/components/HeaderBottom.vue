<script setup>
import { ref, computed } from 'vue'
import { useMenuStore } from '../stores/menu'
import { useRoute } from 'vue-router'

const route = useRoute()
const activeTab = ref(null)

const setActiveTab = (index) => {
    activeTab.value = index
}
const resetActiveTab = () => {
    activeTab.value = null
}

const menuStore = useMenuStore()
// main.js에서 fetchMenu를 호출했기 때문에 여기선 다시 호출 안 함
const structuredMenu = computed(() => menuStore.structuredMenu)
</script>

<template>
    <div class="gap-4 mx-auto max-w-screen-lg bg-white border-t border-b border-gray-200 rounded-md shadow-sm">
        <!-- 탭 리스트 -->
        <div class="flex space-x-6 px-6 py-3 text-sm font-medium text-gray-600">
            <button v-for="(tab, index) in structuredMenu" :key="tab.name" @click="setActiveTab(index)" :class="[
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
                        {{ structuredMenu[activeTab].name }}
                    </h2>
                    <router-link :to="structuredMenu[activeTab].link" class="text-sm text-red-500 hover:underline"
                        @click="resetActiveTab">
                        전체보기 →
                    </router-link>
                </div>

                <!-- 하위 카테고리 존재 시 -->
                <template v-if="structuredMenu[activeTab].subCategories">
                    <div v-for="category in structuredMenu[activeTab].subCategories" :key="category.title" class="mb-5">
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
                        <li v-for="item in structuredMenu[activeTab].items" :key="item"
                            class="hover:underline cursor-pointer">
                            {{ item }}
                        </li>
                    </ul>
                </template>
            </div>
        </transition>
    </div>
</template>

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
