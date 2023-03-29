package com.oracle.oBootHello.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootHello.domain.Member1;
import com.oracle.oBootHello.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);	// 로거 걸어주는 애
	
	// 전통적 - 컨트롤러에서 서비스 부름
//	MemberService memberService = new MemberService();
	
	// 향우 DI방식 적용
	
	private final MemberService memberService ;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	
//	static에 인덱스가 있고(홈화면)
//	컨트롤러에 home과 home.html 없다면(주석) 저쪽(static)으로 감
//	home과 home.html 과 static의 index 둘 다 있으면
//	이쪽 home.html로 간다
	// Controller @GetMapping("/") 존재시 우선순위. 컨트롤러가 우선
	@GetMapping("/")
	public String home() {
		System.out.println("MemberController / home Start...");
		return "home";
	}
	
	
	@GetMapping(value = "members/memberForm")
	public String memberForm() {
		System.out.println("MemberController /members/memberForm Start...");
		return "members/memberForm";
	}
	
	
	
	@PostMapping(value = "/members/save")
	public String save(Member1 member1) {
		System.out.println("MemberController /members/save Start...");
		System.out.println("MemberController /members/save member1.getName() -> " + member1.getName());
		Long id = memberService.memberSave(member1);
		System.out.println("MemberController /members/save id -> " + id);
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/members/memberList")
	public String memberList(Model model) {
		logger.info("memberList Start...");
		List<Member1> memberLists = memberService.allMembers();
		model.addAttribute("memberLists", memberLists);
		logger.info("memberLists.size() -> {}", memberLists.size());
		
		return "members/memberList";
	}
	
}
