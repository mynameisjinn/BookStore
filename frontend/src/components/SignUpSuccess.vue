<template>
    <div class="flex flex-col items-center justify-center min-h-screen bg-gray-100 space-y-8">
        <Steps />

        <div class="flex flex-col items-center max-w-md p-8 rounded-md sm:p-10 bg-white shadow-md text-gray-800">
            <!-- 체크 아이콘 -->
            <div class="w-20 h-20 mb-4 rounded-full bg-green-100 flex items-center justify-center">
                <svg class="w-12 h-12 text-green-500" fill="none" stroke="currentColor" stroke-width="2"
                    viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
                </svg>
            </div>

            <!-- 메시지 -->
            <h1 class="text-3xl font-bold mb-2 text-center">가입이 완료되었습니다!</h1>
            <p class="text-sm text-center text-gray-600 mb-6">이제 로그인을 통해 서비스를 이용하실 수 있습니다.</p>

            <!-- 버튼 -->
            <RouterLink to="/login" class="w-full">
                <button class="w-full px-6 py-3 text-white font-semibold bg-red-600 rounded-md hover:brightness-90">
                    로그인하러 가기
                </button>
            </RouterLink>
        </div>
    </div>
</template>
  