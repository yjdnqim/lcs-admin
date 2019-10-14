<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<style>
		.width900 {
			width:900px;
		}
	</style>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	<link href="plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
	<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table.js"></script>
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;">
						<a class="btn btn-small" onclick="add();">添加计算机</a>
						<a class="btn btn-small" onclick="edit();">修改算机</a>
						<a class="btn btn-small" onclick="del();">删除计算机</a>
						<a class="btn btn-small" onclick="showDetailForButton();">查看计算机详情</a>
						<a class="btn btn-small" onclick="getKey();">获取客户端key</a>
						<a class="btn btn-small" onclick="restart();">重启Server</a>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<div style="overflow: auto;width:100%">
			<table id="table_report" class="table table-striped table-bordered table-hover" style="min-width: 1000px;">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>计算机编号</th>
						<th>名称</th>
						<th>IP</th>
						<th>平台</th>
						<th>描述</th>
						<th>最后保持连接时间</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${agents}" var="var" varStatus="vs">
						<tr ondblclick="showDetails('${var.IP}')">
							<td class="center">
								<label><input type='checkbox' name='IP' value="${var.IP}" /><span class="lbl"></span></label>
							</td>
							<td>${vs.index+1}</td>
							<td>${var.COMPUTER_ID}</td>
							<td>${var.NAME}</td>
							<td>${var.IP}</td>
							<td>${var.OS}</td>
							<td>${var.DESCRIPTION}</td>
							<td>${var.LAST_CONTACT}</td>
							<td>
								<c:if test="${var.STATUS=='Inactive'}">
									<span style="color:red">${var.STATUS}</span>
								</c:if>
								<c:if test="${var.STATUS=='Active'}">
									${var.STATUS}
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="添加计算机";
			 diag.URL = '<%=basePath%>computer/goAdd.do';
			 diag.Width = 750;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		function showDetailForButton() {
			if ($("input[name='IP']:checked").length == 0) {
				bootbox.dialog("您没有选择任何内容!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").length > 1) {
				bootbox.dialog("不允许选中多行!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			}
			var ip = $("input[name='IP']:checked").val();
			showDetails(ip);
		}
		
		//显示详情
		function showDetails(IP){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="计算机详情";
			 diag.URL = '<%=basePath%>computer/goDetails.do?IP=' + IP;
			 diag.Width = 800;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del() {
			if ($("input[name='IP']:checked").length == 0) {
				bootbox.dialog("您没有选择任何内容!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").length > 1) {
				bootbox.dialog("不允许选中多行!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").val() == '127.0.0.1'){
				bootbox.dialog("服务端不允许被删除!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			}
			var ip = $("input[name='IP']:checked").val();
			bootbox.confirm("您确定要删除选择的计算机吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>computer/delete.do?IP="+ip+"&tm="+new Date().getTime();
					$.get(url,function(data){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					});
				}
			});
		}
		
		//重启ossec
		function restart() {
			bootbox.confirm("您确定要重启Server吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>computer/restartServer.do?tm=" + new Date().getTime();
					$.get(url,function(data){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					});
				}
			});
		}
		
		//获取key
		function getKey() {
			if ($("input[name='IP']:checked").length == 0) {
				bootbox.dialog("您没有选择任何内容!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").length > 1) {
				bootbox.dialog("不允许选中多行!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").val() == '127.0.0.1'){
				bootbox.dialog("不能获取服务端key!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			}
			var ip = $("input[name='IP']:checked").val();
			var url = "<%=basePath%>computer/getAgentKey.do?ip=" + ip + "&tm=" + new Date().getTime();
			$.get(url,function(data){
				bootbox.dialog("key:" + data, [{"label" : "关闭","class" : "btn-lg btn-success","callback": function() {}}]);
			});
		}
		
		//修改
		function edit(){
			if ($("input[name='IP']:checked").length == 0) {
				bootbox.dialog("您没有选择任何内容!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").length > 1) {
				bootbox.dialog("不允许选中多行!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			} else if ($("input[name='IP']:checked").val() == '127.0.0.1'){
				bootbox.dialog("不能修改服务端!", [{"label" : "关闭","class" : "btn-small btn-success","callback": function() {}}]);
				return;
			}
			var ip = $("input[name='IP']:checked").val();
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="修改计算机";
			 diag.URL = '<%=basePath%>computer/goEdit.do?IP='+ip;
			 diag.Width = 750;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 top.jzts();
					 setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>orginfo/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>orginfo/excel.do';
		}
		</script>
		
	</body>
</html>

