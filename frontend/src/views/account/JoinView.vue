<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router';
import Steps from '../../components/Steps.vue'
import TermsAgreement from '../../components/TermsAgreement.vue'
import JoinInfo from '../../components/JoinInfo.vue'
import JoinSuccess from '../../components/JoinSuccess.vue'

const currentStep = ref(1)

const stepLabels = [
    'Step 1: 약관 동의',
    'Step 2: 회원정보 입력',
    'Step 3: 가입 완료'
]

</script>

<style lang="scss" scoped></style>

<template>
    <div class="flex flex-col items-center justify-center min-h-screen bg-gray-100 space-y-8">

        <div class="flex items-center space-x-4 mt-20">
            <RouterLink to="/" aria-label="Back to homepage" class="flex items-center p-2">
                <img src="/logo.svg" alt="Logo" class="w-12 h-auto" />
            </RouterLink>
            <Steps :currentStep="currentStep" :stepLabels="stepLabels" />
        </div>


        <div class="flex flex-col max-w-md p-6 rounded-md sm:p-10 bg-gray-50 text-gray-800 w-full">
            <template v-if="currentStep === 1">
                <TermsAgreement @next="currentStep++" />
            </template>
            <template v-else-if="currentStep === 2">
                <JoinInfo @next="currentStep++" />
            </template>
            <template v-else-if="currentStep === 3">
                <JoinSuccess />
            </template>
        </div>
    </div>
</template>