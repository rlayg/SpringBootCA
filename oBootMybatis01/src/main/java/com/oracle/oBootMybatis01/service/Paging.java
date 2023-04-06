package com.oracle.oBootMybatis01.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {	// 얘는 Service 패키지에 넣었는데 진짜 서비스냐?, 얘는 단독서비스라서 controller dao 연결 안하는거라 클래식한 방법으로 하는게 일반적이다
//  och16에서 controller랑 섞어서 했었어
//	여기서는 아예 객체화 시켜서 할거 모듈화 , 객체로 모듈화 / 아예 클라스로 모듈화해서 들고다니면 수정 쉬워
	private int currentPage = 1;
	private int rowPage = 10;
	private int pageBlock = 10;
	private int start;
	private int end;
	private int startPage;
	private int endPage;
	private int total;
	private int totalPage;
	
	public Paging(int total, String currentPage1) {
		this.total = total;	// 21(데이터에따라 바뀔 수 있음)
		if(currentPage1 != null) {	// 1
			this.currentPage = Integer.parseInt(currentPage1);	// 2
		}
//				    1				  10
		start = (currentPage - 1) * rowPage + 1;	// 시작시 1	11
		end	  = start + rowPage - 1;				// 시작시 10	20
//											25	  /   10
		totalPage = (int) Math.ceil((double)total / rowPage);	// 시작시 3  5  14
//					 2				  2
		startPage = currentPage - (currentPage - 1) % pageBlock;	// 시작시 1
		endPage	  = startPage + pageBlock - 1;	// 10
//		공갈Page 제거
//			10			2
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
	}
	
}
