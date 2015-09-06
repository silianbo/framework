
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
	<h2>Hello World!</h2>

	<div>
		<h4>评论列表</h4>
		<br />
		<table id="comments">
			<#list commentList as comment>
				<tr>
					<!-- 输出到HTML标签中的，如果没有任何过滤机制的话，1个<script>alert('xss')</script>即可注入;需要做Html编码 -->
					<td>${comment?html}</td>
					<td>&nbsp;&nbsp;</td>
					<td>
						<!-- 输出到JavaScript中的，也需要对这些变量进行转义，否则把单引号闭合一下即可完成注入，
								譬如: xss1');alert('xss2 ;需要做JavaScript转义 -->
						<a href="#" onclick="alert('${comment?js_string?html}')">点击回复</a>
					</td>
				</tr>		
			</#list>
		</table>
		
		<div>
			<h4>评论区(不做任何过滤)</h4>
			<br />
			<form action="${base}/xss/comment" method="post">
				<input type="text" name="content" value="输入"> 
				<input type="submit" value="提交">
			</form>
		</div>

		<div>
			<h4>评论区1(输入的时候做过滤)</h4>
			<br />
			<form action="${base}/xss/commentByInputFilter" method="post">
				<input type="text" name="content" value="输入"> 
				<input type="submit" value="提交">
			</form>
		</div>
				
		<div>
			<h4>评论区2(输出的时候做过滤)</h4>
			<br />
			<form action="${base}/xss/commentByOutputFilter" method="post">
				<input type="text" name="content" value="输入"> 
				<input type="submit" value="提交">
			</form>
		</div>
		
		<div>
			<h4>评论区3(纯Js操作)</h4>
			<br />
			<form action="#" method="post">
				<input id="jsContent" type="text" name="content" value="输入"> 
				<input type="button" value="提交" onclick="addComment()">
			</form>
		</div>
		
		<script>
			function addComment() {
				var value = document.getElementById('jsContent').value;
				var html = "<tr><td>" + value +"</td><td>&nbsp;&nbsp;</td><td>点击回复</td></tr>"
				var table = document.getElementById('comments');
				table.innerHTML = html;
			}
		</script>
	</div>	
</body>
</html>
