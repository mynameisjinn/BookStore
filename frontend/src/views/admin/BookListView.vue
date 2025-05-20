<script setup>

import BookTable from "../../components/admin/Table.vue";

import axios from 'axios'
import {useAuthStore} from "../../stores/auth.js";

const fetchBooks = async () => {
  const authStore = useAuthStore()
  authStore.loadToken()

  const res = await axios.get('/api/admin/book/list', {
    headers: {
      Authorization: `Bearer ${authStore.token}`
    }
  })

  return res.data.map(book => ({
    id: book.id,
    title: book.title,
    authorName: book.authorName,
    publisherName: book.publisherName,
    publishedDate: book.publishedDate
  }))
}

const headers = [
  { label: 'ID', key: 'id' },
  { label: '도서명', key: 'title' },
  { label: '작가명', key: 'authorName' },
  { label: '출판사', key: 'publisherName' },
  { label: '출판일', key: 'publishedDate' }
]
</script>

<template>
<!--  <BookTable />-->
  <BookTable title="도서 리스트" :headers="headers" :fetchData="fetchBooks" />
</template>

<style scoped>

</style>