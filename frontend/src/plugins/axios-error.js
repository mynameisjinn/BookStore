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
            console.error('500 에러 감지됨, 페이지 이동');
            router.push('/server-error');
        }

        if (status === 401) {
            alert(errorMessage || '인증 정보가 유효하지 않습니다.');
        }

        if (status === 400) {
            alert(errorMessage || '잘못된 요청입니다.');
        }

        return Promise.reject(error);
    }
)