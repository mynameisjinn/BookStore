<script setup>
import { ref, computed, watch } from 'vue'
import { useCategoryStore } from '../../stores/category'
import CloseButton from '../buttons/CloseButton.vue'

const props = defineProps(['isOpen'])
const emit = defineEmits(['close'])

const categoryStore = useCategoryStore()

const form = ref({
    name: '',
    category: '',
    parentCategory: '',
    middleCategory: '',
})

// 컴포넌트 열릴 때 카테고리 불러오기
watch(() => props.isOpen, async (newVal) => {
    if (newVal) {
        await categoryStore.fetchCategory()
    } else {
        // 닫힐 때 초기화
        form.value = {
            name: '',
            category: '',
            parentCategory: '',
            middleCategory: '',
        }
    }
})

// 선택된 대분류에 따른 중분류 필터링
const filteredSubCategory = computed(() => {
    return categoryStore.subCategory.filter(sub => sub.parentId === form.value.parentCategory)
})

const submitForm = () => {
    console.log('Form submitted:', form.value)
    // emit('close')
}
</script>
<template>
    <div>
        <div v-if="isOpen"
            class="fixed top-0 left-0 z-50 w-full h-full flex justify-center items-center bg-black bg-opacity-50">
            <div
                class="relative p-4 w-full max-w-md bg-white rounded-lg shadow-sm dark:bg-gray-700 max-h-[90vh] overflow-auto">

                <div class="flex items-center justify-between p-4 border-b dark:border-gray-600 border-gray-200">
                    <h2 class="text-lg font-semibold text-gray-900 dark:text-white">카테고리</h2>
                    <CloseButton @close="$emit('close')" />
                </div>

                <form class="p-4" @submit.prevent="submitForm">
                    <div class="grid gap-4 mb-4 grid-cols-2">
                        <div class="col-span-2 sm:col-span-1">
                            <label for="category"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">분류</label>
                            <select id="category" v-model="form.category" class="input">
                                <option disabled value="">선택</option>
                                <option value="1">대분류</option>
                                <option value="2">중분류</option>
                                <option value="3">소분류</option>
                            </select>
                        </div>

                        <div class="col-span-2 sm:col-span-1">
                            <label for="name"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이름</label>
                            <input type="text" id="name" v-model="form.name" required class="input" placeholder="카테고리명" />
                        </div>

                        <!-- 대분류 선택 (중분류, 소분류일 때) -->
                        <div v-if="form.category === '2' || form.category === '3'" class="col-span-2">
                            <p class="text-sm text-gray-700 dark:text-gray-300 mb-2">
                                {{ form.category === '2' ? '중분류는 반드시 대분류를 선택해야 합니다.' : '소분류는 반드시 대분류와 중분류를 선택해야 합니다.' }}
                            </p>
                            <label for="parentCategory"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">대분류 선택</label>
                            <select id="parentCategory" v-model="form.parentCategory" class="input">
                                <option disabled value="">대분류 선택</option>
                                <option v-for="item in categoryStore.mainCategory" :key="item.id"
                                    :value="item.id">
                                    {{ item.name }}
                                </option>
                            </select>
                        </div>

                        <!-- 중분류 선택 (소분류일 때만) -->
                        <div v-if="form.category === '3'" class="col-span-2">
                            <label for="middleCategory"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">중분류 선택</label>
                            <select id="middleCategory" v-model="form.middleCategory" class="input">
                                <option disabled value="">중분류 선택</option>
                                <option v-for="item in filteredSubCategory" :key="item.id" :value="item.id">
                                    {{ item.name }}
                                </option>
                            </select>
                        </div>
                    </div>

                    <button type="submit"
                        class="text-white inline-flex items-center bg-red-700 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800">
                        <svg class="me-1 -ms-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                            xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                d="M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z"
                                clip-rule="evenodd"></path>
                        </svg>
                        추가
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>
<style scoped>
.input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 0.375rem;
}
</style>
