<script setup>
import BookCard from "./BookCard.vue";

const bookList = [
  { title: "짧은제목", src: "/images/main/recommend_01.jpg" },
  { title: "행동은 불안을 이긴다", src: "/images/main/recommend_02.jpg" },
  { title: "행복할 거야 이래도 되나 싶을 정도로", src: "/images/main/recommend_03.jpg" },
  { title: "일류의 조건", src: "/images/main/recommend_04.jpg" },
  { title: "테스트용" },
  { title: "테스트용" },
  { title: "테스트용" },
]

</script>

<template>
  <div class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
    <BookCard :books="bookList" />
  </div>
</template>
