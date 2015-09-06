<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>

	<div>
		<h2>login page</h2>
		<form action="${base}/token/doLogin" method="post">
			<input type="hidden" name="_ihome_form_token" value="${_ihome_form_token}">
			<input type="text" name="name" value="">
			<input type="text" name="password">
			<input type="submit" value="login">
		</form>
	</div>
</body>
</html>
