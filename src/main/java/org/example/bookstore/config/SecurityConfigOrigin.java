package org.example.bookstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//@EnableWebSecurity
//@Configuration
//@RequiredArgsConstructor
public class SecurityConfigOrigin {

//    private final String FRONTEND_ADDRESS = "http://localhost:8001";
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .cors(cors -> {}) // CORS 설정 활성화
////                .authorizeHttpRequests(auth -> auth
////                        .anyRequest().authenticated()
////                )
////                .exceptionHandling(ex -> ex
////                        .authenticationEntryPoint((request, response, authException) -> {
////                            response.sendRedirect(FRONTEND_ADDRESS); // 인증 안 된 경우 프론트로 리디렉션
////                        })
////                );
////
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> {}) // CORS 설정 활성화
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login").authenticated() // login 에 대해서는 인증필요
//                        .anyRequest().permitAll() // 그 외 모든 요청은 접근 가능
//                );
////                .exceptionHandling(ex -> ex
////                        .authenticationEntryPoint((request, response, authException) -> {
////                            // 인증되지 않은 경우 프론트로 리디렉션하지 않음
////                            if (!request.getRequestURI().equals("/admin/login")) {
////                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 인증 실패 처리 (리디렉션 없음)
////                            }
////                        })
////                );
//
//        return http.build();
//    }
//
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(FRONTEND_ADDRESS)
//                        .allowedMethods("*")
//                        .allowCredentials(true);
//            }
//        };
//    }

    private final String FRONTEND_ADDRESS = "http://localhost:8001";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // 로그인 요청은 인증 없이 허용
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(FRONTEND_ADDRESS));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



}