<script setup>
import { ref, computed } from 'vue'
import SignUpButton from './SignUpButton.vue'

const email = ref('')
const name = ref('')
const password = ref('')
const confirmPassword = ref('')
const address = ref('')

const errorFields = ref([])
const emailError = ref(false)
const emailInputRef = ref(null)

const passwordError = ref(false)
const passwordFormatError = ref(false)
const passwordInputRef = ref(null)



/* 이메일 */
// 이메일 정규식 검사 함수
function isValidEmail(value) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)
}

// blur 시 이메일 검사
function handleEmailBlur() {
    if (!isValidEmail(email.value)) {
        emailError.value = true
        emailInputRef.value?.focus()
    } else {
        emailError.value = false
    }
}


/* 비밀번호 */
function handlePasswordInput() {
    passwordFormatError.value = !passwordRegex.test(password.value)
}

function handleConfirmPasswordInput() {
    passwordError.value = password.value !== confirmPassword.value
}

const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/

/* 비밀번호 정규식 
(?=.*[A-Za-z]) → 영문 1개 이상

(?=.*\d) → 숫자 1개 이상

(?=.*[!@#$%^&*...]) → 특수문자 1개 이상

.{8,} → 8자 이상
*/


/* 가입하기 버튼 */
const isValid = computed(() =>
    Boolean(
        email.value &&
        name.value &&
        password.value &&
        confirmPassword.value &&
        address.value &&
        !emailError.value &&
        !passwordError.value &&
        !passwordFormatError.value
    )
)

function handleClick() {
    errorFields.value = []

    if (!email.value) errorFields.value.push('email')
    if (!name.value) errorFields.value.push('name')
    if (!password.value) errorFields.value.push('password')
    if (!confirmPassword.value) errorFields.value.push('confirmPassword')
    if (!address.value) errorFields.value.push('address')
}
</script>

<template>
    <div class="mb-8 text-center">
        <h1 class="my-3 text-4xl font-bold">회원 정보입력</h1>
        <p class="text-sm text-gray-600">가입에 필요한 정보를 입력해주세요</p>
    </div>

    <form @submit.prevent class="space-y-12">
        <div class="space-y-4">
            <!-- 이메일 -->
            <div>
                <label for="email" class="block mb-2 text-sm">이메일</label>
                <input ref="emailInputRef" v-model="email" type="email" id="email" placeholder="nickname@example.com"
                    @blur="handleEmailBlur" :class="[
                        'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                        errorFields.includes('email') || emailError ? 'border-red-500' : 'border-gray-300'
                    ]" />
                <p v-if="emailError" class="text-sm text-red-600 mt-1">올바른 이메일 형식이 아닙니다</p>
            </div>

            <!-- 아이디 -->
            <div>
                <label for="name" class="block mb-2 text-sm">아이디</label>
                <div class="flex gap-2">
                    <input v-model="name" type="text" id="name" placeholder="yourID" :class="[
                        'flex-1 px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                        errorFields.includes('name') ? 'border-red-500' : 'border-gray-300'
                    ]" />
                    <button type="button" class="px-4 py-2 text-sm font-medium rounded-md text-white hover:brightness-90"
                        style="background-color: #EB3232;">Check</button>
                    <div class="w-10 h-10 border-2 border-dashed rounded-full animate-spin border-red-600"></div>
                </div>
            </div>

            <!-- 비밀번호 -->
            <div>
                <label for="password" class="block mb-2 text-sm">비밀번호</label>
                <!-- <input v-model="password" type="password" id="password" placeholder="••••••" :class="[
                    'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                    errorFields.includes('password') ? 'border-red-500' : 'border-gray-300'
                ]" /> -->
                <input ref="passwordInputRef" v-model="password" type="password" id="password" placeholder="••••••"
                    @input="handlePasswordInput" :class="[
                        'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                        errorFields.includes('password') || passwordFormatError ? 'border-red-500' : 'border-gray-300'
                    ]" />
                <p v-if="passwordFormatError" class="text-sm text-red-600 mt-1">
                    영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다
                </p>



            </div>

            <!-- 비밀번호 확인 -->
            <div>
                <label for="confirm-password" class="block mb-2 text-sm">비밀번호 확인</label>
                <!-- <input v-model="confirmPassword" type="password" id="confirm-password" placeholder="••••••" :class="[
                    'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                    errorFields.includes('confirmPassword') ? 'border-red-500' : 'border-gray-300'
                ]" /> -->
                <input v-model="confirmPassword" type="password" id="confirm-password" placeholder="••••••"
                    @input="handleConfirmPasswordInput" :class="[
                        'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                        errorFields.includes('confirmPassword') || passwordError ? 'border-red-500' : 'border-gray-300'
                    ]" />
                <p v-if="passwordError" class="text-sm text-red-600 mt-1">비밀번호가 일치하지 않습니다</p>


            </div>

            <!-- 주소 -->
            <div>
                <label for="address" class="block mb-2 text-sm">주소</label>
                <input v-model="address" type="text" id="address" placeholder="주소를 입력해주세요" :class="[
                    'w-full px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                    errorFields.includes('address') ? 'border-red-500' : 'border-gray-300'
                ]" />
            </div>

            <SignUpButton :isValid="isValid" @click="handleClick" />

        </div>
    </form>
</template>
