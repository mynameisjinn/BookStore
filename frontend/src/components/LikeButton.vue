<script setup>
import {computed, onMounted, watch} from 'vue'
import { useConfirmStore } from '../stores/confirm.js'
import { useAuthStore2 } from '../stores/auth-with-refresh.js'
import router from '../router'
import axios from 'axios'
import { useLikeStore } from '../stores/like.js'

const props = defineProps({
  bookId: {
    type: [String, Number],
    required: true
  }
})
const emit = defineEmits(['liked'])

const authStore = useAuthStore2()
const user = computed(() => authStore.user)
const role = computed(() => authStore.role)

const likeStore = useLikeStore()

// 좋아요 여부는 전역에서 관리
// const isLiked = computed(() => likeStore.isBookLiked(props.bookId))

const isLiked = computed(() => {
  const liked = likeStore.isBookLiked(props.bookId)
  // console.log(props.bookId, '좋아요 상태:', liked)
  // console.log('likedBookIds:', likeStore.likedBookIds)

  return liked
})


const confirmStore = useConfirmStore()

const toggleLike = () => {
  if (role.value !== 'ROLE_USER') {
    confirmStore.openConfirm({
      msg: '로그인이 필요한 기능입니다. 로그인하시겠습니까?',
      onOk: () => router.push('/login')
    })
    return
  }

  const message = isLiked.value ? '좋아요를 취소하시겠습니까?' : '좋아요를 누르시겠습니까?'

  confirmStore.openConfirm({
    msg: message,
    onOk: async () => {
      try {
        if (!user.value?.id) return

        if (isLiked.value) {
          // 좋아요 취소
          await axios.delete('/api/like', {
            data: {
              bookId: props.bookId,
              memberId: user.value.id
            }
          })
          likeStore.removeLike(props.bookId)
        } else {
          // 좋아요 등록
          await axios.post('/api/like', {
            bookId: props.bookId,
            memberId: user.value.id
          })
          likeStore.addLike(props.bookId)
        }

        emit('liked', { bookId: props.bookId, liked: !isLiked.value })

      } catch (error) {
        console.error('좋아요 실패:', error)
      }
    }
  })
}

/*onMounted(() => {
  if (user.value?.id) {
    likeStore.fetchLikedBooks(user.value.id)
  }
})*/

/*watch(user, (newUser) => {
  if (newUser?.id) {
    likeStore.fetchLikedBooks(newUser.id)
  }
}, { immediate: true })  // 바로 실행되도록 옵션 추가*/
</script>

<template>
  <button @click="toggleLike"
          class="w-6 h-6 transition-colors"
          :class="isLiked ? 'text-red-500' : 'text-gray-400 hover:text-red-500'">
    <svg xmlns="http://www.w3.org/2000/svg"
         :fill="isLiked ? 'currentColor' : 'none'"
         viewBox="0 0 24 24"
         stroke="currentColor"
         class="w-full h-full">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682
          a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318
          a4.5 4.5 0 00-6.364 0z" />
    </svg>
  </button>
</template>
