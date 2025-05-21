<script setup>
import { ref, watchEffect } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const props = defineProps({
  title: String,
  tabs: Array
})

const route = useRoute()
const router = useRouter()
const openTab = ref(0)

const activeClasses = 'bg-red-600 text-white rounded-t px-4 py-2 font-semibold transition-colors'
const inactiveClasses = 'bg-white text-red-600 border rounded-t px-4 py-2 font-semibold hover:bg-red-50 transition-colors'

// 현재 경로에 따라 openTab 자동 변경
watchEffect(() => {
  const currentPath = route.path

  // path가 일치하는 탭이 있으면 해당 index, 없으면 0 (전체탭)
  const foundIndex = props.tabs.findIndex(tab => tab.path === currentPath)
  openTab.value = foundIndex !== -1 ? foundIndex : 0
})
</script>

<template>
  <div class="mx-auto max-w-screen-lg ">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">{{ title }}</h2>

    <ul class="flex border-b">
      <li
          v-for="(tab, index) in tabs"
          :key="index"
          :class="{ '-mb-px': openTab === index }"
          class="mr-1"
      >
        <button
            @click="() => {
            openTab = index
            if (tab.path) router.push(tab.path)
          }"
            :class="openTab === index ? activeClasses : inactiveClasses"
        >
          {{ tab.label }}
        </button>
      </li>
    </ul>

    <div class="w-full mt-4">
      <div
          v-for="(tab, index) in tabs"
          :key="index"
          v-show="openTab === index"
      >
        <component :is="tab.content" />
      </div>
    </div>
  </div>
</template>
