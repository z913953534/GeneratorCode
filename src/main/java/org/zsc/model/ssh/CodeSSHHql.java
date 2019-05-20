package org.zsc.model.ssh;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GeneratorUtil;

public class CodeSSHHql {
	public static void GenerateHql(GeneratorParams params) throws Exception {

		String packagePath = params.getPackageService() + ".impl.hql";
		String content = CodeSSHHql.parseHql(params, packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + "Hql.java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		// System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代�?
	 */
	static String parseHql(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java

		sb.append("public class " + params.getTableName() + "Hql {\r\n\r\n");

		processAllAttrs(params, sb);
		sb.append("\r\n");
		// processAllMethod(baseModel, sb);
		sb.append("}\r\n");

		System.out.println(sb.toString());
		return sb.toString();

	}

	/**
	 * 解析输出属�?
	 * 
	 * @param baseModel
	 * @param sb
	 */
	static void processAllAttrs(GeneratorParams params, StringBuffer sb) {
		String bTableName = params.getTableName();// 首写字母小写的表�?
		// String sTableName = DatabaseUtils.indexLowerCase(bTableName);//
		// 首写字母小写的表�?
		int isdeleted = 0;// 标记是否存在是否删除字段
		for (int i = 0; i < params.getColnames().size(); i++) {
			if ("isdeleted".equals(params.getColnames().get(i))) {// 如果是主键id
				isdeleted = 1;
			}
		}
		if (isdeleted == 1) {
			sb.append("\tpublic final static String FINDDATA = \"from "
					+ bTableName + " where isdeleted=\'0\'\";\r\n\r\n");
			sb.append("\tpublic final static String DELETEDATA = \"update "
					+ bTableName
					+ " set isdeleted=\'1\' where id in(\'id_from\')\";\r\n\r\n");
		} else {
			sb.append("\tpublic final static String FINDDATA = \"from "
					+ bTableName + "\";\r\n\r\n");
			sb.append("\tpublic final static String DELETEDATA = \"delete "
					+ bTableName + " where id in(\'id_from\')\";\r\n\r\n");
		}

	}

	/**
	 * 生成�?��的get、set方法 （生成的baseModel�?
	 * 
	 * @param baseModel
	 * @param sb
	 */
	static void processAllMethod(GeneratorParams params, StringBuffer sb) {

	}

}
