<script setup>
import {computed,} from 'vue'
import router from "../../router/index.js";
import {useConfirmStore} from "../../stores/confirm.js";
import {useAuthStore2} from "../../stores/auth-with-refresh.js";
import {useLikeStore} from "../../stores/like.js";
import axios from "axios";

const props = defineProps({
  menuId: {
    type: [String, Number],
    required: true
  }
})

const confirmStore = useConfirmStore()
const authStore = useAuthStore2()
const user = computed(() => authStore.user)
const role = computed(() => authStore.role)
const likeStore = useLikeStore()

const isFavorite = computed(() => {
  // 의존성 명시적으로 추적
  return likeStore.favoriteMenuList.some(item => item.id === Number(props.menuId))
})

const toggleFavorite = async () => {

  if (role.value !== 'ROLE_USER') {
    confirmStore.openConfirm({
      msg: '로그인이 필요한 기능입니다. 로그인하시겠습니까?',
      onOk: () => router.push('/login')
    })
    return
  }

  try {
    if (!user.value?.id) return

    if (isFavorite.value) {
      // 즐겨찾기 취소
      await axios.delete('/api/like/menu', {
        data: {
          menuId: props.menuId,
          memberId: user.value.id
        }
      })
      likeStore.removeMenu(props.menuId)
    } else {
      // 즐겨찾기 등록
      await axios.post('/api/like/menu', {
        menuId: props.menuId,
        memberId: user.value.id
      })
      likeStore.addMenu({id: Number(props.menuId)})
    }
  } catch (error) {
    console.error('즐겨찾기 실패:', error)
  }
}
</script>

<template>
  <button @click="toggleFavorite" class="w-6 h-6 transition-colors">
    <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        :fill="isFavorite ? '#facc15' : 'none'"
        class="w-5 h-5 ml-2 shrink-0 cursor-pointer transition-all duration-200"
    >
      <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M11.48 3.499a.562.562 0 0 1 1.04 0l2.125 5.111a.563.563 0 0 0 .475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 0 0-.182.557l1.285 5.385a.562.562 0 0 1-.84.61l-4.725-2.885a.562.562 0 0 0-.586 0L6.982 20.54a.562.562 0 0 1-.84-.61l1.285-5.386a.562.562 0 0 0-.182-.557l-4.204-3.602a.562.562 0 0 1 .321-.988l5.518-.442a.563.563 0 0 0 .475-.345L11.48 3.5Z"
      />
    </svg>
  </button>
</template>
