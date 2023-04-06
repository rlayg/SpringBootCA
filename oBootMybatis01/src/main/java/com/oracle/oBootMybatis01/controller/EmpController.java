package com.oracle.oBootMybatis01.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmpController {
	
	@Autowired
	private final EmpService es;
	
	/*
	private final EmpService es;는 EmpService 객체를 생성하는 것이 아니라, 이미 생성된 EmpService 객체를 참조하기 위한 필드 선언 코드입니다.
	final 키워드는 이 필드가 초기화 이후 변경되지 않음을 나타내며, EmpService는 해당 필드의 자료형입니다.
	@RequiredArgsConstructor 애노테이션이 선언되어 있기 때문에, es 필드는 EmpService 타입의 생성자 파라미터로 선언된 것입니다. 따라서 이 필드는 EmpController 클래스가 생성될 때, 생성자를 통해 주입된 이미 생성된 EmpService 객체를 참조하게 됩니다.
	즉, 이 필드는 EmpController에서 EmpService 객체를 사용하기 위한 인스턴스 변수로, 의존성 주입(Dependency Injection)을 통해 EmpService 객체의 메서드를 사용할 수 있도록 해줍니다.
	*/
	
	@RequestMapping(value = "listEmp")
	public String empList(Emp emp, String currentPage, Model model) {
		System.out.println("EmpController Start listEmp...");
		System.out.println("EmpController emp -> " + emp);
		System.out.println("EmpController currentPage -> " + currentPage);
//		Emp 전체 Count 20 ( 만든거에따라 다를 수 있어)
		int totalEmp = es.totalEmp();
		System.out.println("EmpController totalEmp -> " + totalEmp);
		
		/*
		String currentPage 매개변수는 HTTP 요청의 쿼리 문자열(Query String)에서 "currentPage" 매개변수의 값을 받아옵니다. 
		예를 들어, URL에 "?currentPage=2"와 같이 쿼리 문자열이 포함되어 있는 경우, currentPage 매개변수에는 "2"의 값이 전달됩니다. 
		이렇게 받아온 현재 페이지의 값을 이용해 Paging 객체를 생성하고, 
		이 값을 EmpService.listEmp() 메서드에 전달하여 특정 페이지에 해당하는 직원 목록을 가져옵니다.
		*/
		
// 		Paging 작업
		Paging page = new Paging(totalEmp, currentPage);	// 얘는 왜 injection 안썻을까? totalEmp, currentPage 를 같이 인젝션 시켜야해서/ 얘는 단독서비스 해야해서 전통적인 방법을 사용한다
//		Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart());	// 시작시 1
		emp.setEnd(page.getEnd());		// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size() -> " + listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);
		
		
		return "list";
	}
	
	@GetMapping(value = "detailEmp")
	public String detailEmp(int empno, Model model) {
		System.out.println("EmpController Start detailEmp...");
		System.out.println("EmpController detailEmp empno -> " + empno);
		
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//
//		2. EmpDao   detailEmp method 선언  --> EmpServiceImpl 에서
////		                    mapper ID   ,    Parameter --> EmpDaoImpl 에서
//		emp = session.selectOne("tkEmpSelOne",    empno);
		
//		1.
		Emp emp = es.detailEmp(empno);
		model.addAttribute("emp", emp);
		
		
		return "detailEmp";

		
//		controller -> service -> dao -> mapper(emp.xml)
		
	}
	
	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(int empno, Model model) {
		System.out.println("EmpController Start updateFormEmp...");
	
		Emp emp = es.detailEmp(empno);
		System.out.println("EmpController updateFormEmp emp -> " + emp);
// 		문제 
// 		1. DTO  String hiredate
// 		2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
// 		3.해결책  : 년월일만 짤라 넣어 주어야 함
		String hiredate = "";
		if(emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);	// 0~10BYTE
			emp.setHiredate(hiredate);
		}
		
		model.addAttribute("emp", emp);	//  Controller에서 View로 데이터를 전달하는 방법 
		return "updateFormEmp";
	}
	
	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp Start...");
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//	    2. EmpDao updateEmp method 선언
//         mapper ID   ,    Parameter
//  	updateCount = session.update("tkEmpUpdate",emp);
		
		int updateCount = es.updateEmp(emp);
		System.out.println("EmpController updateEmp updateCount -> " + updateCount);
		
		model.addAttribute("upCnt", updateCount);	// Test Controller간 Date 전달 / 첫 번째 인자는 전달할 데이터의 이름(변수명)이고, 두 번째 인자는 전달할 데이터의 값(변수값)입니다.
		model.addAttribute("kk3", "Message Test");	// Test Controller간 Date 전달
//		이동View 단에 model 전달X
//		return "redirect:listEmp";
//		이동View 단에 model 전달O
		return "forward:listEmp";	// 상대방 View에 model을 이동,전달시키고 싶을때 / redirect로 한다면 listEmp로 전달되어도 그곳의 return list에 값이 전달되지 않는다
	}
	
	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("EmpController writeFormEmp Start...");
//		관리자 사번만 Get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeFormEmp Start...");
		model.addAttribute("empMngList", empList);	// emp Manager List

//		부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList); // dept
		System.out.println("EmpController writeFormEmp deptList.size() -> " + deptList.size());
		
		// 여기 value = "writeFormEmp"와 "writeFormEmp"가 같은 이유는 SELECT로 사번과 코드를 그대로 해당 페이지에 쏴줘야하기 때문
		return "writeFormEmp"; // view로 return 
	}
	
//	Valid 용도
	@RequestMapping(value = "writeFormEmp3")
	public String writeFormEmp3(Model model) {
		System.out.println("EmpController writeFormEmp3 Start...");
//		관리자 사번만 Get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeFormEmp3 Start...");
		model.addAttribute("empMngList", empList);	// emp Manager List

//		부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList); // dept
		System.out.println("EmpController writeFormEmp3 deptList.size() -> " + deptList.size());
		
		// 여기 value = "writeFormEmp"와 "writeFormEmp"가 같은 이유는 SELECT로 사번과 코드를 그대로 해당 페이지에 쏴줘야하기 때문
		return "writeFormEmp3"; // view로 return 
	}
	
	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp, Model model) {
		System.out.println("EmpController writeEmp Start...");
		
//		Service Dao, Mapper명[insertEmp] 까지 -> insert
		int insertResult = es.insertEmp(emp);
		if(insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp";
		}
	}
//	Validation시 참조하세요
	@PostMapping(value = "writeEmp3")
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp, BindingResult result
							, Model model) {
		System.out.println("EmpController writeEmp3 Start...");
		
//		Validation 오류시 Result
		if(result.hasErrors()) {
			System.out.println("EmpController writeEmp3 hasErrors...");
			model.addAttribute("msg", "BindingResult 입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		
//		Service Dao, Mapper명[insertEmp] 까지 -> insert
		int insertResult = es.insertEmp(emp);
		if(insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패 확인해 보세요");
			return "forward:writeFormEmp3";
		}
		
	}
	
//	ajax를 사용하지 않고 검증할 떄, ajax사용을 권장
	@GetMapping(value = "confirm")
	public String confirm(int empno, Model model) {
		Emp emp = es.detailEmp(empno);
		model.addAttribute("empno", empno);
		if(emp != null) {
			System.out.println("EmpController confirm 중복된 사번...");
			model.addAttribute("msg", "중복된 사번입니다");
			return "forward:writeFormEmp";
		} else {
			System.out.println("EmpController confirm 사용 가능한 사번...");
			model.addAttribute("msg", "사용 가능한 사번입니다");
			return "forward:writeFormEmp";
		}
	}
	
//	삭제
	@RequestMapping(value = "deleteEmp")
	public String deleteEmp(int empno, Model model) {
		System.out.println("EmpController delete Start...");
		int result = es.deleteEmp(empno);
		return "redirect:listEmp";
// 		Controller -->  deleteEmp    1.parameter : empno
//      name -> Service, dao , mapper
		
	}
	
	@RequestMapping(value = "listSearch3")
	public String listSearch3(Emp emp, String currentPage, Model model) {
		System.out.println("EmpController listSearch3 listEmp Start...");
//		Emp 전체 Count 25 (변동가능)
		int totalEmp = es.conditionEmpCount(emp);  // 검색했을때 결과에 몇개 안나왓는데 전체페이지 나오는걸 안나오게 하는거 -> totalEmp있던거하면 전체페이지가 나옴 / int totalEmp는 엉킬까봐 일부러 안바꿈
		System.out.println("EmpController listSearch3 totalEmp -> " + totalEmp);
//		Paging 작업
		Paging page = new Paging(totalEmp, currentPage);
//		Parameter emp -> Page만 추가 Setting
		emp.setStart(page.getStart());	// 시작시 1
		emp.setEnd(page.getEnd());		// 시작시 10
		
		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		System.out.println("EmpController listSearch3 listSearchEmp.size() -> " + listSearchEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listSearchEmp);
		model.addAttribute("page", page);
		
		return "list";
	}
	
}
