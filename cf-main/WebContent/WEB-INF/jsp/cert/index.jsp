<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
			<%@ include file="/WEB-INF/jsp/common/left.jsp" %>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" placeholder="请输入查询条件" id="word">
    </div>
  </div>
  <button type="button" class="btn btn-warning" onclick="querypage(1)"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='form.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" id="checkAll"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
             
              </tbody>
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
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				})
				querypage(1);
            });
            function querypage(pageno) {
        		var word=$.trim($("word").val());
        		$.ajax({
        						type : "POST",
        						url : "${APP_PATH}/cert/showCert.do",
        						data : {
        							pageno : pageno,
        							pagesize : 10,
        							word:$("#word").val()
        						},
        						success : function(result) {
        							if (result.success) {
        								var context = "";
        								var page = result.page;
        								var data = page.data;
        								$.each(data,
        										function(i, n) {
        											context += '<tr>';
        											context += '<td>' + (i + 1)
        													+ '</td>';
        											context += '<td><input type="checkbox" id='+n.id+'></td>';
        											context += '<td>' + n.name
        													+ '</td>';
        											context += '<td>';
        											context += '<button type="button" class="btn btn-primary btn-xs" onclick="update('+n.id+')"><i class=" glyphicon glyphicon-pencil"></i></button>';
        											context += '<button type="button" class="btn btn-danger btn-xs" onclick="deleteRole('+n.id+')"><i class=" glyphicon glyphicon-remove"></i></button>';
        											context += '</td>';
        											context += '</tr>';
        										})
        							 $("tbody").html(context);
        							  var pagecontext="";
        							  if(page.pageno==1){
        								  pagecontext+='<li class="disabled"><a onclick="changepage('+(page.pageno-1)+')">上一页</a></li>';
        							  }else{
        								  pagecontext+='<li><a onclick="changepage('+(page.pageno-+1)+')">上一页</a></li>';
        							  }
        					
                                      for(var i=0;i<page.totalno;i++){
                                    	if(i+1==page.pageno){
          								pagecontext+='<li class="active" onclick="choosepage('+(i+1)+')"><a href="#">'+(i+1)+' <span class="sr-only">(current)</span></a></li>';}else{
          									pagecontext+='<li onclick="choosepage('+(i+1)+')"><a href="#">'+(i+1)+'</a></li>';
          								} 								
                                      }
                                      if(page.totalno==page.no){
        								  pagecontext+='<li class="disabled"><a onclick="changepage('+(page.pageno+1)+')">下一页</a></li>';
        							  }else{
        								  pagecontext+='<li><a onclick="changepage('+(page.pageno+1)+')">下一页</a></li>';
        							  }
                                      $(".pagination").html(pagecontext);
        							} else {
        								alert(result.message);
        							}
        						}
        					})
        		};
        		function changepage(n){
        			querypage(n);
        		};
        		function choosepage(n){
        			querypage(n)
        		}
        		function update(n) {
        			window.location.href="${APP_PATH}/cert/update.htm?id="+n;
        		}
        		function deleteRole(n) {
        			$.ajax({
        				type:"POST",
        				url:"${APP_PATH}/cert/delete.do",
        				data:{
        					id:n
        				},
        				success:function(result){
        					if(result.success){
        						querypage(1);
        					}else{
        						alert(result.message);
        					}
        				}
        			})
        		}
        		$("#checkAll").click(function(){
        			var check=this.checked;
        			var checkbox=$("tbody tr td input[type='checkbox']");
        			$.each(checkbox,function(i,n){
        				n.checked=check;
        			})
        		})
        		$("#deleteByBatch").click(function(){
        			var list=$("tbody tr td input:checked");
        			var jsonObj={};
        			$.each(list,function(i,n){
        				jsonObj["ids["+i+"]"]=n.id;
        			})
        			$.ajax({
        				type:"POST",
        				url:"${APP_PATH}/cert/deleteByBatch.do",
        				data:jsonObj,
        				success:function(result){
        					if(result.success){
        						querypage(1);
        					}else{
        						alert(result.message);
        					}
        				}
        			})
        		})
        </script>
  </body>
</html>
