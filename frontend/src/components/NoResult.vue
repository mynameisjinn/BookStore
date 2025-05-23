<script setup>
const props = defineProps({
  keyword: String
})

</script>

<template>
  <div class="w-full max-w-screen-lg  mx-auto text-center py-16 text-gray-500 border rounded-lg bg-gray-50 my-5">
    <p class="text-lg font-semibold mb-2">{{ props.keyword }} 에 대한 검색 결과가 없습니다</p>
    <p class="text-sm text-gray-400">다른 키워드로 다시 검색해보세요</p>
  </div>
</template>

<style scoped>

</style>