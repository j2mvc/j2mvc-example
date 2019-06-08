package com.j2mvc.example.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import com.j2mvc.util.Utils;

/**
 * 格式化日期时间
 */
public class DateFormatTag extends SimpleTagSupport {
	
	Logger log = Logger.getLogger(getClass());

	/** 源 */
	private String source = ""; 
	/** 日期时间格式 */
	private String format = "yyyy-MM-dd";
	private Boolean force = false;  
	
	
	public void doTag() throws JspException, IOException {
		String out = Utils.formatDate(source, format,force);
		// 输出处理结果到页面上
		getJspContext().getOut().println(out);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

}
