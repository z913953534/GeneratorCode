package org.zsc.model.ssm;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSMController {

	public static void GenerateController(GeneratorParams params)
			throws Exception {

		String packagePath = params.getPackageAction();
		String content = CodeSSMController.parseController(params, packagePath);

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

		sb.append(GenImportPackage.iAutowired);
		sb.append(GenImportPackage.iController);
		sb.append(GenImportPackage.iRequestMapping);
		sb.append(GenImportPackage.iResponseBody);
		sb.append(GenImportPackage.iApiImplicitParam);
		sb.append(GenImportPackage.iApiImplicitParams);
		sb.append(GenImportPackage.iApiOperation);
		sb.append(GenImportPackage.iPathVariable);
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");
		sb.append("import " + params.getPackageService() + "."
				+ params.getTableName() + "Service;\r\n");
		sb.append("import "+params.getPackageCommon()+".controller.BaseController;\r\n");
		sb.append("import " + params.getPackageUtil() + ".entity.BaseModel;\r\n");
		sb.append("\r\n");
		sb.append("@Controller\r\n");
		sb.append("@ResponseBody\r\n");
		sb.append("@RequestMapping(\"" + params.getTableName() + "\")\r\n");
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
		sb.append("\t@Autowired\r\n");
		sb.append("\t" + bTableName + "Service " + sTableName + "Service;\r\n\r\n");
		// 分页查询的action
		sb.append("\t@ApiOperation(value = \"分页查询\", httpMethod = \"POST\", response = "+bTableName+".class)\r\n");
		sb.append("\t@RequestMapping(value = \"findPage" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel findPageData(BaseModel baseModel," + bTableName + " "
				+ sTableName + ") throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.findPageData(baseModel," + sTableName
				+ ");\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 添加的action
		sb.append("\t@ApiOperation(value = \"新增\", httpMethod = \"POST\", response = "+bTableName+".class)\r\n");
		sb.append("\t@RequestMapping(value = \"add" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel addData(BaseModel baseModel," + bTableName + " " + sTableName
				+ ") throws Exception {\r\n");	
		sb.append("\t\t" + sTableName + "Service.addData(baseModel," + sTableName
				+ ");\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 修改的action
		sb.append("\t@ApiOperation(value = \"修改\", httpMethod = \"POST\", response = "+bTableName+".class)\r\n");
		sb.append("\t@RequestMapping(value = \"update" + bTableName + "\")\r\n");
		sb.append("\tpublic BaseModel updateData(BaseModel baseModel," + bTableName + " "
				+ sTableName + ") throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.updateData(baseModel," + sTableName
				+ ");\r\n");		
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 删除的action
		sb.append("\t@ApiOperation(value = \"删除\", httpMethod = \"GET\")\r\n");
		//sb.append("\t@ApiImplicitParams({ @ApiImplicitParam(name = \"delIds\", value = \"删除数据的主键id\", required = true, paramType = \"query\") })\r\n");
		sb.append("\t@RequestMapping(\"delete" + bTableName + "/{delIds}\")\r\n");
		sb.append("\tpublic BaseModel deleteData(BaseModel baseModel,@PathVariable(\"delIds\") String delIds) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.deleteData(baseModel,delIds);\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 根据主键id查询记录的action
		sb.append("\t@ApiOperation(value = \"根据主键id查询记录\", httpMethod = \"GET\")\r\n");
		//sb.append("\t@ApiImplicitParams({ @ApiImplicitParam(name = \"pk\", value = \"主键id\", required = true, paramType = \"query\") })\r\n");
		sb.append("\t@RequestMapping(\"find" + bTableName + "ByPk/{pk}\")\r\n");
		sb.append("\tpublic BaseModel findDataByPk(BaseModel baseModel,@PathVariable(\"pk\") String pk) throws Exception {\r\n");
		sb.append("\t\t" + sTableName + "Service.findDataByPk(baseModel,pk);\r\n");	
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");
		
	}

}
