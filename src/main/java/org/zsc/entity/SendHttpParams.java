package org.zsc.entity;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName SendHttpParams
 * @Description http请求-发�?类参�?
 * @author Enzo
 * @date 2016�?�?7�?
 */
public class SendHttpParams {
	private String url;// 请求地址
	private Map<String, String> mapParams;// 请求参数
	private Map<String, String> mapFilePath;// 请求文件参数地址
	private List<Map<String, File>> listFile;// 文件对象列表
	private Map<String, Map<String, String>> entityMapParams;// 按实体类划分的map参数
	private String sessionId;// 会话的sessionid

	public Map<String, Map<String, String>> getEntityMapParams() {
		return entityMapParams;
	}

	public void setEntityMapParams(Map<String, Map<String, String>> entityMapParams) {
		this.entityMapParams = entityMapParams;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public List<Map<String, File>> getListFile() {
		return listFile;
	}

	public void setListFile(List<Map<String, File>> listFile) {
		this.listFile = listFile;
	}

	private String gtParms;// get请求参数

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getMapParams() {
		return mapParams;
	}

	public void setMapParams(Map<String, String> mapParams) {
		this.mapParams = mapParams;
	}

	public String getGtParms() {
		return gtParms;
	}

	public void setGtParms(String gtParms) {
		this.gtParms = gtParms;
	}

	public Map<String, String> getMapFilePath() {
		return mapFilePath;
	}

	public void setMapFilePath(Map<String, String> mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

}
