package org.zsc.model.ssh;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSHServiceImpl {

	public static void GenerateServiceImpl(GeneratorParams params)
			throws Exception {

		String packagePath = params.getPackageService() + ".impl";
		String content = CodeSSHServiceImpl.parseServiceImpl(params,
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
		sb.append(GenImportPackage.iList);
		sb.append(GenImportPackage.iMap);
		sb.append(GenImportPackage.iResource);
		sb.append("import " + params.getPackageService() + "."
				+ params.getTableName() + "Service;\r\n");// service�?
		sb.append("import " + params.getPackageService() + ".impl.hql."
				+ params.getTableName() + "Hql;\r\n");// hql�?
		sb.append("import " + params.getPackageUtil() + ".dao.BaseUtilDao"
				+ ";\r\n");// baseDao�?
		sb.append("import " + params.getPackageUtil() + ".model.BaseModel"
				+ ";\r\n");// baseModel�?
		sb.append("import " + params.getPackageUtil() + ".util.BaseUtil"
				+ ";\r\n");// 高级查询工具�?
		sb.append("import " + params.getPackageUtil() + ".file.ExcelUtil"
				+ ";\r\n");// 高级查询工具�?
		sb.append("import " + params.getPackageUtil()
				+ ".file.UploaderFileUtil" + ";\r\n");// 文件上传工具类
		sb.append("import " + params.getPackageEntity() + "."
				+ params.getTableName() + ";\r\n");// 实体类包
		sb.append("\r\n");
		sb.append("@Service\r\n");
		sb.append("public class " + params.getTableName()
				+ "ServiceImpl implements " + params.getTableName()
				+ "Service{\r\n\r\n");
		sb.append("\t@Resource\r\n");
		sb.append("\tprivate BaseUtilDao<" + params.getTableName()
				+ "> baseDao;\r\n\r\n");

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

		// 查询
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel findData(" + bTableName + " "
				+ sTableName + ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tString hql=" + bTableName + "Hql.FINDDATA;\r\n");
		sb.append("\t\tList<" + bTableName + "> list = (List<" + bTableName
				+ ">)baseDao.findData(hql);\r\n");
		sb.append("\t\tbaseModel.setData(list);\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 添加
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel addData(" + bTableName + " " + sTableName
				+ ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tbaseDao.save(" + sTableName + ");\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 修改
		sb.append("\t@Override\r\n");
		sb.append("\t public BaseModel updateData(" + bTableName + " "
				+ sTableName + ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\t baseDao.update(" + sTableName + ");\r\n");
		sb.append("\t\t return baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 删除
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel deleteData(" + bTableName + " "
				+ sTableName + ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tString deleteIds = baseModel.getIds().replace(\",\", \"\',\'\");\r\n");
		sb.append("\t\tString hql = " + bTableName
				+ "Hql.DELETEDATA.replace(\"id_from\", deleteIds);\r\n");
		sb.append("\t\tbaseDao.updateHql(hql);\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");
		// 分页查询
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel findPageData(" + bTableName + " "
				+ sTableName + ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tString hql=" + bTableName + "Hql.FINDDATA;\r\n");
		sb.append("\t\tList<" + bTableName + "> list = (List<" + bTableName
				+ ">)baseDao.findPageData(hql,baseModel);\r\n");
		sb.append("\t\tbaseModel.setData(list);\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导入
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel impData(" + bTableName + " " + sTableName
				+ ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tExcelUtil.createWorkBook(baseModel);\r\n");
		sb.append("\t\tif(baseModel.getResultFlag()>0){\r\n");
		sb.append("\t\t\treturn baseModel;\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\tList list = (List) baseModel.getData();\r\n");
		sb.append("\t\tint sum=0;\r\n");
		sb.append("\t\tfor (int i = 0; i < list.size(); i++) {\r\n");
		sb.append("\t\t\t" + bTableName + " " + sTableName + "Save = new "
				+ bTableName + "();\r\n");
		sb.append("\t\t\tMap<String, String> map = (Map<String, String>) list.get(i);\r\n");
		sb.append("\t\t\tBaseUtil.reflect(map," + sTableName
				+ "Save, baseModel);// 将excel中的内容存放到实体类中\r\n");
		sb.append("\t\t\tif (baseModel.getResultFlag() == 1) {// 如果类型转换失败\r\n");
		sb.append("\t\t\t\tbaseModel.setMessage(\"已验证\"+i+\"条数据，导入\" + sum + \"行数据，第\" + (i + 1) + \"行\"+ baseModel.getMessage()); \r\n");
		sb.append("\t\t\t\treturn baseModel; \r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\t\tcheckData(" + sTableName + "Save,baseModel);\r\n");
		sb.append("\t\t\tif (baseModel.getResultFlag() == 1) {// 如果类型转换失败\r\n");
		sb.append("\t\t\t\tbaseModel.setMessage(\"已验证\"+i+\"条数据，导入\" + sum + \"行数据，第\" + (i + 1) + \"行\"+ baseModel.getMessage()); \r\n");
		sb.append("\t\t\t\treturn baseModel; \r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\t\t" + sTableName + "Save = (" + bTableName
				+ ") baseModel.getData();// 获得设置的数据\r\n");
		sb.append("\t\t\tbaseDao.save(" + sTableName + "Save);\r\n");
		sb.append("\t\t\tsum++;\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\tbaseModel.setMessage(\"导入\"+sum+\"条数据成功\");\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 导出
		sb.append("\t@Override\r\n");
		sb.append("\tpublic BaseModel expData(" + bTableName + " " + sTableName
				+ ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tString hql = " + bTableName + "Hql.FINDDATA;\r\n");
		sb.append("\t\tList<" + bTableName + "> list = (List<" + bTableName
				+ ">)baseDao.findData(hql);\r\n");
		sb.append("\t\tUploaderFileUtil.download(ExcelUtil.downloadExcel(list, \""
				+ bTableName + ".xls\"),\"" + bTableName + ".xls\");\r\n");
		sb.append("\t\treturn baseModel;\r\n");
		sb.append("\t}\r\n\r\n");

		// 验证
		sb.append("\tpublic BaseModel checkData(" + bTableName + " "
				+ sTableName + ", BaseModel baseModel)throws Exception {\r\n");
		sb.append("\t\tString msg=\"\";\r\n");
		sb.append("\t\tif(msg.length()>0){\r\n");
		sb.append("\t\t\tbaseModel.setResultFlag(1);\r\n");
		sb.append("\t\t\tbaseModel.setMessage(msg);\r\n");
		sb.append("\t\t}\r\n");
		sb.append("\t\treturn baseModel;\r\n");
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
