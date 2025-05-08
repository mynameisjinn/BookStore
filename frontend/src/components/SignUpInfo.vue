<script setup>
import NextButton from './NextButton.vue';

</script>
<template>
    <div class="mb-8 text-center">
        <h1 class="my-3 text-4xl font-bold">회원 정보입력</h1>
        <p class="text-sm text-gray-600">가입에 필요한 정보를 입력해주세요</p>
    </div>

    <form action="" class="space-y-12">
        <div class="space-y-4">
            <div>
                <label for="email" class="block mb-2 text-sm">Email address</label>
                <input type="email" name="email" id="email" placeholder="nickname@example.com"
                    class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
            </div>

            <!-- ID -->
            <div>
                <label for="name" class="block mb-2 text-sm">ID</label>
                <div class="flex gap-2">
                    <input type="text" name="name" id="name" placeholder="yourID"
                        class="flex-1 px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    <button type="button" class="px-4 py-2 text-sm font-medium rounded-md text-white hover:brightness-90"
                        style="background-color: #EB3232;">
                        Check
                    </button>
                    <div class="w-10 h-10 border-2 border-dashed rounded-full animate-spin border-red-600"></div>
                </div>
            </div>

            <!-- 비밀번호 -->
            <div>
                <label for="password" class="block mb-2 text-sm">Password</label>
                <input type="password" name="password" id="password" placeholder="••••••"
                    class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
            </div>

            <!-- 비밀번호 확인 -->
            <div>
                <label for="confirm-password" class="block mb-2 text-sm">Confirm Password</label>
                <input type="password" name="confirm-password" id="confirm-password" placeholder="••••••"
                    class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
            </div>

            <!-- <NextButton /> -->
        </div>
    </form>
</template>