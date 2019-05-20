package org.zsc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.PUT;

import org.zsc.entity.BaseModel;
import org.zsc.entity.SendHttpParams;

public class HttpRequestUtil {
	public static String CHARSET = "UTF-8";// 字符编码
	public static String UPLOADTEMP = "files/temp";// 上传文件临时目录
	public static String REALPATH = "";// 项目根目录

	/**
	 * 
	 * @Description 设置请求参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年8月27日
	 */
	public static SendHttpParams setSendParams(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding(CHARSET);
		response.setCharacterEncoding(CHARSET);
		REALPATH = request.getSession().getServletContext().getRealPath("/");
		SendHttpParams params = new SendHttpParams();
		// 获得cookie对象
		Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sessionId")) {// 获得sessionid
					params.setSessionId(cookie.getValue());
					break;
				}
			}
		}
		// 针对post请求，设置允许接收中文
		Map<String, String> mapParams = new HashMap<String, String>();
		Map<String, Map<String, String>> entityMapParams = new HashMap<String, Map<String, String>>();
		Map mapParms = request.getParameterMap();
		for (Iterator itr = mapParms.entrySet().iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			Object ok = me.getKey();
			Object ov = me.getValue();
			String[] val = new String[1];
			if (ov instanceof String[]) {
				val = (String[]) ov;
			} else {
				val[0] = ov.toString();
			}
			for (int k = 0; k < val.length; k++) {
				String key = ok.toString();
				String kv = val[k];
				if (kv != null && !"".equals(kv)) {
					if (mapParams.get(key) == null) {
						mapParams.put(key, kv);
					} else {
						mapParams.put(key, mapParams.get(ok) + "," + kv);
					}
					if (key.indexOf(".") > 0) {
						String entity = key.substring(0, key.lastIndexOf("."));
						String field = key.substring(key.lastIndexOf(".") + 1);
						if (entityMapParams.get(entity) == null) {// 如果不存在map对象
							Map<String, String> inMapParams = new HashMap<String, String>();
							inMapParams.put(field, kv);
							entityMapParams.put(entity, inMapParams);
						} else {
							entityMapParams.get(entity).put(field, kv);
						}
					}

				}
			}

		}

		params.setMapParams(mapParams);// 设置请求参数
		params.setEntityMapParams(entityMapParams);// 设置多实体类请求参数
		// 设置文件参数
		if (request.getContentType().split(";")[0]
				.equals("multipart/form-data")) {// 如果是文件上传协议,则遍历文件
			List<Map<String, File>> listFile = new ArrayList<Map<String, File>>();
			Map<String, String> mapFilepath = new HashMap<String, String>();
			Collection<Part> parts = request.getParts();
			for (Part part : parts) {
				Map<String, String> map = uploadFile(part);
				if (map.get("filePath") != null
						&& !"".equals(map.get("filePath"))) {// 如果存在上传的文件
					// String FilePath = FTPUtil.getFileServiceFolder();//
					// 获得文件上传地址
					String FilePath = REALPATH + "\\"
							+ UPLOADTEMP.replace("/", "\\");// 获得文件上传地址
					String fieldName = map.get("fieldName");// 获得文件字段
					File file = new File(FilePath + "/" + map.get("filePath"));// 获得上传的文件
					Map<String, File> mapFile = new HashMap<String, File>();// 设置文件map对象
					mapFile.put(fieldName, file);// 添加文件map对象
					listFile.add(mapFile);// 添加文件list对象
					if (mapFilepath.get(fieldName) != null
							&& !"".equals(mapFilepath.get(fieldName))) {// 如果如果该文件的name不是第一个（数组）
						mapFilepath.put(fieldName, mapFilepath.get(fieldName)
								+ "," + map.get("filePath"));
					} else {
						mapFilepath.put(fieldName, map.get("filePath"));
					}
				}
			}
			params.setMapFilePath(mapFilepath);
			params.setListFile(listFile);
		}

		return params;
	}

	/**
	 * 
	 * @Description 获得现在上传的文件信息
	 * @param part
	 * @return
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年8月27日
	 */
	public static Map<String, String> uploadFile(Part part) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String fieldName = part.getName();// 获得字段名
		map.put("fieldName", fieldName);
		String header = part.getHeader("Content-Disposition"); // 获得参数头信息
		String type = part.getHeader("Content-Type");// 获得参数类型（如果是文件则不为空）
		if (type != null && !"".equals(type) && header.split(" ").length > 2) {// 如果是文件则不为空
			String fileName = header.substring(
					header.indexOf("filename=\"") + 10,
					header.lastIndexOf("\""));// 获得文件名
			if (fileName == null || "".equals(fileName)) {// 如果文件为空
				return map;
			}
			// String FilePath = FTPUtil.getFileServiceFolder();// 获得文件上传地址
			String FilePath = REALPATH + "\\" + UPLOADTEMP.replace("/", "\\");// 获得文件上传地址
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String year = sdf.format(date);// 获得当前年份
			String time = date.getTime() + "";
			fileName = time
					+ "_"
					+ fileName.replace(" ", "").replace(",", "，")
							.replace(":", "：").replace("/", "")
							.replace("\\", "");
			InputStream is = part.getInputStream();
			FileOutputStream fos = null;
			try {
				File folder = new File(FilePath + "/micro/" + year);// 获得文件夹对象
				if (!folder.exists()) {// 如果文件夹不存在
					folder.mkdirs();// 创建文件夹
				}
				fos = new FileOutputStream(new File(folder.getPath() + "/"
						+ fileName));
				int b = 0;
				while ((b = is.read()) != -1) {
					fos.write(b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("filePath", "temp/" + year + "/" + fileName);
		}
		return map;
	}

}
