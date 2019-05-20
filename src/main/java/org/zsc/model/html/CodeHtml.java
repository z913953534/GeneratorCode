package org.zsc.model.html;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GeneratorUtil;

public class CodeHtml {
	public static String lt = "\t";
	public static String ln = "\r\n";
	private static String UNAME;
	private static String LNAME;

	public static void GenerateHtml(GeneratorParams params) throws Exception {
		UNAME = params.getTableName();
		LNAME = UNAME.substring(0, 1).toLowerCase()
				+ UNAME.substring(1, UNAME.length());// 首字母转小写
		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/html";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + LNAME + ".html";
		StringBuffer sb = new StringBuffer();
		sb.append("<%@ page language=\"java\" import=\"java.util.*\" pageEncoding=\"UTF-8\"%>"
				+ ln);
		sb.append("<html>");
		sb.append(ln);
		sb.append("<head>");
		sb.append(ln + lt);
		sb.append("<script src=\"test/" + LNAME
				+ ".js\" type=\"text/javascript\"></script>");
		sb.append(ln);
		sb.append("</head>");
		sb.append(ln);
		sb.append("<body>");
		genTask(params, sb);
		sb.append(ln + lt);
		sb.append("<div class=\"taskTabs_gloup\">");
		genListForm(params, sb);
		genEditForm(params, sb);
		sb.append(ln + lt);
		sb.append("</div>");
		sb.append(ln);
		sb.append("</body>");
		sb.append(ln);
		sb.append("</html>");
		GeneratorUtil.writeStringToFile(new File(resPath), sb.toString(),
				params);
	}

	private static StringBuffer genTask(GeneratorParams params, StringBuffer sb) {
		sb.append(ln + lt);
		sb.append("<ul class=\"taskTabs\">");
		sb.append(ln + lt + lt);
		sb.append("<li class=\"currentd\" name=\"#tabList\" id='taskList'>列表</li>");
		sb.append(ln + lt + lt);
		sb.append("<li name=\"#tabSubmit\" class='permissions_add' id='taskAdd'>新增</li>");
		sb.append(ln + lt + lt);
		sb.append("<li name=\"#tabSubmit\" class=\"helpTask permissions_update\" id='taskUpdate'>编辑</li>");
		sb.append(ln + lt + lt);
		sb.append("<li name=\"#tabSubmit\" class=\"helpTask\" id='taskView'>查看</li>");
		sb.append(ln + lt);
		sb.append("</ul>");

		return sb;
	}

	/**
	 * 
	 * @Description 生成全局参数
	 * @param sb
	 * @return
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genListForm(GeneratorParams params,
			StringBuffer sb) {

		sb.append(ln + lt + lt);
		sb.append("<div class=\"taskTabs_tab\" id=\"tabList\">");
		sb.append(ln + lt + lt + lt);
		sb.append("<form action=''>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<a class='btn' href='javascript:;' id=\"btnSearch\">搜索</a>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<a class='btn' href='javascript:;' id=\"btnAdvancedSearch\">高级查询</a>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<a class='btn permissions_delete' href='javascript:;' id=\"btnDelete\">删除</a>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<a class='btn permissions_imp' href='javascript:;' id=\"btnImp\">导入</a>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<a class='btn permissions_exp' href='javascript:;' id=\"btnExp\">导出</a>");
		sb.append(ln + lt + lt + lt);
		sb.append("</form>");
		// end 搜索
		sb.append(ln + lt + lt + lt);
		sb.append("<form id='formList'>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<table  class=\"\">");
		sb.append(ln + lt + lt + lt + lt + lt);
		sb.append("<thead>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("<tr>");
		sb.append(ln + lt + lt + lt + lt + lt + lt + lt);
		sb.append("<th class=\"checkboxTable permissions_delete\"><input type='checkbox'></th>");
		for (int i = 0; i < params.getColnames().size(); i++) {
			sb.append(ln + lt + lt + lt + lt + lt + lt + lt);
			sb.append("<th>" + params.getMemos().get(i) + "</th>");
		}
		sb.append(ln + lt + lt + lt + lt + lt + lt + lt);
		sb.append("<th>操作</th>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("</tr>");
		sb.append(ln + lt + lt + lt + lt + lt);
		sb.append("</thead>");
		sb.append(ln + lt + lt + lt + lt + lt);
		sb.append("<tbody></tbody>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("</table>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<div class='printPage' id='printPage'>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("</div>");
		sb.append(ln + lt + lt + lt);
		sb.append("</form>");
		sb.append(ln + lt + lt);
		sb.append("</div>");

		return sb;
	}

	/**
	 * 
	 * @Description 生成分页查询方法
	 * @param sb
	 * @author Enzo
	 * @date 2016年12月2日
	 */
	private static StringBuffer genEditForm(GeneratorParams params,
			StringBuffer sb) {
		sb.append(ln + lt + lt);
		sb.append("<div class=\"taskTabs_tab\" id=\"tabSubmit\">");
		sb.append(ln + lt + lt + lt);
		sb.append("<form id='formSubmit'>");
		for (int i = 0; i < params.getColnames().size(); i++) {
			sb.append(ln + lt + lt + lt + lt);
			sb.append("<div class='am-input-group'>");
			sb.append(ln + lt + lt + lt + lt + lt);
			sb.append("<label class='am-input-group-label'>"
					+ params.getMemos().get(i) + ":</label>");
			sb.append(ln + lt + lt + lt + lt + lt);
			//sb.append("<div class='controls'>");
			String attr = "";
			String isdate="";
			if ("DATE".equals(params.getColTypes().get(i))) {// 时间
				attr = " onfocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})\"";
				isdate=" alt=\"date\"";
			} else if ("NUMBER".equals(params.getColTypes().get(i))) {
				attr = " onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"";
			}
			sb.append("<input name='" + params.getColnames().get(i)
					+ "' class='am-form-field' type='text' id='"
					+ params.getColnames().get(i) + "_field' placeholder='"
					+ params.getMemos().get(i) + "'" + attr+isdate + ">");
			//sb.append("</div>");
			sb.append(ln + lt + lt + lt + lt);
			sb.append("</div>");
		}
		// 按钮
		sb.append(ln + lt + lt + lt + lt);
		sb.append("<div class='control-group control-foot'>");
		sb.append(ln + lt + lt + lt + lt + lt);
		sb.append("<div class='controls'>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("<a class=\"btn editPageField\" id=\"btnSubmit\">保存</a>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("<a class=\"btn editPageField\" id=\"btnSubmitNextAdd\">保存&新增</a>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("<a class=\"btn editPageField\" id=\"btnSubmitNextAdd\">更新</a>");
		sb.append(ln + lt + lt + lt + lt + lt + lt);
		sb.append("<a class=\"btn return_btn\" id=\"btnReturn\">返回</a>");
		sb.append(ln + lt + lt + lt + lt + lt);
		sb.append("</div>");
		sb.append(ln + lt + lt + lt + lt);
		sb.append("</div>");
		sb.append(ln + lt + lt + lt);
		sb.append("</form>");
		sb.append(ln + lt + lt);
		sb.append("</div>");
		sb.append(ln);
		return sb;
	}

}
