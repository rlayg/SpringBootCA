package com.oracle.oBootJpa02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;
import com.oracle.oBootJpa02.service.MemberService;


//@SpringBootTest : 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패) / 약속
//반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 
//테스트가 끝나면 트랜잭션을 강제로 롤백 (이 어노테이션이 테스트 케이스에서 사용될 때만 롤백) / 기본이 롤백이다
@SpringBootTest
@Transactional
public class MemberServiceTest {
//	Field DI , Field에 DI하는 방식도 있따
	@Autowired
	MemberService	memberService;
	@Autowired
	MemberRepository	memberRepository;
	
	@BeforeEach	// Test 실행전에 @BeforeEach가 실행된다
	public void before1() {
		System.out.println("Test @BeforeEach...");
	}
	
	@Test	// 기본이 롤백
	@Rollback(value = false)	// Test할때 롤백 안하고 값 넣고싶어	/ 이거하면 DB에 SEQ번호 이빨깨져나오는데 상관없어 유일성이 중요한거지 순서는 상관없어
	public void memberSave() {
//		조건
//		회원저장
		Member member = new Member();
		member.setTeamname("고구려");
		member.setName("강이식");
		
//		수행
		Member member3 = memberService.memberSave(member);
//		결과
		System.out.println("MemberServiceTest memberSave member3.getId() -> " + member3.getId());
		System.out.println("MemberServiceTest memberSave member3.getName() -> " + member3.getName());
		System.out.println("MemberServiceTest memberSave member3.getTeam().getName() -> " + member3.getTeam().getName());
	}
//	MemberServiceTest - 우클릭 - JUnit Test
	
	@Test
	@Rollback(value = true)
	public void memberFind() {
//		조건
//		회원조회 --> 이순신
		Long findId = 2L;
//		수행
		Member member = memberService.findByMember(findId);
//		결과
		System.out.println("MemberServiceTest memberFind member.getName() -> " + member.getName());
	}
	
	
	
}
