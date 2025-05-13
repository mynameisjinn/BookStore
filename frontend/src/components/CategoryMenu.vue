<script setup lang="ts">
defineProps<{
    category: {
        name: string
        link: string
        subCategories: {
            title: string
            items: string[]
        }[]
    }
}>()
</script>

<template>
    <aside class="h-full w-full max-w-xs p-6 bg-gray-50 text-gray-800 shadow-lg">
        <h1 class="text-lg font-bold">{{ category.name }}</h1>
        <nav class="space-y-8 text-sm mt-4">
            <div v-for="(sub, index) in category.subCategories" :key="index" class="space-y-2">
                <h2 class="text-sm font-semibold tracking-widest uppercase text-gray-600">
                    {{ sub.title }}
                </h2>
                <div class="flex flex-col space-y-1">
                    <a v-for="(item, idx) in sub.items" :key="idx" href="#" rel="noopener noreferrer">
                        {{ item }}
                    </a>
                </div>
            </div>
        </nav>
    </aside>
</template>
