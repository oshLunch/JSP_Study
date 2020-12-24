package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cos.hello.config.DBConn;
import com.cos.hello.medel.Users;

public class UsersDao {
	// 절대 if로 나누지 않는다 (하나의 함수에서는 하나의 일만 하도록 한다)

	public int insert(Users user) {
		// 2. DB에 연결해서 3가지 값을 INSERT 하기
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션 (장점 : 동기화)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();

//		String sql = "INSERT INTO users(username, password, email)";
//		sql += "VALUES(?,?,?)";	// 자바 String에서는 +를 적는게 좋지 않다 (긴 문장에는 sb를 쓸 것)

		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate(); // 변경된 행의 갯수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Users login(Users user) {
		String sql = "SELECT id, username, email FROM users WHERE username = ? AND password = ?";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Users usersEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				UsersDao usersDao = new UsersDao();
				usersDao.login(usersEntity);
				return usersEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Users selectById(int id) {
		// *를 쓰지 말고 모든 컬럼을 적는다
		String sql = "SELECT id, password, username, email FROM users WHERE id = ?";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Users usersEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.build();
				UsersDao usersDao = new UsersDao();
				usersDao.login(usersEntity);
				return usersEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(Users user) {
		String sql = "UPDATE users SET password = ?, email = ? WHERE id = ?";
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			int result = pstmt.executeUpdate(); // 변경된 행의 갯수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int delete(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate(); // 변경된 행의 갯수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
