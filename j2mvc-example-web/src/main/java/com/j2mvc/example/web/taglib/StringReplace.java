package com.j2mvc.example.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 字符串替换
 * @author 杨朔
 */
public class StringReplace extends SimpleTagSupport {

	String source = ""; 
	String regex = "";
	String replacement = "";

	
	public void doTag() throws JspException, IOException {
		// 输出处理结果到页面上
		getJspContext().getOut().println(source.replaceAll(regex, replacement));
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getRegex() {
		return regex;
	}


	public void setRegex(String regex) {
		this.regex = regex;
	}


	public String getReplacement() {
		return replacement;
	}


	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}


}
