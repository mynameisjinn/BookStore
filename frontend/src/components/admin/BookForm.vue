<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { storeToRefs } from 'pinia'
import axios from 'axios'

// Components
import SearchModal from '../modals/SearchModal.vue'
import CategoryModal from '../modals/CategoryModal.vue'
import SelectBox from '../../components/admin/SelectBox.vue'
import FileUploadButton from '../buttons/FileUploadButton.vue'
import AddButton from '../buttons/AddButton.vue'
import SearchButton from '../buttons/SearchButton.vue'
import SubmitButton from '../buttons/SubmitButton.vue'

// Stores
import { useCategoryStore } from '../../stores/category.js'
import { useAuthorStore } from '../../stores/author.js'
import { usePublisherStore } from '../../stores/publisher.js'

// Form 상태
const form = ref({
  title: '', authorId: '', categoryId: '',
  price: '', publishedDate: '', publisherId: '',
  imgPath: '', imgFile: null
})

const validationErrors = ref({
  title: false, authorId: false, categoryId: false,
  price: false, publishedDate: false, publisherId: false
})

const isSubmitted = ref(false)

// 입력창 포커스용 ref
const titleInput = ref(null)
const authorInput = ref(null)
const publisherInput = ref(null)
const priceInput = ref(null)
const dateInput = ref(null)

// props, emit
const emit = defineEmits(['submit'])
const props = defineProps({
  mode: { type: String, default: 'create' },
  defaultFormData: { type: Object, default: () => ({}) }
})

// 모달 상태
const showAuthorModal = ref(false)
const showPublisherModal = ref(false)
const showCategoryModal = ref(false)

const selectedAuthorName = ref('')
const selectedPublisherName = ref('')

// 카테고리 선택
const selectedMain = ref('')
const selectedSub = ref('')

// 카테고리 불러오기
const categoryStore = useCategoryStore()
onMounted(() => categoryStore.fetchCategory())
const { mainCategory, rawCategory } = storeToRefs(categoryStore)

const filteredSub = computed(() =>
    rawCategory.value.filter(sub => sub.parentId === Number(selectedMain.value))
)
const filteredSmall = computed(() =>
    rawCategory.value.filter(small => small.parentId === Number(selectedSub.value))
)

// 작가 & 출판사 검색
const searchVar = ref('')
const authorStore = useAuthorStore()
const publisherStore = usePublisherStore()

onMounted(async () => {
  await authorStore.fetchAuthors()
  await publisherStore.fetchPublishers()
})

const filteredAuthors = computed(() => authorStore.filteredAuthors(searchVar.value))
const filteredPublishers = computed(() => publisherStore.filteredPublishers(searchVar.value))

const selectItem = (target, { id, name }) => {
  form.value[target] = id
  if (target === 'authorId') selectedAuthorName.value = name
  if (target === 'publisherId') selectedPublisherName.value = name
  if (target === 'authorId') showAuthorModal.value = false
  if (target === 'publisherId') showPublisherModal.value = false
}

// props로 받은 값 세팅
watch(() => props.defaultFormData, (newVal) => {
  form.value = { ...form.value, ...newVal }

  if (props.mode === 'edit') {
    selectedAuthorName.value = newVal.authorName || ''
    selectedPublisherName.value = newVal.publisherName || ''

    const smallCategory = rawCategory.value.find(c => c.id === newVal.categoryId)
    if (smallCategory) {
      const subCategory = rawCategory.value.find(c => c.id === smallCategory.parentId)
      if (subCategory) {
        selectedSub.value = subCategory.id
        selectedMain.value = subCategory.parentId
      }
    }
  }
}, { immediate: true })

// 유효성 검사
watch(form, (newForm) => {
  if (!isSubmitted.value) return
  validateForm()
}, { deep: true })

watch(selectedMain, () => {
  selectedSub.value = ''
  form.value.categoryId = ''
})

const validateForm = () => {
  const f = form.value
  validationErrors.value.title = !f.title
  validationErrors.value.authorId = !f.authorId
  validationErrors.value.categoryId = !f.categoryId || !selectedMain.value || !selectedSub.value
  validationErrors.value.price = !f.price
  validationErrors.value.publishedDate = !f.publishedDate
  validationErrors.value.publisherId = !f.publisherId
}

const handleSubmit = () => {
  isSubmitted.value = true
  validateForm()
  const errors = validationErrors.value
  const focusMap = { title: titleInput, authorId: authorInput, price: priceInput, publishedDate: dateInput, publisherId: publisherInput }

  for (const key in errors) {
    if (errors[key]) {
      focusMap[key]?.value?.focus()
      return
    }
  }
  emit('submit', { ...form.value })
}
</script>
<template>
  <section class="p-6 bg-gray-100 text-gray-900">
    <form @submit.prevent="handleSubmit" class="container flex flex-col mx-auto space-y-5">
      <div class="space-y-1 col-span-full px-2">
        <p class="text-2xl font-semibold">도서 {{ mode === 'edit' ? '수정' : '등록' }}</p>
        <p class="text-sm text-gray-600">도서 정보를 입력해주세요</p>
      </div>

      <div class="grid grid-cols-3 gap-6 p-6 rounded-md shadow-sm bg-gray-50">
        <div class="grid grid-cols-6 gap-4 col-span-full lg:col-span-3">

          <!-- 커버 이미지 -->
          <div class="col-span-full">
            <!--            <FileUploadButton v-model:file="form.imgPath" />-->
            <FileUploadButton
                :imgPath="form.imgPath"
                @update:file="form.imgFile = $event"
            />
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
              <input type="text" v-model="selectedPublisherName" readonly
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
        <SubmitButton :text="mode === 'edit' ? '수정' : '등록'" />
      </div>
    </form>

    <SearchModal v-if="showAuthorModal" @close="showAuthorModal = false" @select="item => selectItem('authorId', item)"
                 sub="저자" :search-var="searchVar" :dataArray="filteredAuthors" @update-search="val => searchVar = val" />

    <SearchModal v-if="showPublisherModal" @close="showPublisherModal = false" @select="item => selectItem('publisherId', item)"
                 sub="출판사" :search-var="searchVar" :dataArray="filteredPublishers" @update-search="val => searchVar = val" />

    <CategoryModal :isOpen="showCategoryModal" @close="showCategoryModal = false" />
  </section>
</template>
