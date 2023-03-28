package com.oracle.oBootDBConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	// 컨트롤러가 정적 파일(static)보다 우선순위가 높음
	@GetMapping("/")
	public String home() {
		System.out.println("HomeController home Start...");
		return "home";
	}
}
