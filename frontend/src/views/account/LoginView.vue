<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'

const email = ref('')
const password = ref('')
const router = useRouter()

const login = async () => {
    try {
        const res = await axios.post('/api/login', {
            email: email.value,
            password: password.value
        })
        // console.log('로그인 성공', res.data)
        router.push('/')
    } catch (err) {
        console.error('로그인 실패', err)
    }
}

</script>

<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="flex flex-col max-w-md p-6 rounded-md sm:p-10 bg-gray-50 text-gray-800">
            <div class="mb-8 text-center">
                <h1 class="my-3 text-4xl font-bold">Sign in</h1>
                <p class="text-sm text-gray-600">Sign in to access your account</p>
            </div>
            <form @submit.prevent="login" class="space-y-12">
                <div class="space-y-4">
                    <div>
                        <label for="email" class="block mb-2 text-sm">Email address</label>
                        <input type="email" id="email" v-model="email" placeholder="nickname@example.com"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                    <div>
                        <div class="flex justify-between mb-2">
                            <label for="password" class="text-sm">Password</label>
                            <!-- <a rel="noopener noreferrer" href="#" class="text-xs hover:underline text-gray-600">Forgot password?</a> -->
                        </div>
                        <input type="password" id="password" v-model="password" placeholder="*****"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                </div>
                <div class="space-y-2">
                    <div>
                        <button type="submit" class="w-full px-8 py-3 font-semibold rounded-md bg-red-600 text-gray-50">
                            Sign in
                        </button>
                    </div>
                    <p class="px-6 text-sm text-center text-gray-600">Don't have an account yet?
                        <RouterLink to="/register" class="hover:underline text-red-600">Sign up</RouterLink>.
                    </p>
                </div>
            </form>
        </div>
    </div>
</template>
