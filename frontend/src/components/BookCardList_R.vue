<script setup>
import BookCard from "./BookCard.vue";
import {computed, onMounted, watch} from 'vue'
import {useBookStore} from "../stores/book.js";


const props = defineProps({
  categoryId: [Number, Array]
})

const bookStore = useBookStore()

const loadBooks = () => {
  const categoryIds = Array.isArray(props.categoryId) ? props.categoryId : [props.categoryId]
  bookStore.fetchBooks(categoryIds)
}

// watch(() => props.categoryId, loadBooks, { immediate: true, deep: true })

watch(() => props.categoryId, () => {
  const ids = Array.isArray(props.categoryId) ? props.categoryId : [props.categoryId]
  bookStore.fetchBooks(ids)
}, { immediate: true })

const books = computed(() => {
  return bookStore.getBooksByCategory(props.categoryId)
})


</script>

<template>
<!--  {{ categoryId }}-->
<!--  {{ bookStore.bookList }}-->
<!--  {{ books }}-->
  <div class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
<!--    <BookCard :books="bookStore.bookList" />-->
    <BookCard :books="books" />
  </div>
</template>
