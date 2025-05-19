package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.security.PrincipalUserDetails;
import org.example.bookstore.security.PrincipalUserDetailsService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Tag(name = "Account", description = "계정 관련 API")
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


    // TODO : 비밀번호 오류시 return body 에 string 말고 custom exception 만들어서 내보내기
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // 이메일과 비밀번호로 인증을 시도
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(email, password);

        try {
            Authentication auth = authenticationManager.authenticate(authInputToken);

            // 1분 만료 시간 설정
//            jwtUtil.setExpirationMs(1000 * 60);

            String jwt = jwtUtil.generateToken((UserDetails) auth.getPrincipal());

            // 로그인한 유저 정보 추출 (CustomUserDetails에서 제공하는 member 정보)
            PrincipalUserDetails userDetails = (PrincipalUserDetails) auth.getPrincipal();
            MemberVO member = userDetails.getMember(); // 로그인한 유저 정보

            MemberVO loginUser = new MemberVO();
            loginUser.setEmail(member.getEmail());
            loginUser.setAddress(member.getAddress());
            loginUser.setId(member.getId());

            // JWT 토큰과 유저 정보를 함께 반환
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", loginUser); // 유저 정보 포함
            response.put("role", userDetails.getRole().getRole());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberVO member) {

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        accountService.saveMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/mypage")
    public ResponseEntity<Map<String, Object>> mypage(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("마이페이지 접근: " + userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        response.put("message", "마이페이지 접근 가능");

        return ResponseEntity.ok(response);
    }





}
