package com.oracle.oBootSecurity01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//필터 체인 관리 시작 어노테이션
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
//		 	/user/** 은 인증 필요 --> 인증만 되면 들어갈수 있음
			.antMatchers("/user/**").authenticated()
			.anyRequest()
			.permitAll();
		
		return http.build();	// build 리턴값 갓다대면 나옴
	}
	
//	가장 기본적이고 해쉬 암호화만 사용 가능
//	@Bean
//	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests()
//			.anyRequest()
//			.permitAll();
//		
//		return http.build();	// build 리턴값 갓다대면 나옴
//	}

}
