package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.security.JwtUtil;
import org.example.bookstore.service.AccountService;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = accountService.checkExistMember(email);
        return ResponseEntity.ok(Map.of("exists", exists));
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication auth = authenticationManager.authenticate(authInputToken);
            String jwt = jwtUtil.generateToken((UserDetails) auth.getPrincipal());

            return ResponseEntity.ok(Map.of("token", jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberVO member) {
//        if (accountService.findByEmail(member.getEmail()) != null) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
//        }

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        accountService.saveMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }





}
