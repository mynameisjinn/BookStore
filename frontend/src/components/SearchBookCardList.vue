<script setup>
import SearchBookCard from "./SearchBookCard.vue";

const props = defineProps({
  books: Array,
  keyword: String
})



</script>

<template>
  <div class="w-full max-w-screen-lg mx-auto flex flex-col gap-4 p-4">

    <SearchBookCard
        v-for="book in props.books"
        :key="book.id"
        :book="book"
    />
  </div>
</template>

