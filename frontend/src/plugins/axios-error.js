import axios from 'axios'
import router from '../router'

// 응답 인터셉터
axios.interceptors.response.use(
    function (response) {
        return response
    },
    function (error) {
        const status = error.response?.status;
        const errorMessage = error.response?.data?.error;

        if (status === 500) {
            if (router.currentRoute.value.path !== '/server-error') {
                router.push('/server-error');
            }

            /*
            // 현재 경로가 server-error거나, 이미 체크한 경우는 무시
            const currentPath = router.currentRoute.value.path;
            const checked = router.currentRoute.value.query.checked;

            if (currentPath !== '/server-error' && !checked) {
                router.push({ path: '/server-error', query: { checked: true } });
            }
*/


            // return 안 하면 무한루프 가능
            return; // 혹은 return Promise.resolve(null);
        }

        if (status === 401) {
            // alert(errorMessage);
        }

        if (status === 400) {
            alert(errorMessage || '잘못된 요청입니다.');
            // router.push('/')
        }

        return Promise.reject(error);
    }
)