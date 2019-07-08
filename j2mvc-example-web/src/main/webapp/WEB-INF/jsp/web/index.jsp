
<%
	/**
		首页
		@Author 杨朔
	*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="${keywords }">
<meta name="description" content="${description }">
<title>${title }</title>
<link rel="icon" href="${base.resourcePrefix}/favicon.ico"
	type="image/x-icon" />
<link rel="stylesheet"  rev="stylesheet" href="${base.resourcePrefix}/css/web/style.css" type="text/css" charset="utf8" media="all"> 
<script src="${base.resourcePrefix}/js/vue.min.js" type="text/javascript"></script>
<script src="${base.resourcePrefix}/js/axios.min.js" type="text/javascript"></script>
</head>
<body>
	<div id="app">
		<div id="page" :style="{minHeight:wrapperHeight+'px'}" ref="wrapper">
			<div class="title logo">j2mvc web示例</div>
			<div class="description">
				本示例仅作参考，展示了数据库操作，以及数据显示。<br>
				本页面采用vue实现异获取数据和更新数据。
			</div>
			<div class="edit-wrapper">
				<form method="post">
					<div class="form-inputs">
						<div class="clear"></div>
						<div>
							<input placeholder="请填写编号" v-model="productId" class="input normalinput" />
						</div>
						<div>
							<input placeholder="请填写标题" v-model="productTitle" class="input" />
						</div>
						<div>
							<input type="number" placeholder="价格" v-model="productPrice" class="input shortinput" />
						</div>
						<div>
							<input type="number" placeholder="库存" v-model="productStock" class="input shortinput" />
						</div>
						<div>
							<button type="button" @click="saveProduct">保存</button>
							<button type="button" class="gray" @click="getData()">刷新</button>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="form-button"></div>
				</form>
			</div>
			<div class="error" v-if="error != ''">{{error}}</div>
			<div id="components">
				<dialog v-if="dialogStatus == 1" :data="dialogData"
					v-on:close="closeDialog"></dialog>
			</div>
			<div class="list">
				<div class="list-item" v-for="(item,index) in items"
					:key="'item'+index">
					<div class="list-item-title">{{item.title}}</div>
					<div>ID:{{item.id}}</div>
					<div class="list-item-toolbar">
						<span>价格:<span class="small red bold">￥</span><span
							class="big red bold">{{item.price}}</span></span> <span>库存:<span
							class="blue bold">{{item.stock}}</span></span>
					</div>
					<div>
					<button class="gray" @click="addCart(item.id)">加入购物车</button>
					</div>
				</div>
			</div>
			<!-- 分页 -->
			<div class="pagination">
				<div>
					<ul>
					<li class='text'>共{{total}}条,{{page}}/{{pageTotal}}页.</li>
						<li class="p" v-if="page > 1"><a @click="skipPage(1)">首页</a></li>
						<li class="p" v-if="page > 1"><a @click="skipPage(page - 1)">上一页</a></li>
						<li class="p" v-if="startpage > 1"><a @click="skipPage(1)">1</a></li>
						<li  v-if="startpage > 1">...</li>
						<li v-for="i in endpage" 
						    v-if="pageTotal > 1 && i>=startpage" 
						    :class="i == page ? 'p current' : 'p'" 
						    :key="i">
							<a v-if="i == page">{{i}}</a>
							<a @click="skipPage(i)" v-else>{{i}}</a> 
						</li>
						<li  v-if="endpage > 1  && endpage < pageTotal">...</li>
						<li v-if="endpage > 1  && endpage < pageTotal" class="p">
							<a @click="skipPage(pageTotal)">{{pageTotal}}</a> 
						</li>
						<li v-if="page < pageTotal" class="p">
							<a @click="skipPage(page + 1)">下一页</a>
						</li>
						<li v-if="page < pageTotal" class="p">
							<a @click="skipPage((pageTotal))">尾页</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="blocks">
				<div class="block">
					<h1>GET方法请求数据</h1>
					<div class="block-content">
						<h2>URL地址格式</h2>
						<p>https://sserver/path?name=value<p>
						<h2>Ajax请求示例</h2>
						<p>
<pre>
							
axios.get('${path}/product/getItems',{
	params:{
		id:this.keyword,
		page:this.page
	}
}).then(function (response) {{ 
	// 请求成功
	console.log(JSON.stringify(response.data));
}).catch(function (error) { 
	// 请求失败
	console.log(error)
 });
</pre>
						<p>
						<h2>服务器设置</h2>
						<p>
1.Action默认方式同时接收get和post为带参URL方式的请求。<br>
2.如果是普通FORM提交，URL不带参，也不用设置不用设置requestMethod和contentType。<br>
3.可以不设置requestMethod,contentType。<br>
4.如果要设置，requestMethod设置为GET，contentType设置为application/x-www-form-urlencoded。
							<pre>
@ActionUri(uri="getItems",requestMethod=RequestMethod.GET,contentType=ContentType.XWwwFormUrlencoded){
	// 逻辑代码
	...
}
							</pre>
						<p>
					</div>
				</div>
				<div class="block">
					<h1>POST方法提交数据</h1>
					<div class="block-content">
						<h2>URL地址格式</h2>
						<p>https://sserver/path<p>
						<h2>Ajax请求示例</h2>
						<p>
							<pre>
							
axios.post("${path}/product/save",{
	id:this.id,
	title:this.title,
	price:this.price,
	stock:this.stock
}).then(function (response){
	if(response.data.code == 1){
		// 提交成功
		console.log(response.data.message);
	}else{
		// 提交失败
		 console.log(response.data.message);
	};
}).catch(function (error) { 
// 请求失败
	console.log(error);
});
							</pre>
						<p>
						<h2>XML请求数据格式</h2>
						<p>
  &lt;!ELEMENT root ANY&gt;<br> 
  &lt;!ATTLIST Product SSN ID #REQUIRED&gt;]&gt;      

&lt;root&gt;<br>
  &lt;Product SSN='id'&gt;XMLID12354&lt;/Product&gt;<br>
  &lt;Product SSN='title'&gt;XML提交的标题 &lt;/Product&gt;<br>
  &lt;Product SSN='price'&gt;55 &lt;/Product&gt; <br>
  &lt;Product SSN='stock'&gt;32 &lt;/Product&gt;<br>
&lt;/root&gt;<br>
						<p>
						
						<h2>服务器设置</h2>
						<p>
1.Post提交方式：服务器设置requestMethod为POST<br>
2.如果地址带参数，服务器应设置contentType为application/x-www-form-urlencoded<br>
3.如是JSON格式，服务器应设置contentType为application/json，通常Ajax的数据提交为application/json<br>
4.如是XML格式，服务器应设置contentType为application/xml<br>
						<pre>
@ActionUri(uri="save",requestMethod=RequestMethod.POST,contentType=ContentType.JSON)
public void saveProduct(Product product,String [] imageUrls,SomeObj [] obj,String key){
	// 逻辑代码
	...
}
						</pre>
						<p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div>
	<!--components -->
		<!--  <div id="dialog">
		<div class="dialog-wrapper" >
			<div class="dialog-title">{{title}}</div>
			<div class="dialog-content">
				<div >{{html}}</div>
			</div>
			<div class="dialog-footer">
				<a class="dialog-button cancel" v-on:click="close">取消</a>
				<a class="dialog-button sure" >确定</a>
			</div>
		</div>
	</div> -->
	</div>
	<script>

	var vm = new Vue({
	  el: '#app',
	  data: {
		  error:'',
		  items:[],
		  wrapperHeight:300,
		  keyword:'',
		  dialogStatus:0,
		  dialogData:{
			  title:'提示',
			  html:'我是一个弹框',
			  width:300,
			  height:200
		  },
		  productId:'',
		  productTitle:'',
		  productPrice:'',
		  productStock:'',
		  // 分页参数
		  pagination:{},
		  startpage:1,
		  maxNum : 15,
		  page:1,
		  endpage:0,
		  pageTotal:0,
		  total:0
	  },
	  mounted () {
          this.wrapperHeight = document.documentElement.clientHeight - this.$refs.wrapper.getBoundingClientRect().top - 50;
		  this.getData();
	  },
	  watch:{
		  keyword(newvalue,oldvalue){
			  console.log('keyword : ' +newvalue+'变为'+oldvalue);
		  },
		  pagination(newvalue,oldvalue){
    		  this.page = this.pagination.page;
    		  this.pageTotal = this.pagination.pageTotal;
    		  this.total = this.pagination.total;
			  this.cal(); 
		  },
		  error(newvalue,oldvalue){
			 setTimeout(()=>{
				 this.error = '';
			 },5000);
		  },
		  items(newvalue,oldvalue){
		  }
	  },
	  methods: { 
		  cal:function(){
				this.maxNum = this.maxNum % 2 == 0 ? this.maxNum + 1 : this.maxNum;
				var endpage = this.maxNum;
				var showNum = this.maxNum;
				var halfNum = (this.maxNum - 1) / 2; // 一半页数

				// stepNum:当前页前页数，或当前页后页数
				// 当前页号 - stepNum 大于1，开始页号为page-halfNum
				if (this.page - halfNum > 1) {
					this.startpage = this.page - halfNum;
				} else {
					this.startpage = 1;
				}
				// 当前页号 + stepNum 小于总页数，结束页号为page+halfNum
				if (this.page + halfNum < this.pageTotal) {
					this.endpage = this.page + halfNum;
				} else {
					this.endpage = this.pageTotal;
				}
				// 开始页号大于总页数-showNum,且总页数大于showNum，开始页号为总页-showNum
				this.startpage = this.startpage > this.pageTotal - showNum && this.pageTotal > showNum ? this.pageTotal - showNum	: this.startpage;
				// 结束页号小于showNum（每页页数）且小于总页数，结束页号showNum
				this.endpage = this.endpage < showNum && this.endpage < this.pageTotal ? showNum : this.endpage;
				this.endpage = this.endpage > this.pageTotal?this.pageTotal:this.endpage;
		  },
		  getData:function(){
			  var _this = this;
			    axios.get('${path}/product/getItems',{
			    	  params:{
			    		  id:this.keyword,
			    		  page:this.page
			    	  }
			      }).then(function (response) {
			    	  if(response.data.code == 1){
			    		  _this.items = response.data.result.list;
			    		  _this.pagination = response.data.result.pagination;
			    	  }else{
			    		  _this.error = response.data.message;
			    	  }
			      }).catch(function (error) { // 请求失败处理
			         _this.error = error;
			      });
		  },
		  skipPage:function(page){ 
			  this.page = page;
			  this.getData();
		  },
	  	  saveProduct:function(){
			  var _this = this;
			  _this.error = '';
			  axios.post("${path}/product/save",{
			    		  id:this.productId,
			    		  title:this.productTitle,
			    		  price:this.productPrice,
			    		  stock:this.productStock
			    }).then(function (response){
			    	  if(response.data.code == 1){
			    		  console.log(response.data.message);
			    		  _this.getData();
			    	  }else{
			    		  _this.error = response.data.message;
			    	  };
				}).catch(function (error) { // 请求失败处理
			         _this.error = error;
			    });
	  	  },
	  	  addCart:function(id){
	  		  console.log('add Cart id:'+id);
	  		  this.showDialog();
	  	  },
	  	  showDialog:function(){
			  this.dialogStatus = 1;
	  	  },
		  closeDialog:function(){
			  this.dialogStatus = 0;
		  }
	  }
	});

</script>
</body>
</html>
