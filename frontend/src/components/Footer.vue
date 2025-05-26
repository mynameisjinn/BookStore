<script setup>
import FooterSocial from "./FooterSocial.vue";
import FooterContact from "./FooterContact.vue";
</script>

<template>
  <footer class="bg-gray-100 border-t border-gray-300 pt-10 text-gray-800">
    <div class="max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="grid gap-10 mb-10 sm:grid-cols-2 lg:grid-cols-4">
        <!-- 브랜드 로고 및 소개 -->
        <div class="sm:col-span-2">
          <a href="/" class="inline-flex items-center">
            <img src="/logo.svg" alt="Logo" class="w-10 h-auto" />
            <span class="ml-2 text-xl font-semibold">MEBOOKS</span>
          </a>
          <p class="mt-4 text-sm leading-relaxed text-gray-700 max-w-sm">
            책으로 연결되는 세상, MEBOOKS와 함께하세요.
          </p>
        </div>

        <!-- 연락처 -->
        <FooterContact />

        <!-- 소셜 & 소개 -->
        <FooterSocial />
      </div>

      <!-- 하단 링크 및 저작권 -->
      <div class="flex flex-col-reverse items-center justify-between border-t pt-6 pb-8 lg:flex-row text-sm text-gray-600">
        <p class="mt-4 lg:mt-0">© 2025 MEBOOKS Corp. All rights reserved.</p>
        <div class="flex space-x-4">
          <a href="#" class="hover:underline">F.A.Q</a>
          <a href="#" class="hover:underline">개인정보처리방침</a>
          <a href="#" class="hover:underline">이용약관</a>
        </div>
      </div>
    </div>
  </footer>
</template>
