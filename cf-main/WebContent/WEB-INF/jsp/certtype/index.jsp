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
    
    input[type=checkbox] {
        width:18px;
        height:18px;        
    }
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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
			  </div>
			  <div class="panel-body">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th >名称</th>
                  <th >商业公司</th>
                  <th >个体工商户</th>
                  <th >个人经营</th>
                  <th >政府及非营利组织</th>
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
				});
			    certtype();
            });
            function certtype(){
            	$.ajax({
			    	type:"post",
			    	url:"${APP_PATH}/cert/certtype.do",
			    	success:function(result){
			    		if(result.success){
			    			var context="";
			    			$.each(result.data,function(i,n){
			    				 context+='<tr>';
			                     context+='<td>'+n.name+'</td>';
			                     context+='<td><input type="checkbox" id="'+n.id+'" accttype="0"></td>';
			                     context+='<td><input type="checkbox" id="'+n.id+'" accttype="1"></td>';
			                     context+='<td><input type="checkbox" id="'+n.id+'" accttype="2"></td>';
			                     context+='<td><input type="checkbox" id="'+n.id+'" accttype="3"></td>';
			                     context+='</tr>';			    
			    			})
			    			$("tbody").html(context);
			    			var checkbox=$(":checkbox");
			    			$.each(result.data1,function(i,n){
			    				$.each(checkbox,function(j,m){
			    					if(m.id==n.certid&&$(m).attr("accttype")===n.accttype){			    						
			    						m.checked="checked";
			    					}
			    				})
			    			})
			    		}else{
			    			alert(result.message);
			    		}
			    	}
			    })
            };
            $(document).on("click", ":checkbox", function() {
            	var jsonObj={};
            	var selected=$("tbody tr td input:checked");
            	$.each(selected,function(i,n){
            		jsonObj["accountTypeCerts["+i+"].accttype"]=$(n).attr("accttype");
            		jsonObj["accountTypeCerts["+i+"].certid"]=$(n).attr("id");
            	})
            	$.ajax({
            		type:"post",
            		url:"${APP_PATH}/cert/updateCerttype.do",
            		data:jsonObj,
            		success:function(result){
            			if(result.success){
            				certtype();
            			}else{
            				alert(result.message);
            			}
            		}
            	})
            })
        </script>
  </body>
</html>
