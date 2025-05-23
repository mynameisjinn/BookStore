<script setup>
import { RouterLink } from 'vue-router';
</script>
<template>
	<section class="flex items-center h-full p-16 bg-gray-50 text-gray-800">
		<div class="container flex flex-col items-center justify-center px-5 mx-auto my-8">
			<div class="max-w-md text-center">
				<h2 class="mb-8 font-extrabold text-9xl text-gray-400">
					<span class="sr-only">에러</span>404
				</h2>
				<p class="text-2xl font-semibold md:text-3xl my-10">죄송합니다. 해당 페이지를 찾을 수 없습니다.</p>
                <RouterLink to="/" class="px-8 py-3 font-semibold rounded bg-red-600 text-gray-50"> 
                    홈페이지로 돌아가기
                </RouterLink>
			</div>
		</div>
	</section>
</template>
