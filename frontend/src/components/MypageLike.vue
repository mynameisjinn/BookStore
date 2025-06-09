<script setup>
import {useLikeStore} from "../stores/like.js";
import {useAuthStore2} from "../stores/auth-with-refresh.js";
import {computed, watch} from "vue";
import BookCard from "./BookCard.vue";

const likeStore = useLikeStore()
const authStore = useAuthStore2()
const user = computed(() => authStore.user)

watch(user, (newUser) => {
  if (newUser?.id) {
    likeStore.fetchLikedBooks(newUser.id)
  }
}, { immediate: true })
</script>

<template>
  <div class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
<!--    <BookCard :books="likeStore.likedBookList" />-->
    <BookCard
        v-if="likeStore.likedBookList.length > 0"
        :books="likeStore.likedBookList"
    />
  </div>
</template>
