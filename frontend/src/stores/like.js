import {defineStore} from "pinia";
import axios from "axios";
import { ref } from 'vue'

export const useLikeStore = defineStore('like', () => {
    const likedBookList = ref([])

    const fetchLikedBooks = async (memberId) => {
        try {

            if (likedBookList.value.length > 0) return

            const res = await axios.get('/api/get/like', {
                params: { memberId },
                withCredentials: true
            })

            // 배열인지 확인 후 처리
            const data = Array.isArray(res.data) ? res.data : Object.values(res.data)

            likedBookList.value = data.map(item => Number(item.bookId ?? item))
            // likedBookList.value = res.data.map(item => item.bookId)
            console.log(likedBookList.value)
        } catch (error) {
            console.error('좋아요 목록 조회 실패:', error)
        }
    }

    // const isBookLiked = (bookId) => likedBookList.value.includes(bookId)
    const isBookLiked = (bookId) => likedBookList.value.includes(Number(bookId))


    const addLike = (bookId) => {
        if (!likedBookList.value.includes(bookId)) {
            likedBookList.value.push(bookId)
        }
    }

    const removeLike = (bookId) => {
        likedBookList.value = likedBookList.value.filter(id => id !== bookId)
    }

    return {
        likedBookList,
        fetchLikedBooks,
        isBookLiked,
        addLike,
        removeLike
    }
})
