<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<h2>login page</h2>
	<form action="/token/doLogin" method="post">
		<input type="hidden" name="_ihome_form_token" value="${_ihome_form_token }">
		<input type="text" name="name" value="">
		<input type="text" name="password">
		<input type="submit" value="login">
	</form>
</div>
</body>
</html>