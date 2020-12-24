package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import config.DBConn;
import medel.Users;

public class UsersDao {

	public int 회원가입(Users user) {
		// 2. DB에 연결해서 3가지 값을 INSERT 하기
		StringBuffer sb = new StringBuffer();	// String 전용 컬렉션 (장점 : 동기화)
		sb.append("INSERT INTO Users(username, password, email) ");
		sb.append("VALUES(?,?,?)" );
		String sql = sb.toString();
		
//		String sql = "INSERT INTO users(username, password, email)";
//		sql += "VALUES(?,?,?)";	// 자바 String에서는 +를 적는게 좋지 않다 (긴 문장에는 sb를 쓸 것)
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate();	// 변경된 행의 갯수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
