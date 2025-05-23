<script setup>
import Carousel from './Carousel.vue'
import AdSlider from './AdSlider.vue'
</script>

<template>
    <section class="my-10">
        <div class="flex gap-4 mx-auto max-w-screen-lg">
            <!-- Carousel: 75% -->
<!--            <div class="flex-[3]">-->
<!--                <Carousel />-->
<!--            </div>-->

<!--            &lt;!&ndash; AdSlider: 25% &ndash;&gt;-->
<!--            <div class="flex-[1]">-->
<!--                <AdSlider />-->
<!--            </div>-->
          <Carousel
              :images="['/images/main/slider01.jpg', '/images/main/slider02.jpg',
              '/images/main/slider03.jpg','/images/main/slider04.jpg']" />
        </div>
    </section>
</template>
