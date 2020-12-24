package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰캣이 들고있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.DBConn;
import dao.UsersDao;
import medel.Users;

// 디스패쳐의 역할 = 분기 = 필요한 view를 응답해주는 것
public class UserController extends HttpServlet {

	// req와 res는 톰캣이 만들어준다 (클라이언트의 요청이 있을 때마다)
	// req는 BufferdReader 할 수 있는 ByteSystem
	// res는 BufferdWriter 할 수 있는 ByteSystem
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("UserContriller 실행됨");
		String gubun = req.getParameter("gubun"); // hello/front
		System.out.println(gubun);
		// http://localhost:8000/hello/user?gubun=login
		route(gubun, req, resp);
	}

	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// throws IOException : 함수 전체가 try/catch로 묶인다
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp"); // gubun=login
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp"); // gubun=join
		} else if (gubun.equals("selectOne")) {		// 유저정보 보기
			// 인증이 필요한 페이지
			String result;
			HttpSession session = req.getSession();
			if(session.getAttribute("sessionUser") != null) {	// 인증끝
				Users user = (Users)session.getAttribute("sessionUser");
				result = "인증되었습니다";
				System.out.println(user);
			} else {
				result = "인증되지 않았습니다";
			}
			// 세션에는 인증정보'만' 저장
			// request에 저장하는게 좋다
			req.setAttribute("result", result);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
			// dispatcher = 리퀘스트, 리스펀스가 유지됨
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp"); // gubun=updateOne
		} else if (gubun.equals("joinProc")) {
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
			
			UsersDao usersDao = new UsersDao();	//get인스턴스 싱글턴 패턴으로 변경(userdao에서)
			int result = usersDao.회원가입(user);
			
			if (result == 1) {	// 1이면 정상
				resp.sendRedirect("auth/login.jsp");
			} else {
				resp.sendRedirect("auth/join.jsp");
			}			
		} else if (gubun.equals("loginProc")) {
			// 1. 전달되는 값 받기
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			System.out.println("==================loginProc Start====================");
			System.out.println(username);
			System.out.println(password);
			System.out.println("===================loginProc End=====================");
			
			// 2. 데이터베이스에 값이 있는지 select 해서 확인
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
			
			// 3. 클라이언트에게 다음에도 들어올 수 있도록 인증을 위한 key 값을 준다.
			// key값은 header에 저장된다
			// 다음에 들어왔을 때 비교할 수 있도록 서버는 세션에, 클라이언트는 쿠키에 각각 key값을 보관한다
			// 쿠키 : 클라이언트 저장소, 세션 : 서버 저장소
			HttpSession session = req.getSession();
			// 세션(session)에는 사용자 패스워드를 절대 넣지 않는다
			session.setAttribute("sessionUser", user);
			// 모든 응답에는 jSessionId가 쿠키로 추가된다.
			
			// 4. index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");
		}
	}
}
