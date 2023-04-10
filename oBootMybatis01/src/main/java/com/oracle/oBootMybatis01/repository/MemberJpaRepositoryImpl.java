package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {
	
	private final EntityManager em;
	
	@Override
	public Member save(Member member) {
		System.out.println("MemberJpaRepositoryImpl save Start...");
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll Start...");
		List<Member> memberList = em.createQuery("SELECT m FROM Member m", Member.class)
									.getResultList();
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return Optional.ofNullable(member);
//		Optional NULL값을 허용해서 던저주는거다. NULL값이라도 받겟다
	}

	@Override
	public void updateByMember(Member member) {
// 		1. 영속성 관리 X		--> Setter 저장 불가
// 		2. merge		--> 현재 Setting 된것만 수정, 나머지는 NULL
		em.merge(member);
		
	}

}
