<script setup>
import {useConfirmStore} from "../../stores/confirm.js";

const confirmStore = useConfirmStore()
</script>
<template>
  <div v-if="confirmStore.visible" class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white p-10 rounded-2xl shadow-2xl w-[500px] text-center">
      <div class="flex justify-center mb-6">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
             stroke-width="1.5" stroke="currentColor" class="w-14 h-14 text-red-500">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M12 9v3.75m9-.75a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 3.75h.008v.008H12v-.008Z" />
        </svg>
      </div>

      <p class="mb-8 text-xl font-medium text-gray-700">{{ confirmStore.message }}</p>

      <div class="flex justify-center space-x-6">
        <button @click="confirmStore.cancel" class="px-6 py-3 text-base bg-gray-200 hover:bg-gray-300 rounded-lg">
          취소
        </button>
        <button @click="confirmStore.confirm" class="px-6 py-3 text-base bg-red-500 text-white hover:bg-red-600 rounded-lg">
          확인
        </button>
      </div>
    </div>
  </div>
</template>




