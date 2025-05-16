<template>
    <div class="flex">
        <div
            class="shrink-0 z-10 inline-flex items-center px-4 text-sm font-medium text-gray-500 bg-gray-100 border border-r-0 rounded-l-md">
            {{ label }}
        </div>

        <select v-model="selected" :disabled="disabled"
            class="w-full p-2.5 text-sm text-gray-900 bg-white border border-gray-300 rounded-r-md focus:ring-blue-500 focus:border-blue-500 disabled:bg-gray-100">
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
    modelValue: String,
    disabled: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['update:modelValue'])
const selected = ref(props.modelValue)

watch(() => props.modelValue, (val) => selected.value = val)
watch(selected, (val) => emit('update:modelValue', val))
</script>
