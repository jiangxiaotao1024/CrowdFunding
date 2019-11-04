<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/main.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<%@ include file="/WEB-INF/jsp/common/top.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/jsp/common/left.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										placeholder="请输入查询条件" id="queryWord">
								</div>
							</div>
							<button type="button" class="btn btn-warning" id="queryByWord">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;" id="deleteBatch">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;" onclick="window.location.href='${APP_PATH}/user/add.htm'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">全选</th>
										<th width="30"><input type="checkbox" id="checkAll"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination">
											</ul>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script type="text/javascript">
	    $(function(){
	    	var pageno=1;
	    	querypage(pageno);
	    })
	    function querypage(pageno,queryWord){
	    	var queryWord=$.trim($("#queryWord").val());
	    	$.ajax({
				type : "POST",
				url : "${APP_PATH}/user/showUser.do",
				data : {
					pageno:pageno,
					pagesize:10,
					queryWord:queryWord
				},
				success : function(result) {
					if (result.success) {
						var context = "";
						var page=result.page;
						$.each(page.data,function(i,n){
							context+="<tr>";
							context+="<td>"+(i+1)+"</td>";
							context+="<td><input type='checkbox' id='"+n.id+"' name='"+n.loginacct+"'></td>";
							context+="<td>"+n.loginacct+"</td>";
							context+="<td>"+n.username+"</td>";
							context+="<td>"+n.email+"</td>";
							context+="<td>";
							context+="<button type='button' class='btn btn-success btn-xs'>";
							context+="<i class=' glyphicon glyphicon-check' onclick='assignRole("+n.id+")'></i>";
							context+="</button>";
							context+="<button type='button' class='btn btn-primary btn-xs' id='"+i+"'>";
							context+="<i class=' glyphicon glyphicon-pencil'  onclick='window.location.href=\"${APP_PATH}/user/edit.htm?id="+n.id+"\"'></i>";
							context+="</button>";
							context+="<button type='button' class='btn btn-danger btn-xs'>";
							context+="<i class=' glyphicon glyphicon-remove' onclick='deleteUser("+n.id+")'></i>";
							context+="</button>";
							context+="</td>";
							context+="</tr>";
						})
						$("tbody").html(context);
						var context1="";
						if(pageno==1){
							context1+="<li><a>上一页</a></li>";
						}else{
							context1+="<li onclick='querypage("+(pageno-1)+")'><a>上一页</a></li>";
						}
						for(var i=0;i<page.totalno;i++){
							if(i+1==page.pageno){
								context1+='<li class="active" onclick="changepage('+(i+1)+')"><a>'+(i+1)+'</a></li>';
							}else{
								context1+='<li onclick="changepage('+(i+1)+')"><a>'+(i+1)+'</a></li>';
							}
						}
						if(pageno==page.totalno){
							context1+="<li><a>下一页</a></li>";
						}else{
							context1+="<li onclick='querypage("+(pageno+1)+")'><a>下一页</a></li>";
						}
						$(".pagination").html(context1);
					} else {
						alert("查询数据失败");
					}
				}
			})
	    }
	    function changepage(n){
	    	pageno=n;
	    	querypage(n);
	    };
	    function deleteUser(n){
	    	$.ajax({
	    		type:"POST",
	    		url:"${APP_PATH}/user/delete.do",
	    		data:{
	    			id:n
	    		},
	    		success:function(result){
	    			if(result.success){
	    				window.location.href="${APP_PATH}/user/index.htm";
	    			}else{
	    				alert(result.message);
	    			}
	    		}
	    	})
	    }
	    $("#checkAll").change(function(){
	    	var a=this.checked;
	    	var checkbox=$("tbody tr td input[type='checkbox']");
    		$.each(checkbox,function(i,n){
    			n.checked=a;
    		})
	    })
	    $("#deleteBatch").click(function() {
	    	var selectCheckbox = $("tbody tr td input:checked");
	    	var jsonObj={};
    		$.each(selectCheckbox,function(i,n){
    			jsonObj["userList["+i+"].id"]=n.id;
    			jsonObj["userList["+i+"].loginacct"]=n.name;
    		})
    		$.ajax({
    			type:"POST",
    			url:"${APP_PATH}/user/deleteBatch.do",
    			data:jsonObj,
    			success:function(result){
    				if(result.success){
    					alert(result.message);
    					window.location.href="${APP_PATH}/user/index.htm";
    				}else{
    					alert(result.message);
    				}
    			}
    		})
		})
		$("#queryByWord").click(function(){
			pageno=1;
			var queryWord=$.trim($("#queryWord").val());
			querypage(pageno,queryWord);
		});
	    function assignRole(id){
	    	window.location.href="${APP_PATH}/user/assignRole.htm?id="+id;
	    }
	</script>
</body>
</html>
