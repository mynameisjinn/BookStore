import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'
import { useAuthStore } from '../stores/auth'

export const useMenuStore = defineStore('menu', () => {
    // 상태 정의
    const rawMenu = ref([])
    const mainMenu = ref([])
    const subMenu = ref([])
    const smallMenu = ref([])
    const isLoaded = ref(false)

    // 메뉴 API 호출
    const fetchMenu = async () => {
        if (isLoaded.value) return

        const authStore = useAuthStore()
        const role = authStore.role ?? 'NOT_LOGIN'

        try {
            const res = await axios.get('/api/menu/get', {
                params: { roleName: role }
            })

            rawMenu.value = res.data
            isLoaded.value = true

            getMainMenu()
            getSubMenu()
            getSmallMenu()
        } catch (e) {
            console.error('메뉴 불러오기 실패:', e)
        }
    }

    // 대분류: parentId가 0
    const getMainMenu = () => {
        mainMenu.value = rawMenu.value.filter(menu => menu.parentId === 0)
    }

    // 중분류: parentId가 mainMenu의 id와 일치
    const getSubMenu = () => {
        subMenu.value = rawMenu.value.filter(menu =>
            menu.parentId !== 0 &&
            mainMenu.value.some(main => main.id === menu.parentId)
        )
    }

    // 소분류: parentId가 subMenu의 id와 일치
    const getSmallMenu = () => {
        smallMenu.value = rawMenu.value.filter(menu =>
            menu.parentId !== 0 &&
            subMenu.value.some(sub => sub.id === menu.parentId)
        )
    }

    // 메뉴 초기화
    const resetMenu = () => {
        rawMenu.value = []
        mainMenu.value = []
        subMenu.value = []
        smallMenu.value = []
        isLoaded.value = false
    }

    return {
        rawMenu,
        mainMenu,
        subMenu,
        smallMenu,
        isLoaded,
        fetchMenu,
        resetMenu
    }
})


// import { defineStore } from 'pinia'
// import { useAuthStore } from '../stores/auth';
// import axios from 'axios'
//
// export const useMenuStore = defineStore('menu', {
//     state: () => ({
//         rawMenu: [],     // 원본 메뉴 데이터
//         mainMenu: [],    // 대분류 메뉴
//         subMenu: [],     // 중분류 메뉴
//         smallMenu: [],   // 소분류 메뉴
//         isLoaded: false, // 메뉴 로드 여부
//     }),
//     actions: {
//         async fetchMenu() {
//             if (this.isLoaded) return
//
//             const authStore = useAuthStore()
//             const role = authStore.role ?? 'NOT_LOGIN'
//
//             // if(!role) role = 'NOT_LOGIN'
//
//             // console.log(role)
//             try {
//                 const res = await axios.get('/api/menu/get', {
//                     params: { roleName: role }
//                 })
//
//                 this.rawMenu = res.data
//                 this.isLoaded = true
//                 this.getMainMenu()  // 대분류 가져오기
//                 this.getSubMenu()   // 중분류 가져오기
//                 this.getSmallMenu() // 소분류 가져오기
//             } catch (e) {
//                 console.error('메뉴 불러오기 실패:', e)
//             }
//         },
//
//         // 대분류 (parentId === 0) 가져오기
//         getMainMenu() {
//             this.mainMenu = this.rawMenu.filter(menu => menu.parentId === 0)
//         },
//
//         // 중분류 (parentId가 대분류 menuId와 일치하는 항목들) 가져오기
//         getSubMenu() {
//             this.subMenu = this.rawMenu.filter(menu => menu.parentId !== 0 && this.mainMenu.some(main => main.id === menu.parentId))
//         },
//
//         // 소분류 (parentId가 중분류 menuId와 일치하는 항목들) 가져오기
//         getSmallMenu() {
//             this.smallMenu = this.rawMenu.filter(menu => menu.parentId !== 0 && this.subMenu.some(sub => sub.id === menu.parentId))
//         },
//
//         resetMenu() {
//             this.rawMenu = []
//             this.mainMenu = []
//             this.subMenu = []
//             this.smallMenu = []
//             this.isLoaded = false
//         },
//     }
// })
