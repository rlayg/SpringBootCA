package com.oracle.oBootSecurity02.controller;

import java.util.Iterator;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootSecurity02.configuration.auth.PrincipalDetails;
import com.oracle.oBootSecurity02.entity.User;
import com.oracle.oBootSecurity02.service.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SecurityController02 {
	private final SecurityService securityServiceImpl;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/user")
	public String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("SecurityController02 user Start...");
		System.out.println("SecurityController02 user principal->"+principal);
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority authority = iter.next();
			System.out.println("Controller authority.getAuthority()->"+authority.getAuthority());
		}
		return "user";
	}
	
	@GetMapping("/user/2")
	public String user2() {
		System.out.println("SecurityController02 user2 Start...");
		return "user";
	}

	@GetMapping("/manager")
	public String manager() {
		System.out.println("SecurityController02 manager Start...");
		return "manager";
	}
	@GetMapping("/manager/2")
	public String manager2() {
		System.out.println("SecurityController02 manager2 Start...");
		return "manager";
	}

	@GetMapping("/admin")
	public String admin() {
		System.out.println("SecurityController02 admin Start...");
		return "admin";
	}
	@GetMapping("/admin/2")
	public String admin2() {
		System.out.println("SecurityController02 admin2 Start...");
		return "admin";
	}

	@Secured("ROLE_MANAGER")
	@GetMapping("/count")
	public String count() {
		System.out.println("SecurityController02 count Start...");
		return "count";
	}
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // or로 해서 어드민 매니저 다 가능
	// @PostAuthorize	들어간다음에 검증하는거라 이건 의미가 없어. 그래도 만들긴 해놧는데 쓸일이 없을듯
	@GetMapping("/count/2")
	public String count2() {
		System.out.println("SecurityController02 count2 Start...");
		return "count";
	}

	
	@GetMapping("/loginFail")
	public String loginFail() {
		System.out.println("loginFail start...");
		return "loginFail";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("SecurityController loginForm 진행 ...");

		return "loginForm";
	}

	// User 등록 Form
	@GetMapping("/joinForm")
	public String joinForm() {
		System.out.println("SecurityController joinForm 진행 ... ");
		return "joinForm";
	}
	
	// 진짜 User 등록 
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		// Spring Security 권장 --> passwd 암호화 
	    String encPasswd = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encPasswd);
		securityServiceImpl.save(user);
		
		return "redirect:/loginForm";

	}
	
	
	
	
	

}
