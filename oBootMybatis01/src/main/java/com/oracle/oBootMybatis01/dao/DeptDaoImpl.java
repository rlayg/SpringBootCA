package com.oracle.oBootMybatis01.dao;

import java.sql.CallableStatement;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {

//	Mybatis DB 연동
	private final SqlSession session;
	
	/*
	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("DeptDaoImpl deptSelect() Start...");
		deptList = session.selectList("tkSelectDept");
		System.out.println("DeptDaoImpl deptSelect() deptList.size() -> " + deptList.size());
		return deptList;				
	}
	*/
	
	@Override
	public List<Dept> deptSelect() {
		return session.selectList("tkSelectDept");				
	}

	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpl insertDept Start...");
		session.selectOne("procDeptInsert", deptVO);	// deptVO는 callByRef
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImpl selListDept() Start...");
		session.selectOne("procDeptList", map);
	}
//	
}
