package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private AccountService accountService;

//    @GetMapping("/check-email")
//    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
//        boolean exists = memberService.checkExistMember(email);
//        return ResponseEntity.ok(Map.of("exists", exists));
//    }
//




}
