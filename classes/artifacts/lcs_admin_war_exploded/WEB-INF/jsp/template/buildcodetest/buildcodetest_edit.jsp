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
	<input type="hidden" name="BUILDCODETEST_ID" value="${pd.BUILDCODETEST_ID}"">

	<div class="layui-form-item">
		<label class="layui-form-label">字段3</label>
		<div class="layui-input-inline">
			<input type="text" layer-date name="F3" id="F3" value="${pd.F3}" lay-verify="required" placeholder="请输入字段3" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">字段一</label>
		<div class="layui-input-inline">
			<input type="text" name="F1" id="F1" value="${pd.F1}" lay-verify="required" placeholder="请输入字段一" autocomplete="off" class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">字段二</label>
		<div class="layui-input-inline">
			<input type="number" name="F2" id="F2" value="${pd.F2}" lay-verify="number" placeholder="请输入字段二" autocomplete="off" class="layui-input">
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
			elem: '#F3'
		});
	})

</script>
</body>
</html>