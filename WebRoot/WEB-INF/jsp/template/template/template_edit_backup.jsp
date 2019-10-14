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
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<style type="text/css">
			.title{
				width: 120px;
			}
		</style>
		<script type="text/javascript">
	
	
			//保存
			function save(){
							if($("#FIELD_1").val()==""){
					$("#FIELD_1").tips({
						side:3,
			            msg:'请输入字段_String',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#FIELD_1").focus();
					return false;
				}
				if($("#FIELD_2").val()==""){
					$("#FIELD_2").tips({
						side:3,
			            msg:'请输入字段_Int',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#FIELD_2").focus();
					return false;
				}
				if($("#FIELD_3").val()==""){
					$("#FIELD_3").tips({
						side:3,
			            msg:'请输入字段_Date',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#FIELD_3").focus();
					return false;
				}
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
			
		</script>
	</head>
<body>
	<form action="template/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="TEMPLATE_ID" id="TEMPLATE_ID" value="${pd.TEMPLATE_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<th class="title">字段_String:</th>
				<td><input type="text" name="FIELD_1" id="FIELD_1" value="${pd.FIELD_1}" maxlength="32" placeholder="这里输入字段_String" title="字段_String"/></td>
			</tr>
			<tr>
				<th class="title">字段_Int:</th>
				<td><input type="number" name="FIELD_2" id="FIELD_2" value="${pd.FIELD_2}" maxlength="32" placeholder="这里输入字段_Int" title="字段_Int"/></td>
			</tr>
			<tr>
				<th class="title">字段_Date:</th>
				<td><input class="span10 date-picker" name="FIELD_3" id="FIELD_3" value="${pd.FIELD_3}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="字段_Date" title="字段_Date"/></td>
			</tr>
			<tr>
				<th class="title"></th>
				<td><!-- style="text-align: center;" -->
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>