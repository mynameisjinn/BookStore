<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

const isOpen = ref(false)
const selected = ref('Parent') // 기본 선택값

const options = ['Parent', 'Child A', 'Child B']

const toggleDropdown = () => {
	isOpen.value = !isOpen.value
}

const selectOption = (option) => {
	selected.value = option
	isOpen.value = false
}
</script>

<template>
	<nav aria-label="breadcrumb" class="w-full p-4 text-gray-800">
		<ol class="flex h-8 space-x-2">
			<!-- 홈 링크 -->
			<li class="flex items-center">
				<RouterLink to="/" title="Back to homepage" class="hover:underline">
					<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
						class="w-5 h-5 pr-1 text-gray-600">
						<path
							d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z" />
					</svg>
				</RouterLink>
			</li>

			<!-- 드롭다운 -->
			<li class="flex items-center space-x-2 relative">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" fill="currentColor"
					class="w-2 h-2 mt-1 rotate-90 text-gray-400">
					<path d="M32 30.031h-32l16-28.061z" />
				</svg>
				<div @click="toggleDropdown" class="flex items-center px-1 capitalize hover:underline cursor-pointer">
					{{ selected }}
					<svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 ml-1" fill="none" viewBox="0 0 24 24"
						stroke="currentColor">
						<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
					</svg>
				</div>
				<ul v-show="isOpen" class="absolute left-5 top-6 z-10 w-32 bg-white border rounded shadow text-sm">
					<li v-for="(option, index) in options" :key="index" class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
						@click="selectOption(option)">
						{{ option }}
					</li>
				</ul>
			</li>

			<!-- 그 외 breadcrumb -->
			<li class="flex items-center space-x-2">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" fill="currentColor"
					class="w-2 h-2 mt-1 rotate-90 text-gray-400">
					<path d="M32 30.031h-32l16-28.061z" />
				</svg>
				<a href="#" class="flex items-center px-1 capitalize hover:underline">SubPage</a>
			</li>

			<li class="flex items-center space-x-2">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" fill="currentColor"
					class="w-2 h-2 mt-1 rotate-90 text-gray-400">
					<path d="M32 30.031h-32l16-28.061z" />
				</svg>
				<a class="flex items-center px-1 capitalize cursor-default text-gray-500">Current</a>
			</li>
		</ol>
	</nav>
</template>
