<script setup>
import {ref} from 'vue'
import {useRouter} from "vue-router";

const router = useRouter()

const searchVar = ref('');
const showWarning = ref(false)
// const lastSearch = ref('');



const search = () => {
  const trimmed = searchVar.value.trim();
  if (!trimmed) {
    showWarning.value = true;
    setTimeout(() => {
      showWarning.value = false;
    }, 2000);
    return;
  }

  // lastSearch.value = trimmed
  searchVar.value = ''

  router.push({ path: '/search', query: { keyword: trimmed } });
}
</script>

<template>
  <div v-if="showWarning"
    class="absolute top-full mt-1 left-0 w-full bg-white border border-red-400 text-red-600 text-sm px-4 py-2 rounded-md shadow-lg z-10">
    검색어를 입력해주세요
  </div>
  <span class="absolute inset-y-0 left-0 flex items-center pl-3">
    <button type="submit" @click="search" title="Search" class="p-1 focus:outline-none focus:ring">
      <img src="/images/search.svg" alt="search" class="w-5 h-5" />
    </button>
  </span>
  <input type="search" name="Search" placeholder="검색어를 입력하세요"
    class="w-full py-3 pl-10 pr-4 text-base rounded-full border border-red-500 bg-gray-50 focus:outline-none focus:ring-2 focus:ring-red-300"
    v-model="searchVar" @keydown.enter="search" />
</template>

