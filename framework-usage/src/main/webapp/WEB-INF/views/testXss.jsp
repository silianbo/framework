<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
	<h2>Hello World!</h2>

	<div>
		<h4>评论列表</h4>
		<br />
		<c:forEach items="${commentList}" var="comment">
			<tr>
				<!-- 输出到HTML标签中的，如果没有任何过滤机制的话，1个alert('xss')即可注入 -->
				<td>${comment}</td>&nbps;&nbps;
				
				<!-- 输出到JavaScript中的，也需要对这些变量进行转义，否则把单引号闭合一下即可完成注入，譬如: xss1');alert('xss2 -->				
				<a href="#" onclick="alert('${comment}')">点击回复</a>
			</tr>
		</c:forEach>
		
		<div>
			<h4>评论区</h4>
			<br />
			<form action="${base}/xss/comment" method="post">
				<input type="text" name="content" value="输入"> <input
					type="submit" value="提交">
			</form>
		</div>
	</div>	
	<!-- JavaScript的xss方式呢? -->
</body>
</html>
