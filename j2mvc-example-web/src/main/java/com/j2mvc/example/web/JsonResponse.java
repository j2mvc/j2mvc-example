package com.j2mvc.example.web;

import java.util.Map;

public class JsonResponse {

	public final static String SUCCESS = "1";
	public final static String ERROR = "0";

	public final static String MESSAGE_OFFLINE = "用户未登录";
	public final static String MESSAGE_SUCCESS = "获取数据完成";
	public final static String MESSAGE_NODATA = "未找到数据";
	public final static String MESSAGE_ERR_PARAM = "请求参数错误";
	public final static String MESSAGE_OK = "您的请求已提交完成";
	public final static String MESSAGE_FAIL = "抱歉，您的请求提交失败";
	public final static String MESSAGE_POST_SUCCESS = "提交数据完成";
	

	private String code;
	
	private String message;
	
	private Map<String,?> result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, ?> getResult() {
		return result;
	}

	public void setResult(Map<String, ?> result) {
		this.result = result;
	}
}
