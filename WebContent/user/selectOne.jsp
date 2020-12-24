<%@page import="medel.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../layout/header.jsp"%>

<h1>User info</h1>
<%
//	String result = (String)session.getAttribute("result");
	String result = (String)request.getAttribute("result");
%>
<%=result %>

<h1>${sessionScope.result}</h1>
<h1>${result}</h1>
</body>
</html>