<script setup>
import { RouterLink } from 'vue-router';
// import Carousel from '../../components/Carousel.vue';
import Tabs from '../../components/Tabs.vue'
import Banner from '../../components/Banner.vue';
// import AdSlider from '../../components/AdSlider.vue'
import Hero from '../../components/Hero.vue';
import CardList from '../../components/CardList.vue'


const tabs = [
  { label: '전체', content: CardList },
  { label: '소설', content: {template: '<div>소설 콘텐츠</div>'  }},
  { label: '컴퓨터/IT', content: {template: '<div>컴퓨터/IT 콘텐츠</div>' }}
]
</script>

<template>
  <div>
    <Hero />
    <!-- <Carousel /> -->
    <!-- <AdSlider /> -->
    <Banner />
    <Tabs title="분야별 BEST 5" :tabs="tabs" />
  </div>
</template>


<style scoped>
</style>
