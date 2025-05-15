package org.example.bookstore.service;

import org.example.bookstore.security.JwtUtil;
import org.example.bookstore.security.PrincipalUserDetails;
import org.example.bookstore.vo.MemberVO;
import org.example.bookstore.vo.RoleVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc // MockMvc 자동 설정 (실제 서버 실행 없이 컨트롤러 테스트 가능)
@SpringBootTest // 통합 테스트 환경 구성 (스프링 빈들 실제로 띄움)
class AdminServiceTest {

    @Autowired
    private MockMvc mockMvc; // 가짜 HTTP 요청을 만들어주는 테스트용 객체

    @Autowired
    private JwtUtil jwtUtil; // JWT 토큰 생성 유틸

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 (실제와 동일하게 사용)

    /**
     * 관리자 권한이 있는 사용자가 /admin/category 엔드포인트에 접근하면 성공하는 테스트
     */
    @Test
    void 관리자_권한으로_admin_페이지_접근_성공() throws Exception {
        // Given: 관리자 권한을 가진 유저를 생성하고 JWT 토큰을 발급함
        MemberVO admin = new MemberVO();
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("admin1@@")); // DB에 저장된 비밀번호와 일치해야 함

        RoleVO role = new RoleVO();
        role.setRole("ROLE_ADMIN"); // 관리자 권한 부여

        PrincipalUserDetails userDetails = new PrincipalUserDetails(admin, role); // 시큐리티 인증 객체
        String jwt = jwtUtil.generateToken(userDetails); // JWT 발급

        // When: 인증 토큰을 가지고 /admin/category 요청
        // Then: 200 OK 반환되어야 함
        mockMvc.perform(get("/admin/category")
                        .header("Authorization", "Bearer " + jwt)) // JWT를 Authorization 헤더에 포함
                .andExpect(status().isOk()); // 접근 성공 검증
    }

    /**
     * 일반 권한(USER)을 가진 사용자가 /admin/test에 접근하면 실패(403)하는 테스트
     */
    @Test
    void 일반_사용자가_admin_페이지_접근_실패() throws Exception {
        // Given: 일반 사용자 생성 및 JWT 발급
        MemberVO user = new MemberVO();
        user.setEmail("a123456@example.com");
        user.setPassword(passwordEncoder.encode("a123456!")); // 테스트용 비밀번호

        RoleVO role = new RoleVO();
        role.setRole("ROLE_USER"); // 일반 사용자 권한

        PrincipalUserDetails userDetails = new PrincipalUserDetails(user, role);
        String jwt = jwtUtil.generateToken(userDetails); // 일반 사용자 JWT 발급

        // When: JWT를 가지고 /admin/test 접근 시도
        // Then: 403 Forbidden 응답이 와야 함
        mockMvc.perform(get("/admin/test")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden()); // 접근 거부 검증
    }
}
