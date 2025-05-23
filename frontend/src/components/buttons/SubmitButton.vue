<script setup>
defineProps({
  text: String
})
</script>

<template>
  <button type="submit"
          class="px-6 py-2 text-sm bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
    {{ text }}
  </button>
</template>