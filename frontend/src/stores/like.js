import {defineStore} from "pinia";
import axios from "axios";
import { ref } from 'vue'

export const useLikeStore = defineStore('like', () => {
    const likedBookList = ref([])
    // const likedBookList = ref({})


    const fetchLikedBooks = async (memberId) => {
        try {

            if (likedBookList.value.length > 0) return

            const res = await axios.get('/api/like/books', {
                params: { memberId },
                withCredentials: true
            })

            // 배열인지 확인 후 처리
            const data = Array.isArray(res.data) ? res.data : Object.values(res.data)

            // likedBookList.value = data.map(item => Number(item.id ?? item))
            likedBookList.value = res.data
            // console.log(res.data)
            // likedBookList.value = res.data.map(item => item.bookId)
            // console.log(likedBookList.value)
        } catch (error) {
            console.error('좋아요 목록 조회 실패:', error)
        }
    }



    // const isBookLiked = (bookId) => likedBookList.value.includes(bookId)
    // const isBookLiked = (bookId) => likedBookList.value.includes(Number(bookId))

    const isBookLiked = (bookId) => likedBookList.value.some(item => item.id === Number(bookId))


    // const addLike = (bookId) => {
    //     if (!likedBookList.value.includes(bookId)) {
    //         likedBookList.value.push(bookId)
    //     }
    // }

    const addLike = (book) => {
        if (!likedBookList.value.some(item => item.id === book.bookId)) {
            likedBookList.value.push(book)
        }
    }


    // const removeLike = (bookId) => {
    //     likedBookList.value = likedBookList.value.filter(id => id !== bookId)
    // }

    const removeLike = (bookId) => {
        likedBookList.value = likedBookList.value.filter(item => item.id !== bookId)
    }


    const favoriteMenuList = ref([])

    const fetchFavoriteMenus = async (memberId) => {
        try {

            if (favoriteMenuList.value.length > 0) return

            const res = await axios.get('/api/like/menus', {
                params: { memberId },
                withCredentials: true
            })

            // 배열인지 확인 후 처리
            const data = Array.isArray(res.data) ? res.data : Object.values(res.data)
            favoriteMenuList.value = res.data

            console.log(favoriteMenuList.value)
        } catch (error) {
            console.error('즐겨찾기 목록 조회 실패:', error)
        }
    }


    const isMenuLiked = (menuId) => favoriteMenuList.value.some(item => item.id === Number(menuId))

    const addMenu = (menu) => {
        if (!favoriteMenuList.value.some(item => item.id === menu.menuId)) {
            favoriteMenuList.value.push(menu)
        }
    }
    const removeMenu = (menuId) => {
        favoriteMenuList.value = favoriteMenuList.value.filter(item => item.id !== menuId)
    }

    return {
        likedBookList,
        fetchLikedBooks,
        isBookLiked,
        addLike,
        removeLike,
        favoriteMenuList,
        fetchFavoriteMenus,
        isMenuLiked,
        addMenu,
        removeMenu
    }
})
