package com.oracle.oBootMybatis01.model;

import lombok.Data;

@Data
public class DeptVO {
//	입력
	private int deptno;
	private String dname;
	private String loc;
//	출력
	private int odeptno;
	private String odname;
	private String oloc;
//	VO는 값 그 자체를 나타내는 객체, 특정 값 자체를 표현하기 때문에 불변성을 보장
}
