import { defineStore } from 'pinia'
import { useAuthStore } from '../stores/auth';
import axios from 'axios'

export const useMenuStore = defineStore('menu', {
    state: () => ({
        rawMenu: [],     // 원본 메뉴 데이터
        mainMenu: [],    // 대분류 메뉴
        subMenu: [],     // 중분류 메뉴
        smallMenu: [],   // 소분류 메뉴
        isLoaded: false, // 메뉴 로드 여부
    }),
    actions: {
        async fetchMenu() {
            if (this.isLoaded) return

            const authStore = useAuthStore()
            const role = authStore.role ?? 'NOT_LOGIN' 

            // if(!role) role = 'NOT_LOGIN'

            console.log(role)
            try {
                const res = await axios.get('/api/menu/get', {
                    params: { roleName: role }
                })

                this.rawMenu = res.data
                this.isLoaded = true
                this.getMainMenu()  // 대분류 가져오기
                this.getSubMenu()   // 중분류 가져오기
                this.getSmallMenu() // 소분류 가져오기
            } catch (e) {
                console.error('메뉴 불러오기 실패:', e)
            }
        },

        // 대분류 (parentId === 0) 가져오기
        getMainMenu() {
            this.mainMenu = this.rawMenu.filter(menu => menu.parentId === 0)
        },

        // 중분류 (parentId가 대분류 menuId와 일치하는 항목들) 가져오기
        getSubMenu() {
            this.subMenu = this.rawMenu.filter(menu => menu.parentId !== 0 && this.mainMenu.some(main => main.menuId === menu.parentId))
        },

        // 소분류 (parentId가 중분류 menuId와 일치하는 항목들) 가져오기
        getSmallMenu() {
            this.smallMenu = this.rawMenu.filter(menu => menu.parentId !== 0 && this.subMenu.some(sub => sub.menuId === menu.parentId))
        },

        resetMenu() {
            this.rawMenu = []
            this.mainMenu = []
            this.subMenu = []
            this.smallMenu = []
            this.isLoaded = false
        },
    }
})
