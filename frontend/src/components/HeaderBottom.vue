<script setup>
import {ref, computed, onMounted, watch} from 'vue'
import { useMenuStore } from '../stores/menu'
import { useRoute } from 'vue-router'
import FavoriteMenuButton from "./buttons/FavoriteMenuButton.vue";
import LikeButton from "./LikeButton.vue";
import {useLikeStore} from "../stores/like.js";
import {useAuthStore2} from "../stores/auth-with-refresh.js";

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

const likeStore = useLikeStore()
const authStore = useAuthStore2()

const user = computed(() => authStore.user)

watch(user, (newUser) => {
  if (newUser?.id) {
    likeStore.fetchFavoriteMenus(newUser.id)
  }
}, { immediate: true })

</script>

<template>
    <div class="gap-4 mx-auto max-w-screen-lg bg-white border-t border-b border-gray-200 rounded-md shadow-sm"
        @mouseleave="resetActiveTab">
      <!-- 대분류 탭 리스트 -->
        <div class="flex space-x-6 px-6 py-3 text-sm font-medium text-gray-600">
            <div v-for="(tab, index) in mainMenu" :key="tab.id"
                @mouseover="setActiveTab(index)"
                class="cursor-pointer transition-colors duration-200"
                :class="[
                    activeTab === index || currentActiveTab === index
                        ? 'text-red-600 border-b-2 border-red-500 pb-2 font-semibold'
                        : 'text-gray-500 hover:text-red-400'
                ]">
                {{ tab.name }}
            </div>
            <div
                v-if="user"
                class="cursor-pointer transition-colors duration-200"
            >
              <router-link
                  to="/mymenus"
                  class="transition-colors duration-200"
                  :class="[
                  route.path === '/mymenus'
                    ? 'text-red-600 border-b-2 border-red-500 pb-2 font-semibold'
                    : 'text-gray-500 hover:text-red-400'
                ]"
              >
                My Menus
              </router-link>
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
                <!-- 중분류 + 소분류 (하위 카테고리) -->
              <template v-if="subMenu.some(menu => menu.parentId === mainMenu[activeTab].id)">
                <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-6">
                  <div
                      v-for="(category, index) in subMenu.filter(menu => menu.parentId === mainMenu[activeTab].id)"
                      :key="category.id"
                      class="flex flex-col pl-4"
                      :class="index !== 0 ? 'border-l border-gray-300' : ''"
                  >
                  <h3 class="text-gray-800 font-semibold mb-2 text-center">
                      {{ category.name }}
                    </h3>
                    <ul class="flex flex-col gap-2 text-gray-700 text-sm">
                      <li
                          v-for="item in smallMenu.filter(menu => menu.parentId === category.id)"
                          :key="item.id"
                          class="flex items-center justify-between hover:underline cursor-pointer"
                      >
                        <router-link
                            :to="item.path"
                            @click="resetActiveTab"
                            class="flex-1"
                        >
                          {{ item.name }}
                        </router-link>
                        <FavoriteMenuButton
                          :menuId="item.id"
                        />
                      </li>
                    </ul>
                  </div>
                </div>
              </template>

              <!-- 소분류만 (중분류가 없는 경우) -->
              <template v-else>
                <ul class="grid grid-cols-2 sm:grid-cols-3 gap-3 text-gray-700 text-sm">
                  <!--                        <li v-for="item in smallMenu.filter(menu => menu.parentId === mainMenu[activeTab].id)"-->
                  <!--                            :key="item.id" class="hover:underline cursor-pointer">-->
                  <!--                            {{ item.name }}-->
                  <!--                        </li>-->
                  <router-link
                      v-for="item in smallMenu.filter(menu => menu.parentId === mainMenu[activeTab].id)"
                      :key="item.id"
                      :to="item.path"
                      class="hover:underline cursor-pointer"
                      @click="resetActiveTab"
                  >
                    {{ item.name }}
                  </router-link>
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
