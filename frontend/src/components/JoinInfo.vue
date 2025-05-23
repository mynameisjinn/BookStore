<script setup>
import axios from 'axios'
import { ref, computed, watch } from 'vue'
import JoinButton from './buttons/JoinButton.vue'


const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const address = ref('')

const errorFields = ref([])
const emailError = ref(false)
const emailInputRef = ref(null)
const emailCheck = ref(false)
const emailDuplicated = ref(false)

const passwordError = ref(false)
const passwordFormatError = ref(false)
const passwordInputRef = ref(null)

const isCheckingEmail = ref(false) // 로딩 여부
const emit = defineEmits(['next'])


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


const checkEmailExists = async () => {
    if (!isValidEmail(email.value)) {
        emailError.value = true
        emailInputRef.value?.focus()
        return
    }

    isCheckingEmail.value = true
    try {
        const response = await axios.get('/api/check-email', {
            params: { email: email.value }
        })
        const exists = response.data.exists
        if (exists) {
            // alert('이미 사용 중인 이메일입니다.')
            emailDuplicated.value = true
        } else {
            // alert('사용 가능한 이메일입니다.')
            emailCheck.value = true
            emailDuplicated.value = false
        }
    } catch (error) {
        console.error('이메일 중복 확인 중 오류 발생:', error)
    } finally {
        isCheckingEmail.value = false
    }
}

watch(email, () => {
    emailCheck.value = false
})



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
        password.value &&
        confirmPassword.value &&
        address.value.trim() &&
        emailCheck.value &&
        !emailError.value &&
        !passwordError.value &&
        !passwordFormatError.value
    )
)

async function handleClick() {
    errorFields.value = []

    if (!email.value) errorFields.value.push('email')
    if (!password.value) errorFields.value.push('password')
    if (!confirmPassword.value) errorFields.value.push('confirmPassword')
    if (!address.value) errorFields.value.push('address')

    if (errorFields.value.length > 0) return

    try {
        const res = await axios.post('/api/join', {
            email: email.value,
            password: password.value,
            address: address.value
        })
        // alert('회원가입 성공! 로그인 페이지로 이동합니다.')

        emit('next')
    } catch (err) {
        // console.error('회원가입 실패', err)
        alert('회원가입에 실패했습니다. 관리자에게 문의해주세요')
    }
}


</script>

<template>
    <div class="mb-8 text-center">
        <h1 class="my-3 text-4xl font-bold">회원 정보입력</h1>
        <p class="text-sm text-gray-600">가입에 필요한 정보를 입력해주세요</p>
    </div>

    <form class="space-y-12">
        <div class="space-y-4">
            <!-- 이메일 -->
            <div>
                <label for="email" class="block mb-2 text-sm">이메일</label>
                <div class="flex gap-2">
                    <input ref="emailInputRef" v-model="email" type="email" id="email" placeholder="nickname@example.com"
                        @blur="handleEmailBlur" :class="[
                            'flex-1 px-3 py-2 border rounded-md bg-gray-50 text-gray-800',
                            errorFields.includes('email') || emailError ? 'border-red-500' : 'border-gray-300'
                        ]" />
                    <button type="button" @click="checkEmailExists" :disabled="emailCheck" :class="[
                        'px-4 py-2 text-sm font-medium rounded-md text-white',
                        !emailCheck && 'hover:brightness-90'
                    ]" :style="{
    backgroundColor: emailCheck ? '#A0A0A0' : '#EB3232',
    cursor: emailCheck ? 'not-allowed' : 'pointer'
}">
                        {{ emailCheck ? 'Checked' : 'Check' }}
                    </button>


                    <div v-if="isCheckingEmail"
                        class="w-6 h-6 border-2 border-dashed rounded-full animate-spin border-red-600"></div>
                </div>
                <p v-if="emailError" class="text-sm text-red-600 mt-1">올바른 이메일 형식이 아닙니다</p>
                <p v-if="emailDuplicated" class="text-sm text-red-600 mt-1">이미 가입된 이메일입니다</p>
                <p v-if="emailCheck" class="text-sm text-green-600 mt-1">사용 가능한 이메일입니다</p>
            </div>

            <!-- 비밀번호 -->
            <div>
                <label for="password" class="block mb-2 text-sm">비밀번호</label>
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
            <!-- <p>{{ isValid }}</p>
            <p>{{ email }}, {{ password }}, {{ confirmPassword }}, {{ address }}</p>
            <p>{{ emailError }}, {{ passwordError }}, {{ passwordFormatError }}</p>
             -->

            <JoinButton :isValid="isValid" @join="handleClick" />



        </div>
    </form>
</template>
