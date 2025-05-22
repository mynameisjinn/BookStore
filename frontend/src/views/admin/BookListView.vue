<script setup>
import { ref } from 'vue'
import BookTable from "../../components/admin/Table.vue"
import axios from 'axios'
import { useAuthStore } from "../../stores/auth.js"
import router from "../../router/index.js"
import { useToast } from "vue-toastification"

const toast = useToast()
const authStore = useAuthStore()
authStore.loadToken()

const callBookTable = ref(null)

const fetchBooks = async () => {
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

const deleteOne = async (id) => {
  try {
    const res = await axios.delete(`/api/admin/book/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${authStore.token}`
      }
    })
    // BookTable 내부 fetchData() 호출
    callBookTable.value?.fetchData()
    toast.warning(res.data)
  } catch (e) {
    toast.error('삭제에 실패했습니다.');
  }
}

const deleteList = async (list) => {
  const idList = list.value;
  console.log(list.value)

  try {
    const authStore = useAuthStore();

    const res = await axios({
      method: 'post',
      url: '/api/admin/book/delete-list',
      data: JSON.stringify(idList),
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authStore.token}`
      }
    });

    toast.warning(res.data);
    callBookTable.value?.fetchData();
  } catch (e) {
    // console.log(e.response.data)
    toast.error(e.response?.data || '삭제에 실패하였습니다.');
  }
}


</script>

<template>
  <BookTable
      ref="callBookTable"
      title="도서 리스트"
      :headers="headers"
      :fetchData="fetchBooks"
      @edit="(id) => router.push(`/admin/book/edit/${id}`)"
      @delete="deleteOne"
      @deleteList="deleteList"
  />
</template>
