<script setup>
defineProps({ isValid: Boolean })
defineEmits(['signup'])
</script>

<template>
    <div class="space-y-2">
        <div>
            <button type="button" @click="$emit('signup')" :disabled="!isValid" :class="[
                'w-full px-8 py-3 font-semibold rounded-md',
                isValid ? 'bg-red-600 text-white' : 'bg-gray-300 text-gray-500 cursor-not-allowed'
            ]">
                가입하기
            </button>
        </div>
        <!-- <p v-if="!isValid" class="px-6 text-sm text-center text-red-600">
            사용한 가능한 아이디인지 체크해주세요 
        </p> -->
    </div>
</template>