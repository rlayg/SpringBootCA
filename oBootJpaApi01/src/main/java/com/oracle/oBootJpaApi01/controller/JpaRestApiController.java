package com.oracle.oBootJpaApi01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.controller.JpaRestApiController.Result;
import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController		//Controller + ResponseBody 
@Slf4j		//Log 한거랑 같은거 / private static final Logger logger = LoggerFactory.getLogger(HelloController.class)
@RequiredArgsConstructor
public class JpaRestApiController {
	
	private final MemberService memberService;
	
	@PostMapping("/restApi/v1/memberSave")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		System.out.println("JpaRestApiController /restApi/v1/memberSave member.getId() -> " + member.getId());
		
		log.info("member.getName() -> {}.", member.getName());
		log.info("member.getSal() -> {}.", member.getSal());
		
		Long id = memberService.saveMember(member);
		
//		CreateMemberResponse createMemberResponse = new CreateMemberResponse(id);
//		return createMemberResponse;	// 밑에 한줄이 이거다
		return new CreateMemberResponse(id);		// Json으로 돌려주려고 객체로 함, 그리고 inner class로 함 그래야 편해
//		값 입력 : http://localhost:8305/restApi/v1/memberSave 여기에
//		포스트맨 사용법postman POST선택 ---> Body --> raw---> JSON  
//		---> {"name":"kkk"} 치기, {"name":"유성룡","sal":"7000"} 이것도 치기
//		team5 에 조선조정 1 해서 만들기
//		이렇게 하면 POSTMAN에서 값이 객체로 와
		
	}
	
//	Bad API 나쁜 API
	@GetMapping("/restApi/v1/members")
	public List<Member> membeVer1(){
		System.out.println("JpaRestApiController /restApi/v1/members Start...");
		List<Member> listMember = memberService.getListAllMember();
		return listMember;
	}	// <Member> 이렇게 하면 모든 데이터가 다 가, 보내줄것만 보내줘야해
	
//	좋은 API easy 버전
// 	Good API  Easy Version
// 	목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v21/members")
	public Result membersVer21() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v21/members findMembers.size() -> " + findMembers.size());
		
		List<MemberRtnDto> resultList = new ArrayList<>();
//		List<Member> findMembers --> List<MemberRtnDto> resultList 이전
//		이전 목적 : 반드시 피리요한 Data 만 보여준다(외부 노출 최대한 금지)
		for(Member member : findMembers) {
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("restApi/v21/members getName -> " + memberRtnDto.getName());
			System.out.println("restApi/v21/members getSal -> " + memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("restApi/v21/members resultList.size() -> " + resultList.size());
		return new Result(resultList.size(), resultList);
	}
	
//	Good API  람다  Version		// easy버전이랑 같은데 람다넣은거
//	목표 : 이름 & 급여 만 전송
	@GetMapping("/restApi/v22/members")
	public Result membersVer22() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v22/members findMembers.size() ->" + findMembers.size());
//		자바 8에서 추가한 스트림(Streams)은 람다를 활용할 수 있는 기술 중 하나
		List<MemberRtnDto> memberCollect = findMembers.stream()
											.map(m->new MemberRtnDto(m.getName(), m.getSal()))
											.collect(Collectors.toList());
		System.out.println("restApi/v22/members memberCollect.size() -> " + memberCollect.size());
		return new Result(memberCollect.size(), memberCollect);
	}
	
/*
 *   수정 API
 *   PUT 방식을사용했는데, PUT은 전체 업데이트를 할 때 사용
 *   URI 상에서 '{ }' 로 감싸여있는 부분과 동일한 변수명을 사용하는 방법
 *   해당 데이터가 있으면 업데이트를 하기에 
 *   PUT요청이 여러번 실행되어도 해당 데이터는 같은 상태이기에 멱등
 *   
 */
	
	@PutMapping("/restApi/v21/members/{id}")	// {id}가 라우팅 방식	/	Put은 수정
	public UpdateMemberResponse updateMemberV21(@PathVariable("id") Long id,	// @PathVariable("id") 의"id"는 {id}
												@RequestBody @Valid UpdateMemberRequest uMember) {
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		Member findMember = memberService.findByMember(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
	}	//	돌려줄 애를 Response / 받을애를 Request
	
	@Data
	static class UpdateMemberRequest{
		private String name;
		private Long   sal;
	}
	
	@Data
	@AllArgsConstructor
	class UpdateMemberResponse{
		private Long	id;
		private String	name;
		private Long	sal;
	}
	
	@Data
	@AllArgsConstructor
	class MemberRtnDto {
		private String name;
		private Long   sal;
	}
	
//	T는 인스턴스를 생성할 때 구체적인 타입으로 변경 --> 유연성 / <T>는 어떤 타입이든지 받을 수 있어
	@Data
	@AllArgsConstructor		// All은 전부다
	class Result<T> {
		private final int totCount;	// 총 인원수 추가
		private final T data;
	}
	
	@Data
	@RequiredArgsConstructor	// Required는 final 들어간것만
	class CreateMemberResponse {	// inner class
		private final Long id;		// Json으로 컨버팅되서 돌려준데
//		public CreateMemberResponse(Long id) {
//			this.id = id;
//		}
		
	}
	
}
