<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import RedButton from "./RedButton.vue";

const props = defineProps({
  title: String,
  headers: Array, // [{ label: '도서명', key: 'title' }, ...]
  fetchData: Function
})

const emit = defineEmits(['edit'])

const dataList = ref([])
const searchQuery = ref('')

// 부모 함수로 데이터 받아오기
async function fetchData() {
  try {
    const data = await props.fetchData()
    dataList.value = data
  } catch (error) {
    console.error('데이터 가져오기 실패:', error)
  }
}

onMounted(() => {
  fetchData()
})

// 검색 필터링
const filteredSearchVar = computed(() => {
  if (!searchQuery.value) return dataList.value
  return dataList.value.filter(item =>
      Object.values(item).some(val =>
          String(val).toLowerCase().includes(searchQuery.value.toLowerCase())
      )
  )
})

function editData(id) {
  // alert(`Edit user with ID: ${id}`);
  emit('edit', id)
}

function deleteData(id) {
  const confirmed = confirm('삭제하시겠습니까?');
  if (confirmed) {
    dataList.value = dataList.value.filter(item => item.id !== id);
  }
}
</script>

<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center mb-8">{{ title }}</h1>

    <div class="flex flex-col md:flex-row justify-between items-center mb-6">
      <div class="w-full md:w-1/3 mb-4 md:mb-0">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="검색"
            class="w-full px-4 py-2 rounded-md border border-gray-300 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <RedButton text="삭제" />
    </div>

    <div class="overflow-x-auto bg-white rounded-lg shadow">
      <table class="w-full table-auto">
        <thead>
        <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
          <th
              v-for="header in props.headers"
              :key="header.key"
              class="py-3 px-6 text-left"
          >
            {{ header.label }}
          </th>
          <th class="py-3 px-6 text-center">편집</th>
        </tr>
        </thead>
        <tbody class="text-gray-600 text-sm">
        <tr
            v-for="item in filteredSearchVar"
            :key="item.id"
            class="border-b border-gray-200 hover:bg-gray-100"
        >
          <td
              v-for="header in props.headers"
              :key="header.key"
              class="py-3 px-6 text-left"
          >
            {{ item[header.key] }}
          </td>
          <td class="py-3 px-6 text-center">
            <div class="flex item-center justify-center">
              <button
                  class="w-4 mr-2 transform hover:text-blue-500 hover:scale-110"
                  @click="editData(item.id)"
              >
                <img src="/images/edit.svg" alt="edit" />
              </button>
              <button
                  class="w-4 mr-2 transform hover:text-red-500 hover:scale-110"
                  @click="deleteData(item.id)"
              >
                <img src="/images/delete.svg" alt="delete" />
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
