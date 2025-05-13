import { defineStore } from 'pinia'
import axios from 'axios'

export const useMenuStore = defineStore('menu', {
    state: () => ({
        rawMenu: [],
        structuredMenu: [],
        isLoaded: false,
    }),
    actions: {
        async fetchMenu() {
            if (this.isLoaded) return // 로드된게 있으면 다시하지않음

            try {
                const res = await axios.get('/api/menu/get')
                this.rawMenu = res.data
                this.structuredMenu = this.formatMenu()
                this.isLoaded = true
            } catch (e) {
                console.error('메뉴 불러오기 실패:', e)
            }
        },
        formatMenu() {
            const map = new Map()
            const result = []

            // 메뉴 항목들을 map에 저장
            this.rawMenu.forEach(menu => {
                map.set(menu.menuId, { ...menu, children: [] })
            })

            // 계층 구조로 변환
            this.rawMenu.forEach(menu => {
                if (menu.parentId === 0) {
                    result.push(map.get(menu.menuId))
                } else {
                    const parent = map.get(menu.parentId)
                    if (parent) parent.children.push(map.get(menu.menuId))
                }
            })

            // tabs 형식으로 변환
            return result.map(top => {
                if (top.children.some(child => child.children?.length > 0)) {
                    // 하위 카테고리가 존재하는 경우
                    return {
                        name: top.name,
                        link: top.path,
                        subCategories: top.children.map(sub => ({
                            title: sub.name,
                            items: sub.children.map(item => item.name)
                        }))
                    }
                } else {
                    // 하위가 없는 일반 카테고리
                    return {
                        name: top.name,
                        link: top.path,
                        items: top.children.map(child => child.name)
                    }
                }
            })
        }
    }
})
