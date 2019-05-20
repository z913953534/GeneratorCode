package org.zsc.dao;

import java.util.List;

import org.zsc.entity.DBEntity;

public interface JDBCDao {
	public List executeQuery(String sql, DBEntity entity) throws Exception;

	public int executeUpdate(String sql, DBEntity entity) throws Exception;
}
