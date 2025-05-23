<script setup>
import { ref, defineEmits, defineProps, watch } from 'vue'
import ImgBox from "../ImgBox.vue";

const props = defineProps({
  label: { type: String, default: '커버 이미지' },
  accept: {
    type: String,
    default: 'image/png, image/jpeg, image/svg+xml, image/gif',
  },
  placeholder: { type: String, default: '선택된 파일 없음' },
  helpText: {
    type: String,
    default: 'SVG, PNG, JPG, GIF (MAX. 800x400px)',
  },
  // inputId: { type: String, default: 'file-upload' },
  imgPath: { type: String, default: '' }, // 기존 이미지 URL
})

const emit = defineEmits(['update:file'])

const fileName = ref('')
const previewUrl = ref(props.imgPath || '/images/default-book.jpg')
const fileInput = ref(null)

const triggerFileInput = () => {
  fileInput.value?.click()
}

const onFileChange = (e) => {
  const file = e.target.files[0]
  if (file) {
    const validTypes = ['image/png', 'image/jpeg', 'image/svg+xml']
    const maxSize = 10 * 1024 * 1024 // 10MB
    if (!validTypes.includes(file.type)) {
      alert('PNG, JPG, SVG 만 업로드 가능합니다.')
      e.target.value = ''
      fileName.value = ''
      emit('update:file', null)
      return
    }

    if (file.size > maxSize) {
      alert('파일 크기는 10MB 이하여야 합니다.')
      e.target.value = ''
      fileName.value = ''
      emit('update:file', null)
      return
    }


    fileName.value = file.name
    previewUrl.value = URL.createObjectURL(file)
    emit('update:file', file)
  }
}

// props.imgPath가 변경되면 previewUrl도 갱신
watch(() => props.imgPath, (newPath) => {
  if (!fileName.value && newPath) {
    previewUrl.value = newPath
  }
}, {immediate: true})

</script>

<template>
  <div>
    <label class="text-sm block mb-1">{{ label }}</label>
    <div class="flex items-start space-x-4">
      <!-- 이미지 미리보기 -->
      <ImgBox :imgPath=previewUrl />

      <!-- 파일 업로드 UI -->
      <div class="flex-1">
        <div class="flex items-center">
          <input
              type="file"
              ref="fileInput"
              @change="onFileChange"
              :accept="accept"
              class="hidden"
          />
          <button
              type="button"
              @click="triggerFileInput"
              class="px-4 py-2 text-sm border border-gray-300 rounded-lg bg-white hover:bg-gray-100 transition"
          >
            업로드
          </button>
          <input
              v-if="!imgPath"
              type="text"
              readonly
              :value="fileName || placeholder"
              class="ml-1 p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900 cursor-not-allowed focus:outline-none"
          />
        </div>
        <p class="mt-1 text-xs text-gray-500">{{ helpText }}</p>
      </div>
    </div>
  </div>
</template>
