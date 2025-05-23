<script setup>
defineProps({ isValid: Boolean })
defineEmits(['click'])
</script>

<template>
    <div class="space-y-2">
        <div>
            <button type="button" @click="$emit('click')" :disabled="!isValid" :class="[
                'w-full px-8 py-3 font-semibold rounded-md',
                isValid ? 'bg-red-600 text-white' : 'bg-gray-300 text-gray-500 cursor-not-allowed'
            ]">
                다음
            </button>
        </div>
        <p v-if="!isValid" class="px-6 text-sm text-center text-red-600">
            필수영역에 모두 동의해주세요
        </p>
    </div>
</template>