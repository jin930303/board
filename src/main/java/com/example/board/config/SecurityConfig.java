package com.example.board.config;


import com.example.board.service.CustomOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/checkNickname", "/checkId", "/checkEmail", "/memberSave", "/upload/**").permitAll()
                        .requestMatchers("/", "/login", "/join", "/board1/**", "/board2/**", "/board3/**", "/board4/**").permitAll()
                        .requestMatchers("/boardSave2", "/detail/{id}", "/board5/**", "/detail5Board/{id}").permitAll()
                        .requestMatchers("/api/comments/**", "/api/detail5Board/{id}").permitAll()
                        .requestMatchers("update4/{id}", "/boardSave4", "/delete4Board/{id}", "update5Board/{id}",
                                "/delete5Board/{id}", "/boardSave").authenticated()
                        .requestMatchers("/inputBoard", "/inputBoard2", "/inputBoard3", "inputBoard4", "/api/board5/like/{boardId}").authenticated()
                        .requestMatchers("/updateBoard", "/updateSave","/api/comments/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("text/plain;charset=UTF-8");
                                response.getWriter().write("로그인 후 이용해주세요");
                            }
                        }))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .oauth2Login(oautch2 -> oautch2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
        return http.build();
    }


}
