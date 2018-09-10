<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="WebMVC/helloWorld">HelloWorld</a>
	<a href="WebMVC/testRedirect">testRedirect</a>
	<a href="WebMVC/testRequestParam">testRequestParam</a>
	<a href="WebMVC/testRequestHeader">testRequestHeader</a>
	<a href="WebMVC/testCookieValue">testCookieValue</a>
	<a href="WebMVC/testServletAPI">testServletAPI</a>
	<a href="WebMVC/testModelAndView">testModelAndView</a>
	<a href="WebMVC/testMap">testMap</a>
	<a href="WebMVC/testSessionAttribute">testSessionAttribute</a>
	<a href="WebMVC/testPathVariable/101">testPathVariable</a>
	<a href="WebMVC/testMyView">testMyView</a>
	<a href="WebMVC/testAntRequestMapping/helloWorld">testAntRequestMapping</a>
	<a href="WebMVC/testParamsAndHeaders?username=beater&password">testParamsAndHeaders</a>
	<form action="WebMVC/testMethod" method="post">
		<input type="submit" value="submit">
	</form>
	<br><br>
	<a href="WebMVC/testREST/101">testRESTGet</a>
	<form action="WebMVC/testREST" method="post">
		<input type="submit" value="testRESTPost">
	</form>
	<form action="WebMVC/testREST/101" method="post">
		<input type="hidden" name="_method" value="DELETE">
		<input type="submit" value="testRESTDelete">
	</form>
	<form action="WebMVC/testREST/101" method="post">
		<input type="hidden" name="_method" value="PUT">
		<input type="submit" value="testRESTPut">
	</form>
	<br><br>
	<form action="WebMVC/testPoJo" method="post"><br>
		userName:<input type="text" name="userName"><br>
		password:<input type="password" name="password"><br>
		age:<input type="text" name="age"><br>
		province:<input type="text" name="address.province"><br>
		city:<input type="text" name="address.city"><br>
		<input type="submit" value="testPoJo"><br>
	</form>
	<br><br>
	<form action="WebMVC/testModelAttribute" method="post"><br>
		userName:<input type="text" name="userName"><br>
		age:<input type="text" name="age"><br>
		<input type="submit" value="testModelAttribute"><br>
	</form>	
</body>
</html>