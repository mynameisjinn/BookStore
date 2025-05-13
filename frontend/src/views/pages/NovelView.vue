<script setup>
import { RouterLink } from 'vue-router';
import { ref, computed, onMounted } from 'vue'
import BreadCrumb from '../../components/BreadCrumb.vue'
import CategoryMenu from '../../components/CategoryMenu.vue'
import Tabs from '../../components/Tabs.vue';
import CardList from '../../components/CardList.vue'
import Test from '../../components/Test.vue'
// script setup 안에서 추가
import { useMenuStore } from '../../stores/menu'

const menuStore = useMenuStore()

const novelMenu = computed(() => {
    return menuStore.rawMenu.find(menu => menu.name === '소설')
})

const novelCategoryTree = computed(() => {
    if (!novelMenu.value) return []

    const result = []

    // 중분류만 먼저 뽑기 (예: 장르별, 국가별)
    const midCategories = menuStore.rawMenu.filter(menu => menu.parentId === novelMenu.value.menuId)

    // 중분류 하나씩 돌면서
    midCategories.forEach(mid => {
        // 해당 중분류의 자식(소분류)을 찾아
        const children = menuStore.rawMenu.filter(menu => menu.parentId === mid.menuId)

        // 하나의 객체로 묶어서 result에 넣어
        result.push({
            menuId: mid.menuId,
            name: mid.name,
            path: mid.path,
            parentId: mid.parentId,
            children: children
        })
    })

    return result
})



const tabs = [
    { label: '전체', content: CardList },
    { label: '장르별', content: { template: '<div>소설 콘텐츠</div>' } },
    { label: '국가별', content: { template: '<div>컴퓨터/IT 콘텐츠</div>' } }
]

</script>
<template>
    <BreadCrumb />

    <div class="flex">
        <div class="w-1/4">
            <!-- <CategoryMenu /> -->
            <CategoryMenu :categoryTree="novelCategoryTree" />

            <!-- <Test /> -->
        </div>
        <div class="w-3/4">
            <Tabs title="소설" :tabs="tabs" />
        </div>
    </div>
</template>
