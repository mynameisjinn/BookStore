import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

// export const useBookStore = defineStore('book', () => {
//     const bookList = ref([])
//     const loading = ref(false)
//
//     // param: categoryIds는 배열
//     const fetchBooks = async (categoryIdList = []) => {
//         loading.value = true
//         try {
//             bookList.value = []  // 초기화 필수!
//             const res = await axios.post('/api/books', categoryIdList)
//             bookList.value = res.data
//             // alert(bookList.value)
//         } finally {
//             loading.value = false
//         }
//     }
//
//     return { bookList, loading, fetchBooks }
// })

export const useBookStore = defineStore('book', () => {
    const bookMap = ref({})  // { [categoryId: number]: book[] }
    const loading = ref(false)

    const fetchBooks = async (categoryIdList = []) => {
        loading.value = true
        try {
            // 안 불러온 것만 요청
            const uncachedIds = categoryIdList.filter(id => !bookMap.value[id])
            if (uncachedIds.length > 0) {
                const res = await axios.post('/api/books', uncachedIds)

                res.data.forEach(book => {
                    const catId = book.categoryId
                    if (!bookMap.value[catId]) {
                        bookMap.value[catId] = []
                    }
                    const exists = bookMap.value[catId].some(b => b.id === book.id)
                    if (!exists) {
                        bookMap.value[catId].push(book)
                    }
                })
            }
        } finally {
            loading.value = false
        }
    }

    // 여러 categoryId에 해당하는 책을 한 배열로 반환
    const getBooksByCategory = (categoryIdList) => {
        const ids = Array.isArray(categoryIdList) ? categoryIdList : [categoryIdList]
        return ids.flatMap(id => bookMap.value[id] || [])
    }

    return { bookMap, loading, fetchBooks, getBooksByCategory }
})
