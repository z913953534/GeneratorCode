var generatorCode = {
	dbEntity : null,
	generatorParams : {
		projectName : "Pokemon",
		projectUrl : "D:/GeneratorCode/",
		packageEntity : "org.zsc.pokemon.entity",
		packageAction : "org.zsc.pokemon.action",
		packageService : "org.zsc.pokemon.service",
		packageDao : "org.zsc.pokemon.dao",
		packageSql : "org.zsc.pokemon.sql",
		packageUtil : "org.zsc.base.util",
		tableNameType : "3",
		columnNameType : "1",
		encoding:"UTF-8",
	},
	generatorParams2 : {
		projectName : "zxkt",
		projectUrl : "D:/GeneratorCode/",
		packageEntity : "org.orclwdp.foundation.model",
		packageAction : "org.orclwdp.foundation.action",
		packageService : "org.orclwdp.foundation.service",	
		packageSql : "org.orclwdp.foundation.sql",
		packageDao : "org.orclwdp.base",
		packageUtil : "org.orclwdp.base",
		tableNameType : "3",
		columnNameType : "1",
		encoding:"UTF-8",
	},
	generatorParams : {
		projectName : "Guard",
		projectUrl : "D:/GeneratorCode/",
		packageEntity : "org.orclwdp.guard.model",
		packageAction : "org.orclwdp.guard.controller",
		packageService : "org.orclwdp.guard.service",
		packageCommon : "org.orclwdp.common",
		packageDao : "org.orclwdp.base.dao",
		packageSql : "org.orclwdp.base.sql",
		packageUtil : "org.orclwdp.base",
		tableNameType : "1",
		columnNameType : "1",
		encoding:"UTF-8",
	},generatorParams : {
		projectName : "hhsales",
		projectUrl : "D:/GeneratorCode/",
		packageEntity : "org.zsc.po",
		packageAction : "org.zsc.controller",
		packageService : "org.zsc.service",
		packageCommon : "org.zsc.common",
		packageDao : "org.zsc.base.dao",
		packageSql : "org.zsc.base.sql",
		packageUtil : "org.zsc.base",
		tableNameType : "3",
		columnNameType : "2",
		encoding:"UTF-8",
	},
}
$(function() {
	$("#selectedAll").click(
			function() {
				$(this).parent("th").parent("tr").parent("thead").siblings(
						"tbody").find("input[type='checkbox']").prop("checked",
						$(this).prop("checked"));
			});
	var dbEntity = $.cookie("dbEntity");// 获取cookie中的登录的用户信息
	if (dbEntity && dbEntity != "null" && dbEntity != null && dbEntity != "") {
		generatorCode.dbEntity = JSON.parse(dbEntity);// 将json字符串转json对象
		$.each(generatorCode.generatorParams, function(i, v) {
			$("#generatorInfo :input[name='" + i + "']").val(v);
		});

		var actionName = "LoginServlet/login";
		baseUtil
				.baseXHR(actionName, generatorCode.dbEntity, "")
				.done(
						function(data) {
							if (data.baseModel.resultFlag > 0) {
								alert(data.baseModel.message);
								window.location.href = "index.jsp";
							} else {
								$("#tableInfo tbody").empty();
								var list = data.baseModel.data.split(",");
								for (var i = 0; i < list.length; i++) {
									var tr = $("<tr></tr>");
									tr
											.append("<td><input type='checkbox' name='tableName' value='"
													+ list[i] + "'></td>");
									tr.append("<td>" + list[i] + "</td>");
									$("#tableInfo tbody").append(tr);
								}

							}
						});
	} else {
		window.location.href = "index.jsp";
	}
});
function submitData(genType) {
	var actionName = "GeneratorServlet/generatorCode";
	var formData = $("#tableInfo tbody input,#generatorInfo :input")
			.serialize();
	var dbParams = "";
	$.each(generatorCode.dbEntity, function(i, v) {
		dbParams += "&dbEntity." + i + "=" + v;
	});
	formData += dbParams + "&genType=" + genType;
	baseUtil.baseXHR(actionName, formData, "").done(function(data) {

		alert(data.baseModel.message);

	});
}
function resetData() {
	$("#generatorInfo input").val("");
	$("#generatorInfo select").val("0");
	$("#tableInfo input[name='checked']").prop("checked", false);
}