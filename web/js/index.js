var index = {
	dbMap : {
		
		"oracle" : {
			driverType : "oracle",
			ip : "172.16.3.234",
			port : "1521",
			databaseName : "orcl",
			username : "guard2017",
			password : "123456",
		},
		"sqlserver" : {
			driverType : "sqlserver",
			ip : "172.16.3.234",
			port : "1433",
			databaseName : "JWeSquare",
			username : "sa",
			password : "9798122",
		},
		"mysql" : {
			driverType : "mysql",
			ip : "localhost",
			port : "3306",
			databaseName : "hhsales",
			username : "myuser",
			password : "mypassword",
		}
	},
	dbEntity : {},
}
$(function() {
	$("#driverType").change(function() {
		index.dbEntity = index.dbMap[$(this).val()];
		$.each(index.dbEntity, function(i, v) {
			$("#form :input[name='" + i + "']").val(v);
		});
	}).change();

});

function login() {
	var actionName = "LoginServlet/login";
	var formData = $("#form :input").serialize();
	if (checkData) {
		$.each($("#form :input"), function(i, v) {
			index.dbEntity[$(this).attr("name")] = $(this).val();
		});
		$.cookie("dbEntity", JSON.stringify(index.dbEntity));// 将json对象转json字符串保存在cookie中
		window.location.href = "generatorCode.jsp";
	}
}
function resetData() {
	$("#form input").val("");
	$("#form selected option:first").attr("checked", "checked");
}
function checkData() {
	String
	str = "";
	$.each($("#form input"), function(i, v) {
		if ($(this).val() == "") {
			str += $(this).siblings("span").text() + "不能为空\n";
			$(this).focus();
		}
	});
	if (str.length > 0) {
		alert(str);
		return false;
	} else {
		return true;
	}
}