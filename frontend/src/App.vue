<script setup>
import { RouterView, useRoute } from 'vue-router';
import HeaderView from './components/Header.vue';
import FooterView from './components/Footer.vue';
import HeaderLayout from './layouts/HeaderLayout.vue';
const route = useRoute();

const hideLayout = ['/login', '/join','/admin/login'];
</script>

<template>
  <div>
    <!-- <HeaderView v-if="!hideLayout.includes(route.path)" /> -->
    <HeaderLayout v-if="!hideLayout.includes(route.path)"/>
    <RouterView />
    <FooterView v-if="!hideLayout.includes(route.path)" />
  </div>
</template>
