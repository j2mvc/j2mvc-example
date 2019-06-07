package com.j2mvc.example.app.entity;

import com.j2mvc.framework.mapping.Column;
import com.j2mvc.framework.mapping.DataSourceName;
import com.j2mvc.framework.mapping.PrimaryKey;
import com.j2mvc.framework.mapping.Table;

/**
 * 商品实体类
 * @author yangshuo
 * 如果只有一个数据源，可以不设置DataSourceName
 */
@DataSourceName("jdbc/j2mvcexample")
@Table("products")
@PrimaryKey(autoIncrement=false)
public class Product {

	/** id */
	@Column(name = "id",length = 32)
	private String id;
	/** 用户名 */
	@Column(name = "title")
	private String title;
	/** 密码 */
	@Column(name = "price")
	private Double price;
	/** 库存 */
	@Column(name = "stock")
	private Integer stock;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
