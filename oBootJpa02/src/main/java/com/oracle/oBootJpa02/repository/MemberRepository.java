package com.oracle.oBootJpa02.repository;

import java.util.List;

import com.oracle.oBootJpa02.domain.Member;

public interface MemberRepository {
	Member			memberSave(Member member);
	List<Member>	findAll();
	Member			findByMember(Long memberId);
	int				updateByMember(Member member);
	List<Member> 	findByNames(String searchName);
	List<Member> 	findByMembers(Long id, Long sal);
	
}
