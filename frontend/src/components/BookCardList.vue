<script setup>

import BookCard from "./BookCard.vue";
</script>

<template>
  <div class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
    <BookCard title="짧은제목"/>
    <BookCard />
    <BookCard title="엄ㅊ어어ㅓ어어ㅓ어어엉ㅇ 긴제목오오오오ㅗ오오곡알어ㅏㅇㄹ아ㅓㄹㅏㅇ너람얼아러아"/>
    <BookCard />
    <BookCard />
    <BookCard title="여긴 짧아"/>
    <BookCard title="이것도 짧ㄱㅇㄴ마림ㄴ어리ㅏㄴㅇ머"/>
    <BookCard title="짧안아런ㅁ이ㅏ럼ㄴ아ㅣ럼ㄴㅇ"/>
  </div>
</template>
