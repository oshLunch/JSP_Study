<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>URL 방식 안됨! URI 방식으로 요청하기!</h1>
<ul>
	<li><a href = "<%=contextPath %>/user?gubun=login">로그인</a></li>
	<li><a href = "<%=contextPath %>/user?gubun=join">회원가입</a></li>
	<li><a href = "<%=contextPath %>/user?gubun=selectOne">유저정보 보기</a></li>
	<li><a href = "<%=contextPath %>/user?gubun=updateOne">유저 수정하기</a></li>
	<li><a href = "<%=contextPath %>/board?gubun=deleteOne">게시글 삭제하기</a></li>
	<li><a href = "<%=contextPath %>/board?gubun=insertOne">게시글 입력하기</a></li>
	<li><a href = "<%=contextPath %>/board?gubun=selectAll">게시글 전체보기</a></li>
	<li><a href = "<%=contextPath %>/board?gubun=updateOne">게시글 수정하기</a></li>
</ul>