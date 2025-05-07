<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
        <div class="flex flex-col max-w-md p-6 rounded-md sm:p-10 bg-gray-50 text-gray-800">
            <div class="mb-8 text-center">
                <h1 class="my-3 text-4xl font-bold">Sign Up</h1>
                <p class="text-sm text-gray-600">Create your account</p>
            </div>
            <form novalidate="" action="" class="space-y-12">
                <div class="space-y-4">
                    <div>
                        <label for="email" class="block mb-2 text-sm">Email address</label>
                        <input type="email" name="email" id="email" placeholder="nickname@example.com"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                    <div>
                        <label for="name" class="block mb-2 text-sm">ID</label>
                        <div class="flex gap-2">
                            <input type="text" name="name" id="name" placeholder="yourID"
                                class="flex-1 px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                            <button type="button"
                                class="px-4 py-2 text-sm font-medium rounded-md text-white hover:brightness-90"
                                style="background-color: #EB3232;">
                                Check
                            </button>
                            <div class="w-10 h-10 border-2 border-dashed rounded-full animate-spin border-red-600"></div>
                        </div>
                    </div>
                    <div>
                        <label for="password" class="block mb-2 text-sm">Password</label>
                        <input type="password" name="password" id="password" placeholder="••••••"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                    <div>
                        <label for="confirm-password" class="block mb-2 text-sm">Confirm Password</label>
                        <input type="password" name="confirm-password" id="confirm-password" placeholder="••••••"
                            class="w-full px-3 py-2 border rounded-md border-gray-300 bg-gray-50 text-gray-800">
                    </div>
                </div>
                <div class="space-y-2">
                    <div>
                        <button type="submit" class="w-full px-8 py-3 font-semibold rounded-md bg-red-600 text-gray-50">Sign
                            Up</button>
                    </div>
                    <p class="px-6 text-sm text-center text-gray-600">Already have an account?
                        <RouterLink to="/login" class="hover:underline text-red-600">Sign in</RouterLink>.
                    </p>
                </div>
            </form>
        </div>
    </div>
</template>

<style lang="scss" scoped></style>