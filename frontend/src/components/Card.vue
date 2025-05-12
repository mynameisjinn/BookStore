<script setup>
defineProps({
	title: String,
	subtitle: String,
	description: String,
	imageUrl: String,
})
</script>

<template>
	<div class="max-w-xs p-6 rounded-md shadow-md bg-gray-50 text-gray-900">
		<img :src="imageUrl" alt="" class="object-cover object-center w-full rounded-md h-72 bg-gray-500" />
		<div class="mt-6 mb-2">
			<span class="block text-xs font-medium tracking-widest uppercase text-red-600">
				{{ subtitle }}
			</span>
			<h2 class="text-xl font-semibold tracking-wide">{{ title }}</h2>
		</div>
		<p class="text-gray-800">{{ description }}</p>
	</div>
</template>
