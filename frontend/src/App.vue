<script setup>
import { RouterView, useRoute } from 'vue-router';
import FooterView from './components/Footer.vue';
import HeaderLayout from './layouts/HeaderLayout.vue';

const route = useRoute();

</script>

<template>
  <div>
    <HeaderLayout v-if="!route.meta.hideLayout" />
    <RouterView />
    <FooterView v-if="!route.meta.hideLayout" />
  </div>
</template>