package org.example.bookstore.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final String FRONTEND_ADDRESS = "http://localhost:8001";

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> {}) // CORS 설정 활성화
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.sendRedirect(FRONTEND_ADDRESS); // 인증 안 된 경우 프론트로 리디렉션
//                        })
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // CORS 설정 활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").authenticated() // /admin/login은 인증 없이 접근 허용
                        .anyRequest().permitAll() // 그 외 모든 요청은 인증 필요
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 인증되지 않은 경우 프론트로 리디렉션하지 않음
                            if (!request.getRequestURI().equals("/admin/login")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 인증 실패 처리 (리디렉션 없음)
                            }
                        })
                );

        return http.build();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(FRONTEND_ADDRESS)
                        .allowedMethods("*")
                        .allowCredentials(true);
            }
        };
    }
}