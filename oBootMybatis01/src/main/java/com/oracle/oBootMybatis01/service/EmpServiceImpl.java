package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	
	private final EmpDao ed;
	private final DeptDao dd;
	
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl Start total...");
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp totEmpCnt -> " + totEmpCnt);
		return totEmpCnt;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager Start...");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size() -> " + empList.size());
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
//		2. EmpDao   detailEmp method 선언 
//	    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
		
		System.out.println("EmpServiceImpl detailEmp...");
		Emp emp = null;
		emp = ed.detailEmp(empno);
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
//      2) Return      updateCount (int)
//
//	    2. EmpDao updateEmp method 선언
//         mapper ID   ,    Parameter
//  	updateCount = session.update("tkEmpUpdate",emp);
		
		System.out.println("EmpServiceImpl updateEmp update ...");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		
		
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager() Start...");
		empList = ed.listManager();
		System.out.println("EmpServiceImpl listEmp empList.size() ->" + empList.size());
		return empList;
	}

	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("EmpServiceImpl deptSelect() Start...");
		deptList = dd.deptSelect();
		System.out.println("EmpServiceImpl deptSelect() deptList.size -> " + deptList.size());
		return deptList;
		
		
	
	}

	@Override
	public int insertEmp(Emp emp) {
		int result = 0;
		System.out.println("EmpServiceImpl insertEmp() Start...");
		result = ed.insertEmp(emp);
		System.out.println("EmpServiceImpl insertEmp() result -> " + result);
		
		return result;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		System.out.println("EmpServiceImpl deleteEmp() Start...");
		result = ed.deleteEmp(empno);
		System.out.println("EmpServiceImpl deleteEmp result -> " + result);
		
		return result;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> empSearchList = null;
		System.out.println("EmpServiceImpl listSearchEmp listEmp Start...");
		empSearchList = ed.empSearchList3(emp);
		System.out.println("EmpServiceImpl listSearchEmp empSearchList.size() -> " + empSearchList.size());
		
		return empSearchList;
	}

	@Override
	public int conditionEmpCount(Emp emp) {
		System.out.println("EmpServiceImpl conditionEmpCount Start...");
		int condEmpCnt = ed.condEmpCnt(emp);
		System.out.println("EmpServiceImpl conditionEmpCount condEmpCnt -> " + condEmpCnt);
		return condEmpCnt;
	}


}
