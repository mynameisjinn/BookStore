<script setup>
import { useLikeStore } from "../stores/like.js";
import { computed, watch } from "vue";
import { useAuthStore2 } from "../stores/auth-with-refresh.js";
import FavoriteMenuButton from "./buttons/FavoriteMenuButton.vue";

const likeStore = useLikeStore();
const authStore = useAuthStore2();

const user = computed(() => authStore.user);

// 유저 정보가 바뀌면 즐겨찾기 목록 가져오기
watch(user, (newUser) => {
  if (newUser?.id) {
    likeStore.fetchFavoriteMenus(newUser.id);
  }
}, { immediate: true });

// 즐겨찾기 메뉴 리스트 접근
const favoriteMenus = computed(() => likeStore.favoriteMenuList);

// mainMenu 기준으로 메뉴를 그룹화
const groupedMenus = computed(() => {
  const groups = {};
  favoriteMenus.value.forEach(menu => {
    const key = menu.mainMenu || "기타";
    // const key = menu.mainMenu?.trim() || "기타";
    if (!groups[key]) groups[key] = [];
    groups[key].push(menu);
  });
  return Object.entries(groups);
});
</script>

<template>
  <div class="w-full max-w-screen-lg mx-auto px-4 mt-3 mb-4">
    <h2 class="text-2xl font-bold mb-6">좋아요 한 메뉴 List</h2>

    <div
        v-for="[mainMenu, menus] in groupedMenus"
        :key="mainMenu"
        class="mb-8"
    >
      <h3 class="text-lg font-semibold text-gray-700 mb-3">
        분야: {{ mainMenu }}
      </h3>
      <ul class="space-y-2">
        <li
            v-for="menu in menus"
            :key="menu.id"
            class="flex items-center justify-between bg-white shadow-sm p-3 rounded-lg border"
        >
          <router-link
              :to="menu.path"
              class="cursor-pointer"
          >
            {{ menu.name }}
          </router-link>
<!--          <span class="text-gray-800"></span>-->
          <FavoriteMenuButton :menuId="menu.id" />
        </li>
      </ul>
    </div>
  </div>
</template>
