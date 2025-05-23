<script setup>
import {RouterLink, useRouter} from 'vue-router'
import {useAuthStore} from '../stores/auth'
import {computed} from 'vue'
import {useToast} from 'vue-toastification'
import HeaderBottom from "../components/HeaderBottom.vue";

const toast = useToast();

const router = useRouter()

const authStore = useAuthStore()
const user = computed(() => authStore.user)

const username = computed(() => {
  const email = user.value?.email
  return email ? email.split('@')[0] : ''
})

const logout = () => {
  authStore.logout()
  router.push('/')
  toast.warning("로그아웃 되었습니다");
}
</script>

<template>
  <div class="bg-red-600 text-white text-sm mb-5">
    <div class="max-w-screen-xl mx-auto px-4 py-2 flex justify-end items-center gap-4">
      <p v-if="user" class="hidden sm:block">안녕하세요, <span class="font-semibold">{{ username }}</span>님</p>
      <RouterLink v-if="!user" to="/login"
                  class="bg-white text-red-600 font-semibold px-3 py-1 rounded hover:bg-gray-100 transition duration-150">
        로그인
      </RouterLink>
      <button v-if="user" @click="logout"
              class="bg-white text-red-600 font-semibold px-3 py-1 rounded hover:bg-gray-100 transition duration-150">
        로그아웃
      </button>
    </div>
  </div>
  <HeaderBottom />
</template>
