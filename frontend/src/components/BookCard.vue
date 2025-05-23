
<script setup>
import ImgBox from "./ImgBox.vue";

const props = defineProps({
  books: {
    type: Array,
    default: () => [
      { title: '기본 제목' },
      { src: '/images/default-book-front.jpg'}
    ]
  },
})

</script>

<template>
  <article
      v-for="(book, index) in books"
      :key="index"
      class="flex flex-col  shadow-sm">
    <a rel="noopener noreferrer" href="#">
      <ImgBox :img-path="book.src"/>
    </a>
    <div class="flex flex-col flex-1 px-3 py-2 space-y-1">
      <h3 class="text-sm font-semibold leading-snug">
        {{ book.title }}
      </h3>
<!--      <p>{{ book.src }}</p>-->
      <p class="text-xs text-gray-600">작가: 홍길동</p>
      <p class="text-xs text-gray-600">출판사: 위대한출판사</p>
      <p class="text-sm text-gray-800 font-bold">₩15,000</p>
    </div>
  </article>
</template>
