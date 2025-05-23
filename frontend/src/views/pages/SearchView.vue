<script setup>
import SearchBookCardList from "../../components/SearchBookCardList.vue";
import { useRoute } from 'vue-router'
import {ref, watch, watchEffect} from 'vue'
import axios from 'axios'
import Banner from "../../components/Banner.vue";
import MiniBanner from "../../components/MiniBanner.vue";
import NoResult from "../../components/NoResult.vue";

const route = useRoute()
const keyword = ref(route.query.keyword || '')
const dataList = ref([])

// query가 바뀔 때마다 keyword, API 재요청
watch(
    () => route.query.keyword,
    async (newKeyword) => {
      keyword.value = newKeyword
      if (!newKeyword) return
      try {
        const res = await axios.get(`/api/search/${newKeyword}`)
        dataList.value = res.data
        console.log('검색 결과: ', dataList.value)
      } catch (e) {
        console.error('검색 실패:', e)
      }
    },
    { immediate: true } // 처음 로딩 시에도 실행되도록
)

const searchImgPath = "/images/main/mini_banner_01.jpg";
</script>

<template>

  <MiniBanner :imgPath="searchImgPath" />

  <!-- 결과 있음 -->
  <div v-if="dataList.length" class="w-full max-w-screen-lg mx-auto px-4 mt-3 mb-4">
    <div class="bg-white border border-gray-200 rounded-lg p-4 shadow-sm">
      <p class="text-base text-gray-700">
        <span class="font-semibold text-red-500">“{{ keyword }}”</span> 에 대한
        <span class="font-medium text-gray-800">{{ dataList.length }}</span>건의 검색 결과가 있습니다.
      </p>
    </div>
  </div>

  <SearchBookCardList :books="dataList" :keyword="keyword" />
  <!-- 결과 없음 -->
  <div v-if="!dataList.length">
    <NoResult :keyword="keyword"/>
  </div>

</template>
