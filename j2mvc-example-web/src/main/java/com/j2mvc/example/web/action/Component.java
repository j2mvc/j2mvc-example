package com.j2mvc.example.web.action;

import com.j2mvc.framework.mapping.ActionPath;
import com.j2mvc.framework.mapping.ActionUri;

@ActionPath(path="/component/",dir="/WEB-INF/jsp/component/")
public class Component  extends BaseAction{

	/**
	 * 首页 
	 * @return String
	 */
	@ActionUri(uri="dialog")
	public String dialog(){
		return "dialog.jsp";
	}
}
