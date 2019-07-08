package com.j2mvc.example.web.entity;

import com.j2mvc.framework.mapping.DataSourceName;
import com.j2mvc.framework.mapping.PrimaryKey;
import com.j2mvc.framework.mapping.Table;
import com.j2mvc.util.json.NotJSONField;
import com.j2mvc.framework.mapping.Column;


/**
 * 用户实体类
 * @author yangshuo
 * 如果只有一个数据源，可以不设置DataSourceName
 */
@DataSourceName("jdbc/j2mvc_example")
@Table("users")
@PrimaryKey(autoIncrement=false)
public class User {

	/** id */
	@Column(name = "id",length = 32)
	private String id;
	/** 用户名 */
	@Column(name = "username")
	private String username;
	/** 密码,不输出json字段 */
	@NotJSONField
	@Column(name = "password")
	private String password;
	/** 用户会话令牌,不输出json字段 */
	@NotJSONField
	@Column(name = "token")
	private String token;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
