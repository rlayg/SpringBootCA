package com.oracle.oBootJpa01.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@ToString	//밑의 toString() 만들어줌
@Data		// @Getter @Setter @ToString 다 처리 / 대신 다른것도 만들어서 쓸대없는 메모리도 먹을 수 있다
@Entity		//Row라고 생각하래
@Table(name = "member1")	// Table명 지정	
public class Member {		// Member는 객체명
	@Id		// PK 지정
	private Long	id;
	private String	name;
//  application.properties에 spring.jpa.hibernate.ddl-auto=create가 있음/ 얘가 table을 자동으로 지우고 새로 만듦. 
//	콘솔에 drop table, create table; drop할게 없다면 에러메세지 나옴 신경 ㄴㄴ 
//	그래서 서버 재시작할때마다 table db도 입력한것 다 지워짐	
//	create가 드랍하고 재생성해서 개발할때는 상관없는데, 이게 싫거나 배포할때는
//	spring.jpa.hibernate.ddl-auto=none . 만들때는 create 만든 후에는 none으로 해서 해도 돼 또는 update
//	spring.jpa.hibernate.ddl-auto=update 이걸로 하기. 여기 수정 변경됫을때 자동으로 update해줌 이게 더 좋을것같데
//	No identifier 에러가 나면 PK가 없다는것
	
	
//	@Override
//	public String toString() {
//		String returnStr = "";
//		returnStr = "[id : " + this.id + ", name : " + this.name + "]";
//		return returnStr;
//	} // 여기에 toString을 만들어놧기에 member를(객체를) 부르면 이렇게 나온다
}
