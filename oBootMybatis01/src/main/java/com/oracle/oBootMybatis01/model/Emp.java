package com.oracle.oBootMybatis01.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Emp {
//	테이블용
	private int empno;
	@NotEmpty(message = "이름은 필수입니다 흐규흐규")
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;	// 요즘은 String으로 해도 알아서 컨버팅해줘
	private int sal;
	private int comm;
	private int deptno;
	
//	조회용	jpa의 트랜제이트 @Transient
	private String search;
	private String keyword;
	private String pageNum;
	private int	start;	
	private int end;	 
	
	
}
