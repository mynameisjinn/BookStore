<script setup>
//@click="showCategoryModal = true"

defineEmits(['add'])
</script>

<template>
  <label class="text-sm select-none">카테고리</label>
  <button type="button" @click="$emit('add')"
          class="w-6 h-6 flex items-center justify-center text-gray-700 border border-gray-400 rounded-full hover:bg-gray-300 transition"
          aria-label="카테고리 추가">
    +
  </button>
</template>

