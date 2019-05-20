package org.zsc.model.ssm;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSMServiceImpl {

	public static void GenerateServiceImpl(GeneratorParams params)
			throws Exception {

		String packagePath = params.getPackageService() + ".impl";
		String content = CodeSSMServiceImpl.parseServiceImpl(params,
				packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName()
				+ "ServiceImpl.java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		// System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代�?
	 */
	static String parseServiceImpl(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java

		sb.append(GenImportPackage.iService);
		sb.append("import " + params.getPackageService() + "."
				+ params.getTableName() + "Service;\r\n");// service�?
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");// 实体类包
		sb.append("import " + params.getPackageUtil()
		+ ".entity.BaseModel;\r\n");
		sb.append(GenImportPackage.iBusinessException);
		
		sb.append("\r\n");
		sb.append("@Service\r\n");
		sb.append("public class " + params.getTableName()
				+ "ServiceImpl implements " + params.getTableName()
				+ "Service{\r\n\r\n");

		processAllAttrs(params, sb);
		sb.append("\r\n");
		processAllMethod(params, sb);
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

		// 分页查询
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void findPageData(BaseModel baseModel," + bTableName + " "
				+ sTableName + ")throws Exception {\r\n");
		sb.append("\r\n");
		sb.append("\t\tbaseModel.setData("+sTableName+".findPage_isdeleted_mysql(baseModel));\r\n");
		sb.append("\t}\r\n\r\n");

		// 添加
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void addData(BaseModel baseModel," + bTableName + " " + sTableName
				+ ")throws Exception {\r\n");
		sb.append("\r\n");
		sb.append("\t\t "+sTableName+".insert("+sTableName+");\r\n");
		sb.append("\t}\r\n\r\n");

		// 修改
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void updateData(BaseModel baseModel," + bTableName + " "
				+ sTableName + ")throws Exception {\r\n");
		sb.append("\r\n");
		sb.append("\t\t "+sTableName+".update("+sTableName+");\r\n");
		sb.append("\t}\r\n\r\n");

		// 删除
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void deleteData(BaseModel baseModel,String delIds)throws Exception {\r\n");
		sb.append("\t\tif (delIds == null || \"\".equals(delIds)) {\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"请选择删除的记录\");\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\tnew "+bTableName+"().deleteInPkValue_isdeleted(delIds);\r\n");
		sb.append("\t}\r\n\r\n");
		
		// 根据id查询记录
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void findDataByPk(BaseModel baseModel,String pk)throws Exception {\r\n");
		sb.append("\t\tif (pk == null || \"\".equals(pk)) {\r\n");
		sb.append("\t\t\tthrow new BusinessException(\"查询记录主键id不能为空\");\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\tbaseModel.setData(new "+bTableName+"().findByPk(pk));\r\n");
		sb.append("\t}\r\n\r\n");		
		

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
