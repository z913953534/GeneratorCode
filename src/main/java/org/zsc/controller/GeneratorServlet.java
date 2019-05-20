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
import org.zsc.entity.GeneratorParams;
import org.zsc.entity.SendHttpParams;
import org.zsc.util.BaseUtil;
import org.zsc.util.GeneratorUtil;
import org.zsc.util.HttpRequestUtil;
import org.zsc.util.JDBCUtil;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class GeneratorServlet
 */
@WebServlet(urlPatterns = { "/GeneratorServlet/generatorCode" })
public class GeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeneratorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// Statement sqlstmt = null;
		// ResultSet rs = null;
		GeneratorParams genParams = new GeneratorParams();
		try {
			SendHttpParams params = HttpRequestUtil.setSendParams(request,
					response);// 获得请求参数
			BaseUtil.reflect(params.getMapParams(), genParams, baseModel);// 利用反射机制设置请求参数
			genParams = (GeneratorParams) baseModel.getData();
			BaseUtil.reflect(params.getEntityMapParams().get("dbEntity"),
					entity, baseModel);// 利用反射机制设置请求参数
			entity = (DBEntity) baseModel.getData();// 获得数据库连接信息
			if (genParams.getTableName() != null
					&& !"".equals(genParams.getTableName())) {
				JDBCUtil.initUrl(entity);// 根据数据库基本信息设置连接信息
				String[] tableNames = genParams.getTableName().split(",");
				String genType = genParams.getGenType();
				if ("SSH".equals(genType)) {
					for (int i = 0; i < tableNames.length; i++) {
						genParams.setOldTableName(tableNames[i].trim());
						GeneratorUtil.getGeneratorParamsItem(genParams, entity);
						GeneratorUtil.genCodeSSH(genParams);
					}
					baseModel.setMessage("生成SSH文件成功");
				} else if ("SSM".equals(genType)) {
					for (int i = 0; i < tableNames.length; i++) {
						genParams.setOldTableName(tableNames[i].trim());
						GeneratorUtil.getGeneratorParamsItem(genParams, entity);
						GeneratorUtil.genCodeSSM(genParams);
					}
					baseModel.setMessage("生成SSM文件成功");
				}else if ("SH".equals(genType)) {
					for (int i = 0; i < tableNames.length; i++) {
						genParams.setOldTableName(tableNames[i].trim());
						GeneratorUtil.getGeneratorParamsItem(genParams, entity);
						GeneratorUtil.genCodeSH(genParams);
					}
					baseModel.setMessage("生成SpringMVC+Hibernate文件成功");
				}else if ("HTML".equals(genType)) {
					for (int i = 0; i < tableNames.length; i++) {
						genParams.setOldTableName(tableNames[i].trim());
						GeneratorUtil.getGeneratorParamsItem(genParams, entity);
						GeneratorUtil.genCodeHTML(genParams);
					}
					baseModel.setMessage("生成HTML+JS文件成功");
				}

			} else {
				baseModel.setResultFlag(1);
				baseModel.setMessage("请至少选择一张表生成数据");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBCUtil.closeConnection(rs, pstmt, sqlstmt, conn);
		}
		mapResult.put("baseModel", baseModel);
		result = JSON.toJSONString(mapResult);
		// 得到响应流对象
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}

}
