<script setup>
import SearchBookCard from "./SearchBookCard.vue";
import {computed} from "vue";

const props = defineProps({
  books: Array,
  keyword: String
})

// 전처리된 books 배열
const changedBooks = computed(() =>
    props.books.map(book => ({
      ...book,
      price: Number(book.price).toLocaleString("ko-KR") + ' 원'
    }))
)

</script>

<template>
  <div class="w-full max-w-screen-lg mx-auto flex flex-col gap-4 p-4">

    <SearchBookCard
        v-for="book in changedBooks"
        :key="book.id"
        :book="book"
    />
  </div>
</template>

