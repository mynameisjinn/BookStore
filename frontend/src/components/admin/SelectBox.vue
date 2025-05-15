<template>
    <div class="flex">
        <div
            class="shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-500 bg-gray-100 border border-gray-300 rounded-s-lg hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600">
            {{ label }}
        </div>

        <label for="category" class="sr-only">Choose a category</label>
        <select id="category"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-e-lg border-s-gray-100 dark:border-s-gray-700 border-s-2 focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            v-model="selected">
            <option disabled value="">선택하세요</option>
            <option v-for="option in options" :key="option.value" :value="option.value">
                {{ option.label }}
            </option>
        </select>
    </div>
</template>
  
<script setup>
import { ref, defineProps, watch } from 'vue'

const props = defineProps({
    label: String,
    options: Array,
    modelValue: String
})

const emit = defineEmits(['update:modelValue'])
const selected = ref(props.modelValue)

watch(() => props.modelValue, (val) => selected.value = val)
watch(selected, (val) => emit('update:modelValue', val))

</script>
  