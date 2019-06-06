package com.j2mvc.example.app.service;

import java.util.List;

import com.j2mvc.example.app.entity.Product;
import com.j2mvc.framework.dao.DaoSupport;

/**
 * description 商品逻辑
 * @Author 杨朔
 */
public class ProductService {
	DaoSupport dao = new DaoSupport(Product.class);

	/**
	 * 插入
	 * @param product
	 * @return Product
	 */
	public Product insert(Product product) {		
		return (Product) dao.insert(product);
	}
	/**
	 * 更新
	 * @param product
	 * @return Product
	 */
	public Product update(Product product) {
		return (Product)dao.update(product);
	}

	/**
	 * 保存
	 * @param product
	 * @return product
	 */
	public Product save(Product product) {
		if(product == null){
			return null;
		}
		if(get(product.getId()) !=null ){
			return update(product);
		}else {
			return insert(product);
		}
	}
	/**
	 * 获取商品
	 * @param id
	 * @return product
	 */
	public Product get(String id) {
		if(id == null){
			return null;
		}
		Object obj = dao.get(id);
		return obj!=null?(Product) obj:null;
	}
	/**
	 * 商品总数 
	 * @return Integer
	 */
	public Integer total(){
		String sql = "SELECT COUNT(*) FROM products";
		return dao.number(sql);
	}
	
	/**
	 * 分页查询
	 * @param start
	 * @param size
	 * @return List<Product>
	 */
	@SuppressWarnings("unchecked")
	public List<Product> query(Integer start,Integer size){
		String sql = "SELECT * FROM products WHERE user_id=? limit ?,? ";
		List<?> list = dao.query(sql,new Object[]{start,size});
		return list!=null?(List<Product>) list:null;
	}
}
