<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
Success!
<%
	if(request.getAttribute("time")!=null){
		out.println(request.getAttribute("time"));
	}
%>
<br>
${requestScope.name}
<br>
requestScopeUser:${requestScope.user1}
sessionScopeUser:${sessionScope.user}
<br>
<br>
requestScopeEmail:${requestScope.email}
sessionScopeEmail:${sessionScope.email}
<br><br>
<fmt:message key="i18n.username"></fmt:message>
<br><br>
<fmt:message key="i18n.password"></fmt:message>
</body>
</html>