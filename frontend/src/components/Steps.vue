<script setup>
defineProps({
	currentStep: Number,
	stepLabels: {
		type: Array,
		required: true,
	}
})
</script>

<template>
	<div class="flex justify-center">
		<div class="p-4 space-y-2 bg-gray-100 text-gray-800 text-center">
			<h3 class="text-base font-semibold">{{ stepLabels[currentStep - 1] }}</h3>
			<div class="flex justify-center space-x-3">
				<span class="w-12 h-2 rounded-sm" :class="currentStep >= 1 ? 'bg-red-600' : 'bg-gray-400'"></span>
				<span class="w-12 h-2 rounded-sm" :class="currentStep >= 2 ? 'bg-red-600' : 'bg-gray-400'"></span>
				<span class="w-12 h-2 rounded-sm" :class="currentStep >= 3 ? 'bg-red-600' : 'bg-gray-400'"></span>
			</div>
		</div>
	</div>
</template>
