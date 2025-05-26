import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'
import { useAuthStore } from './auth.js'

export const useAuthorStore = defineStore('author', () => {
  // 상태 정의
  const authors = ref([])
  const isLoaded = ref(false)

  // 저자 데이터 불러오기
  const fetchAuthors = async () => {
    if (isLoaded.value) return // 이미 불러왔으면 다시 호출하지 않음

    // const authStore = useAuthStore()
    // authStore.loadToken()

    try {
      const res = await axios.get('/api/admin/book/author/all', {
        // headers: {
        //   Authorization: `Bearer ${authStore.token}`
        // }

        withCredentials: true
      })

      authors.value = res.data
      isLoaded.value = true
    } catch (err) {
      console.error('저자 데이터 불러오기 실패:', err)
    }
  }

  // 검색어로 저자 필터링
  const filteredAuthors = computed(() => {
    return (query) => {
      const q = query.toLowerCase()
      return authors.value.filter(author =>
          author.name.toLowerCase().includes(q)
      )
    }
  })

  return {
    authors,
    isLoaded,
    fetchAuthors,
    filteredAuthors
  }
})


// import { defineStore } from 'pinia'
// import axios from 'axios'
// import {useAuthStore} from "./auth.js";
//
// export const useAuthorStore = defineStore('author', {
//   state: () => ({
//     authors: [],
//     isLoaded: false
//   }),
//
//   actions: {
//     async fetchAuthors() {
//       if (this.isLoaded) return  // 이미 로드했다면 중복 호출 방지
//
//       const authStore = useAuthStore()
//       authStore.loadToken()
//
//       try {
//         const res = await axios.get('/api/admin/book/author/all',{
//             headers:{
//               Authorization: `Bearer ${authStore.token}`
//             }
//
//         })
//
//         this.authors = res.data
//         this.isLoaded = true
//       } catch (err) {
//         console.error('저자 데이터 불러오기 실패:', err)
//       }
//     }
//   },
//
//   getters: {
//     // 검색어로 필터링된 결과 반환
//     filteredAuthors: (state) => (query) => {
//       const q = query.toLowerCase()
//       return state.authors.filter(author =>
//           author.name.toLowerCase().includes(q)
//       )
//     }
//   }
// })
