package com.j2mvc.example.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.j2mvc.util.StringUtils;
/**
 * 截取字符串
 * @author 杨朔
 *
 */
public class SubTextTag  extends SimpleTagSupport {
	String source = "";// 源
	Integer length = 10;// 长度
	String postfix = "";// 后缀
	
	public void doTag() throws JspException, IOException {
		
		String result = StringUtils.subHtml(source, length);
		// 输出处理结果到页面上
		getJspContext().getOut().println(result+postfix);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

}
