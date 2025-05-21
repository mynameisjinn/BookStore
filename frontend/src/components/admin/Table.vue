<!--<script setup>-->
<!--import { ref, computed, onMounted, watch } from 'vue';-->
<!--import RedButton from "./RedButton.vue";-->

<!--const props = defineProps({-->
<!--  title: String,-->
<!--  headers: Array, // [{ label: '도서명', key: 'title' }, ...]-->
<!--  fetchData: Function-->
<!--})-->

<!--const emit = defineEmits(['edit'])-->

<!--const dataList = ref([])-->
<!--const searchQuery = ref('')-->

<!--// 부모 함수로 데이터 받아오기-->
<!--async function fetchData() {-->
<!--  try {-->
<!--    const data = await props.fetchData()-->
<!--    dataList.value = data-->
<!--  } catch (error) {-->
<!--    console.error('데이터 가져오기 실패:', error)-->
<!--  }-->
<!--}-->

<!--onMounted(() => {-->
<!--  fetchData()-->
<!--})-->

<!--// 검색 필터링-->
<!--const filteredSearchVar = computed(() => {-->
<!--  if (!searchQuery.value) return dataList.value-->
<!--  return dataList.value.filter(item =>-->
<!--      Object.values(item).some(val =>-->
<!--          String(val).toLowerCase().includes(searchQuery.value.toLowerCase())-->
<!--      )-->
<!--  )-->
<!--})-->

<!--function editData(id) {-->
<!--  // alert(`Edit user with ID: ${id}`);-->
<!--  emit('edit', id)-->
<!--}-->

<!--function deleteData(id) {-->
<!--  const confirmed = confirm('삭제하시겠습니까?');-->
<!--  if (confirmed) {-->
<!--    dataList.value = dataList.value.filter(item => item.id !== id);-->
<!--    -->
<!--    emit('delete',id)-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<template>-->
<!--  <div class="container mx-auto px-4 py-8">-->
<!--    <h1 class="text-3xl font-bold text-center mb-8">{{ title }}</h1>-->

<!--    <div class="flex flex-col md:flex-row justify-between items-center mb-6">-->
<!--      <div class="w-full md:w-1/3 mb-4 md:mb-0">-->
<!--        <input-->
<!--            v-model="searchQuery"-->
<!--            type="text"-->
<!--            placeholder="검색"-->
<!--            class="w-full px-4 py-2 rounded-md border border-gray-300 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"-->
<!--        />-->
<!--      </div>-->
<!--      <RedButton text="삭제" />-->
<!--    </div>-->

<!--    <div class="overflow-x-auto bg-white rounded-lg shadow">-->
<!--      <table class="w-full table-auto">-->
<!--        <thead>-->
<!--        <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">-->
<!--          <th-->
<!--              v-for="header in props.headers"-->
<!--              :key="header.key"-->
<!--              class="py-3 px-6 text-left"-->
<!--          >-->
<!--            {{ header.label }}-->
<!--          </th>-->
<!--          <th class="py-3 px-6 text-center">편집</th>-->
<!--        </tr>-->
<!--        </thead>-->
<!--        <tbody class="text-gray-600 text-sm">-->
<!--        <tr-->
<!--            v-for="item in filteredSearchVar"-->
<!--            :key="item.id"-->
<!--            class="border-b border-gray-200 hover:bg-gray-100"-->
<!--        >-->
<!--          <td-->
<!--              v-for="header in props.headers"-->
<!--              :key="header.key"-->
<!--              class="py-3 px-6 text-left"-->
<!--          >-->
<!--            {{ item[header.key] }}-->
<!--          </td>-->
<!--          <td class="py-3 px-6 text-center">-->
<!--            <div class="flex item-center justify-center">-->
<!--              <button-->
<!--                  class="w-4 mr-2 transform hover:text-blue-500 hover:scale-110"-->
<!--                  @click="editData(item.id)"-->
<!--              >-->
<!--                <img src="/images/edit.svg" alt="edit" />-->
<!--              </button>-->
<!--              <button-->
<!--                  class="w-4 mr-2 transform hover:text-red-500 hover:scale-110"-->
<!--                  @click="deleteData(item.id)"-->
<!--              >-->
<!--                <img src="/images/delete.svg" alt="delete" />-->
<!--              </button>-->
<!--            </div>-->
<!--          </td>-->
<!--        </tr>-->
<!--        </tbody>-->
<!--      </table>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->




<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import RedButton from "./RedButton.vue";

const props = defineProps({
  title: String,
  headers: Array,
  fetchData: Function
});

const emit = defineEmits(['edit', 'delete']);

const dataList = ref([]);
const searchQuery = ref('');

const selectedIds = ref([]);
const selectAll = ref(false);

// 데이터 fetch
async function fetchData() {
  try {
    const data = await props.fetchData();
    dataList.value = data;
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
    selectedIds.value = filteredSearchVar.value.map(item => item.id);
  } else {
    selectedIds.value = [];
  }
});

// 선택된 항목이 전체와 일치하는지 확인해서 selectAll 갱신
watch([filteredSearchVar, selectedIds], () => {
  const visibleIds = filteredSearchVar.value.map(item => item.id);
  selectAll.value = visibleIds.length > 0 && visibleIds.every(id => selectedIds.value.includes(id));
}, { deep: true });

function editData(id) {
  emit('edit', id);
}

function deleteSelected() {
  console.log('삭제할 ID 목록:', selectedIds.value);
  // 여기에서 실제 삭제 로직 추가 가능

  emit('deleteList', selectedIds);
}


function deleteData(id) {
  const confirmed = confirm('삭제하시겠습니까?');
  if (confirmed) {
    // dataList.value = dataList.value.filter(item => item.id !== id);
    emit('delete', id);
  }
}


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
                :value="item.id"
                v-model="selectedIds"
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
              <button class="w-4 hover:text-red-500" @click="deleteData(item.id)">
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
