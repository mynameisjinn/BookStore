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
        <h2 class="text-xl font-bold text-gray-800 mb-4">소설 카테고리</h2>

        <ul class="space-y-4">
            <li v-for="mid in categoryTree" :key="mid.id">
                <h3 class="text-base font-semibold text-gray-700 mb-2 cursor-pointer hover:text-red-500"
                    @click="handleClick(mid.name)">
                    {{ mid.name }}
                </h3>

<!--                <h3-->
<!--                    class="text-base font-semibold mb-2 cursor-pointer transition-colors"-->
<!--                    :class="[-->
<!--                      route.path.startsWith(mid.path)-->
<!--                        ? 'text-red-600 underline'-->
<!--                        : 'text-gray-700 hover:text-red-500'-->
<!--                    ]"-->
<!--                                    @click="handleClick(mid.name)"-->
<!--                                >-->
<!--                    {{ mid.name }}-->
<!--                </h3>-->

              <ul class="space-y-1 pl-4 border-l border-gray-200">
<!--                    <li v-for="child in mid.children" :key="child.id">-->
<!--                        <RouterLink :to="child.path" class="text-sm text-gray-600 hover:text-red-500 transition-colors">-->
<!--                            {{ child.name }}-->
<!--                        </RouterLink>-->
<!--                    </li>-->

                <li v-for="child in mid.children" :key="child.id">
<!--                  {{ child.path }}-->
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
