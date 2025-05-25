<script setup>
import BookCard from "./BookCard.vue";
import {computed, onMounted, watch} from 'vue'
import {useBookStore} from "../stores/book.js";
import LoadingBar from "./LoadingBar.vue";


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

const isLoaded = computed(() => bookStore.loading)


</script>

<template>
  <div v-if="isLoaded"
       class="mt-20">
    <LoadingBar />
  </div>
  <div v-else
      class="grid grid-cols-2 gap-4 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5">
    <BookCard :books="books" />
  </div>
</template>
