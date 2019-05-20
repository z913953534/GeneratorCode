package org.zsc.model.ssh;

import java.io.File;

import org.zsc.entity.GeneratorParams;
import org.zsc.util.GenImportPackage;
import org.zsc.util.GeneratorUtil;

public class CodeSSHEntity {

	public static void GenerateModel(GeneratorParams params) throws Exception {

		String packagePath = params.getPackageEntity();
		String content = CodeSSHEntity.parseModel(params, packagePath);

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
	private static String parseModel(GeneratorParams params, String packagePath) {

		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ";\r\n\r\n"); // D:\Workspaces\BlogSource\BlogSource\tb\org/BlogSource/tb/model/TbArticle.java
		if ("1".equals(params.packageParams.get("date"))) {
			sb.append(GenImportPackage.iDate);
		}
		sb.append(GenImportPackage.iSerializable);
		sb.append(GenImportPackage.iColumn);
		sb.append(GenImportPackage.iEntity);
		sb.append(GenImportPackage.iId);
		sb.append(GenImportPackage.iTable);
		sb.append(GenImportPackage.iGeneratedValue);
		sb.append(GenImportPackage.iGenerationType);// int型id使用
		sb.append(GenImportPackage.iSequenceGenerator);// int型id使用
		sb.append(GenImportPackage.iGenericGenerator);// string型id使用
		sb.append(GenImportPackage.iScope);
		sb.append("\r\n");
		sb.append("@Entity\r\n");
		sb.append("@Table(name = \"" + params.getOldTableName() + "\")\r\n");
		sb.append("@Scope(value = \"prototype\")\r\n");
		sb.append("@SuppressWarnings(\"serial\")\r\n");
		sb.append("public class " + params.getTableName()
				+ " implements Serializable{\r\n\r\n");
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
	private static void processAllAttrs(GeneratorParams params, StringBuffer sb) {
		for (int i = 0; i < params.getColnames().size(); i++) {
			if ("id".equals(params.getColnames().get(i))
					|| "PRI".equals(params.getColKey().get(i))) {// 如果是主键id
				sb.append("\t@Id\r\n");
				if ("int".equals(sqlType2JavaType(params.getColTypes().get(i)))) {
					sb.delete(
							sb.indexOf(GenImportPackage.iGenericGenerator),
							sb.indexOf(GenImportPackage.iGenericGenerator)
									+ GenImportPackage.iGenericGenerator
											.length());
					sb.append(GenImportPackage.atiSequenceGenerator);
					sb.append(GenImportPackage.atiGeneratedValue);

				} else {
					if (sb.indexOf(GenImportPackage.iGenerationType) > -1) {
						sb.delete(
								sb.indexOf(GenImportPackage.iGenerationType),
								sb.indexOf(GenImportPackage.iGenerationType)
										+ GenImportPackage.iGenerationType
												.length());
						sb.delete(
								sb.indexOf(GenImportPackage.iSequenceGenerator),
								sb.indexOf(GenImportPackage.iSequenceGenerator)
										+ GenImportPackage.iSequenceGenerator
												.length());
						sb.append(GenImportPackage.atsGeneratedValue);
						sb.append(GenImportPackage.atsGenericGenerator);
					}

				}
			}
			sb.append("\t@Column(name = \"" + params.getOldColnames().get(i)
					+ "\")\r\n");
			sb.append("\t"
					+ sqlType2JavaType(params.getColTypes().get(i)) + " "
					+ params.getColnames().get(i) + ";//"
					+ params.getMemos().get(i) + "\r\n\r\n");
		}
	}

	/**
	 * 生成所有的get、set方法 （生成的model）
	 * 
	 * @param baseModel
	 * @param sb
	 */
	private static void processAllMethod(GeneratorParams params, StringBuffer sb) {
		for (int i = 0; i < params.getColnames().size(); i++) {
			String fieldName = params.getColnames().get(i);
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

	private static String sqlType2JavaType(String sqlType) {
		sqlType = sqlType.toLowerCase();
		String type = null;
		if (sqlType.equalsIgnoreCase("bit")) {
			type = "bool";
		} else if (sqlType.equals("tinyint")) {
			type = "byte";
		} else if (sqlType.equals("smallint")) {
			type = "short";
		} else if (sqlType.equals("int") || sqlType.equals("integer") || sqlType.equals("number")) {
			type = "int";
		} else if (sqlType.equals("bigint")) {
			type = "long";
		} else if (sqlType.equals("float")) {
			type = "float";
		} else if (sqlType.equals("decimal") || sqlType.equals("numeric")
				|| sqlType.equals("real") || sqlType.equals("money")
				|| sqlType.equals("smallmoney")) {
			type = "double";
		} else if (sqlType.equals("varchar") || sqlType.equals("nvarchar")
				|| sqlType.equals("varchar2") || sqlType.equals("nchar")
				|| sqlType.equals("nvarchar2") || sqlType.equals("text")
				|| sqlType.equals("clob")) {
			type = "String";
		} else if (sqlType.equals("datetime") || sqlType.equals("date")) {
			type = "Date";
		} else if (sqlType.equals("char")) {
			type = "char";
		} else if (sqlType.equals("image")) {
			type = "Blob";
		}
		return type;
	}
}
