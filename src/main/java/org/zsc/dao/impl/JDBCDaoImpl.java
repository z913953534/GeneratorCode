package org.zsc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.zsc.dao.JDBCDao;
import org.zsc.entity.DBEntity;
import org.zsc.util.JDBCUtil;

public class JDBCDaoImpl implements JDBCDao {

	@Override
	public List executeQuery(String sql, DBEntity entity) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = JDBCUtil.openConnection(entity);// 打开数据库
			pstmt = conn.prepareStatement(sql);// 执行查询语句
			rs = pstmt.executeQuery();// 获得返回结果集
			list = JDBCUtil.convertList(rs);// 将结果集存入list对象
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtil.closeConnection(rs, pstmt, null, conn);// 关闭数据库连接
		}
		return list;
	}

	@Override
	public int executeUpdate(String sql, DBEntity entity) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		int count = 0;
		try {
			conn = JDBCUtil.openConnection(entity);// 打开数据库
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtil.closeConnection(null, null, stmt, conn);// 关闭数据库连接
		}
		return count;
	}

}
