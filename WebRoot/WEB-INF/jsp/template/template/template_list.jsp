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

<div class="layui-fluid">
	<div class="layui-card">
		<div class="layui-form layui-card-header layuiadmin-card-header-auto">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">ID</label>
					<div class="layui-input-block">
						<input type="text" name="id" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">用户名</label>
					<div class="layui-input-block">
						<input type="text" name="username" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">邮箱</label>
					<div class="layui-input-block">
						<input type="text" name="email" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">性别</label>
					<div class="layui-input-block">
						<select name="sex">
							<option value="0">不限</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-user-front-search">
						<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
					</button>
				</div>
			</div>
		</div>

		<div class="layui-card-body">
			<div style="padding-bottom: 10px;">
				<button class="layui-btn layuiadmin-btn-useradmin" data-type="batchdel">删除</button>
				<button class="layui-btn layuiadmin-btn-useradmin" data-type="add">添加</button>
			</div>

			<table class="layui-table" id="LAY-data-manage" lay-filter="LAY-data-manage">
				<thead>
				<tr>
					<th lay-data="{field:'TEMPLATE_ID', type: 'checkbox'}">
					</th>
					<th lay-data="{field:'index'}">序号</th>
					<th lay-data="{field:'FIELD_1'}">字段_String</th>
					<th lay-data="{field:'FIELD_2'}">字段_Int</th>
					<th lay-data="{field:'FIELD_3', sort:true}">字段_Date</th>
					<th class="center" lay-data="{field:'ACTION', width:200, toolbar: '#table-tr-action'}">操作</th>
				</tr>
				</thead>

				<tbody>

				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td class='center' style="width: 30px;">${var.TEMPLATE_ID}</td>
									<td class='center' style="width: 30px;">${vs.index+1}</td>
									<td>${var.FIELD_1}</td>
									<td>${var.FIELD_2}</td>
									<td>${var.FIELD_3}</td>
									<td>

									</td>
								</tr>

							</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" style="text-align: center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>


				</tbody>
			</table>
			<script type="text/html" id="imgTpl">
				<img style="display: inline-block; width: 50%; height: 100%;" src={{ d.avatar }}>
			</script>
			<script type="text/html" id="table-tr-action">
				<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
						class="layui-icon layui-icon-edit"></i>编辑</a>
				<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
						class="layui-icon layui-icon-delete"></i>删除</a>
			</script>
		</div>
	</div>
</div>

<script src="static/layuiadmin/layui/layui.js"></script>
<script>
	layui.config({
		base: 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
	}).use(['index', 'table'], function () {
		var $ = layui.$, form = layui.form, table = layui.table;

		<c:choose>
			<c:when test="${not empty varList}">
				<c:if test="${QX.cha == 1 }">
					table.init('LAY-data-manage', {
						limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
						//支持所有基础参数
					});
				</c:if>
			</c:when>
		</c:choose>

		//监听搜索
		form.on('submit(LAY-user-front-search)', function (data) {
			var field = data.field;

			//执行重载
			table.reload('LAY-data-manage', {
				where: field
			});
		});

		//事件
		var active = {
			batchdel: function () {
				var checkStatus = table.checkStatus('LAY-data-manage'), checkData = checkStatus.data; //得到选中的数据
				if (checkData.length === 0) {
					return layer.msg('请选择数据');
				}
				var dataIDs = "";
				for(var i = 0; i < checkData.length; i++){
					if(dataIDs == ""){
						dataIDs += checkData[i].TEMPLATE_ID;
					}else {
						dataIDs += "," + checkData[i].TEMPLATE_ID;
					}
				}
				layer.confirm('确定删除吗？', function (index) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>template/deleteAll.do?tm='+new Date().getTime(),
						data: {DATA_IDS:dataIDs},
						dataType:'json',
						cache: false,
						success: function(data){
							layer.msg('已删除');
							window.location.reload()
						}
					});


				});
			}
			, add: function () {
				layer.open({
					type: 2
					, title: '添加数据'
					, content: 'template/goAdd.do'
					, maxmin: true
					, area: ['700px', '500px']
					, btn: ['确定', '取消']
					, yes: function (index, layero) {
						var iframeWindow = window['layui-layer-iframe' + index]
								, submitID = 'LAY-front-submit'
								, submit = layero.find('iframe').contents().find('#' + submitID);
						//监听提交
						iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
							var field = data.field; //获取提交的字段
							alert(JSON.stringify(field));
							//提交 Ajax 成功后，静态更新表格中的数据
							//$.ajax({});
							table.reload('LAY-front-submit'); //数据刷新
							layer.close(index); //关闭弹层
						});

						submit.trigger('click');
					}
				});
			}
		};

		$('.layui-btn.layuiadmin-btn-useradmin').on('click', function () {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	});
</script>
</body>
</html>

