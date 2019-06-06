package com.j2mvc.example.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.j2mvc.util.MD5;

/**
 * 字符串转换为MD5
 * @author 杨朔
 */
public class MD5Tag  extends SimpleTagSupport {
	String source = "";// 源
	
	public void doTag() throws JspException, IOException {
		
		// 输出处理结果到页面上
		getJspContext().getOut().println(MD5.md5(source));
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
