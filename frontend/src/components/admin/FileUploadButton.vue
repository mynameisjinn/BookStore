<template>
    <div>
        <label :for="inputId" class="text-sm block mb-1">{{ label }}</label>
        <div class="flex items-center">
            <input :id="inputId" type="file" ref="fileInput" @change="onFileChange" :accept="accept" class="hidden" />
            <button type="button" @click="triggerFileInput"
                class="px-4 py-2 text-sm border border-gray-300 rounded-lg bg-white hover:bg-gray-100 transition">
                업로드
            </button>
            <input type="text" readonly :value="fileName || placeholder"
                class="ml-1 p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900 cursor-not-allowed focus:outline-none" />
        </div>
        <p class="mt-1 text-xs text-gray-500">{{ helpText }}</p>
    </div>
</template>
  
<script setup>
import { ref, defineEmits, defineProps } from 'vue'

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
    inputId: { type: String, default: 'file-upload' },
})

const emit = defineEmits(['update:file'])

const fileName = ref('')

const triggerFileInput = () => {
    fileInput.value?.click()
}

const fileInput = ref(null)

const onFileChange = (e) => {
    const file = e.target.files[0]
    if (file) {
        const validTypes = ['image/png', 'image/jpeg', 'image/svg+xml', 'image/gif']
        if (!validTypes.includes(file.type)) {
            alert('PNG, JPG, SVG, GIF만 업로드 가능합니다.')
            e.target.value = ''
            fileName.value = ''
            emit('update:file', null)
            return
        }
        fileName.value = file.name
        emit('update:file', file)
    }
}
</script>
  