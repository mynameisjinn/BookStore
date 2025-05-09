package org.example.bookstore.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.AccountService;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
@RestController
public class TestController {

    @Autowired
    private AccountService accountService;
    @Operation(summary="테스트", description="테스트 중입니다.")
    @GetMapping("test")
    public void getTest(){

        log.info("테스트");

        List<MemberVO> list = accountService.selectMemberInfo();
        log.info(list.toString());
    }





//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> params) {
//        try {
//            log.info("로그인 성공");
//            return "Login success";
//        } catch (AuthenticationException e) {
//            return "Login failed";
//        }
//    }



}
