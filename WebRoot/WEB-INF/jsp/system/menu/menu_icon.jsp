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
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/layuiadmin/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="static/layuiadmin/style/admin.css" media="all">
		
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>	

		<script type="text/javascript">
				
				$(top.hangge());
				//保存
				function save(){
					if($("#MENU_ICON").val()==""){
						alert('请选择图标');
						return false;
					}
					$("#menuForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
			function seticon(icon){
				$("#MENU_ICON").val(icon);
			}	
			
		</script>
		
	</head>
	
	<body>
		<form action="menu/editicon.do" name="menuForm" id="menuForm" method="post">
			<input type="hidden" name="MENU_ID" id="menuId" value="${pd.MENU_ID}"/>
			<input type="hidden" name="MENU_ICON" id="MENU_ICON" value=""/>
			<div id="zhongxin">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<tr>
					<td><label onclick="seticon('layui-icon-home');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-home"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-set');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-set"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-list');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-list"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-template');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-template"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-app');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-app"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-senior');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-senior"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-user');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-user"></i></span></label></td>
					<td><label onclick="seticon('layui-icon-auz');"><input name="form-field-radio" type="radio" value="icon-edit"><span class="lbl">&nbsp;<i class="layui-icon layui-icon-auz"></i></span></label></td>
				</tr>
				<tr>

				</tr>
				<tr>
				<td style="text-align: center;" colspan="100">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
			</table>
			</div>
			<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" /></div>
		</form>
	</body>
</html>