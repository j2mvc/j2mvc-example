package com.j2mvc.example.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2mvc.example.web.entity.Product;
import com.j2mvc.framework.action.RequestUri;
import com.j2mvc.framework.mapping.ActionPath;
import com.j2mvc.framework.mapping.ActionUri;
import com.j2mvc.framework.mapping.ContentType;
import com.j2mvc.framework.mapping.RequestMethod;
import com.j2mvc.util.Pagination;
import com.j2mvc.util.StringUtils;
/**
 * 网址：http://www.j2mvc.com
 * @author 杨朔
 * 商品Action
 */
@ActionPath(path="/product/",dir="/WEB-INF/jsp/")
public class ProductAction extends BaseAction{
	/**
	 * 商品列表
	 * @return String
	 */
	@ActionUri(uri="items([/])?")
	public String items(Integer page,String keyword){
		page = page!=null && page > 0?page:1;
		keyword = keyword!=null ?keyword:"";
		
		// 查询出总数
		Integer total = productService.total(keyword);
		// 创建分页对象
		Pagination pagination  = new Pagination(total,PAGESIZE, page,"page");
		// 查询分页数据
		Integer start = pagination.getStartIndex();
		List<Product> list = productService.query(keyword,start, PAGESIZE);
		// 输出
		put("list", list);
		put("error", !(list != null && list.size() > 0) ? "商品为空呢。" : "");
		put("pagination", pagination);
		String url = request.getRequestURI() 
			+ (!StringUtils.isEmpty(keyword)?"?keyword="+keyword:"");
		put("paginationHtml", pagination.getBootstrapHtml(url, 12, true));
		put("url",url+"&page="+page);
		put("title", "商品列表第"+page+"页 － " + SITENAME );
		put("keywords","商品列表");
		put("description","这是商品列表");
		return jspUtil.jsp("product/items.jsp");
	}


	/**
	 * 商品详情
	 * @return String
	 */
	@ActionUri(uri="items/[a-z\\-\\d]+([/])?")
	public String item(RequestUri uri){
		String id = uri.getValues()[2];
		put("product",productService.get(id));
		return jspUtil.jsp("product/item.jsp");
	}
	
	/**
	 * 获取商品列表
	 */
	@ActionUri(uri="getItems([/])?")
	public void getItems(String keyword,Integer page){
		page = page!=null && page > 0?page:1;
		keyword = keyword!=null ?keyword:"";
		// 查询出总数
		Integer total = productService.total(keyword);
		// 创建分页对象
		Pagination pagination  = new Pagination(total,PAGESIZE, page);
		// 查询分页数据
		Integer start = pagination.getStartIndex();
		List<Product> list = productService.query(keyword,start, PAGESIZE);
		
		if(list!=null && list.size() > 0) {
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("pagination",pagination);
			result.put("list",list);
			success("获取到商品",result);
		}else {
			error("没有获取到商品");
		}
	}
	/**
	 * 获取商品详情
	 */
	@ActionUri(uri="getItem([/])?")
	public void getItem(Product product){
		printJson(product);
	}

	/**
	 * 保存商品
	 */
	@ContentType(ContentType.JSON)
	@RequestMethod(RequestMethod.POST)
	public void saveProduct(Product product){
		if(product == null) {
			error("请求失败！");
			return;
		}
		if(StringUtils.isEmpty(product.getId())) {
			error("未填写商品编号！");
			return;
		}
		if(StringUtils.isEmpty(product.getTitle())) {
			error("未填写商品标题！");
			return;
		}
		if(product.getPrice() == null) {
			error("未填写商品价格！");
			return;
		}
		if(product.getStock() == null) {
			error("未填写商品库存！");
			return;
		}
		if(productService.save(product)!=null){
			success("添加商品成功！",null);
		}else{
			error("添加商品失败！");
		}
	}
	/**
	 * 删除商品
	 */
	public void delProduct(String ids){
		if(ids == null){
			error("没有选择条目！");
		}
		if(productService.delete(ids.split(","))>0){
			success("删除商品成功！",null);
		}else{
			error("删除商品失败！");
		}
	}
}
