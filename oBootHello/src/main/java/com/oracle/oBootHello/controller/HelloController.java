package com.oracle.oBootHello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootHello.domain.Emp;

@Controller
public class HelloController {
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);	// 이 다음부터 로그가 잡힌다
	
	@RequestMapping("hello") 	// 누가 URL로 hello를 친다면
		public String hello(Model model) {
		logger.info("Controller hello Start...");
		model.addAttribute("parameter", "boot Start...");
		//	Prefix -> templates
		//	suffix -> .html
		return "hello";
	}	//	http://localhost:8301/hello / 이런식으로 호출
	
	@ResponseBody
	@GetMapping("ajaxString")
	private String ajaxString(@RequestParam("ajaxName") String aName) {
		System.out.println("HelloController ajaxString aName -> " + aName);
		return aName;
	}	//	http://localhost:8301/ajaxString?ajaxName=kkk // 이런 식으로 호출

	@ResponseBody
	@GetMapping("ajaxEmp")
	public Emp ajaxEmp(@RequestParam("empno") String empno, @RequestParam("ename") String ename) {
		System.out.println("HelloController ajaxEmp empno -> " + empno);
		logger.info("ename -> {}", ename);
		Emp emp = new Emp();
		emp.setEmpno(empno);		
		emp.setEname(ename);
		
		return emp;
	}	// http://localhost:8301/ajaxEmp?empno=1234&ename=kkk 이런식으로 실행
}
