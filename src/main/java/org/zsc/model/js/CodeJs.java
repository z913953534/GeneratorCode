package org.zsc.model.js;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GeneratorUtil;

public class CodeJs {
	public static String lt = "\t";
	public static String ln = "\r\n";
	private static String UNAME;
	private static String LNAME;
	

	public static void GenerateJS(GeneratorParams params) throws Exception {
		UNAME = params.getTableName();
		LNAME = UNAME.substring(0, 1).toLowerCase()
				+ UNAME.substring(1, UNAME.length());// 首字母转小写
		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/js";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + LNAME + ".js";
		StringBuffer sb = new StringBuffer();
		genParams(params, sb);
		genReadload(sb);
		genSetClickEvent(params, sb);
		genFindPageData(sb);
		genShowListData(params, sb);
		genSearchData(sb);
		genDeleteData(sb);
		genShowAddData(sb);
		genShowUpdateData(sb);
		genShowViewData(sb);
		genSubmitAddData(sb);
		genSubmitUpdateData(sb);
		genCheckData(sb);
		//genResetData(sb);
		//genShowImpDialog(sb);
		//genShowAdvancedSearchDialog(sb);
		//genExpData(sb);

		GeneratorUtil.writeStringToFile(new File(resPath), sb.toString(),
				params);
	}

	/**
	 * 
	 * @Description 生成全局参数
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genParams(GeneratorParams params,
			StringBuffer sb) {
		sb.append("var " + LNAME + "Params = {");
		//sb.append(ln + lt);
		//sb.append("listData : new Array(),");
		sb.append(ln + lt);
		sb.append("nodes:{");
		sb.append(ln + lt + lt);
		sb.append("taskList:\"#taskList\",//列表选项卡id");
		sb.append(ln + lt + lt);
		sb.append("taskView:\"#taskView\",//查看选项卡id");
		sb.append(ln + lt + lt);
		sb.append("taskUpdate:\"#taskUpdate\",//修改选项卡id");
		sb.append(ln + lt + lt);
		sb.append("taskAdd:\"#taskAdd\",//添加选项卡id");
		sb.append(ln + lt + lt);
		
		sb.append("formList:\"#formList\",//列表表单id");
		sb.append(ln + lt + lt);
		sb.append("printPage:\"#printPage\",//页码打印id");
		sb.append(ln + lt + lt);
		sb.append("btnSearch:\"#btnSearch\",//查询按钮");
		sb.append(ln + lt + lt);
		sb.append("btnAdvancedSearch:\"#btnAdvancedSearch\",//高级查询按钮");
		sb.append(ln + lt + lt);
		sb.append("btnReturn:\"#btnReturn\",//返回按钮");
		sb.append(ln + lt + lt);
		sb.append("btnDelete:\"#btnDelete\",//批量删除按钮");
		sb.append(ln + lt + lt);
		sb.append("formSearch:\"#formSearch\",//搜索框id");
		sb.append(ln + lt + lt);
		sb.append("dialogAdvancedSearch:\"#dialogAdvancedSearch\",//高级查询框id");
		
		sb.append(ln + lt + lt);
		sb.append("formAdd:\"#formAdd\",//新增表单id");
		sb.append(ln + lt + lt);
		sb.append("formView:\"#formView\",//查看表单id");
		sb.append(ln + lt + lt);
		sb.append("formUpdate:\"#formUpdate\",//修改表单id");
		sb.append(ln + lt + lt);
		sb.append("btnSubmitAdd:\"#btnSubmitAdd\",//新增提交按钮");
		sb.append(ln + lt + lt);
		sb.append("btnSubmitUpdate:\"#btnSubmitUpdate\",//修改提交按钮");
		sb.append(ln + lt + lt);
		sb.append("btnSubmitNextAdd:\"#btnSubmitNextAdd\",//继续新增提交按钮");
		//sb.append(ln + lt + lt);
		//sb.append("btnImp:\"#imp_btn\",//导入按钮");
		//sb.append(ln + lt + lt);
		//sb.append("btnExp:\"#exp_btn\",//导出按钮");
		sb.append(ln + lt + lt);
		
		//sb.append("impDialog:\"#impDialog\",//导入框id");

		sb.append(ln + lt + "},");
		sb.append(ln + lt);
		sb.append("model:{//字段");
		for (int i = 0; i < params.getColnames().size(); i++) {
			sb.append(ln + lt + lt);
			sb.append("" + params.getColnames().get(i) + ":{comment:\""
					+ params.getMemos().get(i) + "\",type:\""
					+ params.getColTypes().get(i) + "\"},");
		}

		sb.append(ln + lt);
		sb.append("},");
		sb.append(ln + lt);
		sb.append("action:{");
		sb.append(ln + lt + lt);
		sb.append("findPageData:\"" + UNAME + "/findPage" + UNAME + "\",");
		sb.append(ln + lt + lt);
		sb.append("addData:\"" + UNAME + "/add" + UNAME + "\",");
		sb.append(ln + lt + lt);
		sb.append("updateData:\"" + UNAME + "/update" + UNAME + "\",");
		sb.append(ln + lt + lt);
		sb.append("deleteData:\"" + UNAME + "/delete" + UNAME + "\",");
		sb.append(ln + lt + lt);
		sb.append("findDataByPk:\"" + UNAME + "/find" + UNAME + "ByPk/\"");
		//sb.append(ln + lt + lt);
		//sb.append("impData:\"" + UNAME + "/imp" + UNAME + "\",");
		//sb.append(ln + lt + lt);
		//sb.append("expData:\"" + UNAME + "/exp" + UNAME + "\",");
		sb.append(ln + lt);
		sb.append("},");
		sb.append(ln + lt);
		sb.append("page:{");
		sb.append(ln + lt + lt);
		sb.append("list:\"list.jsp\",");
		sb.append(ln + lt + lt);
		sb.append("add:\"add.jsp\",");
		sb.append(ln + lt + lt);
		sb.append("view:\"view.jsp?page=view&pk=\",");
		sb.append(ln + lt + lt);
		sb.append("update:\"update.jsp?page=modify&pk=\"");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	private static StringBuffer genReadload(StringBuffer sb) {
		sb.append("$(function(){");
		sb.append(ln + lt);
		sb.append("setClickEvent();");
		sb.append(ln + lt);
		sb.append("if(window.location.href.indexOf("+LNAME+"Params.page.list)>0){//列表页");
		sb.append(ln + lt+lt);
		sb.append("findPageData();");
		sb.append(ln + lt);
		sb.append("}else if(window.location.href.indexOf("+LNAME+"Params.page.update)>0){	//修改页");
		sb.append(ln + lt+lt);
		sb.append("showUpdateData(baseUtil.getUrlParam(\"pk\"));");
		sb.append(ln + lt);
		sb.append("}else if(window.location.href.indexOf("+LNAME+"Params.page.view)>0){	//查看页");
		sb.append(ln + lt+lt);
		sb.append("showViewData(baseUtil.getUrlParam(\"pk\"));");
		sb.append(ln + lt);
		sb.append("}else if(window.location.href.indexOf("+LNAME+"Params.page.add)>0){	//新增页");
		sb.append(ln + lt+lt);
		sb.append("showAddData();");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln);
		sb.append("});");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 事件绑定
	 * @param params
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2017年1月11日
	 */
	private static StringBuffer genSetClickEvent(GeneratorParams params,
			StringBuffer sb) {
		sb.append("function setClickEvent(e) {");
		
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.taskAdd).click(function() {// 新增按钮事件");
		sb.append(ln + lt + lt);
		//sb.append("showAddData();");
		sb.append("window.location.href="+ LNAME + "Params.page.add;");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.btnReturn).click(function() {// 返回按钮事件");
		sb.append(ln + lt + lt);
		sb.append("window.location.href="+ LNAME + "Params.page.list;");
		//sb.append("$(" + LNAME + "Params.nodes.taskList).click();");
		sb.append(ln + lt);
		sb.append("});");
		//sb.append(ln + lt);
		//sb.append("$(" + LNAME + "Params.nodes.taskList).click(function() {// 列表点击事件");
		//sb.append(ln + lt + lt);
		//sb.append("findPageData();");
		//sb.append(ln + lt);
		//sb.append("});");
		//sb.append(ln + lt);
		//sb.append("$(" + LNAME + "Params.nodes.btnExp).click(function() {//导出");
		//sb.append(ln + lt + lt);
		//sb.append("requestUtil.expData(" + LNAME + "Params.action.expData);");
		//sb.append(ln + lt);
		//sb.append("});");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.btnSearch).click(function() {// 查询");
		sb.append(ln + lt + lt);
		sb.append("searchData();");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.btnDelete).click(function() {// 批量删除按钮事件");
		sb.append(ln + lt + lt);
		sb.append("deleteData();");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln + lt);
//		sb.append("dialogUtil.advancedSearchDialog(" + LNAME
//				+ "Params.nodes.advancedSearchDialog," + LNAME
//				+ "Params.model,findPageData," + LNAME
//				+ "Params.nodes.btnAdvancedSearch);");
//		sb.append(ln + lt);
//		sb.append("dialogUtil.setImpDataDialog(" + LNAME
//				+ "Params.nodes.impDialog," + LNAME
//				+ "Params.action.impData,\"" + UNAME + ".xls\",findPageData," + LNAME + "Params.nodes.btnImp);");
		
		sb.append("$(" + LNAME
				+ "Params.nodes.btnSubmitAdd).click(function() {// 新增提交按钮事件");
		sb.append(ln + lt + lt);
		sb.append("submitAddData();");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.btnSubmitNextAdd).click(function() {// 继续提交按钮事件");
		sb.append(ln + lt + lt);
		sb.append("submitAddData(1);");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln + lt);	
		sb.append("$(" + LNAME
				+ "Params.nodes.btnSubmitUpdate).click(function() {// 修改提交按钮事件");
		sb.append(ln + lt + lt);
		sb.append("submitUpdateData();");
		sb.append(ln + lt);
		sb.append("});");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成分页查询方法
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genFindPageData(StringBuffer sb) {
		sb.append("function findPageData(actionName, cpage) {");
		sb.append(ln + lt);
		sb.append("requestUtil.findPageData( " + LNAME + "Params.action.findPageData, cpage, " + LNAME
				+ "Params.nodes.printPage, findPageData,showListData);");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成列表显示数据
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genShowListData(GeneratorParams params,
			StringBuffer sb) {
		String pk="id";
		for(int i=0;i<params.getColKey().size();i++){
			if(params.getColKey()!=null&&!"".equals(params.getColKey())){
				pk=params.getColnames().get(i);
				break;
			}
		}
		sb.append("function showListData(list) {");
		sb.append(ln + lt);
		sb.append("var node=" + LNAME + "Params.nodes.formList;");
		sb.append(ln + lt);
		sb.append("if (baseUtil.defaultShowListData(list, node)) {");
		sb.append(ln + lt + lt);
		sb.append("return;");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln + lt);
		//sb.append("" + LNAME + "Params.listData = list;");
		sb.append(ln + lt);
		sb.append("for (var i = 0; i < list.length; i++) {");
		sb.append(ln + lt + lt);
		sb.append("var tr = $(\"<tr></tr>\");");
		sb.append(ln + lt + lt);
		sb.append("tr.append(\"<td class='permissions_delete'><input type='checkbox' class='checkedInputTable' name='ids' value='\"+ list[i]."+pk+" + \"' /></td>\");");
		for (int i = 0; i < params.getColnames().size(); i++) {
			sb.append(ln + lt + lt);
			String val="list[i]."+params.getColnames().get(i);
			if("DATE".equals(params.getColTypes().get(i))){
				val="validation.YearMonthDayTimer("+val+")";
			}
			sb.append("tr.append(\"<td>\"+"
					+ val + "+\"</td>\");");
		}
		sb.append(ln + lt + lt);
		//sb.append("var check = \"<a class='editIcon' title='查看' href='javascript:;' onclick='showViewData(\"+ list[i]."+pk+" + \")'> <i class='icon-search'></i></a>\";");
		//sb.append(ln + lt + lt);
		//sb.append("var edit = \"<a class='editIcon permissions_update' title='编辑' href='javascript:;' onClick='showUpdateData(\"+ list[i]."+pk+" + \")'> <i class='icon-edit'></i></a>\";");
		//sb.append(ln + lt + lt);
		//sb.append("var remove = \"<a class='deleteIcon permissions_delete' title='删除' href='javascript:;' onclick=deleteData('\"+ list[i]."+pk+" + \"')> <i class='icon-trash'></i></a>\";");
		//sb.append(ln + lt + lt);
		//sb.append("tr.append(\"<td>\" + check + edit + remove + \"</td>\");");
		//sb.append(ln + lt + lt);
		//sb.append("tr.append(\"<td>\" + baseUtil.temp.list_btn + \"</td>\");");
		sb.append("var btns = $(\"<td></td>\");");
		sb.append(ln + lt + lt);
		sb.append("btns.append(baseUtil.temp.list_btn_update().attr(\"onclick\", \"window.location.href='\" + " + LNAME + "Params.page.update + list[i]."+pk+" + \"'\"));");
		sb.append(ln + lt + lt);
		sb.append("btns.append(baseUtil.temp.list_btn_delete().attr(\"onclick\", \"deleteData('\" + list[i]."+pk+" + \"')\"));");
		sb.append(ln + lt + lt);
		sb.append("tr.append(btns);");
		sb.append(ln + lt + lt);
		//sb.append("tr.find(\".btn_min_update\").attr(\"onclick\", \"window.location.href='\"+" + LNAME + "Params.page.update+\"?pk=\" + list[i]."+pk+" + \"'\");");
		//sb.append(ln + lt + lt);
		//sb.append("tr.find(\".btn_min_delete\").attr(\"onclick\", \"deleteData('\" + list[i]."+pk+" + \"')\");");
		//sb.append(ln + lt + lt);
		sb.append("$(node + \" tbody\").append(tr);");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成添加页面数据
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genShowAddData(StringBuffer sb) {
		sb.append("function showAddData() {");
		sb.append(ln + lt);
		sb.append("baseUtil.defaultShowAddData(" + LNAME + "Params.nodes.formAdd);");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;

	}

	/**
	 * 
	 * @Description 生成修改页面数据
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genShowUpdateData(StringBuffer sb) {
		sb.append("function showUpdateData(pk) {");
		sb.append(ln + lt);
		//sb.append("$(" + LNAME + "Params.nodes.taskUpdate).click();");
		//sb.append(ln + lt);
		sb.append("baseUtil.defaultShowAddData(" + LNAME + "Params.nodes.formUpdate);");
		//sb.append("var list = " + LNAME + "Params.listData[n];");
		sb.append(ln + lt);
		sb.append("requestUtil.xhr_get(" + LNAME + "Params.action.findDataByPk+pk).done(function(baseModel) {");
		sb.append(ln + lt+ lt);
		sb.append("if (baseModel.data != null) {");
		sb.append(ln + lt+ lt+ lt);
		sb.append("var list = baseModel.data;");
		//sb.append("var list = " + LNAME + "Params.listData[n];");	
		sb.append(ln + lt+ lt+ lt);
		sb.append("baseUtil.defaultShowEditData(list, " + LNAME
				+ "Params.nodes.formUpdate);");
		sb.append(ln+ lt+ lt);
		sb.append("}");
		sb.append(ln+ lt);
		sb.append("});");		
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成查看页面数据
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genShowViewData(StringBuffer sb) {
		sb.append("function showViewData(pk) {");
		sb.append(ln + lt);
		//sb.append("$(" + LNAME + "Params.nodes.taskView).click();");
		//sb.append(ln + lt);
		sb.append("requestUtil.xhr_get(" + LNAME + "Params.action.findDataByPk+pk).done(function(baseModel) {");
		sb.append(ln + lt+ lt);
		sb.append("if (baseModel.data != null) {");
		sb.append(ln + lt+ lt+ lt);
		sb.append("var list = baseModel.data;");
		//sb.append("var list = " + LNAME + "Params.listData[n];");	
		sb.append(ln + lt+ lt+ lt);
		sb.append("baseUtil.defaultShowViewData(list, " + LNAME+ "Params.nodes.formView);");
		sb.append(ln+ lt+ lt);
		sb.append("}");
		sb.append(ln+ lt);
		sb.append("});");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成提交方法
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genSubmitAddData(StringBuffer sb) {
		sb.append("function submitAddData(isNext) {");
		sb.append(ln + lt);
		sb.append("if (checkData()) {");
		sb.append(ln + lt + lt);
		sb.append("var formData = baseUtil.submitIgnoreParams(" + LNAME
				+ "Params.nodes.formAdd+ \" :input\");");
		sb.append(ln + lt + lt);
		sb.append("requestUtil.xhr("
				+ LNAME + "Params.action.addData, formData).done(function(baseModel) {");
		sb.append(ln + lt + lt + lt);
		sb.append("alert(baseModel.message);");
		sb.append(ln + lt + lt + lt);
		sb.append("if (isNext == 1) {");
		sb.append(ln + lt + lt + lt+ lt);
		sb.append("showAddData();");
		sb.append(ln + lt + lt + lt );
		sb.append("} else {");
		sb.append(ln + lt + lt + lt + lt );
		sb.append("$(" + LNAME + "Params.nodes.btnReturn).click();");
		sb.append(ln + lt + lt + lt);
		sb.append("}");
		sb.append(ln + lt + lt);
		sb.append("});");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}
	/**
	 * 
	 * @Description 生成提交方法
	 * @param sb
	 * @author Enzo
	 * @date 2018年2月1日
	 */
	private static StringBuffer genSubmitUpdateData(StringBuffer sb) {
		sb.append("function submitUpdateData() {");
		sb.append(ln + lt);
		sb.append("if (checkData()) {");
		sb.append(ln + lt + lt);
		sb.append("var formData = baseUtil.submitIgnoreParams(" + LNAME
				+ "Params.nodes.formUpdate+ \" :input\");");
		sb.append(ln + lt + lt);
		sb.append("requestUtil.xhr("
				+ LNAME + "Params.action.updateData, formData).done(function(baseModel) {");
		sb.append(ln + lt + lt + lt);
		sb.append("alert(baseModel.message);");
		sb.append(ln + lt + lt + lt);
		sb.append("$(" + LNAME + "Params.nodes.btnReturn).click();");	
		sb.append(ln + lt + lt);
		sb.append("});");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成删除方法
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genDeleteData(StringBuffer sb) {
		sb.append("function deleteData(delId) {");
		sb.append(ln + lt);
		sb.append("requestUtil.deleteData(" + LNAME
				+ "Params.action.deleteData,delId," + LNAME + "Params.nodes.formList, findPageData);");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成提交验证方法
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genCheckData(StringBuffer sb) {
		sb.append("function checkData() {");
		sb.append(ln + lt);
		sb.append("var flag = true;");
		sb.append(ln + lt);
		sb.append("var str = \"\";");
		sb.append(ln + lt);
		sb.append(ln + lt);
		sb.append("if (str.length > 0) {");
		sb.append(ln + lt + lt);
		sb.append("flag = false;");
		sb.append(ln + lt + lt);
		sb.append("alert(str);");
		sb.append(ln + lt);
		sb.append("}");
		sb.append(ln + lt);
		sb.append("return flag;");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成重置方法
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genResetData(StringBuffer sb) {
		sb.append("function resetData() {");
		sb.append(ln + lt);
		sb.append("baseUtil.showEditPageField();");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.submitForm+\" input\").val(\"\");");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 生成查询方法
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genSearchData(StringBuffer sb) {
		sb.append("function searchData() {");
		sb.append(ln + lt);
		sb.append("baseUtil.defaultSearchParms(" + LNAME
				+ "Params.nodes.formSearch);");
		sb.append(ln + lt);
		sb.append("findPageData();");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 显示导出对话框事件
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2017年5月4日
	 */
	private static StringBuffer genShowImpDialog(StringBuffer sb) {
		sb.append("function searchData() {");
		sb.append(ln + lt);
		sb.append("$(" + LNAME + "Params.nodes.impDialog).dialog(\"open\");");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 显示高级查询框事件
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2017年5月4日
	 */
	private static StringBuffer genShowAdvancedSearchDialog(StringBuffer sb) {
		sb.append("function searchData() {");
		sb.append(ln + lt);
		sb.append("$(" + LNAME
				+ "Params.nodes.advancedSearchDialog).dialog(\"open\");");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

	/**
	 * 
	 * @Description 设置导出事件
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2017年5月4日
	 */
	private static StringBuffer genExpData(StringBuffer sb) {
		sb.append("function searchData() {");
		sb.append(ln + lt);
		sb.append("requestUtil.expData(" + LNAME + "Params.action.expData);");
		sb.append(ln);
		sb.append("}");
		sb.append(ln);
		return sb;
	}

}
