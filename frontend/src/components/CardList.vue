<script setup>
import Card from './Card.vue'

const cards = [
    {
        title: 'Nam maximus purus',
        subtitle: 'Quisque',
        description: 'Mauris et lorem at elit tristique dignissim et ullamcorper elit.',
        imageUrl: 'https://source.unsplash.com/random/300x300/?1',
    },
    {
        title: 'Pellentesque habitant',
        subtitle: 'Curabitur',
        description: 'Pellentesque in ipsum id orci porta dapibus.',
        imageUrl: 'https://source.unsplash.com/random/300x300/?2',
    },
    {
        title: 'Vestibulum ante ipsum',
        subtitle: 'Donec',
        description: 'Nulla quis lorem ut libero malesuada feugiat.',
        imageUrl: 'https://source.unsplash.com/random/300x300/?3',
    },
    {
        title: 'Vivamus suscipit tortor',
        subtitle: 'Aliquam',
        description: 'Proin eget tortor risus.',
        imageUrl: 'https://source.unsplash.com/random/300x300/?4',
    },
    {
        title: 'Cras ultricies ligula',
        subtitle: 'Aenean',
        description: 'Curabitur arcu erat, accumsan id imperdiet et.',
        imageUrl: 'https://source.unsplash.com/random/300x300/?5',
    },
]
</script>

<template>
    <div class="flex gap-4 overflow-x-auto p-4">
        <Card v-for="(card, index) in cards" :key="index" :title="card.title" :subtitle="card.subtitle"
            :description="card.description" :imageUrl="card.imageUrl" />
    </div>
</template>
