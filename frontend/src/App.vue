<script setup>
import { RouterView, useRoute } from 'vue-router';
import HeaderView from './components/Header.vue';
import FooterView from './components/Footer.vue';
import HeaderLayout from './layouts/HeaderLayout.vue';
import DangerAlert from './components/alert/DangerAlert.vue';
import { useAlertStore } from './stores/alert';

const route = useRoute();
const alertStore = useAlertStore()

const hideLayout = ['/login', '/join','/admin/login','/server-error'];
</script>

<template>
  <DangerAlert :message="alertStore.message" />
  <div>
    <!-- <HeaderView v-if="!hideLayout.includes(route.path)" /> -->
    <HeaderLayout v-if="!hideLayout.includes(route.path)"/>
    <RouterView />
    <FooterView v-if="!hideLayout.includes(route.path)" />
  </div>

</template>
