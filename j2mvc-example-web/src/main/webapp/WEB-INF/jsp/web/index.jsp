<%
/**
	首页
	@Author 杨朔
*/
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>  
<head>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="${keywords }">
<meta name="description" content="${description }">
<title>${title }</title>
<link rel="icon" href="${path }/${RESOURCE_PREFIX}/favicon_16X16.ico" type="image/x-icon"/> 
<script src="${path }/${RESOURCE_PREFIX}/js/vue.min.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<style>
#app {
	width: 800px;
	margin:0 auto;
	padding:10px;
	border: #f3f3f3 1px solid;
}
.toolbar{
	margin:10px;
}
.list-item{
	display:flex;
	border-bottom:1px solid #f0f0f0;
	padding:10px;
}
.list-item{
	display:flex;
	border-bottom:1px solid #f0f0f0;
	padding:10px;
}

.error{
	text-align:center;
	line-height:100px;
}

</style>
</head>
<body>
<div id="app">
	<div class="title">j2mvc web示例</div>
	<div class="list">
		<div class="list-item" v-for="(item,index) in items" :key="'item'+index">
			<div class="list-item-title">{{item.title}}</div>
			<div class="list-item-toolbar">
				<span>价格:￥{{item.price}}</span>
				<span>库存:{{item.stock}}</span>
				<button>加入购物车</button>
			</div>
		</div>
		<div class="error" v-if="error!='' && items.length ==0">{{error}}</div> 
	</div>
	<div class="toolbar">
		<button>添加商品</button>
	</div>
</div>

<script>
new Vue({
  el: '#app',
  data: {
	  
	  error:'',
	  items:[],
	  page:1,
	  keyword:''
  },
  mounted () {
	  this.getDdata();
  },
  methods: {
	  getDdata:function(){
		  var _this = this;
		    axios.get('${path}/product/getItems',{
		    	  params:{
		    		  keyword:this.keyword,
		    		  page:this.page
		    	  }
		      }).then(function (response) {
		    	  if(response.data.code == 1){
		    		  _this.items = response.data.result.list;
		    	  }else{
		    		  _this.error = response.data.result.error.message;
		    	  }
		      }).catch(function (error) { // 请求失败处理
		        console.log(error);
		      });
	  }
  }
  
})
</script>
</body>
</html>
