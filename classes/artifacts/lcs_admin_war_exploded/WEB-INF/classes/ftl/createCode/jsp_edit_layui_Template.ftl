<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top_layui.jsp" %>
</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-form-admin" id="layuiadmin-form-admin" style="padding: 20px 30px 0 0;">
	<input type="hidden" name="${objectNameUpper}_ID" value="${r"${pd."}${objectNameUpper}_ID${r"}"}"">
<#list fieldList as var>
	<#if var[3] == "是">

	<div class="layui-form-item">
		<label class="layui-form-label">${var[2] }</label>
		<div class="layui-input-inline">
		<#if var[1] == 'Integer'>
			<input type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" lay-verify="number" placeholder="请输入${var[2] }" autocomplete="off" class="layui-input">
		<#else>
			<input type="text" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" lay-verify="required" placeholder="请输入${var[2] }" autocomplete="off" class="layui-input">
		</#if>
		</div>
	</div>
	</#if>
</#list>
	<div class="layui-form-item layui-hide">
		<button lay-submit lay-filter="LAY-front-submit" id="LAY-front-submit">确认</button>
	</div>
</div>
<script src="static/layuiadmin/layui/layui.js"></script>
<script>

	layui.config({
		base: 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
	}).use(['index', 'form', 'laydate'], function(){
		var form = layui.form, laydate = layui.laydate;
		laydate.render({
			elem: '#DATE_FIELD_ELE_ID'
		});
	})

</script>
</body>
</html>