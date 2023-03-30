package com.oracle.oBootJpa01.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

@Repository
public class JpaMemberRepository implements MemberRepository {
// 	JPA DML --> EntityManager 설정 필수

	private final EntityManager em;
	
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Member memberSave(Member member) {
		em.persist(member);		//이렇게 하면(persist) JPA프레임워크가 알아서 인서트 해준다
		System.out.println("JpaMemberRepository memberSaver member After...");
		return member;
	}

//	전체조회 로직
	@Override
	public List<Member> findAllMember() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class) // Member, Member.class의 Member는 domain의 Member / Member.class는 멤버를 class타입으로 뽑아내겟다
									.getResultList();	//	List<Member> 의 Member타입이 Member.class, getResultList()이 List
		System.out.println("JpaMemberRepository findAllMember memberList.size() -> " + memberList.size());
		return memberList;
	}

//	부분조회 로직
	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + '%';
		System.out.println("JpaMemberRepository findByNames pname -> " + pname);
		List<Member> memberList = em.createQuery("select m from Member m where name Like :name", Member.class)
									.setParameter("name", pname)	// "name"은 위 쿼리문의 name
									.getResultList();
		System.out.println("JpaMemberRepository memberList.size() -> " + memberList.size());
		return memberList;
	}

}
