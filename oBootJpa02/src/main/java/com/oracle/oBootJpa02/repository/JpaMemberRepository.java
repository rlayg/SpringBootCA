package com.oracle.oBootJpa02.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

@Repository
public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;
	
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Member memberSave(Member member) {
//		팀 저장
		Team team = new Team();
//		1. 전라좌수사 --> team.setName(member.getTeamname());
//		2. Before --> select team_seq_generator.nextval from dual(team_id:1) 가 발동
//		3. Hibernate: insert into team (teamname, team_id) values (?, ?)
		
//		team.setName(member.getTeam().getName()); 원시적, Member에 @Transient private	String	teamname; 없을때
		team.setName(member.getTeamname());
		em.persist(team);	// Hibernate: insert into team (teamname, team_id) values (?, ?) 가 발동
//		결과 Team
		System.out.println("JpaMemberRepository memberSave " + team);

//		회원 저장
//		4. 위의 1,2를 통째로 member객체에 Setting
		member.setTeam(team);
		em.persist(member);
		
		return member;
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		return memberList;
	}

	@Override
	public Member findByMember(Long memberId) {
//								Entity			PK
		Member member = em.find(Member.class, memberId);
		return member;		
	}

	@Override
	public int updateByMember(Member member) {
		int result = 0;
		System.out.println("JpaMemberRepository updateByMember member.getId() -> " + member.getId());
		Member member3 = em.find(Member.class, member.getId());
		if(member3 != null) {
//			팀 저장
			System.out.println("JpaMemberRepository updateByMember member.getTeamid() -> " + member.getTeamid());
			Team team = em.find(Team.class, member.getTeamid());
			if(team != null) {
				System.out.println("JpaMemberRepository updateByMember member.getTeamname() -> " + member.getTeamname());
				team.setName(member.getTeamname());
				em.persist(team);
			}
//			회원 저장
			System.out.println("JpaMemberRepository updateByMember member.getName() -> " + member.getName());
			member3.setTeam(team);				// 단방향 연관관계 설정, 참조 저장
			member3.setName(member.getName());  // 단방향 연관관계 설정, 참조 저장		/ 여기 수정할 이름을 넣어
// 			jpa01 set만 해도 수정만 넣어도(여기서 바뀌는건 아님) 영속성때문에 persist 주석처리해도 service의 @Transactional때 커밋할때 수정이 된다			
//			em.persist(member3);	// 주석처리해도 수정 된다	
			result = 1;
		} else {	// 존재하지 않는 맴버(0)이라면 없다고 뜸
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist...");
		}
		return result;
		
	}

	@Override
	public List<Member> findByNames(String searchName) {
		String pname = '%' + searchName + '%';
		System.out.println("JpaMemberRepository findByNames name -> " + pname);
		
		List<Member> memberList = em.createQuery("select m from Member m where name Like :name", Member.class)	// name 는 변수명
									.setParameter("name", pname)
									.getResultList();
		System.out.println("JpaMemberRepository memberList.size() -> " + memberList.size());
		return memberList;
	}

	@Override
	public List<Member> findByMembers(Long pid, Long psal) {
		System.out.println("JpaMemberRepository findByNames id -> " + pid);
		System.out.println("JpaMemberRepository findByNames psal -> " + psal);
		
		
		List<Member> memberList = em.createQuery("select m from Member m where id > :id and sal > :sal", Member.class)	
				.setParameter("id", pid)
				.setParameter("sal", psal)
				.getResultList();
		System.out.println("JpaMemberRepository memberList.size() -> " + memberList.size());
		return memberList;
	}
	

}
