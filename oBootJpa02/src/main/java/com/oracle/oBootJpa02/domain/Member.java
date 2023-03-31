package com.oracle.oBootJpa02.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(name = "member_seq_gen",	//	Sequence넘버 걸어주는거 , SEQ 만드는거 / 객체SEQ    / Hibernate: select team_seq_generator.nextval from dual 이게 발동(실행시 conSole창에 나옴)
				   sequenceName = "member_seq_generator",		 // DB SEQ	/ DB에 만들어지는 시퀸스
				   initialValue = 1,
				   allocationSize = 1
				   )
public class Member {
	@Id		// PK 걸어주는거 javax로 임포트
	@GeneratedValue(strategy = GenerationType.SEQUENCE,	//SEQUENCE 타입으로 가져갈거야
					generator = "member_seq_gen"
					)
	@Column(name = "member_id")	// 컬럼명 기술 / 실제 테이블에서는 member_id로
	private	Long	id;
	@Column(name = "username", length = 50)	// length도 지정
	private	String	name;
//	관계 설정
//	@ManyToOne(fetch = FetchType.EAGER)		//	@ManyToOne 은 FK를 걸어주는 테이블에서 @ManyToOne을 잡는다 / Many 많은곳에서 FK를 걸어준다 / (fetch = FetchType.EAGER) 기본이 즉시로딩
	@ManyToOne(fetch = FetchType.LAZY)		//	필요한 sql만 볼때 날려줘
	@JoinColumn(name = "team_id")
	private Team team;
	
	
	private	Long	sal;
	
//	테이블이나 컬럼 - javax.persistance
//	트랜젝션 - 스프링프레임워크
	
//	Buffer용도(로만 사용)
	@Transient		// javax로 임포트 / @Transient는 테이블로 관리하라는 뜻
	private	Long	teamid;
	@Transient
	private	String	teamname;
	
}
