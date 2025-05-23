<script setup>
import {useRoute} from 'vue-router';
import {computed, onMounted} from 'vue'
import CategoryMenu from '../../components/CategoryMenu.vue'
import Tabs from '../../components/Tabs.vue';
import {useMenuStore} from '../../stores/menu'
import BookAdvertisement from "../../components/BookAdvertisement.vue";
import BookCardList from "../../components/BookCardList.vue";

const menuStore = useMenuStore()
const route = useRoute()

const currentMainMenu = computed(() => {
  return menuStore.rawMenu.find(menu =>
      route.path.startsWith(menu.path) &&  menu.depth === 1
  )
})

const pageName = computed(() => currentMainMenu.value?.name)

const isRootNovelPage = computed(() => {
  return route.path === currentMainMenu.value?.path
})

const menuTree = computed(() => {
    if (!currentMainMenu.value) return []

    const result = []

    // 중분류만 먼저 뽑기 (예: 장르별, 국가별)
    const midCategories = menuStore.rawMenu.filter(menu => menu.parentId === currentMainMenu.value.id)

    // 중분류 하나씩 돌면서
    midCategories.forEach(mid => {
        // 해당 중분류의 자식(소분류)을 찾아
        const children = menuStore.rawMenu.filter(menu => menu.parentId === mid.id)

        // 하나의 객체로 묶어서 result에 넣어
        result.push({
            id: mid.id,
            name: mid.name,
            path: mid.path,
            parentId: mid.parentId,
            categoryId: mid.categoryId,
            children: children
        })
    })
    return result
})



const currentMidCategory = computed(() => {
  return menuStore.rawMenu.find(menu =>
      route.path.startsWith(menu.path) &&  menu.depth === 2
  )
})


const tabs = computed(() => {

  const matchedMid = menuTree.value.find(mid => {
    return mid.path === currentMidCategory.value.path
  })

  const baseTab = {
    label: '전체',
    path: matchedMid.path,
    content: BookCardList
  }


  if (!matchedMid) return [baseTab]

  const dynamicTabs = matchedMid.children.map(sub => ({
    label: sub.name,
    path: sub.path,
    content: {
      template: BookCardList
    }
  }))

  return [baseTab, ...dynamicTabs]
})
</script>
<template>
    <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
        <div class="flex-[1]">
            <CategoryMenu :categoryTree="menuTree" />

        </div>
        <div class="flex-[3]">
          <component :is="isRootNovelPage ? BookAdvertisement : Tabs"
                     :title=pageName
                     :tabs="!isRootNovelPage ? tabs : undefined" />
        </div>
    </div>
</template>
