package org.example.bookstore.service;

import org.example.bookstore.vo.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void selectMemberInfo() {
        // when: 실제 서비스 호출
        List<MemberVO> result = memberService.selectMemberInfo();

        // then: 결과 출력
        for (MemberVO vo : result) {
            System.out.println("회원 Email: " + vo.getEmail());
        }

        // 검증
        assertFalse(result.isEmpty(), "회원 목록이 비어 있으면 안 됩니다.");
    }

    @Test
    void checkExistMember(){

        // when
        String email = "hyejin@example.com11";
        boolean exist = memberService.checkExistMember(email);

        // then 결과
        if(exist) System.out.println("해당 이메일 존재");

        if(!exist) System.out.println("가입 가능");

    }


}