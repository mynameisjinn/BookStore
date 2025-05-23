<script setup>
import { RouterLink } from 'vue-router';
import { ref } from 'vue'

const isVisible = ref(true)
</script>
<template>
    <aside v-if="isVisible" class="fixed top-50 h-full w-full max-w-xs p-6 bg-gray-50 text-gray-800 shadow-lg z-50">
        <button @click="isVisible = false" class="absolute top-4 right-4 text-gray-600 hover:text-black">
            ✕
        </button>
        <nav class="space-y-8 text-sm mt-8">
            <div class="space-y-2">
                <h2 class="text-sm font-semibold tracking-widest uppercase text-gray-600">Getting Started</h2>
                <div class="flex flex-col space-y-1">
                    <a rel="noopener noreferrer" href="#">Installation</a>
                    <a rel="noopener noreferrer" href="#">Plugins</a>
                    <a rel="noopener noreferrer" href="#">Migrations</a>
                    <a rel="noopener noreferrer" href="#">Appearance</a>
                    <a rel="noopener noreferrer" href="#">Mamba UI</a>
                </div>
            </div>
            <div class="space-y-2">
                <h2 class="text-sm font-semibold tracking-widest uppercase text-gray-600">Dashboard</h2>
                <div class="flex flex-col space-y-1">
                    <a rel="noopener noreferrer" href="#">Header</a>
                    <a rel="noopener noreferrer" href="#">Drawer</a>
                    <a rel="noopener noreferrer" href="#">Page Title</a>
                    <a rel="noopener noreferrer" href="#">Menus</a>
                    <a rel="noopener noreferrer" href="#">Sidebar</a>
                    <a rel="noopener noreferrer" href="#">Footer</a>
                </div>
            </div>
            <div class="space-y-2">
                <h2 class="text-sm font-semibold tracking-widest uppercase text-gray-600">Pages</h2>
                <div class="flex flex-col space-y-1">
                    <a rel="noopener noreferrer" href="#">Homepage</a>
                    <a rel="noopener noreferrer" href="#">Users</a>
                    <a rel="noopener noreferrer" href="#">Tools</a>
                    <a rel="noopener noreferrer" href="#">Settings</a>
                </div>
            </div>
            <div class="space-y-2">
                <h2 class="text-sm font-semibold tracking-widest uppercase text-gray-600">Misc</h2>
                <div class="flex flex-col space-y-1">
                    <a rel="noopener noreferrer" href="#">Tutorials</a>
                    <a rel="noopener noreferrer" href="#">Changelog</a>
                </div>
            </div>
        </nav>
    </aside>
</template>