<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

defineProps({
    categoryTree: {
        type: Array,
        required: true
    }
})

const emit = defineEmits(['select-tab'])

const handleClick = (name) => {
    emit('select-tab', name)  // 탭 이름을 emit
}
</script>

<template>
    <div class="p-6 bg-white rounded shadow-sm border border-gray-200">
        <h2 class="text-xl font-bold text-gray-800 mb-4">카테고리</h2>

        <ul class="space-y-4">
            <li v-for="mid in categoryTree" :key="mid.id">
              <RouterLink :to="mid.path">
                <h3
                    @click="handleClick(mid.name)"
                    :class="[
                      route.path === mid.path
                      ? 'ext-base font-semibold text-red-500'
                      : 'ext-base font-semibold text-gray-700 mb-2'
                      ]"
                >
                    {{ mid.name }}
                </h3>
              </RouterLink>
              <ul class="space-y-1 pl-4 border-l border-gray-200">
                <li v-for="child in mid.children" :key="child.id">
                  <RouterLink :to="child.path"
                  :class="[
                      route.path.startsWith(child.path)
                      ? 'text-red-600 underline'
                      : 'text-gray-700 hover:text-red-500'
                      ]"
                  >
                    {{ child.name }}
                  </RouterLink>
                </li>
                </ul>
            </li>
        </ul>
    </div>
</template>
