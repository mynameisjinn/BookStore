<template>
  <div v-show="showButton" class="fixed bottom-0 right-0 p-4 z-50">
    <button
        @click="scrollToTop"
        class="bg-red-600 text-white rounded-full w-10 h-10 flex items-center justify-center"
    >
      <svg
          class="w-6 h-6"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
      >
        <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M5 10l7-7m0 0l7 7m-7-7v18"
        ></path>
      </svg>
    </button>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted} from 'vue';

const showButton = ref(false);

const handleScroll = () => {
  showButton.value = window.scrollY > 200;
};

const scrollToTop = () => {
  window.scrollTo({top: 0, behavior: 'smooth'});
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>
