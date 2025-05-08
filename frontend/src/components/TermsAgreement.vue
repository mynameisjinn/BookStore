<script setup>
import { ref, watch, computed } from 'vue'
import NextButton from './NextButton.vue';

const emit = defineEmits(['next'])

const checked = ref({
    terms: false,
    privacy: false,
    marketing: false,
})

const allChecked = ref(false)

const toggleAll = () => {
    const value = allChecked.value
    checked.value.terms = value
    checked.value.privacy = value
    checked.value.marketing = value
}

const isRequiredChecked = computed(() => checked.value.terms && checked.value.privacy)

watch(checked, (newVal) => {
    allChecked.value = newVal.terms && newVal.privacy && newVal.marketing
}, { deep: true })

const goToNext = () => {
    if (isRequiredChecked.value) {
        emit('next')
    }
}
</script>

<template>
    <div class="mb-8 text-center">
        <h1 class="my-3 text-4xl font-bold">약관동의</h1>
        <p class="text-sm text-gray-600">아래 약관에 동의해주세요</p>
    </div>
    <div class="space-y-6">
        <!-- 약관 전문 -->
        <div class="p-4 bg-white border rounded-md max-h-64 overflow-y-auto text-sm text-gray-700">
            <h2 class="text-lg font-semibold mb-2">서비스 이용약관</h2>
            <p>
                본 약관은 귀하와 [회사명] 간의 법적 계약을 구성하며, 귀하는 본 서비스를 이용함으로써 본 약관에 동의하게 됩니다. 본 약관은 서비스 이용에 관한 조건과 책임을 명시하며, 귀하의 권리와 의무를
                규정합니다.
            </p>
            <p class="mt-2">
                귀하는 서비스 이용 시 관련 법률을 준수해야 하며, 회사는 사전 통지 없이 약관을 변경할 수 있습니다. 변경된 약관은 웹사이트에 게시되며, 게시 후 서비스를 계속 이용하면 변경 사항에 동의한
                것으로 간주됩니다.
            </p>
            <p class="mt-2">
                자세한 내용은 전체 약관을 참고하시기 바랍니다.
            </p>
        </div>

        <!-- 동의 체크박스 -->
        <div class="space-y-3">
            <div class="flex items-center">
                <input type="checkbox" id="all" v-model="allChecked" @change="toggleAll" class="w-4 h-4 mr-2">
                <label for="all" class="text-sm font-medium">전체 동의</label>
            </div>
            <div class="pl-4 space-y-2 text-sm text-gray-700">
                <div class="flex items-center">
                    <input type="checkbox" id="terms" v-model="checked.terms" class="w-4 h-4 mr-2">
                    <label for="terms">[필수] 이용약관 동의</label>
                </div>
                <div class="flex items-center">
                    <input type="checkbox" id="privacy" v-model="checked.privacy" class="w-4 h-4 mr-2">
                    <label for="privacy">[필수] 개인정보 처리방침 동의</label>
                </div>
                <div class="flex items-center">
                    <input type="checkbox" id="marketing" v-model="checked.marketing" class="w-4 h-4 mr-2">
                    <label for="marketing">[선택] 마케팅 정보 수신 동의</label>
                </div>
            </div>
        </div>

        <NextButton :isValid="isRequiredChecked" @click="goToNext" />
    </div>
</template>

