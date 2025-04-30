<script setup>
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
  <a href="/api/test">테스트 콘솔에 찍히나</a>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
