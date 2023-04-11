<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>다양한 Ajax Test</h1>
    <a href="/helloText">helloText </a><p>
    <a href="/sample/sendVO2?deptno=123">sample/sendVO2 </a><p>
    <a href="/sendVO3">sendVO3 </a><p>
    <a href="/getDeptName?deptno=50">getDeptName(controller) </a><p>	<!-- deptno=50 50번이 없으면 Null이 나옴 있는 번호로 하기 -->
    <a href="/listEmpAjaxForm">listEmpAjaxForm(aJax JSP 연동) </a><p>
    <a href="/listEmpAjaxForm2">listEmpAjaxForm2(aJax JSP 객체리스트 Get) </a><p>
</body>
</html>