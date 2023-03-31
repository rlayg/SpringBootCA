package com.oracle.oBootJpa02.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/new")
	public String createForm() {
		System.out.println("MemberController /members/new Start...");
		return "members/createMemberForm";
	}
	
	@PostMapping(value = "/memberSave")
	public String memberSave(Member member) {
		System.out.println("MemberController create Start...");
		System.out.println("member.getTeamname() -> " + member.getTeam());
		System.out.println("member.getName() -> " + member.getName());
		memberService.memberSave(member);
		return "redirect:/";
	}
	
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		List<Member> memberList = memberService.getListAllMember();
		System.out.println("memberList.get(0).getName() ->" + memberList.get(0).getName());	// get(0)은 list의 첫번째
		System.out.println("memberList.get(0).getTeam().getName() ->" + memberList.get(0).getTeam().getName());
		model.addAttribute("memberList", memberList);	// memberList에 memberList를 담아셔 뷰단에서 볼 수 있다
		return "members/memberList";
	}
	
	@GetMapping(value = "/memberModifyForm")
	public String memberModify(Long id, Model model) {
		System.out.println("MemberController memberModify id -> " + id);
		Member member = memberService.findByMember(id);
		
		System.out.println("member.get().getId() -> " + member.getId());
		System.out.println("member.get().getName() -> " + member.getName());
		System.out.println("member.getTeam().getName() -> " + member.getTeam().getName());
		model.addAttribute("member", member);
		
		return "members/memberModify";
	}
	
//	회원 정보 수정
	@PostMapping(value = "/members/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberController memberUpdate id -> " + member.getId());
		System.out.println("MemberController memberUpdate member.getName() -> " + member.getName());
		System.out.println("MemberController memberUpdate member.getTeamname() -> " + member.getTeamname());
		memberService.memberUpdate(member);
		System.out.println("MemberController memberUpdate memberService.updateByMember After...");
		return "redirect:/members";		// redirect는 controller 내에서 돌아가는 애야
		
	}
	
//	회원검색(단일)
	@GetMapping(value = "/members/search")
	public String search(Member member, Model model) {
		System.out.println("/members/search member.getName() -> " + member.getName());
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		System.out.println("MemberController /members/search memberList.size() -> " + memberList.size());
		model.addAttribute("memberList", memberList);
		return "members/memberList";		
	}
	
//	
	@GetMapping(value = "findByListMembers")
	public String findByListMembers(Member member, Model model) {
		List<Member> memberList = memberService.getListFindByMembers(member);
		System.out.println("memberList.get(0).getName() ->" + memberList.get(0).getName());	// get(0)은 list의 첫번째
		System.out.println("memberList.get(0).getTeam().getName() ->" + memberList.get(0).getTeam().getName());

		model.addAttribute("memberList", memberList);
		return "members/memberList";		
	}
	
	
	
	
	
	
	
}
