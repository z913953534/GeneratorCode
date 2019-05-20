package org.zsc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zsc.entity.DBEntity;
import org.zsc.entity.GeneratorParams;
import org.zsc.model.html.CodeHtml;
import org.zsc.model.js.CodeJs;
import org.zsc.model.sh.CodeSHController;
import org.zsc.model.ssh.CodeSSHAction;
import org.zsc.model.ssh.CodeSSHEntity;
import org.zsc.model.ssh.CodeSSHHql;
import org.zsc.model.ssh.CodeSSHService;
import org.zsc.model.ssh.CodeSSHServiceImpl;
import org.zsc.model.ssm.CodeSSMController;
import org.zsc.model.ssm.CodeSSMEntity;
import org.zsc.model.ssm.CodeSSMService;
import org.zsc.model.ssm.CodeSSMServiceImpl;
import org.zsc.model.ssm.CodeSSMxml;

import com.mysql.jdbc.DatabaseMetaData;

public class GeneratorUtil {

	/**
	 * 
	 * @Description 生成ssh框架代码
	 * @param params
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static void genCodeSSH(GeneratorParams params) throws Exception {
		CodeSSHEntity.GenerateModel(params);
		CodeSSHAction.GenerateAction(params);
		CodeSSHService.GenerateService(params);
		CodeSSHServiceImpl.GenerateServiceImpl(params);
		CodeSSHHql.GenerateHql(params);
		
	}
	public static void genCodeHTML(GeneratorParams params) throws Exception {
		CodeJs.GenerateJS(params);
		CodeHtml.GenerateHtml(params);
	}

	public static void genCodeSSM(GeneratorParams params) throws Exception {
		CodeSSMEntity.GenerateModel(params);
		CodeSSMService.GenerateService(params);
		CodeSSMServiceImpl.GenerateServiceImpl(params);
		CodeSSMController.GenerateController(params);
		CodeSSMxml.GenerateModel(params);
	}
	public static void genCodeSH(GeneratorParams params) throws Exception {
		CodeSSHEntity.GenerateModel(params);
		CodeSHController.GenerateController(params);
		CodeSSHService.GenerateService(params);
		CodeSSHServiceImpl.GenerateServiceImpl(params);
		CodeSSHHql.GenerateHql(params);
		CodeJs.GenerateJS(params);
		CodeHtml.GenerateHtml(params);
	}

	/**
	 * 
	 * @Description 根据选择的表信息，获得生成class的数据源
	 * @param params
	 * @param dbEntity
	 * @return
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static GeneratorParams getGeneratorParamsItem(
			GeneratorParams params, DBEntity dbEntity) throws Exception {
		Connection conn = null;
		DatabaseMetaData rsmd = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;// 用于在已经建立数据库连接的基础上，向数据库发送要执行的SQL语句。用于执行不带参数的简单SQL语句。
		Statement sqlstmt = null;
		String strsql = dbEntity.findTableInfoByTableNameSql.get(
				dbEntity.getDriverType()).replace("tableName_from",
				params.getOldTableName()).replace("{database}", dbEntity.getDatabaseName());
		int f_util = 0;// 是否需要导入util.Date包
		try {
			conn = JDBCUtil.openConnection(dbEntity); // 打开数据库连接
			pstmt = conn.prepareStatement(strsql);

			if ("mysql".equals(dbEntity.getDriverType())) {// mysql
				/*
				 * rsmd = (DatabaseMetaData) conn.getMetaData(); resultSet =
				 * rsmd.getColumns(null, "%", params.getOldTableName(), "%");
				 */
				sqlstmt = conn.createStatement();
				resultSet = sqlstmt.executeQuery(strsql);
			} else if ("oracle".equals(dbEntity.getDriverType())) {// orcl
				resultSet = pstmt.executeQuery(strsql);
			} else if ("sqlserver".equals(dbEntity.getDriverType())) {// sqlserver
				sqlstmt = conn.createStatement();
				resultSet = sqlstmt.executeQuery(strsql);// 读取数据
			}

			List<String> colnames = new ArrayList<String>(); // 列名数组
			List<String> oldColnames = new ArrayList<String>();// 原列名数组
			List<String> colTypes = new ArrayList<String>();// 列名类型数组
			List<String> memos = new ArrayList<String>();// 列名类型数组
			List<String> keys = new ArrayList<String>();// 主键
			List<Integer> colSizes = new ArrayList<Integer>(); // 列名大小数组
			while (resultSet.next()) {
				String fieldName = resultSet.getString("COLUMN_NAME");
				if (params.getColumnNameType() == 1) {//全部转小写，遇下划线转大写
					fieldName = getCamelStr(resultSet.getString("COLUMN_NAME"));
				}else if (params.getColumnNameType() == 2) {//全部小写
					fieldName=fieldName.toLowerCase();
				}
				colnames.add(fieldName);// id,title,content,careatetime,praisenum
				oldColnames.add(resultSet.getString("COLUMN_NAME"));// id,title,content,createTime,praiseNum
				if ("mysql".equals(dbEntity.getDriverType())) {// mysql
					// colTypes.add(resultSet.getString("TYPE_NAME"));//
					// varchar,varchar,varchar,datetime,int
					colTypes.add(resultSet.getString("DATA_TYPE"));// varchar,varchar,varchar,datetime,int
					// memos.add(resultSet.getString("REMARKS"));//
					// 编号,标题,内容,创建时间,顶
					memos.add(resultSet.getString("COLUMN_COMMENT"));
					keys.add(resultSet.getString("COLUMN_KEY"));
					String size = resultSet
							.getString("CHARACTER_MAXIMUm_LENGTH");
					if (size != null) {
						colSizes.add(Integer.parseInt(size));
					} else {
						colSizes.add(0);
					}

					if (resultSet.getString("DATA_TYPE").equalsIgnoreCase(
							"datetime")) {
						f_util = 1;
					}
					if (resultSet.getString("DATA_TYPE").equalsIgnoreCase(
							"image")
							|| resultSet.getString("DATA_TYPE")
									.equalsIgnoreCase("text")) {
					}

				} else if ("oracle".equals(dbEntity.getDriverType())) {// orcl
					colTypes.add(resultSet.getString("DATA_TYPE"));
					memos.add(resultSet.getString("COMMENTS"));
					keys.add("");
					if (resultSet.getString("DATA_TYPE").equalsIgnoreCase(
							"date")) {
						f_util = 1;
					}
					if (resultSet.getString("DATA_TYPE").equalsIgnoreCase(
							"image")
							|| resultSet.getString("DATA_TYPE")
									.equalsIgnoreCase("text")) {
					}
				} else if ("sqlserver".equals(dbEntity.getDriverType())) {// sqlserver
					colTypes.add(resultSet.getString("TYPE_NAME"));// varchar,varchar,varchar,datetime,int
					memos.add(resultSet.getString("REMARKS"));// 编号,标题,内容,创建时间,顶
					keys.add("");
					if (resultSet.getString("TYPE_NAME").equalsIgnoreCase(
							"datetime")) {
						f_util = 1;
					}
					if (resultSet.getString("TYPE_NAME").equalsIgnoreCase(
							"image")
							|| resultSet.getString("TYPE_NAME")
									.equalsIgnoreCase("text")) {
					}
				}
			}
			params.setColnames(colnames);
			params.setOldColnames(oldColnames);
			params.setColTypes(colTypes);
			params.setColSizes(colSizes);
			params.setMemos(memos);
			params.setColKey(keys);
			params.packageParams.put("date", f_util + "");
			initEntityName(params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(resultSet, pstmt, sqlstmt, conn);
		}
		return params;
	}

	/**
	 * 
	 * @Description 将首写字母转大写
	 * @param str
	 * @return
	 * @author Enzo
	 * @date 2015-5-7
	 */
	public static String indexLowerCase(String str) {
		str = str.replaceFirst(str.substring(0, 1), str.substring(0, 1)
				.toLowerCase());
		return str;
	}

	/**
	 * 
	 * @Description 遇下划线去下划线改大写（例：user_name --> userName）
	 * @param s
	 * @return
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static String getCamelStr(String s) {
		String[] values = s.split("_");
		String re = "";
		String value = "";
		re = values[0].toLowerCase();
		for (int i = 1; i < values.length; i++) {
			value = values[i];
			re += value.substring(0, 1).toUpperCase()
					+ value.substring(1, value.length()).toLowerCase();
		}
		return re;
	}

	/**
	 * 
	 * @Description 首字母大写，遇下划线去下划线改大写
	 * @param s
	 * @return
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static String initcap(String s) {
		String[] values = s.split("_");
		String re = "";
		String value = "";
		for (int i = 0; i < values.length; i++) {
			value = values[i].toLowerCase();
			re += value.substring(0, 1).toUpperCase()
					+ value.substring(1, value.length()).toLowerCase();
		}
		return re;
	}

	/**
	 * 
	 * @Description 写文件
	 * @param file
	 * @param content
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static void writeStringToFile(File file, String content,GeneratorParams params)
			throws Exception {
		// File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos =null;
		OutputStreamWriter pw = null;//定义一个流
		try {
			fos= new FileOutputStream(file);		
			pw = new OutputStreamWriter(fos,params.getEncoding());
			pw.write(content);//将要写入文件的内容，可以多次write			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();//关闭流
			}
			if(fos!=null){
				fos.close();
			}
		}
	}
	
	/**
	 * 
	 * @Description 根据实体类命名规则，设置实体类类名
	 * @param params
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static void initEntityName(GeneratorParams params) {
		int sn = 0;// 截取下划线前缀数
		if (params.getTableNameType() == 0 || params.getTableNameType() == 1) {
			sn = 0;
		} else if (params.getTableNameType() == 2
				|| params.getTableNameType() == 3) {
			sn = 1;
		} else if (params.getTableNameType() == 4) {
			sn = 2;
		}
		String OldTableName[] = params.getOldTableName().split("_");
		String newTableName = "";// 设置实体类名
		for (int i = sn; i < OldTableName.length; i++) {// 截取表前缀
			newTableName += OldTableName[i];
			if (i != OldTableName.length - 1) {
				newTableName += "_";
			}
		}
		if (params.getTableNameType() == 1 || params.getTableNameType() == 3) {// 如果全要转小写，遇下划线转大写
			newTableName = initcap(newTableName);
		}
		newTableName = newTableName.substring(0, 1).toUpperCase()
				+ newTableName.substring(1);
		params.setTableName(newTableName);

	}
}
