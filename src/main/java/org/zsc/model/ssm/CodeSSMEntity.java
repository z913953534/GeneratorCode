package org.zsc.model.ssm;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSMEntity {

	public static void GenerateModel(GeneratorParams params) throws Exception {

		String packagePath = params.getPackageEntity();
		String content = CodeSSMEntity.parseModel(params, packagePath);

		String path = params.getProjectUrl() + "/" + params.getProjectName()
				+ "/" + packagePath.replaceAll("\\.", "/");

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String resPath = path + "/" + params.getTableName() + ".java";// D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		//System.out.println(resPath);
		GeneratorUtil.writeStringToFile(new File(resPath), content, params);
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 * 
	 * @param baseModel
	 * @param packagePath
	 * @return
	 */
	static String parseModel(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		if ("1".equals(params.packageParams.get("date"))) {
			sb.append(GenImportPackage.iDate);
		}
//		sb.append(GenImportPackage.iApiModelProperty);
//		sb.append(GenImportPackage.iApiModel);
		sb.append(GenImportPackage.iSerializable);
//		sb.append(GenImportPackage.iModelDao);
//		sb.append(GenImportPackage.iModelBind);
//		sb.append(GenImportPackage.iModelPk);
		sb.append("\r\n");
		
		sb.append("//@ApiModel(value = \""+(params.getTableName().charAt(0)+"").toLowerCase()+params.getTableName().substring(1)+"\")\r\n");
//		sb.append("@ModelBind(table = \""+params.getOldTableName()+"\")\r\n");
		
//		sb.append("public class " + params.getTableName() + " extends ModelDao<"+params.getTableName()+">{\r\n");
		sb.append("public class " + params.getTableName() + "  implements Serializable {\r\n");

		//sb.append("\tpublic final static "+params.getTableName()+" dao = new "+params.getTableName()+"();\r\n\r\n");
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
		for (int i = 0; i < params.getColnames().size(); i++) {
			if(params.getColKey()!=null&&params.getColKey().get(i)!=null&&params.getColKey().get(i).indexOf("PRI")>-1){//mysql主键字段
//				sb.append("\t@ModelPk"+ "\r\n");
			}
//			sb.append("\t@ApiModelProperty(value = \""+params.getMemos().get(i)+"\")"+ "\r\n");
			sb.append("\t/**\r\n");
			sb.append("\t *"+params.getMemos().get(i)+""+ "\r\n");
			sb.append("\t */\r\n");
			sb.append("\tprivate "
					+ sqlType2JavaType(params.getColTypes().get(i)) + " "
					+ params.getColnames().get(i) + ";" +"\r\n\r\n");
		}
	}

	/**
	 * 生成所有的get、set方法 （生成的model）
	 * 
	 * @param baseModel
	 * @param sb
	 */
	static void processAllMethod(GeneratorParams params, StringBuffer sb) {
		for (int i = 0; i < params.getColnames().size(); i++) {
			String fieldName = params.getOldColnames().get(i);
			fieldName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1, fieldName.length());// 首字母转大写
			sb.append("\tpublic void set" + fieldName + "("
					+ sqlType2JavaType(params.getColTypes().get(i)) + " "
					+ params.getColnames().get(i) + "){\r\n");
			sb.append("\t\tthis." + params.getColnames().get(i) + "="
					+ params.getColnames().get(i) + ";\r\n");
			sb.append("\t}\r\n\r\n");

			sb.append("\tpublic "
					+ sqlType2JavaType(params.getColTypes().get(i)) + " get"
					+ fieldName + "(){\r\n");
			sb.append("\t\treturn " + params.getColnames().get(i) + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
	}

	static String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Short";
		} else if (sqlType.equalsIgnoreCase("int")
				|| sqlType.equalsIgnoreCase("integer")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")
				|| sqlType.equalsIgnoreCase("number")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("varchar2")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("nvarchar2")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")
				|| sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("char")) {
			return "char";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		}
		return null;
	}
}
