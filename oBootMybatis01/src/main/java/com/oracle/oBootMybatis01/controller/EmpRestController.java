package com.oracle.oBootMybatis01.controller;

import java.util.List;import org.springframework.aop.support.DelegatePerTargetObjectIntroductionInterceptor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

// @Controller + @ResponseBody
@RestController	// aJax, RestAPI 두가지 용도로 사용된다
@RequiredArgsConstructor	// Auto와잉어드 한거랑 똑같다
public class EmpRestController {
	private final EmpService es;
	
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController Start...");
		String hello = "안녕";
//					StringConverter
		return	hello;
	}
	
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendVO2(int deptno) {
		System.out.println("@RestController deptno -> " + deptno);
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(deptno);
//		객체면 --> JsonConverter
		return vo;
	}
	// http://jsonviewer.stack.hu/		/ test에 넣으면 viewer로 깨끗하게 볼 수 있다
	
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3 (){
		System.out.println("@RestController sendVO3 Start...");
		List<Dept> deptList = es.deptSelect();
		return deptList;
	}
	
	@RequestMapping(value = "/empnoDelete")
	public String empnoDelete(Emp emp) {
		System.out.println("@RestController empnoDelete Start...");
		System.out.println("@RestController empnoDelete eno.getEname() -> " + emp.getEname());
		
		int delStatus = es.deleteEmp(emp.getEmpno());
		String delStatusStr = Integer.toString(delStatus);
		return delStatusStr;
		
	}
	
	
}
