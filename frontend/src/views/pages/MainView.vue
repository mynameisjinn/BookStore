<script setup>
import Banner from '../../components/Banner.vue';
import Hero from '../../components/Hero.vue';
import SubMainList from "../../components/SubMainList.vue";
import MainPopup from "../../components/modals/MainAdPopup.vue";


const tabs = [
  // { label: '전체', content: CardList },
  { label: '소설', content: {template: '<div>소설 콘텐츠</div>'  }},
  { label: '컴퓨터/IT', content: {template: '<div>컴퓨터/IT 콘텐츠</div>' }}
]
</script>

<template>
  <div>
    <Hero />
    <Banner />
<!--    <Tabs title="이달의 추천 리스트" :tabs="tabs" />-->
    <div class="flex gap-4 mx-auto max-w-screen-lg">
      <SubMainList />
    </div>
  </div>
  <MainPopup />
</template>


<style scoped>
</style>
