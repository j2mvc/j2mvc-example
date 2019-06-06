package com.j2mvc.example.web.service;

import com.j2mvc.example.web.entity.User;
import com.j2mvc.framework.dao.DaoSupport;
import com.j2mvc.util.StringUtils;
import com.j2mvc.util.Utils;

/**
 * description 用户逻辑
 * @Author 杨朔
 */
public class UserService {
	DaoSupport dao = new DaoSupport(User.class);

	/**
	 * 插入
	 * @param user
	 * @return User
	 */
	public User insert(User user) {		
		return (User) dao.insert(user);
	}
	/**
	 * 更新
	 * @param user
	 * @return User
	 */
	public User update(User user) {
		return (User)dao.update(user);
	}

	/**
	 * 保存
	 * @param user
	 * @return User
	 */
	public User save(User user) {
		if(user == null){
			return null;
		}
		if(get(user.getId()) !=null ){
			return update(user);
		}else {
			return insert(user);
		}
	}
	/**
	 * 获取用户
	 * @param id
	 * @return User
	 */
	public User get(String id) {
		if(id == null){
			return null;
		}
		Object obj = dao.get(id);
		return obj!=null?(User) obj:null;
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param domain
	 * @return User
	 */
	public User login(String username,String password,String domain){
		if(username.equals("") || password.equals(""))
			return null;
		String sql = "SELECT * FROM users WHERE username=? and password=MD5(?)";
		Object obj = dao.queryForObject(sql,new String[]{username,username,password});
		User user = obj!=null?(User)obj:null;
		if(user!=null){
			String token = Utils.createToken(username, !StringUtils.isEmpty(domain)?domain:"j2mvc.com",password);
			user = setSessionUser(user);
			user.setToken(token);
			// 保存token
			saveToken(token,user.getId());
			return user;
		}else{
			return null;
		}
	}
	/**
	 * 设置会话用户变量
	 * @param user
	 * @param password
	 * @return
	 */
	private User setSessionUser(User user){
		User sessionUser = new User();
		sessionUser.setId(user.getId());
		sessionUser.setUsername(user.getUsername());
		return sessionUser;
	}
	
	/**
	 * 保存token
	 * @param token
	 * @param id
	 * @return
	 */
	public boolean saveToken(String token,String id){
		String updateSql = "update users set token=? WHERE id=?";
		return dao.update(updateSql, new String []{token,id})>0;
	}
	/**
	 * 根据令牌获取
	 * @param token
	 * @return User
	 */
	public User getByToken(String token){
		String sql = "SELECT * FROM users WHERE token=?";
		Object obj = dao.queryForObject(sql,new String[]{token});
		return obj!=null?(User) obj:null;
	}
	/**
	 * 清除token
	 * @param id 可以是用户ID，也可是会话令牌
	 * @return
	 */
	public boolean clearToken(String key){
		String updateSql = "update users set token='' WHERE id=? or token=?";
		return dao.update(updateSql, new String []{key,key})>0;
	}
}
