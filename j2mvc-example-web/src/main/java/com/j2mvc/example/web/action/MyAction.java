package com.j2mvc.example.web.action;

import com.j2mvc.framework.mapping.ActionPath;
import com.j2mvc.framework.mapping.ActionUri;
/**
 * 网址：http://www.j2mvc.com
 * @author 杨朔
 * 购物车Action
 */
@ActionPath(path="/my/")
public class MyAction extends BaseAction{
	/**
	 * 用户首页 
	 * @return String
	 */
	@ActionUri(uri="([/])?")
	public String index(){
		put("title",SITENAME+"首页");
		put("keywords","J2mvc,web,示例");
		put("description","这是一个J2mvcWeb示例网站");
		return jspUtil.jsp("my/index.jsp");
	}

}
