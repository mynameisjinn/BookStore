
<script setup>
import ImgBox from "./ImgBox.vue";

const props = defineProps({
  title : {
    type: String,
    default: 'Te nulla oportere reprimique his dolorum'
  }
})
</script>

<template>
  <article class="flex flex-col  shadow-sm">
    <a rel="noopener noreferrer" href="#">
      <ImgBox img-path="/images/default-book-front.jpg"/>
    </a>
    <div class="flex flex-col flex-1 px-3 py-2 space-y-1">
      <h3 class="text-sm font-semibold leading-snug">
<!--        Te nulla oportere reprimique his dolorum-->
        {{ title }}
      </h3>
      <p class="text-xs text-gray-600">작가: 홍길동</p>
      <p class="text-xs text-gray-600">출판사: 위대한출판사</p>
      <p class="text-sm text-gray-800 font-bold">₩15,000</p>
    </div>
  </article>
</template>
