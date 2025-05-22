<script setup>
import {computed, onMounted, ref, watch} from 'vue';
import RedButton from "./RedButton.vue";
import {useToast} from "vue-toastification";
import {useConfirmStore} from "../../stores/confirm.js";

const toast = useToast()
const confirmStore = useConfirmStore()

const props = defineProps({
  title: String,
  headers: Array,
  fetchData: Function
});


const emit = defineEmits(['edit', 'delete']);

const dataList = ref([]);
const searchQuery = ref('');

const selectedList = ref([]);
const selectAll = ref(false);

// 데이터 fetch
async function fetchData() {
  try {
    dataList.value = await props.fetchData();
  } catch (error) {
    console.error('데이터 가져오기 실패:', error);
  }
}

onMounted(() => {
  fetchData();
});


// 검색 필터링
const filteredSearchVar = computed(() => {
  if (!searchQuery.value) return dataList.value;
  return dataList.value.filter(item =>
      Object.values(item).some(val =>
          String(val).toLowerCase().includes(searchQuery.value.toLowerCase())
      )
  );
});

// 전체 선택 토글
watch(selectAll, (val) => {
  if (val) {
    selectedList.value = filteredSearchVar.value.map(item => item); // 객체 자체 저장
  } else {
    selectedList.value = [];
  }
});

// 선택된 항목이 전체와 일치하는지 확인해서 selectAll 갱신
watch([filteredSearchVar, selectedList], () => {
  const visibleIds = filteredSearchVar.value.map(item => item.id);
  const selectedIds = selectedList.value.map(item => item.id);

  selectAll.value = visibleIds.length > 0 && visibleIds.every(id => selectedIds.includes(id));
}, { deep: true });

function editData(id) {
  emit('edit', id);
}

function deleteSelected() {
  confirmStore.openConfirm({
    msg: '선택한 항목을 모두 삭제하시겠습니까?',
    onOk: () => {
      emit('deleteList', selectedList);
    }
  })
}


const deleteData = async (book) => {
  // console.log(id)
  confirmStore.openConfirm({
    msg: '선택한 항목을 삭제하시겠습니까',
    onOk: () => {
      emit('delete', book)
    }
  })
}


defineExpose({
  fetchData
})
</script>
<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold text-center mb-8">{{ title }}</h1>

    <div class="flex flex-col md:flex-row justify-between items-center mb-6">
      <div class="w-full md:w-1/3 mb-4 md:mb-0">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="검색"
            class="w-full px-4 py-2 rounded-md border border-gray-300 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>
      <RedButton text="삭제" @click="deleteSelected" />
    </div>

    <div class="overflow-x-auto bg-white rounded-lg shadow">
      <table class="w-full table-fixed">
        <thead>
        <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
          <!-- 체크박스 열 -->
          <th class="w-10 px-2 text-center">
            <input type="checkbox" v-model="selectAll" />
          </th>

          <!-- 동적 헤더 -->
          <th
              v-for="(header, index) in props.headers"
              :key="header.key"
              :class="['py-3 px-4 text-left truncate', index === 0 ? 'w-20' : '']"
          >
            {{ header.label }}
          </th>

          <!-- 편집 열 -->
          <th class="w-20 py-3 px-4 text-center">편집</th>
        </tr>
        </thead>
        <tbody class="text-gray-600 text-sm">
        <tr
            v-for="item in filteredSearchVar"
            :key="item.id"
            class="border-b border-gray-200 hover:bg-gray-100"
        >
          <!-- 체크박스 열 -->
          <td class="w-10 py-3 px-2 text-center">
            <input
                type="checkbox"
                :value="item"
                v-model="selectedList"
            />
          </td>
          <!-- 데이터 셀 -->
          <td
              v-for="(header, index) in props.headers"
              :key="header.key"
              :class="['py-3 px-4 text-left truncate', index === 0 ? 'w-20' : '']"
          >
            {{ item[header.key] }}
          </td>

          <!-- 편집 버튼 -->
          <td class="w-20 py-3 px-4 text-center">
            <div class="flex items-center justify-center space-x-2">
              <button class="w-4 hover:text-blue-500" @click="editData(item.id)">
                <img src="/images/edit.svg" alt="edit" />
              </button>
              <button class="w-4 hover:text-red-500" @click="deleteData(item)">
                <img src="/images/delete.svg" alt="delete" />
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
