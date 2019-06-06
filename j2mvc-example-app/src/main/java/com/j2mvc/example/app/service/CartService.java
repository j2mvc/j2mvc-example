package com.j2mvc.example.app.service;

import java.util.List;

import com.j2mvc.example.app.entity.Cart;
import com.j2mvc.framework.dao.DaoSupport;

/**
 * description 用户逻辑
 * @Author 杨朔
 */
public class CartService {
	DaoSupport dao = new DaoSupport(Cart.class);

	/**
	 * 插入
	 * @param cart
	 * @return Cart
	 */
	public Cart insert(Cart cart) {		
		return (Cart) dao.insert(cart);
	}
	/**
	 * 更新
	 * @param cart
	 * @return Cart
	 */
	public Cart update(Cart cart) {
		return (Cart)dao.update(cart);
	}

	/**
	 * 保存
	 * @param cart
	 * @return Cart
	 */
	public Cart save(Cart cart) {
		if(cart == null){
			return null;
		}
		if(get(cart.getId()) !=null ){
			return update(cart);
		}else {
			return insert(cart);
		}
	}
	/**
	 * 获取
	 * @param id
	 * @return Cart
	 */
	public Cart get(String id) {
		if(id == null){
			return null;
		}
		Object obj = dao.get(id);
		return obj!=null?(Cart) obj:null;
	}
	/**
	 * 查询用户购物车总数
	 * @param userId
	 * @return
	 */
	public Integer total(String userId){
		String sql = "SELECT COUNT(*) FROM carts WHERE user_id=? limit ?,? ";
		return dao.number(sql,new Object[]{userId});
	}
	
	/**
	 * 分页查询
	 * @param userId
	 * @param start
	 * @param size
	 * @return List<Cart>
	 */
	@SuppressWarnings("unchecked")
	public List<Cart> query(String userId,Integer start,Integer size){
		String sql = "SELECT * FROM carts WHERE user_id=? limit ?,? ";
		List<?> list = dao.query(sql,new Object[]{userId,start,size});
		return list!=null?(List<Cart>) list:null;
	}
}
