package com.oracle.oBootHello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootHello.domain.Member1;
import com.oracle.oBootHello.repository.MemberRepository;
import com.oracle.oBootHello.repository.MemoryMemberRepository;

@Service	// 이걸 해 야 Bean으로 떠잇게 된다
public class MemberService {

	//전통적
//	MemberRepository memberRepository = new MemoryMemberRepository();	// 멤버변수로
	
	// boot방법  DI방법
	private final MemberRepository memberRepository;
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	
	
	
	
	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSave Start...");
		memberRepository.save(member1);
		return member1.getId();
		
	}

	public List<Member1> allMembers() {
		System.out.println("MemberService allMembers Start..");
		List<Member1> memList = null;
		memList = memberRepository.findAll();
		System.out.println("memList.size() -> " + memList.size());
		return memList;
	}

}
