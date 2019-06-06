package com.j2mvc.example.web.action;

import java.util.List;

import com.j2mvc.example.web.entity.Cart;
import com.j2mvc.example.web.entity.Product;
import com.j2mvc.util.Pagination;
import com.j2mvc.util.Utils;
import com.j2mvc.util.mapping.ActionPath;
import com.j2mvc.util.mapping.ActionUri;
/**
 * 网址：http://www.j2mvc.com
 * @author 杨朔
 * 购物车Action
 */
@ActionPath(path="/cart/")
public class CartAction extends BaseAction{
	/**
	 * 购物车列表
	 * @return String
	 */
	@ActionUri(uri="items([/])?")
	public String index(Integer page){
		
		page = page!=null && page > 0?page:1;
		
		// 查询出总数
		Integer total = cartService.total(user.getId());
		// 创建分页对象
		Pagination pagination  = new Pagination(total,PAGESIZE, page);
		// 查询分页数据
		Integer start = pagination.getStartIndex();
		List<Cart> list = cartService.query(user.getId(),start, PAGESIZE);
		// 输出
		put("list", list);
		put("error", !(list != null && list.size() > 0) ? "购物车空空如也！" : "");
		put("pagination", pagination);
		String url = request.getRequestURI();
		put("paginationHtml", pagination.getBootstrapHtml(url, 12, true));
		put("url",url+"&page="+page);
		put("title", "我的购物车" );
		return jspUtil.jsp("cart/items.jsp");
	}
	
	/**
	 * 添加购物车
	 */
	@ActionUri(uri="add([/])?")
	public void add(String productId,Integer num){
		Cart cart = cartService.get(productId,user.getId());
		if(cart == null){
			cart = new Cart();
			cart.setId(Utils.createId());
		}
		cart.setNum(cart.getNum()+num);
		cart.setProduct(new Product(productId));
		
		if(cartService.save(cart)!=null){
			success("添加购物车成功！",null);
		}else{
			error("添加购物车失败！");
		}
	}

	/**
	 * 添加购物车
	 */
	@ActionUri(uri="update([/])?")
	public void updateCart(Cart cart){
		if(cartService.save(cart)!=null){
			success("更改购物车成功！",null);
		}else{
			error("更改购物车失败！");
		}
	}
	/**
	 * 删除物车
	 */
	@ActionUri(uri="del([/])?")
	public void add(String ids){
		if(ids == null){
			error("没有选择条目！");
		}
		Integer num = cartService.delete(ids.split(","));
		if(num>0){
			success("删除购物车成功！",null);
		}else{
			error("添加购物车失败！");
		}
	}
}
