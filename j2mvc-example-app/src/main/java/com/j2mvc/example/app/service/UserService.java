package com.j2mvc.example.app.service;

import com.j2mvc.example.app.entity.User;
import com.j2mvc.framework.dao.DaoSupport;

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
	 * 根据用户名和密码获取 
	 * @param username
	 * @param password
	 * @return User
	 */
	public User get(String username,String password){
		String sql = "SELECT * FROM users WHERE username=? and password=?";
		Object obj = dao.queryForObject(sql,new String[]{username,password});
		return obj!=null?(User) obj:null;
	}
}
