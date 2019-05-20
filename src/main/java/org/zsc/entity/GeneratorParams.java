package org.zsc.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorParams {
	private String projectName;// 项目名称
	private String projectUrl;// 保存项目的路径
	private String oldTableName;// 数据库中的表名称
	private String tableName;// 表名称
	private String packageEntity;// 实体类
	private String packageAction;// action
	private String packageService;// 业务层包
	private String packageDao;
	private String packageSql;
	private String packageUtil;// 工具包
	private String packageCommon;// 工具包
	private List<Integer> colSizes; // 列名大小数组
	private List<String> colnames = new ArrayList<String>(); // 列名数组
	private List<String> colKey = new ArrayList<String>();// 主键
	private List<String> oldColnames = new ArrayList<String>();; // 原列名数组
	private List<String> colTypes = new ArrayList<String>();; // 列名类型数组，类型
	private List<String> memos = new ArrayList<String>();; // 列名类型数组,备注
	private int tableNameType;// 表名命名方式
	private int columnNameType;// 字段名命名方式
	private String genType;// 生成类型（SSH、SSM）
	private String encoding;// 编码

	public Map<String, String> packageParams = new HashMap<String, String>() {
		{
			put("date", "0");
		}
	};// 其他包参数

	public String getPackageCommon() {
		return packageCommon;
	}

	public void setPackageCommon(String packageCommon) {
		this.packageCommon = packageCommon;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getPackageDao() {
		return packageDao;
	}

	public void setPackageDao(String packageDao) {
		this.packageDao = packageDao;
	}

	public String getPackageSql() {
		return packageSql;
	}

	public void setPackageSql(String packageSql) {
		this.packageSql = packageSql;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getOldTableName() {
		return oldTableName;
	}

	public void setOldTableName(String oldTableName) {
		this.oldTableName = oldTableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPackageEntity() {
		return packageEntity;
	}

	public void setPackageEntity(String packageEntity) {
		this.packageEntity = packageEntity;
	}

	public String getPackageAction() {
		return packageAction;
	}

	public void setPackageAction(String packageAction) {
		this.packageAction = packageAction;
	}

	public String getPackageUtil() {
		return packageUtil;
	}

	public void setPackageUtil(String packageUtil) {
		this.packageUtil = packageUtil;
	}

	public String getPackageService() {
		return packageService;
	}

	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}

	public List<Integer> getColSizes() {
		return colSizes;
	}

	public void setColSizes(List<Integer> colSizes) {
		this.colSizes = colSizes;
	}

	public List<String> getColKey() {
		return colKey;
	}

	public void setColKey(List<String> colKey) {
		this.colKey = colKey;
	}

	public Map<String, String> getPackageParams() {
		return packageParams;
	}

	public void setPackageParams(Map<String, String> packageParams) {
		this.packageParams = packageParams;
	}

	public List<String> getColnames() {
		return colnames;
	}

	public void setColnames(List<String> colnames) {
		this.colnames = colnames;
	}

	public List<String> getOldColnames() {
		return oldColnames;
	}

	public void setOldColnames(List<String> oldColnames) {
		this.oldColnames = oldColnames;
	}

	public List<String> getColTypes() {
		return colTypes;
	}

	public void setColTypes(List<String> colTypes) {
		this.colTypes = colTypes;
	}

	public List<String> getMemos() {
		return memos;
	}

	public void setMemos(List<String> memos) {
		this.memos = memos;
	}

	public int getTableNameType() {
		return tableNameType;
	}

	public void setTableNameType(int tableNameType) {
		this.tableNameType = tableNameType;
	}

	public int getColumnNameType() {
		return columnNameType;
	}

	public void setColumnNameType(int columnNameType) {
		this.columnNameType = columnNameType;
	}

	public String getGenType() {
		return genType;
	}

	public void setGenType(String genType) {
		this.genType = genType;
	}

}
