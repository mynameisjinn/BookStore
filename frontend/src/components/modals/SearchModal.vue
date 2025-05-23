<script setup>
import CloseButton from '../buttons/CloseButton.vue';
import { onMounted, ref } from 'vue';

const props = defineProps({
  sub: String,
  searchVar: String,
  dataArray: Array
})
const emit = defineEmits(['close', 'select', 'update-search'])

const inputBox = ref(null)
const selectedIndex = ref(-1)

const onInput = (e) => {
  emit('update-search', e.target.value)
  selectedIndex.value = -1  // 검색어 바뀌면 선택 초기화
}

const select = (data) => {
  emit('select', data)
  emit('update-search', '')
  selectedIndex.value = -1
}

// 검색 목록 내 방향키 이동 + 선택 함수
const onKeyDown = (e) => {
  if (!props.dataArray.length) return;

  if (e.key === 'ArrowDown') {
    e.preventDefault();
    selectedIndex.value = (selectedIndex.value + 1) % props.dataArray.length;
  } else if (e.key === 'ArrowUp') {
    e.preventDefault();
    selectedIndex.value = (selectedIndex.value - 1 + props.dataArray.length) % props.dataArray.length;
  } else if (e.key === 'Enter') {
    e.preventDefault();
    if (selectedIndex.value >= 0) {
      select(props.dataArray[selectedIndex.value]);
    }
  }
}

onMounted(() => {
  inputBox.value?.focus()
})
</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg p-6 w-full max-w-md space-y-4 shadow-lg">
      <div class="flex justify-between items-center">
        <h2 class="text-xl font-bold">{{ sub }} 검색</h2>
        <CloseButton @close="$emit('close')" />
      </div>

      <input
          ref="inputBox"
          :value="searchVar"
          @input="onInput"
          @keyup.enter="onInput"
          @keydown="onKeyDown"
          type="text"
          class="w-full border p-2 rounded-md"
        placeholder="입력" />

      <ul v-if="searchVar && searchVar.trim().length > 0 && dataArray.length > 0">
<!--        <li v-for="data in dataArray" :key="data.id" class="p-2 hover:bg-gray-100 cursor-pointer"-->
<!--          @mousedown="select(data)">-->
        <li
            v-for="(data, index) in dataArray"
            :key="data.id"
            class="p-2 cursor-pointer"
            :class="{ 'bg-gray-200': index === selectedIndex }"
            @mousedown.prevent="select(data)"
        >
          {{ data.name }}
        </li>
      </ul>

    </div>
  </div>
</template>

