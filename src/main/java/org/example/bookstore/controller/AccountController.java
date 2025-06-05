package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.security.PrincipalUserDetails;
import org.example.bookstore.security.PrincipalUserDetailsService;
import org.example.bookstore.security.JwtUtil;
import org.example.bookstore.service.AccountService;
import org.example.bookstore.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Operation(summary="이메일 중복체크", description="회원가입시 이메일 중복체크")
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = accountService.checkExistMember(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @Operation(summary="로그인", description="jwt 를 이용한 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {

        try {

            Map<String, Object> loginResult = accountService.login(body.get("email"), body.get("password"));

            long accessTokenAge = jwtUtil.getAccessTokenExpirationMs() / 1000;
            long refreshTokenAge = jwtUtil.getRefreshTokenExpirationMs() / 1000;


            // 쿠키에 저장
            ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken",  (String) loginResult.get("accessToken"))
                    .httpOnly(true) //javaScript에서 접근 불가하게 설정.→ XSS 공격 방지 목적 (보안성 ↑)
                    .secure(false) // HTTPS에서만 사용하려면 true
                    .path("/") //이 쿠키를 보낼 요청 경로 범위 지정.'/'이므로 모든 경로에 대해 쿠키가 전송됨
                    .maxAge(accessTokenAge)
                    /*
                        CSRF 방지 관련 설정
                        Lax는 일반적인 상황(링크 클릭 등)에서는 쿠키를 전송하지만, 크로스 사이트 폼 전송 같은 일부 상황에서는 막음
                        → 보안과 유연성의 균형
                    */
                    .sameSite("Lax") // 또는 "Strict" / "None"
                    .build();

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", (String) loginResult.get("refreshToken"))
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(refreshTokenAge)
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());


            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("user",  loginResult.get("user"));
            responseBody.put("role", loginResult.get("role"));

            return ResponseEntity.ok(responseBody);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }


    @Operation(summary="회원가입", description="사용자 회원가입")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberVO member) {

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        accountService.saveMember(member);
        return ResponseEntity.ok("회원가입 성공");
    }

    @Operation(summary="마이페이지 조회", description="사용자 마이페이지 조회")
    @GetMapping("/mypage")
    public ResponseEntity<Map<String, Object>> mypage(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("마이페이지 접근: " + userDetails.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        response.put("message", "마이페이지 접근 가능");

        return ResponseEntity.ok(response);
    }





}
