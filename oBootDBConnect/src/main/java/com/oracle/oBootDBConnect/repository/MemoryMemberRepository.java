package com.oracle.oBootDBConnect.repository;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;
//	JdbcMemberRepository에 @Repository 하면 이곳의 @Repository는 주석처리 @Repository는 하나만 있어야해. oracle에 연결시키려고. 여기는 메모리에 띄어주는거 
//	JdbcMemberRepository와 MemoryMemberRepository의 @Repository 모두 주석처리 하고 SpringConfig 에 @Configuration하면 돼
//@Repository
public class MemoryMemberRepository implements MemberRepository {
	private static Map<Long, Member1> store = new HashMap<Long, Member1>();
	private static long sequence = 0L;
	
	
	
	@Override
	public Member1 save(Member1 member1) {
		System.out.println("MemoryMemberRepository save Start...");
		member1.setId(++sequence);
		store.put(member1.getId(), member1);
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll Start...");
		return new ArrayList<Member1>(store.values());
	}

}
