import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useAuthStore } from './auth'
import axios from 'axios'

export const useCategoryStore = defineStore('category', () => {
    const rawCategory = ref([])     // 전체 카테고리
    const mainCategory = ref([])    // 대분류
    const subCategory = ref([])     // 중분류
    const smallCategory = ref([])   // 소분류
    const isLoaded = ref(false)

    const fetchCategory = async () => {
        if (isLoaded.value) return

        // const authStore = useAuthStore()
        // authStore.loadToken()

        try {
            const res = await axios.get('/api/admin/book/category', {
                // headers: {
                //     Authorization: `Bearer ${authStore.token}`
                // }
                withCredentials: true
            })

            rawCategory.value = res.data
            isLoaded.value = true

            getMainCategory()
            getSubCategory()
            getSmallCategory()
        } catch (e) {
            console.log(e.response)
        }
    }

    // 대분류
    const getMainCategory = () => {
        mainCategory.value = rawCategory.value.filter(cat => cat.parentId === 0)
    }

    // 중분류
    const getSubCategory = () => {
        subCategory.value = rawCategory.value.filter(cat =>
            cat.parentId !== 0 &&
            mainCategory.value.some(main => main.id === cat.parentId)
        )
    }

    // 소분류
    const getSmallCategory = () => {
        smallCategory.value = rawCategory.value.filter(cat =>
            cat.parentId !== 0 &&
            subCategory.value.some(sub => sub.id === cat.parentId)
        )
    }

    const resetCategory = () => {
        rawCategory.value = []
        mainCategory.value = []
        subCategory.value = []
        smallCategory.value = []
        isLoaded.value = false
    }

    const getSubCategoryByMain = (mainId) =>
        rawCategory.value.filter(sub => sub.parentId === Number(mainId))

    const getSmallCategoryBySub = (subId) =>
        rawCategory.value.filter(small => small.parentId === Number(subId))

    return {
        rawCategory,
        mainCategory,
        subCategory,
        smallCategory,
        isLoaded,
        fetchCategory,
        resetCategory,
        getSubCategoryByMain,
        getSmallCategoryBySub
    }
})

// import { defineStore } from 'pinia'
// import { useAuthStore } from './auth';
// import axios from 'axios'
//
//
// export const useCategoryStore = defineStore('category', {
//     state: () => ({
//         rawCategory: [],     // 원본
//         mainCategory: [],    // 대분류
//         subCategory: [],     // 중분류
//         smallCategory: [],   // 소분류
//         isLoaded: false, // 로드 여부
//     }),
//     actions: {
//         async fetchCategory() {
//             if (this.isLoaded) return
//
//             const authStore = useAuthStore()
//             authStore.loadToken()
//
//             try {
//                 const res = await axios.get('/api/admin/book/category', {
//                     headers: {
//                         Authorization: `Bearer ${authStore.token}`
//                     }
//                 })
//
//                 this.rawCategory = res.data
//                 this.isLoaded = true
//                 this.getMainCategory()  // 대분류 가져오기
//                 this.getSubCategory()   // 중분류 가져오기
//                 this.getSmallCategory() // 소분류 가져오기
//             } catch (e) {
//                 // alert(e.response);
//                 console.log(e.response)
//             }
//         },
//
//         // 대분류 (parentId === 0) 가져오기
//         getMainCategory() {
//             this.mainCategory = this.rawCategory.filter(category => category.parentId === 0)
//         },
//
//         // 중분류 (parentId가 대분류 categoryId 일치하는 항목들) 가져오기
//         getSubCategory() {
//             this.subCategory = this.rawCategory.filter(menu => menu.parentId !== 0 && this.mainCategory.some(main => main.id === menu.parentId))
//         },
//
//         // 소분류 (parentId가 중분류 categoryId 일치하는 항목들) 가져오기
//         getSmallCategory() {
//             this.smallCategory = this.rawCategory.filter(menu => menu.parentId !== 0 && this.subMenu.some(sub => sub.id === menu.parentId))
//         },
//
//         resetCategory() {
//             this.rawCategory = []
//             this.mainCategory = []
//             this.subCategory = []
//             this.smallCategory = []
//             this.isLoaded = false
//         },
//     },
//     getters: {
//         getSubCategoryByMain: (state) => (mainId) =>
//             state.rawCategory.filter(sub => sub.parentId === Number(mainId)),
//
//         getSmallCategoryBySub: (state) => (subId) =>
//             state.rawCategory.filter(small => small.parentId === Number(subId))
//     }
// })
