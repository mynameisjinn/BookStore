<!-- views/BookEditView.vue -->
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useAuthStore } from '../../stores/auth'
import BookForm from "../../components/admin/BookForm.vue";
import router from "../../router/index.js";

const route = useRoute()
const authStore = useAuthStore()
authStore.loadToken()

const bookId = route.params.id
const bookData = ref(null)

const fetchBookDetail = async () => {
  try {
    const res = await axios.get(`/api/admin/book/edit/${bookId}`, {
      headers: { Authorization: `Bearer ${authStore.token}` }
    })
    bookData.value = res.data
    console.log('저장된 도서 데이터')
    console.log(bookData.value)
  } catch (e) {
    console.error('도서 정보 불러오기 실패:', e)
  }
}

onMounted(fetchBookDetail)

const handleUpdate = async (formData) => {
  const data = new FormData()
  Object.entries(formData).forEach(([key, value]) => {
    if (value !== null) data.append(key, value)
  })

  try {
    await axios.put(`/api/admin/book/edit/${bookId}`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${authStore.token}`
      }
    })
    alert('도서 수정 완료!')
    router.push('/admin/book/list') // 수정 후 목록으로 이동
  } catch (e) {
    console.error('수정 실패:', e)
  }
}
</script>

<template>
  <section class="p-6 bg-gray-100 text-gray-900">
    <BookForm v-if="bookData" mode="edit" :defaultFormData="bookData" @submit="handleUpdate" />
    <p v-else>도서 정보를 불러오는 중입니다...</p>
  </section>
</template>
