<script setup>
import { ref } from 'vue'
import RedButton from "./buttons/RedButton.vue"
import ImgBox from "./ImgBox.vue";
import {useConfirmStore} from "../stores/confirm.js";
import LikeButton from "./LikeButton.vue";

// const isLiked = ref(false)
const confirmStore = useConfirmStore()

defineProps({
  book: {
    type: Object,
    required: true
  }
})

/*const handleLike = async () => {
  const message = isLiked.value ? '좋아요를 취소하시겠습니까?' : '좋아요를 누르시겠습니까?'
  confirmStore.openConfirm({

    msg: message,
    onOk: () => {
      isLiked.value = !isLiked.value
      // alert(isLiked.value ? '좋아요 클릭!' : '좋아요 취소!')
    }
  })
}*/

const handleLike = ({ bookId, liked }) => {
  console.log(`📘 Book ID: ${bookId}, 좋아요 상태: ${liked}`)
}

</script>

<template>
<!--  <h1> {{ book.imgPath  }}</h1>-->
  <div class="flex flex-col md:flex-row border-b border-gray-200 py-4">
    <!-- 이미지 -->

    <ImgBox :imgPath="book.imgPath || '/images/default-book-front.jpg'" />


    <!-- 내용 -->
    <div class="mt-4 md:mt-0 md:ml-6 flex flex-col justify-between flex-1">
      <div>
        <h2 class="text-lg font-bold">{{ book.title }}</h2>
<!--        <h2 class="text-lg font-bold">{{ book.id }}</h2>-->
        <div class="mt-2 text-gray-600 flex flex-wrap gap-x-2">
          <span>{{ book.authorName }}</span>
          <span class="hidden sm:inline">|</span>
          <span>{{ book.publisherName }}</span>
          <span class="hidden sm:inline">|</span>
          <span>{{ book.publishedDate }}</span>
        </div>
        <p class="mt-3 text-xl font-semibold text-black">{{ book.price}}</p>
      </div>

      <!-- 버튼들 -->
      <div class="mt-4 flex items-center space-x-4">
        <!-- 좋아요 버튼 -->
<!--        <button @click="handleLike"-->
<!--                class="w-6 h-6 transition-colors"-->
<!--                :class="isLiked ? 'text-red-500' : 'text-gray-400 hover:text-red-500'">-->
<!--          <svg xmlns="http://www.w3.org/2000/svg"-->
<!--               :fill="isLiked ? 'currentColor' : 'none'"-->
<!--               viewBox="0 0 24 24"-->
<!--               stroke="currentColor"-->
<!--               class="w-full h-full">-->
<!--            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"-->
<!--                  d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682-->
<!--          a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318-->
<!--          a4.5 4.5 0 00-6.364 0z" />-->
<!--          </svg>-->
<!--        </button>-->

        <LikeButton :bookId="book.id" @liked="handleLike" />


        <!-- 장바구니 버튼 -->
        <button class="w-6 h-6">
          <img src="/images/cart.svg" alt="cart" />
        </button>

        <!-- 구매 버튼 -->
        <RedButton text="구매" />
      </div>
    </div>
  </div>
</template>
