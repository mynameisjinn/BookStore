<template>
    <section class="p-6 bg-gray-100 text-gray-900">
        <form @submit.prevent="handleSubmit" class="container flex flex-col mx-auto space-y-12">
            <div class="grid grid-cols-4 gap-6 p-6 rounded-md shadow-sm bg-gray-50">
                <div class="space-y-2 col-span-full lg:col-span-1">
                    <p class="font-medium">Profile</p>
                    <p class="text-xs">Adipisci fuga autem eum!</p>
                </div>

                <div class="grid grid-cols-6 gap-4 col-span-full lg:col-span-3">
                    <div class="col-span-full sm:col-span-3">
                        <label for="username" class="text-sm">Username</label>
                        <input id="username" v-model="form.username" type="text" placeholder="Username"
                            class="w-full rounded-md focus:ring focus:ring-opacity-75 text-gray-900 focus:ring-red-600 border-gray-300">
                    </div>

                    <div class="col-span-full sm:col-span-3">
                        <label for="website" class="text-sm">Website</label>
                        <input id="website" v-model="form.website" type="text" placeholder="https://"
                            class="w-full rounded-md focus:ring focus:ring-opacity-75 text-gray-900 focus:ring-red-600 border-gray-300">
                    </div>

                    <div class="col-span-full">
                        <label for="bio" class="text-sm">Bio</label>
                        <textarea id="bio" v-model="form.bio"
                            class="w-full rounded-md focus:ring focus:ring-opacity-75 text-gray-900 focus:ring-red-600 border-gray-300"></textarea>
                    </div>

                    <div class="col-span-full">
                        <label for="photo" class="text-sm">Photo</label>
                        <div class="flex items-center space-x-2">
                            <img :src="previewImage || defaultImage" alt="preview"
                                class="w-10 h-10 rounded-full bg-gray-300 object-cover" />
                            <input id="photo" type="file" ref="fileInput" @change="handleFileChange" class="hidden" />
                            <button type="button" @click="$refs.fileInput.click()"
                                class="px-4 py-2 border rounded-md border-gray-800">Change</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 예시 저장 버튼 -->
            <div class="self-end">
                <button type="submit" class="px-6 py-2 bg-red-600 text-white rounded-md">Save</button>
            </div>
        </form>
    </section>
</template>

<script setup>
import { ref } from 'vue'

const form = ref({
    username: '',
    website: '',
    bio: '',
    photo: null,
})

const previewImage = ref(null)
const defaultImage = 'https://source.unsplash.com/30x30/?random'

const handleFileChange = (event) => {
    const file = event.target.files[0]
    if (file) {
        form.value.photo = file
        previewImage.value = URL.createObjectURL(file)
    }
}

const handleSubmit = () => {
    const formData = new FormData()
    formData.append('username', form.value.username)
    formData.append('website', form.value.website)
    formData.append('bio', form.value.bio)
    if (form.value.photo) {
        formData.append('photo', form.value.photo)
    }

    // 실제 업로드 예시 (axios 사용)
    // import axios from 'axios'
    // axios.post('/api/profile', formData)
    //   .then(response => console.log(response))
    //   .catch(error => console.error(error))

    console.log('폼 데이터 준비 완료', [...formData.entries()])
}
</script>
