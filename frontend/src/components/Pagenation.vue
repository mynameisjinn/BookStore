<template>
    <div class="flex justify-center space-x-2 mt-4">
        <button :disabled="page <= 1" @click="$emit('change', page - 1)"
            class="px-3 py-1 border rounded disabled:opacity-50">
            이전
        </button>

        <span class="px-4 py-1">{{ page }} / {{ totalPages }}</span>

        <button :disabled="page >= totalPages" @click="$emit('change', page + 1)"
            class="px-3 py-1 border rounded disabled:opacity-50">
            다음
        </button>
    </div>
</template>

<script setup>
import { computed } from 'vue'
const props = defineProps({
    total: Number,
    page: Number,
    limit: Number,
})

const totalPages = computed(() => Math.ceil(props.total / props.limit))
</script>
