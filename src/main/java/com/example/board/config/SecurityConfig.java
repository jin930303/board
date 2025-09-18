package com.example.board.config;


import com.example.board.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**","/checkNickname", "/checkId", "/checkEmail", "/memberSave").permitAll()
                        .requestMatchers("/login", "/join", "/board1/**", "/board2/**", "/board3/**", "/").permitAll()
                        .requestMatchers("/boardSave", "/inputBoard", "/updateBoard", "/updateSave").permitAll()
                        .requestMatchers("inputBoard2", "/boardSave2", "/detail/{id}","/board5/**").permitAll()
                        .requestMatchers("/board4/**","update4/{id}","/boardSave4","/delete4Board/{id}","update5Board/{id}","/delete5Board/{id}").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .oauth2Login(oautch2 ->oautch2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo->userInfo
                                        .userService(customOAuth2UserService)
                                )
                        .defaultSuccessUrl("/",true)
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
        return http.build();
    }




}
