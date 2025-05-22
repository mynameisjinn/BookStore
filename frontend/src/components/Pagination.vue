<script setup>
import { computed } from 'vue'

const props = defineProps({
  currentPage: Number,
  totalItems: Number,
  itemsPerPage: Number,
  maxVisible: {
    type: Number,
    default: 5  // 기본 최대 버튼 수
  }
})

const emit = defineEmits(['page-changed'])

const totalPages = computed(() => Math.ceil(props.totalItems / props.itemsPerPage))

const visiblePages = computed(() => {
  const pages = []

  const half = Math.floor(props.maxVisible / 2)
  let start = props.currentPage - half
  let end = props.currentPage + half

  if (start < 1) {
    start = 1
    end = props.maxVisible
  }

  if (end > totalPages.value) {
    end = totalPages.value
    start = totalPages.value - props.maxVisible + 1
  }

  start = Math.max(start, 1)

  for (let i = start; i <= end; i++) {
    pages.push(i)
  }

  return pages
})

function changePage(page) {
  if (page >= 1 && page <= totalPages.value) {
    emit('page-changed', page)
  }
}
</script>

<template>
  <div class="flex justify-center space-x-1 text-gray-800 mt-4">
    <button @click="changePage(props.currentPage - 1)" :disabled="props.currentPage === 1" class="w-8 h-8 border rounded">«</button>

    <button
        v-for="page in visiblePages"
        :key="page"
        @click="changePage(page)"
        :class="['w-8 h-8 border rounded', page === props.currentPage ? 'bg-red-500 text-white' : '']"
    >
      {{ page }}
    </button>

    <button @click="changePage(props.currentPage + 1)" :disabled="props.currentPage === totalPages" class="w-8 h-8 border rounded">»</button>
  </div>
</template>
