<script setup>
import Carousel from "./Carousel.vue";

const images = ['/images/novel/no-slider01.jpg','/images/novel/no-slider02.jpg',
  '/images/novel/no-slider03.jpg','/images/novel/no-slider04.jpg','/images/novel/no-slider05.jpg']
</script>
<template>
  <Carousel
      :images=images height="h-[250px]" />
</template>
