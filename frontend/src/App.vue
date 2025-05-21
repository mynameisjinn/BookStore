<script setup>
import { RouterView, useRoute } from 'vue-router';
import FooterView from './components/Footer.vue';
import HeaderLayout from './layouts/HeaderLayout.vue';
import PopupModal from "./components/modal/PopupModal.vue";
import TopButton from "./components/TopButton.vue";
import AdminHeaderLayout from "./layouts/AdminHeaderLayout.vue";

const route = useRoute();

</script>

<template>
  <div>
    <HeaderLayout v-if="!route.meta.hideLayout" />
    <AdminHeaderLayout v-if="route.meta.adminHeader" />
    <RouterView />
    <FooterView v-if="!route.meta.hideLayout" />
  </div>
  <TopButton/>
  <PopupModal />
</template>