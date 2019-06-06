package com.j2mvc.example.web.service;

import java.util.List;

import com.j2mvc.example.web.entity.Product;
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
	 * 删除
	 * @param id
	 * @return int
	 */
	public int delete(String...ids){
		return dao.delete(ids);
	}
	/**
	 * 商品总数 
	 * @param keyword 关键字
	 * @return Integer
	 */
	public Integer total(String keyword){
		String sql = "SELECT COUNT(*) FROM products WHERE title like";
		return dao.number(sql,new String[]{keyword});
	}
	
	/**
	 * 分页查询
	 * @param keyword 关键字
	 * @param start 开始位置
	 * @param size 多少条
	 * @return List<Product>
	 */
	@SuppressWarnings("unchecked")
	public List<Product> query(String keyword,Integer start,Integer size){
		String sql = "SELECT * FROM products WHERE title like ? limit ?,? ";
		List<?> list = dao.query(sql,new Object[]{"%"+keyword+"%",start,size});
		return list!=null?(List<Product>) list:null;
	}
}
