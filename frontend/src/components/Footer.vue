<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
    <div class="pt-10 border-t border-gray-300  bg-gray-100">
        <div class="mx-auto sm:max-w-xl md:max-w-full lg:max-w-screen-xl md:px-24 lg:px-8">
            <div class="grid gap-10 row-gap-6 mb-8 sm:grid-cols-2 lg:grid-cols-4">
                <!-- Company Info -->
                <div class="sm:col-span-2">
                    <a href="/" aria-label="Go home" title="Company" class="inline-flex items-center">
                        <!-- SVG 로고 -->
                        <img src="/logo.svg" alt="Logo" class="w-12 h-auto"  />
                        <span class="ml-2 text-xl font-bold tracking-wide text-gray-800 uppercase">Mebooks</span>
                    </a>
                    <div class="mt-6 lg:max-w-sm">
                        <p class="text-sm text-gray-800">
                            Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
                            laudantium,
                            totam rem aperiam.
                        </p>
                    </div>
                </div>

                <!-- Contact Info -->
                <div class="space-y-2 text-sm">
                    <p class="text-base font-bold tracking-wide text-gray-900">Contacts</p>
                    <div class="flex">
                        <p class="mr-1 text-gray-800">Phone:</p>
                        <a href="tel:850-123-5021"
                            class="text-deep-purple-accent-400 hover:text-deep-purple-800">850-123-5021</a>
                    </div>
                    <div class="flex">
                        <p class="mr-1 text-gray-800">Email:</p>
                        <a href="mailto:info@lorem.mail"
                            class="text-deep-purple-accent-400 hover:text-deep-purple-800">info@lorem.mail</a>
                    </div>
                    <div class="flex">
                        <p class="mr-1 text-gray-800">Address:</p>
                        <a href="https://www.google.com/maps" target="_blank" rel="noopener noreferrer"
                            class="text-deep-purple-accent-400 hover:text-deep-purple-800">312 Lovely Street, NY</a>
                    </div>
                </div>

                <!-- Social -->
                <div>
                    <span class="text-base font-bold tracking-wide text-gray-900">Social</span>
                    <div class="flex items-center mt-1 space-x-3">
                        <!-- 소셜 아이콘들 -->
                        <!-- ... 기존 아이콘 그대로 유지 ... -->
                    </div>
                    <p class="mt-4 text-sm text-gray-500">
                        Bacon ipsum dolor amet short ribs pig sausage prosciutto chicken spare ribs salami.
                    </p>
                </div>
            </div>

            <!-- 하단 저작권 및 링크 -->
            <div class="flex flex-col-reverse justify-between pt-5 pb-10 border-t lg:flex-row">
                <p class="text-sm text-gray-600">© Copyright 2025 Mebooks Corp. All rights reserved.</p>
                <ul class="flex flex-col mb-3 space-y-2 lg:mb-0 sm:space-y-0 sm:space-x-5 sm:flex-row">
                    <li><a href="/" class="text-sm text-gray-600 hover:text-deep-purple-accent-400">F.A.Q</a></li>
                    <li><a href="/" class="text-sm text-gray-600 hover:text-deep-purple-accent-400">Privacy Policy</a></li>
                    <li><a href="/" class="text-sm text-gray-600 hover:text-deep-purple-accent-400">Terms &amp;
                            Conditions</a></li>
                </ul>
            </div>
        </div>
    </div>
</template>
  