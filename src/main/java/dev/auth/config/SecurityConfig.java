package dev.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.formLogin()
			.defaultSuccessUrl("/", true)
			.loginPage("/login") // 커스텀 로그인 페이지 설정
			.permitAll(); // 로그인 페이지는 누구나 접근 가능

		// 요청 경로에 따른 권한 설정
		http.authorizeRequests()
			.mvcMatchers("/").permitAll() // 루트 경로는 인증 없이 접근 가능
			.mvcMatchers("/admin/**").hasRole("ADMIN") // "/admin/**" → ADMIN만 접근 가능
			.mvcMatchers("/movie/**").hasAnyRole("USER", "ADMIN") // "/movie/**" → USER, ADMIN 접근 가능
			.anyRequest().authenticated(); // 그 외 모든 요청은 인증 필요

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
