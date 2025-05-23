<script setup>
defineProps({
  text: String
})
</script>

<template>
  <button class="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition duration-300">
    {{ text }}
  </button>
</template>

<style scoped>

</style>