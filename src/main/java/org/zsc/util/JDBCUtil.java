package org.zsc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zsc.entity.DBEntity;

public class JDBCUtil {

	public static Connection openConnection(DBEntity entity) {
		Connection conn = null;
		try {
			Class.forName(entity.getDriver());// 加载驱动
			conn = DriverManager.getConnection(entity.getUrl(),
					entity.getUsername(), entity.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("数据库连接失败！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 
	 * @Description 关闭数据库连接
	 * @param rs
	 * @param ps
	 * @param conn
	 * @author Enzo
	 * @date 2016年6月7日
	 */
	public static void closeConnection(ResultSet rs, PreparedStatement ps,
			Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				System.err.println("关闭ResultSet失败");
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
				ps = null;
			} catch (SQLException e) {
				System.err.println("关闭PreparedStatement失败");
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				System.err.println("关闭Statement失败");
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				System.err.println("关闭Connection失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Description 将查询结果集存入map对象中
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static List convertList(ResultSet rs) throws SQLException {
		List list = new ArrayList();
		ResultSetMetaData md = rs.getMetaData();// 得到结果集(rs)的结构信息，比如字段数、字段名等
		int columnCount = md.getColumnCount();// 返回此 ResultSet 对象中的列数
		while (rs.next()) {
			Map rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				String name = md.getColumnName(i);
				if ("如果需要转换字符".indexOf("1") > 0) {
					String[] str = md.getColumnName(i).toLowerCase().split("_");
					name = str[0].trim();
					for (int j = 1; j < str.length; j++) {
						name += str[j].trim().substring(0, 1).toUpperCase()
								+ str[j].trim().substring(1);
					}
				}

				rowData.put(name, rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}

	/**
	 * 
	 * @Description 初始化设置url链接地址和驱动
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年10月7日
	 */
	public static DBEntity initUrl(DBEntity entity) throws Exception {
		entity.setDriver(entity.driverMap.get(entity.getDriverType()));
		if ("sqlserver".equals(entity.getDriverType())) {
			entity.setUrl("jdbc:sqlserver://" + entity.getIp() + ":"
					+ entity.getPort() + ";DatabaseName="
					+ entity.getDatabaseName());
		} else if ("oracle".equals(entity.getDriverType())) {
			entity.setUrl("jdbc:oracle:thin:@" + entity.getIp() + ":"
					+ entity.getPort() + ":" + entity.getDatabaseName());
		} else if ("mysql".equals(entity.getDriverType())) {
			entity.setUrl("jdbc:mysql://" + entity.getIp() + ":"
					+ entity.getPort() + "/" + entity.getDatabaseName()+"??characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
		}
		return entity;
	}
}
