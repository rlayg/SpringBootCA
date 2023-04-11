<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
 
    UpLoad Image : <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${savedName}">	<%-- ${pageContext.request.contextPath} 처음, 루트로 간다 --%>
	
     <form id="form1" action="uploadForm" method="post" enctype="multipart/form-data" >	 <!-- 비디오 이미지는 무조건 얘 약속이야 method="post" enctype="multipart/form-data -->
		<input type="file" name="file1"> <p>
		<input type="text" name="title"> <p>
		<input type="submit">
	</form>
	<a href="uploadFileDelete">upLoad삭제Test</a>





</body>
</html>

