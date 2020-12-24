package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.medel.Users;
import com.cos.hello.util.Script;

public class UsersService {

	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 username=ssar&password=1234&email=ssar@nate.com
		// getParameter 함수는 key값(name)의 데이터를 파싱하여 받아준다
		// getParameter 함수는 GET 방식의 데이터와 POST 방식의 데이터를 모두 받을 수 있다
		// 단, POST 방식에서는 데이터 타입이 x-www-from-urlencoded 방식만 받을 수 있음

		// 1. form의 input 태그에 있는 3가지 값 username, password, email 받기
		String username = req.getParameter("username"); // join의 name 데이터가 날아간다
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		Users user = Users.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();

		UsersDao usersDao = new UsersDao(); // get인스턴스 싱글턴 패턴으로 변경(userdao에서)
		int result = usersDao.insert(user);

		if (result == 1) { // 1이면 정상
			resp.sendRedirect("auth/login.jsp");
		} else {
			resp.sendRedirect("auth/join.jsp");
		}
	}

	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// SELECT id, username, email FROM users WHERE username = ? AND password = ?
		// DAO의 함수명 : login() Users 오브잭트를 리턴 (재사용이 안됨)
		// 정상 : 세션에 users 오브잭트 담고 index.jsp, 비정상 : login.jsp

		// 1. 전달되는 값 받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// 2. 데이터베이스에 값이 있는지 select 해서 확인
		Users user = Users.builder()
				.username(username)
				.password(password)
				.build();

		UsersDao usersDao = new UsersDao();
		Users usersEntity = usersDao.login(user);

		if (usersEntity != null) {
			// 3. 클라이언트에게 다음에도 들어올 수 있도록 인증을 위한 key 값을 준다.
			// key값은 header에 저장된다
			// 다음에 들어왔을 때 비교할 수 있도록 서버는 세션에, 클라이언트는 쿠키에 각각 key값을 보관한다
			// 쿠키 : 클라이언트 저장소, 세션 : 서버 저장소
			HttpSession session = req.getSession();
			// 세션(session)에는 사용자 패스워드를 절대 넣지 않는다
			session.setAttribute("sessionUser", usersEntity);
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.
			// 4. index.jsp 페이지로 이동
			// 한글 처리를 위해 resp 객체를 건드린다 (MIME 타입)
			// Http Header에 Content type
			Script.href(resp, "index.jsp", "로그인 성공");
		} else {
			Script.back(resp, "로그인 실패");
		}
	}

	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 세션 체크	
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		Users user = (Users)session.getAttribute("sessionUser");
		
		UsersDao usersDao = new UsersDao();
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}		
	}
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 세션 체크	
		// 인증이 필요한 페이지
		HttpSession session = req.getSession();
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}		
	}
	
	public void 정보수정(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1. 전달되는 값 받기
		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		Users user = Users.builder()
				.id(id)
				.password(password)
				.email(email)
				.build();

		UsersDao usersDao = new UsersDao(); // get인스턴스 싱글턴 패턴으로 변경(userdao에서)
		int result = usersDao.update(user);

		if (result == 1) { // 1이면 정상
			resp.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동
			resp.sendRedirect("user?gubun=updateOne");
		}
	}
	
	public void 회원삭제(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1. 전달되는 값 받기
		int id = Integer.parseInt(req.getParameter("id"));

		UsersDao usersDao = new UsersDao(); // get인스턴스 싱글턴 패턴으로 변경(userdao에서)
		int result = usersDao.delete(id);

		if (result == 1) { // 1이면 정상
			HttpSession session = req.getSession();
			session.invalidate();
			// 세선을 무효화시키는 코드
			resp.sendRedirect("index.jsp");
		} else {
			// 이전 페이지로 이동
			resp.sendRedirect("user?gubun=selectOne");
		}
	}
}
