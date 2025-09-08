package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (개발 편의상, 실제로는 활성화 권장)
                .authorizeHttpRequests(authorize -> authorize
                        // .antMatchers("/login", "/join").permitAll() // 이전 방식
                        .requestMatchers("/login", "/join").permitAll() // 새로운 방식: 특정 URL 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )

                // 폼 기반 로그인 설정
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")             // 로그인 페이지 URL
                        .loginProcessingUrl("/login")    // 로그인 폼 POST 요청을 처리할 URL (자동 처리)
                        .defaultSuccessUrl("/home", true)// 로그인 성공 시 이동할 URL
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")            // 로그아웃 요청 URL
                        .logoutSuccessUrl("/login")      // 로그아웃 성공 시 이동할 URL
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
