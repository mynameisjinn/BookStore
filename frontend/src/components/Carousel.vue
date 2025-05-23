<script setup>
import { ref, onMounted, onUnmounted } from 'vue'



const props = defineProps({
  images: {
    type: Array,
    required: true
  },
  height: {
    type: String,
    default: 'h-[400px]'
  }
})

const activeSlide = ref(0)
const slides = props.images.length
let timer = null

const startAutoSlide = () => {
    timer = setInterval(() => {
        activeSlide.value = (activeSlide.value + 1) % slides
    }, 3000)
}

const stopAutoSlide = () => {
    clearInterval(timer)
}

const goToPrev = () => {
    activeSlide.value = (activeSlide.value - 1 + slides) % slides
    stopAutoSlide()
}

const goToNext = () => {
    activeSlide.value = (activeSlide.value + 1) % slides
    stopAutoSlide()
}

const goToSlide = (index) => {
    activeSlide.value = index
    stopAutoSlide()
}

onMounted(() => {
    startAutoSlide()
})

onUnmounted(() => {
    stopAutoSlide()
})
</script>

<template>
    <section class="bg-gray-100 px-0 py-0">
        <div class="w-full relative group">
            <div class="relative overflow-hidden rounded-lg shadow-xl">
                <div class="flex transition-transform duration-500 ease-in-out"
                    :style="`transform: translateX(-${activeSlide * 100}%)`">
                  <div
                      v-for="(image, index) in images"
                      :key="index"
                      class="min-w-full flex items-center justify-center text-white text-4xl overflow-hidden"
                      :class="height"
                  >
                    <img
                        :src="image"
                        :alt="`슬라이드 ${index + 1}`"
                        class="w-full h-full object-cover"
                    />
                  </div>
                </div>
            </div>

            <button @click="goToPrev"
                class="absolute top-1/2 left-4 -translate-y-1/2 bg-white/30 hover:bg-white/50 rounded-full p-2.5 transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                    class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                        d="M15.75 19.5L8.25 12l7.5-7.5" />
                </svg>
            </button>

            <button @click="goToNext"
                class="absolute top-1/2 right-4 -translate-y-1/2 bg-white/30 hover:bg-white/50 rounded-full p-2.5 transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                    class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                </svg>
            </button>

            <div class="absolute bottom-4 left-1/2 -translate-x-1/2 flex space-x-2">
                <button v-for="i in slides" :key="i" @click="goToSlide(i - 1)"
                    class="w-3 h-3 rounded-full transition-colors"
                    :class="activeSlide === i - 1 ? 'bg-white' : 'bg-white/50'"></button>
            </div>
        </div>
    </section>
</template>
