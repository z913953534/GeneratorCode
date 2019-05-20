package org.zsc.entity;

public class BaseModel {
	private int resultFlag;
	private String message;
	private Object data;
	private String condition;

	public int getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(int resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
