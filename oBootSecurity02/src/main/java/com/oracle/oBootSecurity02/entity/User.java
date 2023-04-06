package com.oracle.oBootSecurity02.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@SequenceGenerator( name = "user_seq_gen",
					sequenceName = "user_seq_generator", //매핑할 DB 시퀀스 이름
					initialValue = 1, 
					allocationSize = 1)
@Data
@Table(name = "user01")
public class User {
	@Id      // primary key
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	                generator = "user_seq_gen")
	private int       id;
	private String    username;
	private String    password;
	private String    email;
	private String    role; // ROLE_USER, ROLE_ADMIN , ROLE_MANAGER
	@CreationTimestamp
	private Timestamp createdate;	
	private Timestamp logindate;

}
