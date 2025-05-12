<script setup>
import { ref } from 'vue'
import CardList from './CardList.vue'

const openTab = ref(1)

const activeClasses = 'bg-red-600 text-white rounded-t px-4 py-2 font-semibold transition-colors'
const inactiveClasses = 'bg-white text-red-600 border rounded-t px-4 py-2 font-semibold hover:bg-red-50 transition-colors'

</script>

<template>
    <div class="my-10 mx-auto max-w-screen-lg">
        <h2 class="text-2xl font-bold text-gray-800 mb-6"> 분야별 BEST 5</h2>
        <ul class="flex border-b">
            <li :class="{ '-mb-px': openTab === 1 }" class="-mb-px mr-1">
                <button @click="openTab = 1" :class="openTab === 1 ? activeClasses : inactiveClasses">
                    전체
                </button>

            </li>
            <li :class="{ '-mb-px': openTab === 2 }" class="mr-1">
                <button @click="openTab = 2"
                    :class="openTab === 2 ? activeClasses : inactiveClasses">
                    소설
                </button>
            </li>
            <li :class="{ '-mb-px': openTab === 3 }" class="mr-1">
                <button @click="openTab = 3"
                    :class="openTab === 3 ? activeClasses : inactiveClasses">
                    컴퓨터/IT
                </button>
            </li>
        </ul>


        <div class="w-full mt-4">
            <div v-show="openTab === 1">
                <CardList />
            </div>
            <div v-show="openTab === 2">Tab #2</div>
            <div v-show="openTab === 3">Tab #3</div>
        </div>
    </div>
</template>
