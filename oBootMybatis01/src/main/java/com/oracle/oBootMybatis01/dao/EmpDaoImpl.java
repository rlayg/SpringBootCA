package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {

//	Mybatis DB 연동
	private final SqlSession session;	//	mabatis 연결시키는거
	
	@Override
	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start total...");
		
		try {
			totEmpCount = session.selectOne("empTotal");	// selectOne 한개의 Row라는 뜻
			System.out.println("EmpDaoImpl empTotal totEmpCount -> " + totEmpCount);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp Exception -> " + e.getMessage()); // 익셉션 반드시 걸어주기
		}
		return totEmpCount;	// service에 total 넘어갈거야
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp Start...");
		try {
//										  Map Id  		 parameter
			empList = session.selectList("tkEmpListAll", emp);	// session이 Mybatis DB를 연결하기위한 거  / 여러개의 Row를 가져올때는 selectList 위의 selectOne 은 한개의 Row / <Emp> 안에 "tkEmpListAll"를 넣는다 / start와 end 
			System.out.println("EmpDaoImpl listEmp empList.size() -> " + empList.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp e.getMessage() -> " + e.getMessage());
		}
		return empList;
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpDaoImpl detailEmp Start...");
		Emp emp = new Emp();
//	    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
		try {
//									mapper Id	,		Parameter
			emp = session.selectOne("tkEmpSelOne",    empno);
			System.out.println("EmpDaoImpl detailEmp getEname -> " + emp.getEname());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl detailEmp Exception -> " + e.getMessage());
		}
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl updateEmp update...");
		int updateCount = 0;
		try {
			updateCount = session.update("tkEmpUpdate",emp);
			System.out.println("EmpDaoImpl updateEmp updateCount() -> " + updateCount);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl updateEmp Exception -> " + e.getMessage());
		}
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listManager() Start...");
		try {
//			emp 관리자만 Select					Naming Rule
			empList = session.selectList("tkSelectManager");
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listManager() Exception -> " + e.getMessage());
		}
		return empList;
	}

	@Override
	public int insertEmp(Emp emp) {
		int result = 0;
		System.out.println("EmpDaoImpl insertEmp Start ...");
		try {
			result = session.insert("InsertEmp", emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl insertEmp() Exception -> " + e.getMessage());
		}
		return result;
	
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		System.out.println("EmpDaoImpl deleteEmp() Start... ");
		try {
			result = session.delete("DeleteEmp", empno);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl deleteEmp() Exception -> " + e.getMessage());
		}
		return result;
	}

	@Override
	public List<Emp> empSearchList3(Emp emp) {
		List<Emp> empSearchList3 = null;
		System.out.println("EmpDaoImpl empSearchList3 Start...");
		try {
//			keyword 검색
//			Naming Rule								Map ID			parameter
			empSearchList3 = session.selectList("tkEmpSearchList3", emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl empSearchList3 Exception -> " + e.getMessage());
		}
		return empSearchList3;
	}

	@Override
	public int condEmpCnt(Emp emp) {
		int conditionEmpCount = 0;
		System.out.println("EmpDaoImpl Start total...");
		
		try {
			conditionEmpCount = session.selectOne("condEmpCnt", emp);	// selectOne 한개의 Row라는 뜻
			System.out.println("EmpDaoImpl empTotal conditionEmpCount -> " + conditionEmpCount);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp Exception -> " + e.getMessage()); // 익셉션 반드시 걸어주기
		}
		return conditionEmpCount;	// service에 total 넘어갈거야
	}	
	// session에서 mapper Emp.xml로 가자
}
