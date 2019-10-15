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
	<input type="hidden" name="TEMPLATE_ID" value="${pd.TEMPLATE_ID}">
	<div class="layui-form-item">
		<label class="layui-form-label">字段_String</label>
		<div class="layui-input-inline">
			<input type="text" name="FIELD_1" lay-verify="required" value="${pd.FIELD_1}" placeholder="请输入字段_String" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">字段_Int</label>
		<div class="layui-input-inline">
			<input type="number" name="FIELD_2" lay-verify="number" value="${pd.FIELD_2}" placeholder="请输入字段_Int" autocomplete="off" class="layui-input">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">字段_Date</label>
		<div class="layui-input-inline">
			<input type="text" id="FIELD_3" name="FIELD_3" value="${pd.FIELD_3}" lay-verify="date" placeholder="请输入字段_Date" autocomplete="off" class="layui-input">
		</div>
	</div>
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
			elem: '#FIELD_3'
		});
	})

</script>
</body>
</html>

