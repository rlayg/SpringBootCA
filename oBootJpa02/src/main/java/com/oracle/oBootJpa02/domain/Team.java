package com.oracle.oBootJpa02.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
@SequenceGenerator(
					name = "team_seq_gen",	// 객체명
					sequenceName = "team_seq_generator",	// 매핑할 DB SEQ명
					initialValue = 1,
					allocationSize = 1
				   )
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "team_seq_gen"
					)
	private Long	team_id;
	@Column(name = "teamname")	// 안하면 디폴트로 작성
	private String	name;
	
}
