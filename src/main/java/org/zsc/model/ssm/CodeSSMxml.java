package org.zsc.model.ssm;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GeneratorUtil;

public class CodeSSMxml {

	public static void GenerateModel(GeneratorParams params) throws Exception {

		String content = CodeSSMxml.parseModel(params);

		String path = params.getProjectUrl() + "/" + params.getProjectName() + "/mapper";

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + "Mapper.xml";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		// System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 * 
	 * @param baseModel
	 * @param packagePath
	 * @return
	 */
	static String parseModel(GeneratorParams params) {

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		sb.append("\r\n");
		sb.append(
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
		sb.append("\r\n");
		sb.append("<mapper namespace=\"" + params.getPackageDao() + "." + params.getTableName() + "Dao\">");
		sb.append("\r\n");
		addResultMap(params, sb);
		addFindPage(params, sb);
		addCount(params, sb);
		sb.append("</mapper>");

		System.out.println(sb.toString());
		return sb.toString();

	}

	/**
	 * 
	 * <h3>生成resultMap</h3>
	 * 
	 * @param params
	 * @param sb
	 * @author Enzo
	 * @date 2018年3月14日 上午9:27:42
	 */
	static void addResultMap(GeneratorParams params, StringBuffer sb) {
		sb.append("\t");
		sb.append("<resultMap id=\"map" + params.getTableName() + "Vo\" type=\"" + params.getPackageEntity() + "."
				+ params.getTableName() + "\">");
		for (int i = 0; i < params.getColnames().size(); i++) {
			if (params.getColKey() != null && params.getColKey().get(i) != null
					&& params.getColKey().get(i).indexOf("PRI") > -1) {// mysql主键字段
				sb.append("\r\n\t\t");
				sb.append("<id property=\"" + params.getColnames().get(i) + "\" column=\"" + params.getColnames().get(i)
						+ "\" />");
			} 
		}
		sb.append("\r\n\t\t");
		sb.append("<association property=\""+GeneratorUtil.indexLowerCase(params.getTableName())+"\" javaType=\""+params.getPackageEntity() + "."
				+ params.getTableName()+"\">");
		for (int i = 0; i < params.getColnames().size(); i++) {
			sb.append("\r\n\t\t\t");
			if (params.getColKey() != null && params.getColKey().get(i) != null
					&& params.getColKey().get(i).indexOf("PRI") > -1) {// mysql主键字段
				sb.append("<id property=\"" + params.getColnames().get(i) + "\" column=\"" + params.getColnames().get(i)
						+ "\" />");
			} else {
				sb.append("<result column=\"" + params.getColnames().get(i) + "\" property=\""
						+ params.getColnames().get(i) + "\" />");
			}
		}
		sb.append("\r\n\t\t");
		sb.append("</association>");
		sb.append("\r\n\t");
		sb.append("</resultMap>");
		sb.append("\r\n");
	}

	/**
	 * 
	 * <h3>生成分页查询语句</h3>
	 * 
	 * @param params
	 * @param sb
	 * @author Enzo
	 * @date 2018年3月14日 上午9:28:04
	 */
	static void addFindPage(GeneratorParams params, StringBuffer sb) {
		sb.append("\r\n\t");
		sb.append("<select id=\"findPage" + params.getTableName() + "Vo\" resultMap=\"map" + params.getTableName()
				+ "Vo\" parameterType=\"" + params.getPackageUtil() + ".entity.QueryParams\">");
		sb.append("\r\n\t\t");
		sb.append("select * from " + params.getOldTableName());
		sb.append("\r\n\t\t");
		sb.append(" where isdeleted='0'");
		sb.append("\r\n\t\t");
		sb.append("<if test=\"where!=null and where !=''\">and</if>");
		sb.append("\r\n\t\t");
		sb.append("${where} ${order} limit ${index_count},${page_size}");
		sb.append("\r\n\t");
		sb.append("</select>");
		sb.append("\r\n");
	}

	static void addCount(GeneratorParams params, StringBuffer sb) {
		sb.append("\r\n\t");
		sb.append("<select id=\"count" + params.getTableName() + "Vo\" parameterType=\"" + params.getPackageUtil()
				+ ".entity.QueryParams\" resultType=\"int\">");
		sb.append("\r\n\t\t");
		sb.append("select count(*) from "+ params.getOldTableName());
		sb.append("\r\n\t\t");
		sb.append("where isdeleted='0'");
		sb.append("\r\n\t\t");
		sb.append("<if test=\"where!=null and where !=''\">and</if> ${where}");
		sb.append("\r\n\t");
		sb.append("</select>");
		sb.append("\r\n");
	}

}
