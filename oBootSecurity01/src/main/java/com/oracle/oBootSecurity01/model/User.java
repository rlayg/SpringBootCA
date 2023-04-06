package com.oracle.oBootSecurity01.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@SequenceGenerator( name = "user_seq_gen",
					sequenceName = "user_seq_generator",	// 매핑할 DB 시퀸스 이름
					initialValue = 1,
					allocationSize = 1
					)
@Table(name = "user01")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "user_seq_gen")
	private	int	id;
	private	String username;
	private	String password;
	private	String email;
	private	String role;	//ROLE_USER, ROLE_ADMIN
//	Hibernate에서는 엔티티 객체에 대해 INSERT, UPDATE 등의 쿼리가 발생할 때, 현재 시간을 자동으로 저장
	@CreationTimestamp
	private	Timestamp createdate;

}
