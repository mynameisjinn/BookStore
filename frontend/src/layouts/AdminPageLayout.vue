<script setup>

import {useMenuStore} from "../stores/menu.js";
import {computed} from "vue";
import CategoryMenu from "../components/CategoryMenu.vue";

const menuStore = useMenuStore()

const adminMenu = computed(() => {
  return menuStore.rawMenu.find(menu => menu.name === '관리자')
})

const adminCategoryTree = computed(() => {
  if (!adminMenu.value) return []

  const result = []

  // 중분류만 먼저 뽑기 (예: 장르별, 국가별)
  const midCategories = menuStore.rawMenu.filter(menu => menu.parentId === adminMenu.value.id)

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
</script>

<template>
<!--  <div>-->
<!--    <h1>Admin Layout</h1>-->
<!--  </div>-->

    <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
      <div class="flex-[1]">
        <CategoryMenu :categoryTree="adminCategoryTree" />
      </div>
      <div class="flex-[3]">
        <RouterView />
      </div>
    </div>
</template>
