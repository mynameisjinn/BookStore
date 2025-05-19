<template>
    <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg p-6 w-full max-w-md space-y-4 shadow-lg">
            <div class="flex justify-between items-center">
                <h2 class="text-xl font-bold">{{ sub }} 검색</h2>
                <CloseButton @close="$emit('close')" />
            </div>

            <input v-model="query" @input="onInput" type="text" class="w-full border p-2 rounded-md"
                placeholder="입력" />
            <ul>
                <li v-for="data in datas" :key="data.id" class="p-2 hover:bg-gray-100 cursor-pointer"
                    @click="select(data)">
                    {{ data.name }}
                </li>
            </ul>

        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import CloseButton from '../admin/CloseBtn.vue';
const props = defineProps({
  onSearch: Function,
  sub: String
})
const emit = defineEmits(['close', 'select'])

const query = ref('')
const datas = ref([])

// 임시 검색 예시
// const searchAuthor = () => {
//     authors.value = [
//         { id: 1, name: '홍길동' },
//         { id: 2, name: '김영희' },
//     ].filter(a => a.name.includes(query.value))
// }
const onInput = async () => {
  if (props.onSearch) {
    datas.value = await props.onSearch(query.value)
  }
}

const select = (author) => {
    emit('select', author)
}
</script>