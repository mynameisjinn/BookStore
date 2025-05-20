<script setup>
import {ref, computed, onMounted, watch} from 'vue'
import SearchAuthor from '../../components/modal/SearchModal.vue'
import SearchPublisher from '../../components/modal/SearchModal.vue'
import CategoryModal from '../../components/modal/CategoryModal.vue'
import SelectBox from '../../components/admin/SelectBox.vue'
import FileUploadButton from '../../components/admin/FileUploadButton.vue'
import { storeToRefs } from 'pinia'
import { useCategoryStore } from '../../stores/category.js'
import AddButton from "../../components/admin/AddButton.vue";
import SearchButton from "../../components/admin/SearchButton.vue";
import axios from "axios";
import {useAuthStore} from "../../stores/auth.js";
import SubmitButton from "../../components/admin/SubmitButton.vue";

const selectedMain = ref('')
const selectedSub = ref('')

const selectedAuthorName = ref('')
const selectedAuthorName2 = ref('')

const showAuthorModal = ref(false)
const showPublisherModal = ref(false)
const showCategoryModal = ref(false)

const isSubmitted = ref(false)


const titleInput = ref(null)
const authorInput = ref(null)
const publisherInput = ref(null)
const priceInput = ref(null)
const dateInput = ref(null)


const validationErrors = ref({
  title: false,
  authorId: false,
  categoryId: false,
  price: false,
  publishedDate: false,
  publisherId: false
})

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
    categoryId: '',
    price: '',
    publishedDate: '',
    publisherId: '',
    imgFile: null,
})


const selectAuthor = ({ id, name }) => {
    form.value.authorId = id
    selectedAuthorName.value = name
    showAuthorModal.value = false
}

const selectPublisher = ({ id, name }) => {
  form.value.publisherId = id
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




watch(form, (newForm) => {

  if (!isSubmitted.value) return

  validationErrors.value.title = !newForm.title
  validationErrors.value.authorId = !newForm.authorId
  validationErrors.value.categoryId = !newForm.categoryId
  validationErrors.value.price = !newForm.price
  validationErrors.value.publishedDate = !newForm.publishedDate
  validationErrors.value.publisherId = !newForm.publisherId
}, { deep: true })



const handleSubmit = async () => {

  isSubmitted.value = true

  // 유효성 검사
  const errors = validationErrors.value

  errors.title = !form.value.title
  errors.authorId = !form.value.authorId
  errors.categoryId = !form.value.categoryId
  errors.price = !form.value.price
  errors.publishedDate = !form.value.publishedDate
  errors.publisherId = !form.value.publisherId

  const focusMap = {
    title: titleInput,
    authorId: authorInput,
    // categoryId: categoryInput,
    price: priceInput,
    publishedDate: dateInput,
    publisherId: publisherInput
  }

  for (const key in errors) {
    if (errors[key]) {
      focusMap[key]?.value?.focus()
      return
    }
  }

  // 제출 로직
  console.log('도서 등록 폼 데이터:', form.value)

  const formData = new FormData()

  // BookVO 필드
  formData.append("title", form.value.title)
  formData.append("authorId", form.value.authorId)
  formData.append("categoryId", form.value.categoryId)
  formData.append("price", form.value.price)
  formData.append("publishedDate", form.value.publishedDate)
  formData.append("publisherId", form.value.publisherId)

  // 이미지 (FileUploadButton에서 file 객체로 바인딩된다고 가정)
  if (form.value.imgFile) {
    formData.append("imgFile", form.value.imgFile)
  }



  try {
    const authStore = useAuthStore()
    authStore.loadToken()

    await axios.post('/api/admin/book/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: `Bearer ${authStore.token}`
      }
    })
    alert('등록 성공!')
  } catch (err) {
    console.error(err)
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

            <div class="grid grid-cols-3 gap-6 p-6 rounded-md shadow-sm bg-gray-50">
                <div class="grid grid-cols-6 gap-4 col-span-full lg:col-span-3">

                    <!-- 커버 이미지 -->
                    <div class="col-span-full">
                        <FileUploadButton v-model:file="form.imgFile" />
                    </div>

<!--                  focus:outline-none focus:ring-1-->
                    <!-- 제목 -->
                    <div class="col-span-full sm:col-span-3">
                      <label for="title" class="text-sm font-medium text-gray-700">제목</label>
                      <input
                          id="title"
                          v-model="form.title"
                          ref="titleInput"
                          type="text"
                          placeholder="도서 제목"
                          class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900"
                      />
<!--                      <p class="mt-1 text-sm text-red-500">제목은 필수입니다</p>-->
                      <p v-if="validationErrors.title" class="mt-1 text-sm text-red-500">도서 제목은 필수입니다</p>

                    </div>


                  <!-- 저자 -->
                    <div class="col-span-full sm:col-span-3">
                        <label class="text-sm">저자</label>
                        <div class="flex space-x-2">
                            <input type="text" v-model="selectedAuthorName" readonly
                                   ref="authorInput"
                                class="flex-1 p-2.5 text-sm rounded-md border border-gray-300 bg-gray-100 text-gray-900 " />
                          <SearchButton @search="showAuthorModal = true"/>
                        </div>
                      <p v-if="validationErrors.authorId" class="mt-1 text-sm text-red-500">저자는 필수입니다</p>
                    </div>

                    <div class="col-span-full flex items-center space-x-2">
                      <AddButton  @add="showCategoryModal= true"/>
                    </div>

                    <!-- 카테고리 -->
                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="selectedMain" :label="'대분류'"
                            :options="mainCategory.map(c => ({ value: c.id, label: c.name }))" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="selectedSub" :label="'중분류'"
                            :options="filteredSub.map(c => ({ value: c.id, label: c.name }))"
                            :disabled="!selectedMain" />
                    </div>

                    <div class="col-span-full sm:col-span-2">
                        <SelectBox v-model="form.categoryId" :label="'소분류'"
                            :options="filteredSmall.map(c => ({ value: c.id, label: c.name }))"
                            :disabled="!selectedSub" />

                      <p v-if="validationErrors.categoryId" class="mt-1 text-sm text-red-500">카테고리는 소분류까지 선택해주세요</p>
                    </div>



                    <!-- 출판사 -->
                    <div class="col-span-full sm:col-span-3">
                        <label class="text-sm">출판사</label>
                        <div class="flex space-x-2">
                            <input type="text" v-model="selectedAuthorName2" readonly
                                   ref="publisherInput"
                                class="flex-1 p-2.5 text-sm rounded-md border border-gray-300 bg-gray-100 text-gray-900" />
                            <SearchButton @search="showPublisherModal = true"/>
                        </div>
                        <p v-if="validationErrors.publisherId" class="mt-1 text-sm text-red-500">출판사는 필수입니다</p>
                    </div>

                    <!-- 출판일 -->
                    <div class="col-span-full sm:col-span-3">
                        <label for="publishedDate" class="text-sm">출판일</label>
                        <input id="publishedDate" v-model="form.publishedDate"
                               ref="dateInput"
                               type="date"
                            class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900" />
                        <p v-if="validationErrors.publishedDate" class="mt-1 text-sm text-red-500">출판일은 필수입니다</p>
                    </div>

                    <!-- 가격 -->
                    <div class="col-span-full sm:col-span-3">
                        <label for="price" class="text-sm">가격</label>
                        <input id="price" v-model="form.price"
                               ref="priceInput"
                               type="number" min="0" placeholder="예: 15000"
                            class="w-full p-2.5 text-sm rounded-md border border-gray-300 focus:ring-red-600 focus:border-red-600 bg-white text-gray-900" />
                        <p v-if="validationErrors.price" class="mt-1 text-sm text-red-500">가격은 필수입니다</p>
                    </div>
                </div>
            </div>

            <!-- 저장 버튼 -->
            <div class="self-end">
              <SubmitButton text="등록 ? 수정"/>
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
  
