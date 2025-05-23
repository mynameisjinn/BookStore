<template>
    <div class="gallery border rounded bg-white w-[300px] h-[400px] ml-5">
        <div class="top flex justify-center items-center p-1 border-b gap-1">
            <!-- 왼쪽 화살표 버튼 -->
            <button @click="prev" :disabled="currentIndex === 0"
                class="w-6 h-6 flex items-center justify-center bg-gray-200 rounded-full hover:bg-gray-300 disabled:opacity-50">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                    class="w-4 h-4">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                        d="M15.75 19.5L8.25 12l7.5-7.5" />
                </svg>
            </button>

            <!-- 현재 인덱스 / 총 이미지 수 -->
            <div class="text-xs text-gray-600">
                {{ currentIndex + 1 }} / {{ images.length }}
            </div>

            <!-- 오른쪽 화살표 버튼 -->
            <button @click="next" :disabled="currentIndex === images.length - 1"
                class="w-6 h-6 flex items-center justify-center bg-gray-200 rounded-full hover:bg-gray-300 disabled:opacity-50">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor"
                    class="w-4 h-4">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
                </svg>
            </button>
        </div>

        <div class="content-area w-full h-[340px] flex items-center justify-center p-10 my-3">
            <img :src="images[currentIndex]" alt="동물 이미지" class="w-full h-full object-contain" />
        </div>

    </div>
</template>

<script>
export default {
    data() {
        return {
            images: [
                'https://images-na.ssl-images-amazon.com/images/I/81eJpEEQwYL._AC_SL1500_.jpg', // Tiger
                'https://image.freepik.com/free-vector/cute-lion-cartoon_160606-353.jpg', // Lion
                'https://i.pinimg.com/originals/07/1d/d0/071dd09d7b36e49139fe2cf08ff728a8.jpg', // Rat
                'https://image.freepik.com/free-vector/cartoon-owl-tree-branch_194935-43.jpg' // Owl
            ],
            currentIndex: 0,
        };
    },
    methods: {
        next() {
            if (this.currentIndex < this.images.length - 1) {
                this.currentIndex++;
            }
        },
        prev() {
            if (this.currentIndex > 0) {
                this.currentIndex--;
            }
        },
    },
};
</script>
  