import { defineStore } from 'pinia'
import { useAuthStore } from './auth';
import axios from 'axios'

export const useCategoryStore = defineStore('category', {
    state: () => ({
        rawCategory: [],     // 원본  
        mainCategory: [],    // 대분류 
        subCategory: [],     // 중분류 
        smallCategory: [],   // 소분류 
        isLoaded: false, // 로드 여부
    }),
    actions: {
        async fetchCategory() {
            if (this.isLoaded) return

            // const authStore = useAuthStore()
            // const role = authStore.role ?? 'NOT_LOGIN' 

            try {
                const res = await axios.get('/api/admin/category')

                this.rawCategory = res.data
                this.isLoaded = true
                this.getMainCategory()  // 대분류 가져오기
                this.getSubCategory()   // 중분류 가져오기
                this.getSmallCategory() // 소분류 가져오기
            } catch (e) {
                console.error('카테고리 불러오기 실패:', e)
            }
        },

        // 대분류 (parentId === 0) 가져오기
        getMainCategory() {
            this.mainCategory = this.rawCategory.filter(category => category.parentId === 0)
        },

        // 중분류 (parentId가 대분류 categoryId 일치하는 항목들) 가져오기
        getSubCategory() {
            this.subCategory = this.rawCategory.filter(menu => menu.parentId !== 0 && this.mainCategory.some(main => main.categoryId === menu.parentId))
        },

        // 소분류 (parentId가 중분류 categoryId 일치하는 항목들) 가져오기
        getSmallCategory() {
            this.smallCategory = this.rawCategory.filter(menu => menu.parentId !== 0 && this.subMenu.some(sub => sub.categoryId === menu.parentId))
        },

        resetCategory() {
            this.rawCategory = []
            this.mainCategory = []
            this.subCategory = []
            this.smallCategory = []
            this.isLoaded = false
        },
    }
})
