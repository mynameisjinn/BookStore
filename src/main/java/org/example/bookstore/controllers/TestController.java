package org.example.bookstore.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
@RestController
public class TestController {

    @GetMapping("test")
    public void getTest(){
        log.info("테스트");
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
