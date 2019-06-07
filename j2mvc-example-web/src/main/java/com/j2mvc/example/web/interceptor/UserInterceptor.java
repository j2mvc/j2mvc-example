package com.j2mvc.example.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.j2mvc.example.web.entity.User;
import com.j2mvc.example.web.service.UserService;
import com.j2mvc.framework.interceptor.MeasureInterceptor;
import com.j2mvc.util.CookieUtil;
import com.j2mvc.util.StringUtils;

/**
 * 
 * 用户拦截器
 * 这是一个简单的权限控制，若有更复杂的需求，请参考j2mvc-authorization
 * 您也可继承此拦截器开发更复杂的权限系统
 * Site j2mvc.com
 */
public class UserInterceptor extends MeasureInterceptor{	
	  
	Logger log = Logger.getLogger(UserInterceptor.class.getCanonicalName());

	String userParamName = "user";
	String tokenParamName = "token";
	String[] auths = new String[]{
			"/my([/])?",
			"/cart/add([/])?",
			"/cart/update([/])?",
			"/cart/del([/])?",
			"/product/save([/])?",
			"/product/del([/])?"};
	
	UserService userService = new UserService();
	
	/**
	 * 需要权限的路径
	 * @param uri
	 * @return boolean
	 */
	private boolean auth(String uri){
		for(String auth:auths){
			if(uri.matches(auth)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean execute(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(userParamName);
		String token = (String) session.getAttribute(tokenParamName);
		// Session无用户，从cookie获取到token，再从数据库查询出用户
		if(user == null){
			// 如果会话用户为空，从cookie中获取token
			token = token !=null?token: CookieUtil.getCookieValue(request, "token");
			if(!StringUtils.isEmpty(token)){
				user = userService.getByToken(token);
			} 
		}
		if(user !=null){
			// 用户真实，保存到session
			session.setAttribute(tokenParamName, token);	
			session.setAttribute(userParamName, user);	
		}
		String uri = request.getRequestURI();
		if(auth(uri) && user == null){
			log.info("当前Uri无权限访问=>"+uri);
			// 用户未登录
			return false;
		}else{
			return true;
		}
	}
 
	@Override
	public void error(HttpServletRequest request,HttpServletResponse response){
		// 在此实现除登陆权限以外的其他操作
		try {
			response.sendRedirect(request.getContextPath()+"/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void success(HttpServletRequest arg0, HttpServletResponse arg1) {
		
	}
}