<template>
    <div>

        <!-- Main modal -->
        <div v-if="isOpen"
            class="fixed top-0 left-0 z-50 w-full h-full flex justify-center items-center bg-black bg-opacity-50">
            <div
                class="relative p-4 w-full max-w-md bg-white rounded-lg shadow-sm dark:bg-gray-700 max-h-[90vh] overflow-auto">
                <!-- Modal header -->
                <div class="flex items-center justify-between p-4 border-b dark:border-gray-600 border-gray-200">
                    <h2 class="text-lg font-semibold text-gray-900 dark:text-white">
                        카테고리
                    </h2>
                    <CloseButton @close="$emit('close')" />
                </div>

                <!-- Modal body -->
                <form class="p-4" @submit.prevent="submitForm">
                    <div class="grid gap-4 mb-4 grid-cols-2">

                        <div class="col-span-2 sm:col-span-1">
                            <label for="category"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">분류</label>
                            <select id="category" v-model="form.category" class="input">
                                <option value="">대분류</option>
                                <option value="TV">중분류</option>
                                <option value="PC">소분류</option>
                            </select>
                        </div>
                        <div class="col-span-2 sm:col-span-1">
                            <label for="name"
                                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이름</label>
                            <input type="text" id="name" v-model="form.name" required class="input" placeholder="카테고리명" />
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
                        Add
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref, watch } from 'vue'
import CloseButton from './CloseBtn.vue';

const props = defineProps(['isOpen'])
const emit = defineEmits(['close'])

const form = ref({
    name: '',
    category: '',
})

const submitForm = () => {
    console.log('Form submitted:', form.value)
    emit('close') // 부모에게 닫기 요청
}
</script>
