package com.j2mvc.example.web.action;

import java.util.List;

import com.j2mvc.example.web.entity.Product;
import com.j2mvc.framework.action.RequestUri;
import com.j2mvc.util.Pagination;
import com.j2mvc.util.StringUtils;
import com.j2mvc.util.Success;
import com.j2mvc.util.mapping.ActionPath;
import com.j2mvc.util.mapping.ActionUri;
/**
 * 网址：http://www.j2mvc.com
 * @author 杨朔
 * 商品Action
 */
@ActionPath(path="/product/",dir="/WEB-INF/jsp/")
public class ProductAction extends BaseAction{
	/**
	 * 商口列表
	 * @return String
	 */
	@ActionUri(uri="items([/])?")
	public String index(Integer page,String keyword){
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
	 * 商口详情
	 * @return String
	 */
	@ActionUri(uri="items/[a-z\\-\\d]+([/])?")
	public String product(RequestUri uri){
		String id = uri.getValues()[2];
		put("product",productService.get(id));
		return jspUtil.jsp("product/item.jsp");
	}

	/**
	 * 保存商品
	 */
	@ActionUri(uri="save([/])?")
	public void saveProduct(Product product){
		if(productService.save(product)!=null){
			printJson(new Success("添加购物车成功！"));
		}else{
			printJson(new Error("添加购物车失败！"));
		}
	}
	/**
	 * 删除商品
	 */
	@ActionUri(uri="del([/])?")
	public void delProduct(String ids){
		if(ids == null){
			printJson(new Error("没有选择条目！"));
		}
		if(productService.delete(ids.split(","))>0){
			printJson(new Success("删除商品成功！"));
		}else{
			printJson(new Error("删除商品失败！"));
		}
	}
}
