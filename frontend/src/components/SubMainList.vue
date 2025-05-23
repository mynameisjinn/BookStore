<script setup>
const books = [
  { title: "마흔에 읽는 비트겐슈타인", src: "/images/main/recommend_01.jpg" },
  { title: "행동은 불안을 이긴다", src: "/images/main/recommend_02.jpg" },
  { title: "행복할 거야 이래도 되나 싶을 정도로", src: "/images/main/recommend_03.jpg" },
  { title: "일류의 조건", src: "/images/main/recommend_04.jpg" }
]
</script>

<template>
  <section class="py-6 sm:py-12 text-gray-800">
    <div class="container p-6 mx-auto space-y-8">
      <div class="space-y-2 text-center">
        <h2 class="text-3xl font-bold">이달의 추천 도서</h2>
        <p class="font-serif text-sm text-gray-600">이달의 추천도서를 만나보세요</p>
      </div>
      <div class="grid grid-cols-1 gap-x-4 gap-y-8 md:grid-cols-2 lg:grid-cols-4">
        <article v-for="(book, index) in books"
                 :key="index"
                 class="flex flex-col">
          <a
              rel="noopener noreferrer"
              href="#"
              class="flex justify-center items-center h-[300px]"
          >
            <img
                alt="책 표지"
                class="object-contain h-full border-2"
                :src="book.src"
            >
          </a>
          <div class="flex flex-col flex-1 p-4">
            <h3 class="text-base font-semibold leading-snug">
              {{ book.title }}
            </h3>
          </div>
        </article>
      </div>
    </div>
  </section>
</template>
<script setup>
</script>