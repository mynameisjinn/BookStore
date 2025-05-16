<script setup>
//@click="showCategoryModal = true"

defineEmits(['search'])
</script>
<template>
  <button type="button" @click="$emit('search')"
          class="px-4 py-2 text-sm border rounded-md border-gray-800 bg-white hover:bg-gray-100">
    검색
  </button>
</template>