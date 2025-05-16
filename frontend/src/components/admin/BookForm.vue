<script setup>
import { ref, computed, onMounted } from 'vue'
import AuthorModal from './Modal.vue'
import CategoryModal from './CategoryModal.vue'
import SelectBox from './SelectBox.vue'
import FileUploadButton from './FileUploadButton.vue'
import { storeToRefs } from 'pinia'
import { useCategoryStore } from '../../stores/category'

const selectedMain = ref('')
const selectedSub = ref('')

const categoryStore = useCategoryStore()

// 카테고리 데이터를 가져오기
onMounted(() => {
    categoryStore.fetchCategory()
})

// const mainCategory = computed(() => categoryStore.getMainCategory)
// const subCategory = computed(() => categoryStore.getSubCategory)
// const smallCategory = computed(() => categoryStore.getSmallCategory)


// const filteredSub = computed(() =>
//     subCategory.value.filter(sub => sub.parentId === Number(selectedMain.value))
// )

// const filteredSmall = computed(() =>
//     smallCategory.value.filter(small => small.parentId === Number(selectedSub.value))
// )

const { mainCategory, rawCategory } = storeToRefs(categoryStore)

const filteredSub = computed(() =>
    rawCategory.value.filter(sub => sub.parentId === Number(selectedMain.value))
)

const filteredSmall = computed(() =>
    rawCategory.value.filter(small => small.parentId === Number(selectedSub.value))
)


const form = ref({
    title: '',
    authorId: '',
    category1: '',
    category2: '',
    category3: '',
    price: '',
    publishedDate: '',
    publisherId: '',
    photo: null,
})

const selectedAuthorName = ref('')
const showModal = ref(false)
const showCategoryModal = ref(false)
const defaultImage = 'https://source.unsplash.com/60x60/?book'





const selectAuthor = ({ id, name }) => {
    form.value.authorId = id
    selectedAuthorName.value = name
    showModal.value = false
}

const submitForm = () => {
    if (form.value.photo) {
        console.log('업로드된 파일:', form.value.photo)
    } else {
        // console.log('파일 없음')
        form.value.photo.value = defaultImage
    }
}

</script>


<template>
    <section class="p-6 bg-gray-100 text-gray-900">
        <form @submit.prevent="handleSubmit" class="container flex flex-col mx-auto space-y-5">
            <div class="space-y-1 col-span-full px-2">
                <p class="text-2xl font-semibold">도서 등록</p>
                <p class="text-sm text-gray-600">도서 정보를 입력해주세요</p>
            </div>

            <div class="grid grid-cols-4 gap-6 p-6 rounded-md shadow-sm bg-gray-50">
                <div class="grid grid-cols-6 gap-4 col-span-full lg:col-span-3">

                    <!-- 커버 이미지 -->
                    <div class="col-span-full">
                        <FileUploadButton v-model:file="form.photo" />
                    </div>

                    <!-- 제목 -->
                    <div class="col-span-full sm:col-span-3">
                        <label for="title" class="text-sm">제목</label>
                        <input id="title" v-model="form.title" type="text" placeholder="도서 제목"
                            class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900" />
                    </div>

                    <!-- 저자 -->
                    <div class="col-span-full sm:col-span-3">
                        <label class="text-sm">저자</label>
                        <div class="flex space-x-2">
                            <input type="text" v-model="selectedAuthorName" readonly
                                class="flex-1 p-2.5 text-sm rounded-md border border-gray-300 bg-gray-100 text-gray-900" />
                            <button type="button" @click="showModal = true"
                                class="px-4 py-2 text-sm border rounded-md border-gray-800 bg-white hover:bg-gray-100">
                                검색
                            </button>
                        </div>
                    </div>

                    <div class="col-span-full flex items-center space-x-2">
                        <label class="text-sm select-none">카테고리</label>
                        <button type="button" @click="showCategoryModal = true"
                            class="w-6 h-6 flex items-center justify-center text-gray-700 border border-gray-400 rounded-full hover:bg-gray-300 transition"
                            aria-label="카테고리 추가">
                            +
                        </button>
                    </div>

                    <!-- 카테고리 -->
                    <!-- <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="form.category1" :label="'대분류'" :options="[
                            { value: '문학', label: '문학' },
                            { value: '과학', label: '과학' }
                        ]" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="form.category1" :label="'중분류'" :options="[
                            { value: '문학2', label: '문학2' },
                            { value: '과학2', label: '과학2' }
                        ]" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="form.category1" :label="'소분류'" :options="[
                            { value: '문학3', label: '문학3' },
                            { value: '과학3', label: '과학3' }
                        ]" />
                    </div> -->


                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="selectedMain" :label="'대분류'"
                            :options="mainCategory.map(c => ({ value: c.categoryId, label: c.name }))" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="selectedSub" :label="'중분류'"
                            :options="filteredSub.map(c => ({ value: c.categoryId, label: c.name }))"
                            :disabled="!selectedMain" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="form.category3" :label="'소분류'"
                            :options="filteredSmall.map(c => ({ value: c.categoryId, label: c.name }))"
                            :disabled="!selectedSub" />
                    </div>



                    <!-- 출판사 -->
                    <div class="col-span-full sm:col-span-3">
                        <label class="text-sm">출판사</label>
                        <div class="flex space-x-2">
                            <input type="text" v-model="selectedAuthorName" readonly
                                class="flex-1 p-2.5 text-sm rounded-md border border-gray-300 bg-gray-100 text-gray-900" />
                            <button type="button" @click="showModal = true"
                                class="px-4 py-2 text-sm border rounded-md border-gray-800 bg-white hover:bg-gray-100">
                                검색
                            </button>
                        </div>
                    </div>

                    <!-- 출판일 -->
                    <div class="col-span-full sm:col-span-3">
                        <label for="publishedDate" class="text-sm">출판일</label>
                        <input id="publishedDate" v-model="form.publishedDate" type="date"
                            class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900" />
                    </div>

                    <!-- 가격 -->
                    <div class="col-span-full sm:col-span-3">
                        <label for="price" class="text-sm">가격</label>
                        <input id="price" v-model="form.price" type="number" min="0" placeholder="예: 15000"
                            class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900" />
                    </div>
                </div>
            </div>

            <!-- 저장 버튼 -->
            <div class="self-end">
                <button type="submit"
                    class="px-6 py-2 text-sm bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
                    도서 등록
                </button>
            </div>
        </form>

        <!-- 저자 모달 -->
        <AuthorModal v-if="showModal" @close="showModal = false" @select="selectAuthor" />

        <!-- 카테고리 추가 모달 -->
        <!-- <CategoryModal v-if="showCategoryModal" />  -->
        <CategoryModal :isOpen="showCategoryModal" @close="showCategoryModal = false" />
    </section>
</template>
  
