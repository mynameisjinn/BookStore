<script setup>
import Carousel from './Carousel.vue'
import AdSlider from './AdSlider.vue'
</script>

<template>
    <section class="bg-gray-100 my-10">
        <div class="flex gap-4 mx-auto max-w-screen-lg">
            <!-- Carousel: 75% -->
            <div class="flex-[3]">
                <Carousel />
            </div>

            <!-- AdSlider: 25% -->
            <div class="flex-[1]">
                <AdSlider />
            </div>
        </div>
    </section>
</template>
