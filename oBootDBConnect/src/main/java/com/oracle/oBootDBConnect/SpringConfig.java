package com.oracle.oBootDBConnect;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDBConnect.repository.JdbcMemberRepository;
import com.oracle.oBootDBConnect.repository.MemberRepository;
import com.oracle.oBootDBConnect.repository.MemoryMemberRepository;

//	@Configuration하면 repository에 @repository 다 안해도 돼
@Configuration	//	자바 코드로 직접 스프링 빈 등록하기 / och03_di03_DI08 
public class SpringConfig {
	private final DataSource dataSource;
//	생성자
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public MemberRepository memberRepository() {
		return new JdbcMemberRepository(dataSource);
//		return new MemoryMemberRepository();
		
	}
}
