package com.oracle.oBootJpa01.repository;

import java.util.List;

import com.oracle.oBootJpa01.domain.Member;

//	Repository는 interface 무조건 만들기. service도 웬만해서는 만들기

public interface MemberRepository {
	Member memberSave(Member member);

	List<Member> findAllMember();
}
