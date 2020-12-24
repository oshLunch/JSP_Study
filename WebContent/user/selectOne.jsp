<%@page import="com.cos.hello.medel.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../layout/header.jsp"%>

<h1>회원정보보기</h1>
<table border = 1>
	<tr>
		<th>번호</th>
		<th>유저네임</th>
		<th>패스워드</th>
		<th>이메일</th>
	</tr>
	<tr>
		<th>${user.id}</th>
		<th>${user.username}</th>
		<th>${user.password}</th>
		<th>${user.email}</th>
	</tr>
</table>
<form action = "user?gubun=deleteProc" method = "post">
	<input type = "hidden" name = "id" value = "${user.id}" />
	<button>삭제</button>
</form>
</body>
</html>