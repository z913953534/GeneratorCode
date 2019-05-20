package org.zsc.model.ssh;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSHAction {

	public static void GenerateAction(GeneratorParams params) throws Exception {

		String packagePath = params.getPackageAction();
		String content = CodeSSHAction.parseAction(params, packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + "Action.java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		//System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 */
	static String parseAction(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java

		sb.append(GenImportPackage.iResource);
		sb.append(GenImportPackage.iAction);
		sb.append(GenImportPackage.iResult);
		sb.append(GenImportPackage.iResults);
		sb.append(GenImportPackage.iNamespace);
		sb.append(GenImportPackage.iModelDriven);
		sb.append(GenImportPackage.iInputStream);
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");
		sb.append("import " + params.getPackageService() + "."
				+ params.getTableName() + "Service;\r\n");
		sb.append("import " + params.getPackageCommon() + ".action.BaseAction;\r\n");
		sb.append("import " + params.getPackageUtil() + ".model.BaseModel;\r\n");
		sb.append("\r\n");
		sb.append("@SuppressWarnings(\"serial\")\r\n");
		sb.append("@Namespace(\"/" + params.getTableName() + "\")\r\n");
		sb.append("@Results({\r\n");
		sb.append("\t@Result(name = \"success\", type = \"json\")\r\n");
		sb.append("})\r\n");
		sb.append("public class " + params.getTableName()
				+ "Action extends BaseAction implements ModelDriven<"
				+ params.getTableName() + ">{\r\n\r\n");
		// sb.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
		processAllAttrs(params, sb);
		sb.append("\r\n");
		processAllMethod(params, sb);
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
		sb.append("\tprivate " + bTableName + "Service " + sTableName
				+ "Service;\r\n");
		sb.append("\tprivate InputStream inputStream;\r\n");
		sb.append("\tprivate BaseModel baseModel = new BaseModel();\r\n");
		sb.append("\tprivate " + bTableName + " " + sTableName + " = new "
				+ bTableName + "();\r\n\r\n");
		// 查询的action
		sb.append("\t@Action(value = \"find" + bTableName+ "\")\r\n");
		sb.append("\tpublic String find" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\tbaseModel = " + sTableName + "Service.findData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 添加的action
		sb.append("\t@Action(value = \"add" + bTableName+ "\")\r\n");
		sb.append("\tpublic String add" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\tbaseModel = " + sTableName + "Service.addData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 修改的action
		sb.append("\t@Action(value = \"update" + bTableName+ "\")\r\n");
		sb.append("\tpublic String update" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\t baseModel = " + sTableName + "Service.updateData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 删除的action
		sb.append("\t@Action(value = \"delete" + bTableName+ "\")\r\n");
		sb.append("\tpublic String delete" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\tbaseModel = " + sTableName + "Service.deleteData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t }catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 分页查询的action
		sb.append("\t@Action(value = \"findPage" + bTableName+ "\")\r\n");
		sb.append("\tpublic String findPage" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\t baseModel = " + sTableName + "Service.findPageData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导入的action
		sb.append("\t@Action(value = \"imp" + bTableName+ "\")\r\n");
		sb.append("\tpublic String imp" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\t try {\r\n");
		sb.append("\t\t\tbaseModel = " + sTableName + "Service.impData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导出的action
		sb.append("\t@Action(value = \"exp" + bTableName
				+ "\", results = {\r\n");
		sb.append("\t\t@Result(name = \"exp\", type = \"stream\", params = { \"contentType\",\"application/octet-stream\", \"inputName\", \"inputStream\",\"contentDisposition\",\"attachment;filename=\\\""
				+ bTableName + ".xls\\\"\", \"bufferSize\",\"40960\" })})\r\n");
		sb.append("\tpublic String exp" + bTableName
				+ "Action() throws Exception {\r\n");
		sb.append("\t\ttry {\r\n");
		sb.append("\t\t\t inputStream = " + sTableName + "Service.expData("
				+ sTableName + ", baseModel);\r\n");
		sb.append("\t\t}catch (Exception e) {\r\n");
		sb.append("\t\t\t baseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\t e.printStackTrace();\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn \"exp\";\r\n");
		sb.append("\t}\r\n\r\n");

	}

	/**
	 * 生成所有的get、set方法 （生成的baseModel）
	 * 
	 * @param baseModel
	 * @param sb
	 */
	static void processAllMethod(GeneratorParams params, StringBuffer sb) {

		sb.append("\tpublic void setBaseModel(BaseModel baseModel){\r\n");
		sb.append("\t\t this.baseModel = baseModel;\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\tpublic BaseModel getBaseModel(){\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		sb.append("\tpublic void setInputStream(InputStream inputStream){\r\n");
		sb.append("\t\t this.inputStream = inputStream;\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("\tpublic InputStream getInputStream(){\r\n");
		sb.append("\t\treturn inputStream;\r\n");
		sb.append("\t}\r\n\r\n");

		sb.append("\tpublic " + params.getTableName() + " getModel(){\r\n");
		sb.append("\t\treturn "
				+ GeneratorUtil.indexLowerCase((params.getTableName()))
				+ ";\r\n");
		sb.append("\t}\r\n\r\n");
	}

}
