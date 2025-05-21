<script setup>
import {RouterLink} from 'vue-router';
import MainSearchInput from "./MainSearchInput.vue";
</script>

<template>
    <header class="bg-white text-gray-800 px-4 py-3 gap-4 mx-auto max-w-screen-lg">
        <div class="max-w-screen-xl mx-auto flex items-center gap-4">

            <div class="flex items-center gap-4 flex-1">
                <RouterLink to="/" aria-label="Back to homepage" class="flex items-center mr-4">
                    <img src="/logo.svg" alt="Logo" class="w-20 h-auto" />
                </RouterLink>
              <div class="relative w-full max-w-xl">
                <MainSearchInput />
              </div>
            </div>
        </div>
    </header>
</template>
