package com.j2mvc.example.web.action;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import com.j2mvc.example.web.Cache;
import com.j2mvc.example.web.entity.User;
import com.j2mvc.example.web.service.CartService;
import com.j2mvc.example.web.service.ProductService;
import com.j2mvc.example.web.service.UserService;
import com.j2mvc.example.web.util.JspUtil;
import com.j2mvc.framework.action.Action;
import com.j2mvc.framework.i18n.I18n;
import com.j2mvc.util.CookieUtil;
import com.j2mvc.util.IdentifyCode;
import com.j2mvc.util.StringUtils;
import com.j2mvc.util.Success;
import com.j2mvc.util.mapping.ActionPath;
import com.j2mvc.util.mapping.ActionUri;

/**
 * 网址：http://www.j2mvc.com
 * @author 杨朔
 * Action基础
 */
@ActionPath(path="/")
public class BaseAction extends Action{
	protected Logger log = Logger.getLogger(getClass().getCanonicalName());
	/** 内容分页每页条数 */
	protected final Integer PAGESIZE = I18n.i18n.get("PAGESIZE")!=null &&  I18n.i18n.get("PAGESIZE").matches("\\d+")?Integer.valueOf(I18n.i18n.get("PAGESIZE")):20;
	/** 站点名称  */
	protected final String SITENAME = I18n.i18n.get("SITENAME");

	/** 默认域名 */
	protected String DOMAIN = I18n.i18n.get("DOMAIN");

	protected String userParamName = "user";
	protected String tokenParamName = "token";
	protected User user;

	protected UserService userService = new UserService();
	protected ProductService productService = new ProductService();
	protected CartService cartService = new CartService();
	
	JspUtil jspUtil = new JspUtil(request,response);

	@Override
	public String onStart() {
		String serverName = request.getServerName();
		if(serverName.equals(DOMAIN)){
			// 301重定向//
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);   
			response.setHeader("Location","http://www."+DOMAIN);
			return null;
		}else if(!serverName.equals("www."+DOMAIN)){
		}
		return null;
	}

	/**
	 * 首页 
	 * @return String
	 */
	@ActionUri(uri="([/])?")
	public String index(){
		put("title",SITENAME+"首页");
		put("keywords","J2mvc,web,示例");
		put("description","这是一个J2mvcWeb示例网站");
		return jspUtil.jsp("index.jsp");
	}

	@ActionUri(uri="/login([/])?",description="登录界面")
	public String login(){
		return jspUtil.jsp("login.jsp");
	}

	/**
	 * 登陆
	 * @param username 用户名
	 * @param password 密码
	 */
	@ActionUri(uri="/login([/])?",query="method=submit",description="提交登录")
	public void loginSubmit(String username,String password,String code){
		if(!identifyCode(code)){
			return;
		}
		User user = userService.login(username, password,DOMAIN);
		if(user!=null){
			session.setAttribute(userParamName, user);	
			session.setAttribute(tokenParamName, user.getToken());	
	 		response.addCookie(CookieUtil.addCookie(tokenParamName,user.getToken(),  null,"",""));
			printJson(new Success("登陆成功."));
		}else{
			printJson(new Error("用户名或密码不正确."));
		}
	}

	/**
	 * 退出
	 */
	@ActionUri(uri="/logout([/])?",description="退出")
	public void logout(){
		User user = (User)session.getAttribute(userParamName);
		if(user!=null){
			userService.clearToken(user.getId());
		}
		CookieUtil.reMoveCookie(request, response, tokenParamName, "/", "");
		session.removeAttribute(tokenParamName);
		session.removeAttribute(userParamName);
		try {
			response.sendRedirect(path+"/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ActionUri(uri = "/identify-code",description="验证码是否正确")
	public boolean identifyCode(String code) {
		if (StringUtils.isEmpty(code)) {
			printJson(new Error("未输入验证码"));
			return false;
		} else if (Cache.identifyCode.equalsIgnoreCase(code)) {
			printJson(new Success("验证码正确"));
			return true;
		} else{
			printJson(new Error("验证码错误"));
			return false;
		}
	}

	@ActionUri(uri = "/show-identify-code",description="显示验证码图片")
	public void showIdentifyCode(){
		String lengthStr = I18n.i18n.get("IDENTIFY_CODE_LENGTH");
		int length = 0;
		if(lengthStr!=null && lengthStr.matches("\\d+")){
			length = Integer.valueOf(lengthStr);
		}
		IdentifyCode ic = null;
		if(length > 0){
			ic = new IdentifyCode(length);
		}else{
			ic = new IdentifyCode();
		}
		String code = ic.CreateIdentifyCode();
		Cache.identifyCode = code;
		ic.CreateImageOnPage(code, response);
	}
}
