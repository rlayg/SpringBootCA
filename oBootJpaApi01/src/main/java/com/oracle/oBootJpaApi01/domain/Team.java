package com.oracle.oBootJpaApi01.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@NoArgsConstructor
@SequenceGenerator(
					name = "team_seq_gen5",	// 객체명
					sequenceName = "team_seq_generator5",	// 매핑할 DB SEQ명
					initialValue = 1,
					allocationSize = 1
				   )
@Table(name = "team5")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "team_seq_gen5"
					)
	private Long	team_id;
	@Column(name = "teamname", length = 50)	// 안하면 디폴트로 작성
	private String	name;
	
}
