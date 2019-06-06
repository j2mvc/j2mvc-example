package com.j2mvc.example.app.service;

import java.util.List;

import com.j2mvc.framework.dao.DaoSupport;

/**
 * 
 * 公共服务
 * @Author yangshuo
 */
public class CommonService{

	DaoSupport dao;

	public CommonService(){
		dao = new DaoSupport();
	}
	public CommonService(String dataSourceName){
		dao = new DaoSupport(dataSourceName);
	}

	/**
	 * 获取数值
	 * @param sql
	 * @param params
	 * @return Integer
	 */
	public Integer number(String sql,Object[] params){
		return dao.number(sql, params);
	}
	/**
	 * 获取字符串
	 * @param sql
	 * @param params
	 * @return String
	 */
	public String string(String sql,Object[] params){
		return dao.string(sql, params);
	}

	/**
	 * 获取字符串数组
	 * @param sql
	 * @param params
	 * @return List<String>
	 */
	public List<String> array(String sql,Object[] params){
		return dao.queryArray(sql, params);
	}

}
