<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>삭제파일 : ${deleteFile}</p>
	<p>삭제상태 : 
		<c:if test="${delResult == 1}">파일삭제 성공</c:if><p>
	    <c:if test="${delResult == 0}">파일삭제 실패</c:if><p> 
	    <c:if test="${delResult == -1}">파일이 존재하지 않습니다</c:if><p>
	
	
	<!-- 실제프로젝트에서는 jstl로 1이면 삭제되었습니다 0이면 실패 -1이면 파일없습니다 나오게 출력 -->
</body>
</html>