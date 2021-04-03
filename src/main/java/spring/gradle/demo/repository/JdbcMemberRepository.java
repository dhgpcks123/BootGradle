package spring.gradle.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import spring.gradle.demo.domain.Member;

public class JdbcMemberRepository implements MemberRepository {

	private final DataSource dataSource;
	
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Member save(Member member) {
		String sql = "insert into member(name) values(?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//여기에 try catch resource방식 쓸 수 있는거지.
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,  member.getName());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				member.setId(rs.getLong(1));
			}else {
				throw new SQLException("id 조회 실패");
			}
			return member;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}finally {
			close(conn, pstmt, rs);
		}
	}


	@Override
	public Optional<Member> findById(Long id) {
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			}
		} catch (Exception e) {
			close(conn, pstmt, rs);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Member> findByName(String name) {
		String sql = "select * from member where name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			}
		} catch (Exception e) {
			close(conn, pstmt, rs);
		}
		return Optional.empty();
	}

	@Override
	public List<Member> findAll() {
		String sql = "select * from member";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Member> members = new ArrayList<>();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(conn, pstmt, rs);
		}
		return null;
	}
	
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
