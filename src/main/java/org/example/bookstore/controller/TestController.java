package org.example.bookstore.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.MemberService;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
@RestController
public class TestController {

    @Autowired
    private MemberService memberService;
    @Operation(summary="테스트", description="테스트 중입니다.")
    @GetMapping("test")
    public void getTest(){

        log.info("테스트");

        List<MemberVO> list = memberService.selectMemberInfo();
        log.info(list.toString());
    }





    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> params) {
        try {
            log.info("로그인 성공");
            return "Login success";
        } catch (AuthenticationException e) {
            return "Login failed";
        }
    }



}
