<script setup>

const props = defineProps({
  imgPath: {
    type: String,
    default: '/images/default-book-front.jpg'
  }
})

</script>

<template>
  <div class="flex-shrink-0 w-32 h-50 overflow-hidden rounded border">
    <img
        :src=imgPath
        alt="Product image"
        class="w-full h-full object-cover"
    >
  </div>
</template>
