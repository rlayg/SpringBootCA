package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;
import com.oracle.oBootMybatis01.service.SampleInterCeptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmpController {
	
	@Autowired  // 이거 안함 선생님은 성태씨도
	private final EmpService es;
	private final JavaMailSender mailSender; // 메일 보내주는애
	
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
		if(result.hasErrors()) {	// BindingResult를 걸어서 hasErrors를 가져올 수 있어 
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
	
//	검색시 전체페이지 X 나온 글 개수만큼 페이지 출력
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
	
	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		System.out.println("EmpController listEmpDept Start...");
// 		Service , DAO(ed) -> listEmpDept
// 		Mapper만 ->tkListEmpDept
		List<EmpDept> listEmpDept = es.listEmpDept();
		model.addAttribute("listEmpDept", listEmpDept);
		return "listEmpDept";
		
	}
	
//	보안인증때문에 확인 불가 코드는 문제없데
	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("EmpController mailTransport Start...");
		String tomail = "rla@naver.com";	// 받는 사람 메일
		System.out.println(tomail);
		String setfrom = "rla2@gmail.com";
		String title = "mailTransprot 입니다";	// 제목
		
		try {
//			Mime 전자우편 Internet 표준 Format - Mime = 다목적인터넷메일익스텐션
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);		// 보낸 사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail);		// 받는사람 이메일
			messageHelper.setSubject(title);	// 메일제목은 생략이 가능하다
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";	
			messageHelper.setText("임시 비밀번호입니다" + tempPassword);	// 메일 내용
			System.out.println("임시 비밀번호입니다" + tempPassword);
			DataSource dataSource = new FileDataSource("c:\\log\\hwa.png");  // DataSource 첨부문서를 보낼 수 있다고 보여주는거
			messageHelper.addAttachment(MimeUtility.encodeText("hwa.png", "UTF-8", "B"), dataSource);
			
			mailSender.send(message);
			model.addAttribute("check", 1);		// 정상 전달
//			DB tempPassword Logic 구성
		} catch (Exception e) {
			System.out.println("메일 전달 실패 EmpController mailTransport e.getMessage() -> " + e.getMessage());
			model.addAttribute("check", 2);		// 메일 전달 실패
		}
		
		return "mailResult";
	}
	
//	Procedure Test 입력화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("EmpController writeDeptIn() Start.... ");
		return "writeDept3";
	}
	
//	Procedure 통한 Dept 입력후 VO 전달
	@PostMapping(value = "writeDept")
	public String writeDept(DeptVO deptVO, Model model) {
		es.insertDept(deptVO);
		if(deptVO == null) {
			System.out.println("deptVO NULL");
		} else {
			System.out.println("deptVO.getOdeptno() -> " + deptVO.getOdeptno());
			System.out.println("deptVO.getOdname() -> " + deptVO.getOdname());
			System.out.println("deptVO.getOloc() -> " + deptVO.getOloc());
			model.addAttribute("msg", "정상 입력 되었습니다");
			model.addAttribute("dept", deptVO);
		}
		return "writeDept3";
	}
	
//	Map 적용 - Parameter Map방식 2023-04-10 메모장 참고
	@GetMapping(value = "writeDeptCursor")
	public String writeDeptCursor(Model model) {
		System.out.println("EmpController writeDeptCursor Start..."); 
//		부서범위 조회
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("sDeptno", 10);
		map.put("eDeptno", 55);
		
		es.selListDept(map);
		List<Dept> deptLists = (List<Dept>) map.get("dept");
		for(Dept dept : deptLists) {
			System.out.println("dept.getDname -> " + dept.getDname());
			System.out.println("dept.getLoc -> " + dept.getLoc());
		}
		System.out.println("deptList Size -> " + deptLists.size());
		model.addAttribute("deptList", deptLists);
		
		return "writeDeptCursor";
		
	}
	
//	interCeptor 시작 화면
	@RequestMapping(value = "interCeptorForm")
	public String interCeptorForm(Model model) {
		System.out.println("EmpController interCeptorForm Start...");
		return "interCeptorForm";
	}
	
//	2.interCeptor Number 2
	@RequestMapping(value = "interCeptor")
	public String interCeptor(String id, Model model) {
		System.out.println("EmpController interCeptor Test Start...");
		System.out.println("EmpController interCeptor id -> " + id);
//		존재 : 1 , 비존재 : 0
		int memCnt = es.memCount(id);
		
		System.out.println("EmpController interCeptor memCnt -> " + memCnt);
		
		model.addAttribute("id", id);
		model.addAttribute("memCnt", memCnt);
		System.out.println("EmpController interCeptor Test End");
		
		return "interCeptor";	// User 존재하면 User 이용 조회 Page
		
	}
	
//	SampleInterCeptor 내용을 받아 처리
	@RequestMapping(value = "doMemberWrite", method = RequestMethod.GET)
	public String doMemberWrite(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite 부터 작성하세요");
		model.addAttribute("id", ID);
		return "doMemberWrite";
		
	}
	
//	interCeptor 진행 Test
	@RequestMapping(value = "doMemberList")
	public String doMemberList(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("EmpController doMemberList Test Start... Id -> " + ID);
		Member1 member1 = null;
//		Member1 List Get Service
		List<Member1> listMem = es.listMem(member1);
		model.addAttribute("ID", ID);
		model.addAttribute("listMem", listMem);
		return "doMemberList";
	}
	
//	=================== 이 밑부터 Ajax ===================
	
//	ajaxForm Test 입력화면
	@RequestMapping(value = "ajaxForm")
	public String ajaxForm(Model model) {
		System.out.println("ajaxForm Start...");
		return "ajaxForm";
	}
	
//	일반 Controller에 Ajax 하려고 해
	@ResponseBody	// @ResponseBody는 나 호출한 놈의 몸에 들어가는거
	@RequestMapping(value = "getDeptName")
	public String getDeptName(int deptno, Model model) {
		System.out.println("deptno -> " + deptno);
		String	deptName = es.deptName(deptno);
		System.out.println("deptName -> " + deptName);
		return	deptName;	// 나 호출한 놈의 몸에 들어가는거
		
	}
	
	
//	Ajax List Test
	@RequestMapping(value = "listEmpAjaxForm")
	public String listEmpAjaxForm(Model model) {
		Emp emp = new Emp();
		System.out.println("Ajax List Test Start...");
//		Parameter emp --> Page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(10);		// 시작시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjaxForm() listEmp.size() -> " + listEmp.size());
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm";
	}
	
	@RequestMapping(value = "listEmpAjaxForm2")
	public String listEmpAjaxForm2(Model model) {
		System.out.println("EmpController listEmpAjaxForm2 Start...");
		Emp emp = new Emp();
		System.out.println("Ajax List Test Start...");
//		Parameterer emp --> Page만 추가 Setting
		emp.setStart(1);	// 시작시 1
		emp.setEnd(15);		// 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm2";
	}
	
}
