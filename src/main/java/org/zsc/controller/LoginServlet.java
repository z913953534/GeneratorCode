package org.zsc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zsc.entity.BaseModel;
import org.zsc.entity.DBEntity;
import org.zsc.entity.SendHttpParams;
import org.zsc.util.BaseUtil;
import org.zsc.util.HttpRequestUtil;
import org.zsc.util.JDBCUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/LoginServlet/login" })
public class LoginServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BaseModel baseModel = new BaseModel();
		Map<String, Object> mapResult = new HashMap<String, Object>();
		DBEntity entity = new DBEntity();// 创建数据库连接信息对象
		String result = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement sqlstmt = null;
		ResultSet rs = null;
		try {
			SendHttpParams params = HttpRequestUtil.setSendParams(request,
					response);// 获得请求参数
			BaseUtil.reflect(params.getMapParams(), entity, baseModel);// 利用反射机制设置请求参数
			entity = (DBEntity) baseModel.getData();// 将请求参数保存到实体类中
			JDBCUtil.initUrl(entity);// 根据数据库基本信息设置连接信息
			conn = JDBCUtil.openConnection(entity); // 打开数据库连接
			String findTableSql = DBEntity.findTableSql.get(entity
					.getDriverType());// 获得查询数据库表信息sql语句
			if (!findTableSql.equals("")) {
				if ("sqlserver".equals(entity.getDriverType())) {
					sqlstmt = conn.createStatement();
					rs = sqlstmt.executeQuery(findTableSql);// 读取数据
				} else {
					pstmt = conn.prepareStatement(findTableSql);
					rs = pstmt.executeQuery(findTableSql);// 读取数据
				}
				String tableNames = "";
				while (rs.next()) {// 遍历所有的查询结果
					tableNames += rs.getString(1) + ",";// 获得所有表名
				}
				if (tableNames.length() > 0) {
					tableNames = tableNames.substring(0,
							tableNames.length() - 1);
				}
				baseModel.setData(tableNames);
			}
		} catch (Exception e) {
			baseModel.setResultFlag(1);
			baseModel.setMessage("数据库连接异常");
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(rs, pstmt, sqlstmt, conn);
		}
		mapResult.put("baseModel", baseModel);
		result = JSON.toJSONString(mapResult);
		// 得到响应流对象
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

}
