package org.zsc.entity;

import java.util.HashMap;
import java.util.Map;

public class DBEntity {
	private String driver;// 驱动
	private String ip;// ip地址ַ
	private String port;// 端口
	private String databaseName;// 数据库名称
	private String username;// 用户名
	private String password;// 密码
	private String url;// 链接地址
	private String driverType;// 驱动类型ַ
	@SuppressWarnings("serial")
	public static Map<String, String> driverMap = new HashMap<String, String>() {
		{
			put("oracle", "oracle.jdbc.OracleDriver");
			put("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			put("mysql", "com.mysql.jdbc.Driver");
		}
	};
	/**
	 * 查询数据库表名sql
	 */
	public static Map<String, String> findTableSql = new HashMap<String, String>() {
		{
			put("oracle", "SELECT TABLE_NAME FROM USER_TABLES");
			put("mysql", "show tables");
			put("sqlserver",
					"select name from sysobjects where xtype='u' order by name asc");

		}
	};
	/**
	 * 查询数据字段表详细信息
	 */
	public static Map<String, String> findTableInfoByTableNameSql = new HashMap<String, String>() {
		{
			put("oracle",
					"SELECT * FROM USER_TAB_COLUMNS INNER JOIN USER_COL_COMMENTS ON USER_TAB_COLUMNS.TABLE_NAME=USER_COL_COMMENTS.TABLE_NAME AND USER_TAB_COLUMNS.COLUMN_NAME=USER_COL_COMMENTS.COLUMN_NAME WHERE USER_TAB_COLUMNS.TABLE_NAME='tableName_from'");
			put("mysql",
					"SELECT COLUMN_NAME,DATA_TYPE,CHARACTER_MAXIMUm_LENGTH,COLUMN_KEY,COLUMN_COMMENT FROM INFORMATION_SCHEMA.Columns WHERE TABLE_NAME='tableName_from' and table_schema='{database}'");
			put("sqlserver",
					"SELECT a.name COLUMN_NAME,b.name TYPE_NAME,cast(isnull(g.value,'') as varchar(500)) REMARKS FROM dbo.syscolumns a left join dbo.systypes b on a.xusertype=b.xusertype inner join dbo.sysobjects d on a.id=d.id and d.xtype='U' and d.name<>'dtproperties' left join sys.extended_properties g on a.id=g.major_id and a.colid=g.minor_id where d.name ='tableName_from' order by COLUMN_NAME asc");
			put("oracle_key",
					"select col.column_name , uc.constraint_type from user_tab_columns col left join user_cons_columns ucc on ucc.table_name=col.table_name and ucc.column_name=col.column_name left join user_constraints uc on uc.constraint_name = ucc.constraint_name and uc.constraint_type='P' where col.table_name = 'tableName_from'");
		}
	};

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
