package com.oracle.oBootDBConnect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;

// 여기다가 @Repository 하면 oracle에 연결돼. MemoryMemberRepository 여기있는거 지우고 여기 걸어야함. 2개걸면 에러남
//@Repository
public class JdbcMemberRepository implements MemberRepository {
//	JDBC 사용
	private final DataSource dataSource;	//final이라 생성자에 얘가 들어가야한다
	
	@Autowired	//단일일때는 생략해 되지만 FM으로 하래
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
//	oracle과 연결 근데 여기서만 쓸거라 private
	private Connection getConnection() {	// 프레임워크에서 내부적으로 여기서 한데 그래서 릴리스 따로 한거
		return DataSourceUtils.getConnection(dataSource);	// spring에서는 DataSourceUtils를 쓰는게 권장이래
	}	//	DataSourceUtils 했으면 닫을때, 릴리즈할때 DataSourceUtils로 하는걸 권장
	
	@Override
	public Member1 save(Member1 member1) {
		String sql = "insert into member1(id,name) values(member_seq.nextval,?)";
		System.out.println("JdbcMemberRepository Member1 save sql -> " + sql);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member1.getName());
			pstmt.executeUpdate();
			System.out.println("JdbcMemberRepository psmt.executeUpdate After");
			return member1;
			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			System.out.println("close rs.close e.getMessage ->" + e.getMessage());
		}
		
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			System.out.println("close pstmt.close e.getMessage ->" + e.getMessage());
		}
		try {
			if(conn != null) {
				close(conn);
//				conn.close();
			}
		} catch (Exception e) {
			System.out.println("close rs.close e.getMessage ->" + e.getMessage());
		}
		
	}
	
//	DataSourceUtils는 이렇게 밖에 빼서 닫아줘야해 / DataSourceUtils 했으면 닫을때, 릴리즈할때 DataSourceUtils로 하는걸 권장
	private void close(Connection conn) {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

	
	@Override
	public List<Member1> findAll() {
		String sql = "select * from member1";
		System.out.println("JdbcMemberRepository List<Member1> sql -> " + sql);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
//			System.out.println("JdbcMemberRepository psmt.executeUpdate After");
			List<Member1> members = new ArrayList<Member1>();
			while (rs.next()) {
				Member1 member = new Member1();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

}
