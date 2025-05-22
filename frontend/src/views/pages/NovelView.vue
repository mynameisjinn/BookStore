<script setup>
import {useRoute} from 'vue-router';
import {computed} from 'vue'
import CategoryMenu from '../../components/CategoryMenu.vue'
import Tabs from '../../components/Tabs.vue';
import {useMenuStore} from '../../stores/menu'
import BookAdvertisement from "../../components/BookAdvertisement.vue";
import BookCardList from "../../components/BookCardList.vue";


const route = useRoute()

const isRootNovelPage = computed(() => {
  return route.path === novelMenu.value?.path
})


const currentMidCategory = computed(() => {
  const segments = route.path.split('/').slice(0, 4)
  return segments.join('/')
})

const menuStore = useMenuStore()

const novelMenu = computed(() => {
    return menuStore.rawMenu.find(menu => menu.name === '소설')
})

const novelCategoryTree = computed(() => {
    if (!novelMenu.value) return []

    const result = []

    // 중분류만 먼저 뽑기 (예: 장르별, 국가별)
    const midCategories = menuStore.rawMenu.filter(menu => menu.parentId === novelMenu.value.id)

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
            children: children
        })
    })
    return result
})


const tabs = computed(() => {

  const matchedMid = novelCategoryTree.value.find(mid => {
    return mid.path === currentMidCategory.value
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
      template: `<div>${sub.name} 콘텐츠</div>`
    }
  }))

  return [baseTab, ...dynamicTabs]
})




</script>
<template>
    <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
        <div class="flex-[1]">
            <CategoryMenu :categoryTree="novelCategoryTree" />
        </div>
        <div class="flex-[3]">
          <component :is="isRootNovelPage ? BookAdvertisement : Tabs"
                     :title="'소설'"
                     :tabs="!isRootNovelPage ? tabs : undefined" />
        </div>
    </div>
</template>
