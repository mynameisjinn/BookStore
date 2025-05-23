<template>
  <div
      v-if="isVisible"
      class="fixed top-20 left-1/2 translate-x-20  bg-white shadow-lg rounded-md overflow-hidden w-[500px] z-50"
  >
    <div class="flex">
      <!-- 책 이미지 -->
      <img
          src="/images/main/ad_01.jpg"
          alt="책 이미지"
          class="w-1/3 object-contain bg-white p-2"
      />

      <!-- 설명 -->
      <div class="relative flex-1 bg-lightBlue-950 text-white p-4 flex flex-col justify-between">
        <!-- AD 뱃지 오른쪽 상단 고정 -->
        <span class="absolute top-2 right-2 bg-lightBlue-900 text-blue-300 text-xs font-medium px-2.5 py-0.5 rounded-full">AD</span>
        <div>
          <p class="text-lg font-bold leading-snug mb-5">하버드 의사가 처방하는<br>회복탄력성의 뇌과학</p>
          <p class="text-sm mb-5">김대식, 권준수<br>뇌과학자 강력 추천!</p>
          <p class="text-sm text-indigo-200">회복탄력성의 뇌과학<br>아디티 네루카 저 | 현대지성</p>
        </div>
        <div class="flex justify-between items-center mt-4 text-xs">
          <label class="flex items-center gap-1">
            <input type="checkbox" v-model="dontShowToday" />
            오늘은 그만 보기
          </label>
          <button @click="closePopup" class="text-white hover:underline">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const isVisible = ref(true)
const dontShowToday = ref(false)

const STORAGE_KEY = 'hidePopupUntil'

onMounted(() => {
  // 테스트용
  // localStorage.removeItem('hidePopupUntil')

  const stored = localStorage.getItem(STORAGE_KEY)
  if (stored && new Date(stored) > new Date()) {
    isVisible.value = false
  }
})

const closePopup = () => {
  if (dontShowToday.value) {
    const tomorrow = new Date()
    tomorrow.setDate(tomorrow.getDate() + 1)
    tomorrow.setHours(0, 0, 0, 0)
    localStorage.setItem(STORAGE_KEY, tomorrow.toISOString())
  }
  isVisible.value = false
}
</script>
