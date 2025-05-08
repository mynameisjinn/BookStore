<script setup>
import { RouterView } from 'vue-router'; 
import HeaderView from './components/Header.vue';
import FooterView from './components/Footer.vue';
</script>

<template>
  <HeaderView />
  <RouterView />
  <FooterView />
</template>

