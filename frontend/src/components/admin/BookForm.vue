<script setup>
import { ref, computed, onMounted } from 'vue'
import SearchAuthor from '../modal/SearchModal.vue'
import SearchPublisher from '../modal/SearchModal.vue'
import CategoryModal from '../modal/CategoryModal.vue'
import SelectBox from './SelectBox.vue'
import FileUploadButton from './FileUploadButton.vue'
import { storeToRefs } from 'pinia'
import { useCategoryStore } from '../../stores/category'
import AddButton from "./AddButton.vue";
import SearchButton from "./SearchButton.vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth.js";

const selectedMain = ref('')
const selectedSub = ref('')

const categoryStore = useCategoryStore()

// 카테고리 데이터를 가져오기
onMounted(() => {
    categoryStore.fetchCategory()
})

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
    authorName: '',
    category1: '',
    category2: '',
    category3: '',
    price: '',
    publishedDate: '',
    publisherId: '',
    photo: null,
})

const selectedAuthorName = ref('')
const selectedAuthorName2 = ref('')

const showAuthorModal = ref(false)
const showPublisherModal = ref(false)

const showCategoryModal = ref(false)
const defaultImage = 'https://source.unsplash.com/60x60/?book'

const selectAuthor = ({ id, name }) => {
    form.value.AuthorId = id
    selectedAuthorName.value = name
    showAuthorModal.value = false
}

const selectPublisher = ({ id, name }) => {
  form.value.PublisherId = id
  selectedAuthorName2.value = name
  showPublisherModal.value = false
}

const searchAuthor = async (query) => {

  const authStore = useAuthStore()
  authStore.loadToken()

  try {
    const res = await axios.get('/api/admin/book/author', {
      headers: {
        Authorization: `Bearer ${authStore.token}`
      },
      params: {
        searchVal: query
      }
    })
    return res.data

  } catch (e) {
    console.log(e)
  }

}


const searchPublisher = async (query) => {

  const authStore = useAuthStore()
  authStore.loadToken()

  try {
    const res = await axios.get('/api/admin/book/publisher', {
      headers: {
        Authorization: `Bearer ${authStore.token}`
      },
      params: {
        searchVal: query
      }
    })
    console.log(res.data)

    return res.data

  } catch (e) {
    console.log(e)
  }
}


const submitForm = () => {
  if (form.value.photo) {
    console.log('업로드된 파일:', form.value.photo)
  } else {
    // console.log('파일 없음')
    form.value.photo.value = defaultImage
  }
}

const handleSubmit = () => {
  console.log('도서 등록 폼 데이터:', form.value)
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
                          <SearchButton @search="showAuthorModal = true"/>
                        </div>
                    </div>

                    <div class="col-span-full flex items-center space-x-2">
                      <AddButton  @add="showCategoryModal= true"/>
                    </div>

                    <!-- 카테고리 -->
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
                            <input type="text" v-model="selectedAuthorName2" readonly
                                class="flex-1 p-2.5 text-sm rounded-md border border-gray-300 bg-gray-100 text-gray-900" />
                            <SearchButton @search="showPublisherModal = true"/>
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

      <SearchAuthor
          v-if="showAuthorModal"
          @close="showAuthorModal = false"
          @select="selectAuthor"
          :onSearch="searchAuthor"
          sub="저자"
      />

      <SearchPublisher
          v-if="showPublisherModal"
          @close="showPublisherModal = false"
          @select="selectPublisher"
          :onSearch="searchPublisher"
          sub="출판사"
      />

      <!-- 카테고리 추가 모달 -->
        <CategoryModal :isOpen="showCategoryModal" @close="showCategoryModal = false" />
    </section>
</template>
  
