import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'
import { useAuthStore } from './auth.js'

export const usePublisherStore = defineStore('publisher', () => {
  const publishers = ref([])
  const isLoaded = ref(false)

  const fetchPublishers = async () => {
    if (isLoaded.value) return // 중복 호출 방지

    const authStore = useAuthStore()
    authStore.loadToken()

    try {
      const res = await axios.get('/api/admin/book/publisher/all', {
        headers: {
          Authorization: `Bearer ${authStore.token}`
        }
      })

      publishers.value = res.data
      isLoaded.value = true
    } catch (err) {
      console.error('출판사 데이터 불러오기 실패:', err)
    }
  }

  // 검색어로 출판사 필터링
  const filteredPublishers = (query) => {
    const q = query.toLowerCase()
    return publishers.value.filter(publisher =>
        publisher.name.toLowerCase().includes(q)
    )
  }

  return {
    publishers,
    isLoaded,
    fetchPublishers,
    filteredPublishers
  }
})

// import { defineStore } from 'pinia'
// import axios from 'axios'
// import {useAuthStore} from "./auth.js";
//
// export const usePublisherStore = defineStore('publisher', {
//   state: () => ({
//     publishers: [],
//     isLoaded: false
//   }),
//
//   actions: {
//     async fetchPublishers() {
//       if (this.isLoaded) return  // 이미 로드했다면 중복 호출 방지
//
//       const authStore = useAuthStore()
//       authStore.loadToken()
//
//       try {
//         const res = await axios.get('/api/admin/book/publisher/all',{
//             headers:{
//               Authorization: `Bearer ${authStore.token}`
//             }
//
//         })
//
//         this.publishers = res.data
//         this.isLoaded = true
//       } catch (err) {
//         console.error('출판사 데이터 불러오기 실패:', err)
//       }
//     }
//   },
//
//   getters: {
//     // 검색어로 필터링된 결과 반환
//     filteredPublishers: (state) => (query) => {
//       const q = query.toLowerCase()
//       return state.publishers.filter(publisher =>
//           publisher.name.toLowerCase().includes(q)
//       )
//     }
//   }
// })
