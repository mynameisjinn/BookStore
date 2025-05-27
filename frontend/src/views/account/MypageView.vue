<script setup>
import UserSidebar from "../../components/UserSidebar.vue";
import {useAuthStore2} from "../../stores/auth-with-refresh.js";
import {computed, watch} from "vue";
import BookCard from "../../components/BookCard.vue";
import {useLikeStore} from "../../stores/like.js";

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
  <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
    <div class="flex-[1]">
      <UserSidebar />
    </div>
    <div class="flex-[3]">
<!--      <BookCard :books="likeStore.likedBookList" />-->
      <div class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
        <BookCard :books="likeStore.likedBookList" />
      </div>
    </div>
  </div>
</template>
