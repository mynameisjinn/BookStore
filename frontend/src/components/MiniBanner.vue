<script setup>
const props = defineProps({
  imgPath: String
})
</script>

<template>
  <div class="w-full max-w-screen-lg  mx-auto p-4 flex justify-center">
    <img :src=props.imgPath alt="BannerImg"
         class="h-18 w-auto rounded-lg" />
  </div>
</template>

<style scoped>

</style>