package com.j2mvc.example.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JSP 文件工具类
 */
public  class JspUtil{
	HttpServletRequest request;
	HttpServletResponse response;
  
	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
	// 字符串在编译时会被转码一次,所以是 "\\b"
	// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
	String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
			+"|windows (phone|ce)|blackberry"
			+"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
			+"|laystation portable)|nokia|fennec|htc[-_]"
			+"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
			+"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
	 

	public JspUtil(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public boolean checkMobile(String userAgent){
		if(null == userAgent){
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if(matcherPhone.find() || matcherTable.find()){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 是否手机端访问
	 * @return boolean
	 */
	public boolean isMobile(){
		String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
		 
		if(null == userAgent){
			userAgent = "";
		}
		return checkMobile(userAgent);
		
	}
	/**
	 * 返回jsp页面
	 * @param jsp
	 * @return String
	 */
	public String jsp(String jsp){
		String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
		 
		if(null == userAgent){
			userAgent = "";
		}
		if(checkMobile(userAgent)){
			return "/WEB-INF/jsp/mobile/"+jsp;
		} else {
			return "/WEB-INF/jsp/web/"+jsp;
		}
	}
}
