package com.oracle.oBootMybatis01.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class EmpDept {
//	Join 목적으로 만듦
	
//	Emp용
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;	// 요즘은 String으로 해도 알아서 컨버팅해줘
	private int sal;
	private int comm;
	private int deptno;
	
//	Dept용 (많다는 가정)
	private	String	dname;
	private String	loc;
}
