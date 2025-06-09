<script setup>
import { useLikeStore } from "../stores/like.js";
import { computed, watch } from "vue";
import { useAuthStore2 } from "../stores/auth-with-refresh.js";

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
</script>

<template>
  <div>
    <h2>좋아요 한 메뉴</h2>
    <ul>
      <li v-for="menu in favoriteMenus" :key="menu.id">
        {{ menu.name }}
      </li>
    </ul>
  </div>
</template>
