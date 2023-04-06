package com.oracle.oBootSecurity01.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootSecurity01.model.User;
import com.oracle.oBootSecurity01.service.SecurityService;

@Controller
public class SecurityController {
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("IndexController loginForm 진행...");
		
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		System.out.println("IndexController joinForm 진행...");
		return "joinForm";
	}

	@ResponseBody
	@GetMapping("/")
	public String login() {
		System.out.println("login페이지 Start...");
		return "login 페이지입니다.";
	}
	
	@ResponseBody
	@GetMapping("/admin")
	public String admin() {
		System.out.println("어드민 페이지 Start...");
		return "어드민 페이지입니다.";
	}
	
	@ResponseBody
	@GetMapping("/manager/1")
	public String manager1() {
		return "매니저 1페이지입니다.";
	}
	
	@ResponseBody
	@GetMapping("/manager/2")
	public String manager2() {
		return "매니저 2페이지입니다.";
	}
	
	@ResponseBody
	@GetMapping("/user/1")
	public String user1() {
		return "user 1페이지입니다.";
	}
	
	@ResponseBody
	@GetMapping("/user/2")
	public String user2() {
		return "user 2페이지입니다.";
	}
	
//	암호화 방식
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행: " + user);
		String rawPassword = user.getPassword();
		String encPassword =bCryptPasswordEncoder.encode(rawPassword);	// 해시암호화
		user.setPassword(encPassword);	// 이게 암호화
		securityServiceImpl.save(user);
//		userRepository.save(user);
		return "redirect:/loginForm";
	}		
	
}
