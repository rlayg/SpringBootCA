package com.oracle.oBootHello.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootHello.domain.Member1;

//Memory를 가지고 CRUD, DB에 연결X 
@Repository		// 이걸 해야 boot 갖자대면 @4개 나오는데 이 @로 안의 4개를 한번에 처리했다는거	/ Component는 어플리케이션컨텍스는 뉴 어풀~ 이거랑 같은거
public class MemoryMemberRepository implements MemberRepository {
//						KEY   Value
	private static Map<Long, Member1> store = new HashMap<Long, Member1>();
	private static Long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		member1.setId(++sequence);
		store.put(member1.getId(), member1);
		System.out.println("MemoryMemberRepository sequence -> " + sequence);
		System.out.println("MemoryMemberRepository member1.getName() -> " + member1.getName());
		
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll Start...");
		//	store의 value(Member1)
		List<Member1> listMember = new ArrayList<Member1>(store.values());
		System.out.println("MemoryMemberRepository findAll lisMember.size() -> " + listMember.size());
		return listMember;
	}
}
