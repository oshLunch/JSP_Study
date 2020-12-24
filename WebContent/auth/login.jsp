<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file = "../layout/header.jsp"%>

<h1>Login Page</h1>
<form action = "/hello/user?gubun=loginProc" method = "post">
	<input type = "text" name = "username" placeholder = "username"/>
	<input type = "password" name = "password" placeholder = "password"/>
	<button>로그인</button>
</form>
</body>
</html>