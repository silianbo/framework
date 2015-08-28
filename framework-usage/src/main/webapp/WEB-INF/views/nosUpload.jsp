<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
</head>

<body>
	<div>
		<h2>NOS图片上传示例</h2>
		<!-- 上传图片的话，若图片要展示，ContentType可以指定为image -->
		<form method="post" action="http://${hostName}/${bucket}"
			enctype="multipart/form-data" accept-charset="utf-8">
			<input name="x-nos-token" type="hidden" value="${token}"> <input
				name="Object" type="hidden" value="${object}"> <input
				name="file" type="file" /> <input type="submit" />
		</form>
	</div>

	<div>
		<h2>NOS文件上传示例</h2>
		<form method="post" action="http://${hostName}/${bucket}"
			enctype="multipart/form-data" accept-charset="utf-8">
			<input name="x-nos-token" type="hidden" value="${token}"> <input
				name="Object" type="hidden" value="${object}"> <input
				name="file" type="file" /> <input type="submit" />

			<!-- 如果文件上传后要直接在浏览器展示的话，可以指定ContentType为text/plain -->
			<input name="Content-Type" type="hidden" value="text/plain">
		</form>
	</div>


	<div>
		<h2>NOS私有桶图片上传示例</h2>
		<div>http://${hostName}/${bucket}/${object}?${reqUrl}</div>
		<!-- 上传图片的话，若图片要展示，ContentType可以指定为image -->
		<form method="post" action="http://${hostName}/${bucket}"
			enctype="multipart/form-data" accept-charset="utf-8">
			<input name="x-nos-token" type="hidden" value="${token}"> <input
				name="Object" type="hidden" value="${object}"> <input
				name="Content-Type" type="hidden" value="image/png"> <input
				name="file" type="file" /> <input type="submit" />
		</form>
	</div>

	
	<script type="text/javascript">
		function onSubmits() {
			alert("abc");
			iframe_old = document.getElementById("hidden_frame");
			setTimeout(function() {
				iframe = document.getElementById("hidden_frame");
				doc = iframe.contentWindow
				doc.document.writeln('<span>It is write by parent document</span>');
				doc.document.close();
			}, 10000);
		}
	</script>
	
	<div>
		<h2>NOS无刷新上传图片的测试</h2>
		<div>http://${hostName}/${bucket}/${object}?${reqUrl}</div>
		<!-- 这里提交到myself.com其实是本地，为了模拟跨域的行为，因为之前NOS还没实现ReturnUrl，所以只能自己模拟1个重定向的url来测试这个解决方案 -->
		<form id="uploadForm" method="post" action="http://myself.com:8080/ihome-framework-usage/nos/testNosUpload" enctype="multipart/form-data"
			accept-charset="utf-8" target="hidden_frame" onsubmit="onSubmits()">
			<input name="x-nos-token" type="hidden" value="${token}"> 
			<input name="Object" type="hidden" value="${object}"> 
			<input name="Content-Type" type="hidden" value="image/png"> 
			<input name="file" type="file" /> 
			<input type="button" id="btn" value="按我">
			<input type="submit">
			<iframe name='hidden_frame' id="hidden_frame" style='display: none' />
		</form>
	</div>

</body>
</html>
