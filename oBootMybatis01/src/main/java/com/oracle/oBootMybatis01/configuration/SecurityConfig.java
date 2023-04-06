package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity	// 필터 체인 관리 시작 어노테이션
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.anyRequest()
			.permitAll(); // 이걸 반드시 걸어주어야 한다 / 모든 요청을 인증 없이 허용하기 위해 
		
		return http.build();
	}
	/*
	 @EnableWebSecurity 어노테이션을 사용하여 필터 체인 관리를 시작합니다.
	 BCryptPasswordEncoder를 빈으로 등록하고, 이를 이용하여 비밀번호를 암호화 할 수 있습니다.
	 filterChain() 메소드에서는 HttpSecurity 객체를 매개변수로 받아 필터 체인을 설정합니다. 
	 http.csrf().disable()을 통해 CSRF 보호 기능을 비활성화하고, 
	 모든 요청을 인증 없이 허용하기 위해 http.authorizeRequests().anyRequest().permitAll()을 호출합니다. 
	 이 메소드는 필수적으로 호출되어야 합니다. 
	 마지막으로 http.build()를 호출하여 SecurityFilterChain 객체를 반환합니다.
	 */
}
