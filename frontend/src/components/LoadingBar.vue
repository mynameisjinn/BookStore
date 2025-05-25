<template>
  <div class="flex items-center justify-center space-x-2">
    <div
        v-for="(dot, index) in 3"
        :key="index"
        class="w-4 h-4 rounded-full bg-red-600"
        :class="{ 'opacity-30': current !== index }"
    ></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const current = ref(0)
let interval

onMounted(() => {
  interval = setInterval(() => {
    current.value = (current.value + 1) % 3
  }, 300)
})

onBeforeUnmount(() => {
  clearInterval(interval)
})
</script>

<style scoped>
/* transition 효과 추가하면 부드러움 */
div > div {
  transition: opacity 0.2s ease;
}
</style>
