
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
<link rel="icon" href="${path }/${RESOURCE_PREFIX}/favicon.ico"
	type="image/x-icon" />
<link rel="stylesheet"
	href="${path }/${RESOURCE_PREFIX}/css/web/style.css" type="text/css"
	charset="utf8" />
<script src="${path }/${RESOURCE_PREFIX}/js/vue.min.js"
	type="text/javascript"></script>
<script src="${path }/${RESOURCE_PREFIX}/js/axios.min.js"
	type="text/javascript"></script>
</head>
<body>
	<div id="app">
		<div id="page" :style="{minHeight:wrapperHeight+'px'}" ref="wrapper">
			<div class="title logo">j2mvc web示例</div>

			<div class="edit-wrapper">
				<form method="post">
					<div class="form-inputs">
						<div class="clear"></div>
						<div>
							<input placeholder="请填写编号" v-model="productId"
								class="normalinput" />
						</div>
						<div>
							<input placeholder="请填写标题" v-model="productTitle" />
						</div>
						<div>
							<input placeholder="价格" v-model="productPrice" class="shortinput" />
						</div>
						<div>
							<input placeholder="库存" v-model="productStock" class="shortinput" />
						</div>
						<div>
							<button type="button" @click="saveProduct">保存</button>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<div class="form-button"></div>
				</form>
			</div>
			<div id="components">
				<dialog v-if="dialogStatus == 1" :data="dialogData"
					v-on:close="closeDialog"></dialog>
			</div>
			<div class="list">
				<div class="list-item" v-for="(item,index) in items"
					:key="'item'+index">
					<div class="list-item-title">{{item.title}}</div>
					<div class="list-item-toolbar">
						<span>价格:<span class="small red bold">￥</span><span
							class="big red bold">{{item.price}}</span></span> <span>库存:<span
							class="blue bold">{{item.stock}}</span></span>
						<button class="blue" @click="addCart(item.id)">加入购物车</button>
					</div>
				</div>
				<div class="error" v-if="error!='' && items.length ==0">{{error}}</div>
			</div>
			<!-- <dialog :title="'提示''" :msg="'我是一个弹框'"></dialog> -->
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
window.onload = function(){
	Vue.component('dialog', {
  		template: '<div>{{data.title}}</div>',
  		props:['data'],
		data:function() {
			return {
			}
		},
	 	 methods: {
		    close: function () {
		    	this.$emit('close')
		    }
  		},
	})
	
	var vm = new Vue({
	  el: '#app',
	  data: {
		  error:'',
		  items:[],
		  page:1,
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
		  productStock:''
	  },
	  mounted () {
          this.wrapperHeight = document.documentElement.clientHeight - 50;//this.$refs.wrapper.getBoundingClientRect().top - 40;
		  this.getDdata();
	  },
	  watch:{
		  keyword(newvalue,oldvalue){
			  console.log('keyword : ' +newvalue+'变为'+oldvalue);
		  }
	  },
	  methods: {
		  getDdata:function(){
			  var _this = this;
			    axios.get('${path}/product/getItems.view',{
			    	  params:{
			    		  id:this.keyword,
			    		  page:this.page
			    	  }
			      }).then(function (response) {
			    	  if(response.data.code == 1){
			    		  _this.items = response.data.result.list;
			    	  }else{
			    		  _this.error = response.data.message;
			    	  }
			      }).catch(function (error) { // 请求失败处理
			        console.log(error);
			      });
		  },
	  	  saveProduct:function(){
	  		  console.log('save product id :'+this.productId);
			  var _this = this;
			  axios({
				    method:"get",
				    url:"${path}/product/save.view",
				    headers:{
				        'Content-type': 'application/x-www-form-urlencoded'
				    },
				    params:{
			    		  id:this.productId,
			    		  title:this.productTitle,
			    		  price:this.productPrice,
			    		  stock:this.productStock
				    },
				    transformRequest: [function (data) {
				        let ret = ''
				        for (let it in data) {
				          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
				        }
				        return ret
				      }]
				}).then(function (response){
			    	  if(response.data.code == 1){
			    		  _this.getDdata();
			    	  }else{
			    		  console.log(response.data.message);
			    	  };
				}).catch(function (error) { // 请求失败处理
			        console.log(error);
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
}
</script>
</body>
</html>
