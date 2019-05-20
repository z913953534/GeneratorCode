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

<title>生成选项-代码生成器</title>

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

<script type="text/javascript" src="js/generatorCode.js"></script>
<link type="text/css" rel="stylesheet" href="css/index.css">
</head>

<body>

	<form action="" id='form' class="editForm">
		<h2>代码自动生成器</h2>
		<ul id="generatorInfo" class="generatorInfo">
			<li><span>项目名称:</span><input type="text" name="projectName"></li>
			<li><span>项目保存路径:</span><input type="text" name="projectUrl"></li>
			<li><span>entity包路径:</span><input type="text"
				name="packageEntity"></li>
			<li><span>action包路径:</span><input type="text"
				name="packageAction"></li>
			<li><span>service包路径:</span><input type="text"
				name="packageService"></li>
			<li><span>dao包路径:</span><input type="text" name="packageDao"></li>
			<li><span>sql包路径:</span><input type="text" name="packageSql"></li>
			<li><span>util包路径:</span><input type="text" name="packageUtil"></li>
			<li><span>common包路径:</span><input type="text"
				name="packageCommon"></li>
			<li><span>编码:</span><select name="encoding"><option
						value="UTF-8">UTF-8</option>
					<option value="GBK">GBK</option></select></li>
			<li><span>实体类命名规则:</span><select name="tableNameType"><option
						value="0">实体类名与数据库一致,首字母大写</option>
					<option value="1">实体类名全小写,遇下划线转大写,首字母大写</option>
					<option value="2">截取前缀1，实体类名与数据库一致,首字母大写</option>
					<option value="3">截取前缀1，实体类名全小写,遇下划线转大写,首字母大写</option>
					<option value="4">截取前缀2，实体类名与数据库一致,首字母大写</option></select></li>
			<li><span>字段命名规则:</span><select name="columnNameType"><option
						value="0">字段名和数据库一致</option>
					<option value="1">字段名全小写，遇下划线转大写</option>
					<option value="2">字段名全小写</option></select></li>
			<li class="btnGroup"><a class="btn" onclick="submitData('SSH')">生成SSH</a><a
				class="btn" onclick="submitData('SSM')">生成SSM</a><a class="btn"
				onclick="submitData('SH')">生成SpringMVC+Hibernate</a>
				<a class="btn"
				onclick="submitData('HTML')">生成Html+js</a><a class="btn"
				onclick="resetData()">重置</a><a class="btn" href="index.jsp">退出</a></li>
			<li>
				<a class="btn" onclick="submitData('po')">生成PO</a>
				<a class="btn" onclick="submitData('controller')">生成Controller</a>
				<a class="btn" onclick="submitData('service')">生成Service</a>
				<a class="btn" onclick="submitData('mybatisMapper')">生成MybatisMapper</a>
				<a class="btn" onclick="submitData('mybatisXML')">生成MybatisXML</a>
				<a class="btn" onclick="submitData('html')">生成HTML</a>
				<a class="btn" onclick="submitData('js')">生成JS</a>
			</li>
		</ul>
		<table id="tableInfo" class="tableInfo">
			<thead>
				<tr>
					<th><input type="checkbox" id="selectedAll"></th>
					<th>表名</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>

	</form>
</body>
</html>
