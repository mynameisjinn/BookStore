<script setup>
import { ref, computed, onMounted } from 'vue'
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

// 메뉴 데이터를 가져오기
onMounted(() => {
    menuStore.fetchMenu()  // 메뉴 데이터를 fetchMenu로 불러옴
})

// 대분류, 중분류, 소분류에 따른 메뉴 처리
const mainMenu = computed(() => menuStore.mainMenu)
const subMenu = computed(() => menuStore.subMenu)
const smallMenu = computed(() => menuStore.smallMenu)

const currentActiveTab = computed(() => {
    return mainMenu.value.findIndex(menu => route.path.startsWith(menu.path))
})
</script>

<template>
    <div class="gap-4 mx-auto max-w-screen-lg bg-white border-t border-b border-gray-200 rounded-md shadow-sm"
        @mouseleave="resetActiveTab">
        <!-- 대분류 탭 리스트 -->
        <div class="flex space-x-6 px-6 py-3 text-sm font-medium text-gray-600">
            <div v-for="(tab, index) in mainMenu" :key="tab.menuId"
                @mouseover="setActiveTab(index)"
                class="cursor-pointer transition-colors duration-200"
                :class="[
                    activeTab === index || currentActiveTab === index
                        ? 'text-red-600 border-b-2 border-red-500 pb-2 font-semibold'
                        : 'text-gray-500 hover:text-red-400'
                ]">
                {{ tab.name }}
            </div>
        </div>

        <!-- 콘텐츠 -->
        <transition name="fade">
            <div v-if="activeTab !== null" class="bg-gray-50 px-6 py-4 border-t border-gray-200 rounded-b-md">
                <!-- 탭제목 + 전체보기 링크 -->
                <div class="flex justify-between items-center mb-4">
                    <h2 class="text-lg font-bold text-gray-800">
                        {{ mainMenu[activeTab].name }}
                    </h2>
                    <router-link :to="mainMenu[activeTab].path" class="text-sm text-red-500 hover:underline"
                        @click="resetActiveTab">
                        전체보기 →
                    </router-link>
                </div>

                <!-- 중분류 (하위 카테고리) -->
                <template v-if="subMenu.some(menu => menu.parentId === mainMenu[activeTab].menuId)">
                    <div v-for="category in subMenu.filter(menu => menu.parentId === mainMenu[activeTab].menuId)"
                        :key="category.menuId" class="mb-5">
                        <h3 class="text-gray-800 font-semibold mb-2">
                            {{ category.name }}
                        </h3>
                        <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                            <li v-for="item in smallMenu.filter(menu => menu.parentId === category.menuId)"
                                :key="item.menuId" class="hover:underline cursor-pointer">
                                {{ item.name }}
                            </li>
                        </ul>
                    </div>
                </template>

                <!-- 소분류 (하위 항목) -->
                <template v-else>
                    <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                        <li v-for="item in smallMenu.filter(menu => menu.parentId === mainMenu[activeTab].menuId)"
                            :key="item.menuId" class="hover:underline cursor-pointer">
                            {{ item.name }}
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
