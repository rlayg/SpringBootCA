package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberJpaController {
	
	private final MemberJpaService memberJpaService;
	
	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		System.out.println("MemberJpaController /memberJpa/new Start...");
		return "memberJpa/createMemberForm";
	}
	
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		System.out.println("MemberJpaController create Start...");
		System.out.println("member.getId() -> " + member.getId());
		System.out.println("member.getName() -> " + member.getName());
		memberJpaService.join(member);
		
		return "memberJpa/createMemberForm";
	}
	
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		System.out.println("MemberJpaController listMember Start...");
		List<Member> memberList = memberJpaService.getListAllMember();
		model.addAttribute("members", memberList);
		return "memberJpa/memberList";
	}
	
	@GetMapping(value = "memberJpa/memberUpdateForm")
	public String memberUpdateForm(Long id, Model model) {
		Member member = null;
		String rtnJsp = "";
		System.out.println("MemberJpaController memberUpdateForm id -> " + id);
//		목적 : 객체가 NULL Check 용이
		Optional<Member> maybeMember = memberJpaService.findById(id);	// Optional NULL값을 허용해서 던저주는거다. NULL값이라도 받겟다
		if(maybeMember.isPresent()) {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is Not NULL");
			member = maybeMember.get();
			model.addAttribute("member", member);
			rtnJsp = "memberJpa/memberModify";
		} else {
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is NULL");
			model.addAttribute("message", "member가 존재하지 않으니, 입력부터 수행해 주세요");
			rtnJsp = "forward:/members";	
		}
		
		return rtnJsp;	// 위에서 바로 리턴하지말고 이렇게 몰아서 리턴하래 / 리턴을 통할할 수 있으면 통합해라 분기가 많아지면 오류가 발생할 가능성 올라간대
	}
	
	@GetMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberJpaController memberUpdate id -> " + member.getId());
		System.out.println("MemberJpaController memberUpdate member.getName -> " + member.getName());
		memberJpaService.memberUpdate(member);
		return "redirect:/members";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
