package org.zsc.model.ssm;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSMService {

	public static void GenerateService(GeneratorParams params) throws Exception {

		String packagePath = params.getPackageService();
		String content = CodeSSMService.parseService(params, packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + "Service.java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		//System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代�?
	 */
	static String parseService(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");
		sb.append("import " + params.getPackageUtil()
				+ ".entity.BaseModel;\r\n");
		sb.append("\r\n");
		sb.append("public interface " + params.getTableName()
				+ "Service {\r\n\r\n");

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
		String sTableName = GeneratorUtil.indexLowerCase(bTableName);// 首写字母小写的表�?
		sb.append("\tvoid findPageData(BaseModel baseModel," + bTableName
				+ " " + sTableName + ")throws Exception;\r\n\r\n");
		sb.append("\tvoid addData(BaseModel baseModel," + bTableName + " " + sTableName
				+ ")throws Exception;\r\n\r\n");
		sb.append("\tvoid updateData(BaseModel baseModel," + bTableName + " " + sTableName
				+ ")throws Exception;\r\n\r\n");
		sb.append("\tvoid deleteData(BaseModel baseModel,String delIds)throws Exception;\r\n\r\n");
		sb.append("\tvoid findDataByPk(BaseModel baseModel,String pk)throws Exception;\r\n\r\n");

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
