<script setup>
import axios from 'axios'
import { useAuthStore } from '../../stores/auth'
import BookForm from "../../components/admin/BookForm.vue";
import router from "../../router/index.js";
import {useToast} from "vue-toastification";

const toast = useToast();
const handleRegister = async (formData) => {
  const authStore = useAuthStore()
  authStore.loadToken()

  const data = new FormData()
  Object.entries(formData).forEach(([key, value]) => {
    if (value !== null) data.append(key, value)
  })

  try {
    const res = await axios.post('/api/admin/book/upload', data, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${authStore.token}`
      }
    })
    // alert('도서 등록 성공!')
    router.push('/admin/book/list')
    toast.success(res.data)
  } catch (e) {
    // console.error('등록 실패:', e)
    toast.error(e)
  }
}
</script>

<template>
  <section class="p-6 bg-gray-100 text-gray-900">
    <BookForm mode="create" @submit="handleRegister" />
  </section>
</template>

