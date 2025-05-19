<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useMenuStore } from '../stores/menu'

const route = useRoute()
const menuStore = useMenuStore()

// "소설" 메뉴 객체 찾기
const novelMenu = computed(() => {
    return menuStore.rawMenu.find(menu => menu.name === '소설')
})

// "소설"의 중분류 찾기 (parentId === 소설.menuId)
const novelChildren = computed(() => {
    if (!novelMenu.value) return []
    return menuStore.rawMenu.filter(menu => menu.parentId === novelMenu.value.id)
})

// "소설"의 중분류 각각에 대한 소분류 찾기
const novelGrandChildren = computed(() => {
    const result = []
    novelChildren.value.forEach(mid => {
        const children = menuStore.rawMenu.filter(menu => menu.parentId === mid.id)
        result.push(...children)
    })
    return result
})

// 최종 리스트: 중분류 + 소분류 전부
const novelAllSubMenus = computed(() => {
    return [...novelChildren.value, ...novelGrandChildren.value]
})
</script>

<template>
    <div class="p-4">
        <h2 class="text-lg font-bold mb-3">📚 소설 카테고리 하위 메뉴</h2>
        <ul class="space-y-1 text-gray-700">
            <li v-for="menu in novelAllSubMenus" :key="menu.id">
                <router-link :to="menu.path" class="hover:underline">{{ menu.name }}</router-link>
            </li>
        </ul>
    </div>
</template>
