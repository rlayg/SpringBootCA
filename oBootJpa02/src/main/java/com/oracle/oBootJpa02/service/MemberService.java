package com.oracle.oBootJpa02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;

@Service
@Transactional	//jpa에서 service는 @Transactional는 필수
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public Member memberSave(Member member) {
		System.out.println("MemberService join member.getName() -> " + member.getName());
		memberRepository.memberSave(member);
		return member;
	}
	
//	전체 회원 조회
	public List<Member> getListAllMember(){
		List<Member> listMember = memberRepository.findAll();
		System.out.println("MemberService getListAllMember listMember.size() -> " + listMember.size());
		return listMember;
	}

	public Member findByMember(Long memberId) {
		Member member1 = memberRepository.findByMember(memberId);
		System.out.println("MemberService findByMember member1.getId() -> " + member1.getId());
		System.out.println("MemberService findByMember member1.getName() -> " + member1.getName());
		System.out.println("MemberService findByMember member1.getTeam().getName() -> " + member1.getTeam().getName());
		
		return member1;
	}

	public void memberUpdate(Member member) {
		System.out.println("memberService memberUpdate member.getName() -> " + member.getName());
		System.out.println("memberService memberUpdate member.getTeamname() -> " + member.getTeamname());
		memberRepository.updateByMember(member);
		System.out.println("memberService memberUpdate memberRepository.updateByMember After...");
		return;
		
	}

	public List<Member> getListSearchMember(String searchName) {
		System.out.println("memberService getListSearchMember Start...");
//		String pSearchName = searchName + '%';
		System.out.println("memberService getListSearchMember searchName -> " + searchName);
		List<Member> listMember = memberRepository.findByNames(searchName);
		System.out.println("memberService getListSearchMember listMember.size() -> " + listMember.size());
		return listMember;
	}

	public List<Member> getListFindByMembers(Member member) {
		List<Member> listMember = memberRepository.findByMembers(member.getId(), member.getSal());
		System.out.println("memberService getListFindByMembers listMember.size() -> " + listMember.size());
		return listMember;
	
	}
}
