package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor	//지정된 속성들에 대해서 생성자를 만들어줌 / 생성자 만들어주는 롬북 / final 키워드가 붙은 필드를 생성자에 포함시켜주는 생성자 코드를 자동으로 생성
public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;
	
	@Override
	public Long save(Member member) {
		System.out.println("JpaMemberRepository save before...");
		em.persist(member);
		return member.getId();
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		System.out.println("JpaMemberRepository findAll memberList.size() -> " + memberList.size());
		return memberList;
	}

	@Override
	public int updateByMember(Member member) {
		int result = 0;
		Member member3 = em.find(Member.class, member.getId());
		if(member3 != null) {
//			회원 저장
			member3.setName(member.getName());
			member3.setSal(member.getSal());
			result = 1;
			System.out.println("JpaMemberRepository updateByMember Update...");
		} else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist...");
		}
		return result;
	}

	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

}
