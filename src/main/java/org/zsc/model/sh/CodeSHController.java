package org.zsc.model.sh;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSHController {

	public static void GenerateController(GeneratorParams params)
			throws Exception {

		String packagePath = params.getPackageAction();
		String content = CodeSHController.parseController(params, packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + "Controller.java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		// System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 */
	static String parseController(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java

		sb.append(GenImportPackage.iResource);
		sb.append(GenImportPackage.iController);
		sb.append(GenImportPackage.iRequestMapping);
		sb.append(GenImportPackage.iResponseBody);
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");
		sb.append("import " + params.getPackageService() + "."
				+ params.getTableName() + "Service;\r\n");
		sb.append("import " + params.getPackageCommon()
				+ ".controller.BaseController;\r\n");
		sb.append("import " + params.getPackageUtil() + ".model.BaseModel;\r\n");
		sb.append("\r\n");
		sb.append("@Controller\r\n");
		sb.append("@ResponseBody\r\n");
		sb.append("@RequestMapping(\"/" + params.getTableName() + "\")\r\n");
		sb.append("public class " + params.getTableName()
				+ "Controller extends BaseController {\r\n\r\n");
		processAllAttrs(params, sb);
		sb.append("\r\n");
		sb.append("}\r\n");
		System.out.println(sb.toString());
		return sb.toString();

	}

	/**
	 * 解析输出属性
	 * 
	 * @param baseModel
	 * @param sb
	 */
	static void processAllAttrs(GeneratorParams params, StringBuffer sb) {
		String bTableName = params.getTableName();// 首写字母小写的表名
		String sTableName = GeneratorUtil.indexLowerCase(bTableName);// 首写字母小写的表名
		sb.append("\t@Resource\r\n");
		sb.append("\t" + bTableName + "Service " + sTableName + "Service;\r\n\r\n");
		// 查询的action
		sb.append("\t@RequestMapping(value = \"/find" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel findData(" + bTableName + " "
				+ sTableName + ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.findData(" + sTableName
				+ ", baseModel);\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 添加的action
		sb.append("\t@RequestMapping(value = \"/add" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel addData(" + bTableName + " " + sTableName
				+ ",BaseModel baseModel) throws Exception {\r\n");	
		sb.append("\t\t" + sTableName + "Service.addData(" + sTableName
				+ ", baseModel);\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 修改的action
		sb.append("\t@RequestMapping(value = \"/update" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel updateData(" + bTableName + " "
				+ sTableName + ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.updateData(" + sTableName
				+ ", baseModel);\r\n");		
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 删除的action
		sb.append("\t@RequestMapping(\"/delete" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel deleteData(" + bTableName + " "
				+ sTableName + ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.deleteData(" + sTableName
				+ ", baseModel);\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 分页查询的action
		sb.append("\t@RequestMapping(\"/findPage" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel findPageData(" + bTableName + " "
				+ sTableName + ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.findPageData(" + sTableName
				+ ", baseModel);\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导入的action
		sb.append("\t@RequestMapping(\"/imp" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel impData(" + bTableName + " " + sTableName
				+ ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.impData(" + sTableName
				+ ", baseModel);\r\n");		
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导出的action
		sb.append("\t@RequestMapping(\"/exp" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel expData(" + bTableName + " " + sTableName
				+ ",BaseModel baseModel) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.expData(" + sTableName
				+ ", baseModel);\r\n");		
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

	}

}
