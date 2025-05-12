<script setup>
import { ref } from 'vue'
import CardList from './CardList.vue'

const openTab = ref(1)

const activeClasses = 'border-l border-t border-r rounded-t text-blue-700'
const inactiveClasses = 'text-blue-500 hover:text-blue-700'
</script>

<template>
    <div class="my-10 mx-auto max-w-screen-lg">
        <h2 class="text-2xl font-bold text-gray-800 mb-6"> 분야별 BEST 5</h2>
        <ul class="flex border-b">
            <li @click="openTab = 1" :class="{ '-mb-px': openTab === 1 }" class="-mb-px mr-1">
                <a href="#"
                    :class="[openTab === 1 ? activeClasses : inactiveClasses, 'bg-white inline-block py-2 px-4 font-semibold']">
                    Active
                </a>
            </li>
            <li @click="openTab = 2" :class="{ '-mb-px': openTab === 2 }" class="mr-1">
                <a href="#"
                    :class="[openTab === 2 ? activeClasses : inactiveClasses, 'bg-white inline-block py-2 px-4 font-semibold']">
                    Tab
                </a>
            </li>
            <li @click="openTab = 3" :class="{ '-mb-px': openTab === 3 }" class="mr-1">
                <a href="#"
                    :class="[openTab === 3 ? activeClasses : inactiveClasses, 'bg-white inline-block py-2 px-4 font-semibold']">
                    Tab
                </a>
            </li>
        </ul>

        <div class="w-full mt-4">
            <div v-show="openTab === 1"><CardList /></div>
            <div v-show="openTab === 2">Tab #2</div>
            <div v-show="openTab === 3">Tab #3</div>
        </div>
    </div>
</template>
