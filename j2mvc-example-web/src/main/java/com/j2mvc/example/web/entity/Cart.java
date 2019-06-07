package com.j2mvc.example.web.entity;

import com.j2mvc.framework.mapping.Column;
import com.j2mvc.framework.mapping.DataSourceName;
import com.j2mvc.framework.mapping.Foreign;
import com.j2mvc.framework.mapping.PrimaryKey;
import com.j2mvc.framework.mapping.Table;

/**
 * 购物车实体类
 * @author yangshuo
 * 如果只有一个数据源，可以不设置DataSourceName
 */
@DataSourceName("jdbc/j2mvc_example")
@Table("carts")
@PrimaryKey(autoIncrement=false)
public class Cart {

	/** id */
	@Column(name = "id",length = 32)
	private String id;
	/** 用户Id */
	@Column(name = "user_id")
	private String userId;
	
	/** 商品*/
	@Foreign
	@Column(name = "product_id")
	private Product product;
	
	/** 数量 */
	@Column(name = "num")
	private Integer num = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}
