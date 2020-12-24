<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<h1>회원정보수정</h1>
<form action = "user?gubun=updateProc" method = "post">
	<table border=1>
		<tr>
			<th>번호</th>
			<th>유저네임</th>
			<th>패스워드</th>
			<th>이메일</th>
		</tr>
		<tr>
			<th>
				<input type="hidden" name="id" value= "${user.id}" />
				${user.id}
			</th>
			<th>${user.username}</th>
			<th>
				<input type="password" name="password" value="${user.password}" />
			</th>
			<th>
				<input type="email" name="email" value="${user.email}" />
			</th>
		</tr>
	</table>
	<button>회원수정</button>
</form>
</body>
</html>