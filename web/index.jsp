<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页-代码生成器</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/baseUtil.js"></script>

<script type="text/javascript" src="js/index.js"></script>
<link type="text/css" rel="stylesheet" href="css/index.css">
</head>

<body>
	<form action="" id="form" class="editForm">
		<h2>数据库连接信息</h2>
		<ul class="generatorInfo">
			<li><span>数据库类型:</span><select name="driverType" id="driverType">
					<option value='mysql'>mysql</option>
					<option value='sqlserver'>sql server</option>
					<option value='oracle'>oracle</option>
			</select></li>
			<li><span>ip地址:</span><input type="text" name="ip" value=""></li>
			<li><span>端口号:</span><input type="text" name="port" value=""></li>
			<li><span>数据库名称:</span><input type="text" name="databaseName"
				value=""></li>
			<li><span>用户名:</span><input type="text" name="username" value=""></li>
			<li><span>密码:</span><input type="text" name="password" value=""></li>
			<li class='btnGroup'><a class="btn" onclick="login()">登录</a><a
				class="btn" onclick="resetData()">重置</a></li>
		</ul>
	</form>
</body>
</html>
