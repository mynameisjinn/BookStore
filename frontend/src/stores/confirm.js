// Pinia의 store를 정의하는 함수
import { defineStore } from 'pinia'

// 반응형 상태를 만들기 위한 Vue의 ref
import { ref } from 'vue'

// 'confirm'이라는 이름으로 store 생성
export const useConfirmStore = defineStore('confirm', () => {

    // 모달의 표시 여부 (true면 모달 보임)
    const visible = ref(false)

    // 모달에 표시될 메시지
    const message = ref('')

    // 확인(확인 버튼 클릭 시 실행할 함수)
    let onConfirm = () => {}

    // 취소(취소 버튼 클릭 시 실행할 함수)
    let onCancel = () => {}

    // 모달을 열고 메시지/이벤트 핸들러를 설정
    const openConfirm = ({ msg, onOk, onCancelFn }) => {
        message.value = msg             // 모달에 보여줄 메시지 설정
        visible.value = true           // 모달 보이도록 설정
        onConfirm = onOk               // 확인 버튼 클릭 시 실행할 함수 저장
        onCancel = onCancelFn || (() => {})  // 취소 함수 없으면 빈 함수로
    }

    // 확인 버튼 클릭 시 실행
    const confirm = () => {
        onConfirm()        // 등록한 확인 함수 실행
        visible.value = false  // 모달 닫기
    }

    // 취소 버튼 클릭 시 실행
    const cancel = () => {
        onCancel()         // 등록한 취소 함수 실행
        visible.value = false  // 모달 닫기
    }

    // 외부에서 사용할 수 있도록 내보내기
    return {
        visible,     // 모달 보임 여부
        message,     // 모달 메시지
        openConfirm, // 모달 열기 함수
        confirm,     // 확인 처리 함수
        cancel,      // 취소 처리 함수
    }
})
