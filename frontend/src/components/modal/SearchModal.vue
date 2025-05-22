<script setup>
import { ref } from 'vue'
import CloseButton from '../admin/CloseButton.vue';
const props = defineProps({
  // onSearch: Function,
  sub: String,
  searchVar: String,
  dataArray: Array
})
const emit = defineEmits(['close', 'select','update-search'])

// const query = ref('')
// const dataList = ref([])

// const onInput = async () => {
//   if (props.onSearch) {
//     dataList.value = await props.onSearch(query.value)
//   }
// }

const onInput = (e) => {
  emit('update-search', e.target.value)
}

const select = (data) => {
  console.log('선택된 데이터 여기!!!')
  console.log(data)
  // query.value = ''        // 인풋창 비우기
  // dataList.value = []        // 리스트도 비우기 (선택 완료되었으므로)
  emit('select', data)
  emit('update-search', '')
}
</script>

<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg p-6 w-full max-w-md space-y-4 shadow-lg">
            <div class="flex justify-between items-center">
                <h2 class="text-xl font-bold">{{ sub }} 검색</h2>
                <CloseButton @close="$emit('close')" />
            </div>

<!--            <input v-model="query" @input="onInput" @keyup.enter="onInput" type="text" class="w-full border p-2 rounded-md"-->
<!--                placeholder="입력" />-->
<!--            <ul>-->
<!--                <li v-for="data in dataList" :key="data.id" class="p-2 hover:bg-gray-100 cursor-pointer"-->
<!--                    @mousedown ="select(data)">-->
<!--                    {{ data.name }}-->
<!--                </li>-->
<!--            </ul>-->

          <input :value="searchVar" @input="onInput"
                 @keyup.enter="onInput" type="text"
                 class="w-full border p-2 rounded-md"
                 placeholder="입력"
          />

                    <ul v-if="searchVar && searchVar.trim().length > 0 && dataArray.length > 0">
                      <li v-for="data in dataArray"
                          :key="data.id"
                          class="p-2 hover:bg-gray-100 cursor-pointer"
                          @mousedown ="select(data)"
                      >
                        {{ data.name }}
                      </li>
                    </ul>

                  </div>
              </div>
          </template>

