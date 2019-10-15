<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="top_layui.jsp"%>
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<body class="layui-layout-body">
<div id="LAY_app">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<!-- 头部区域 -->
			<ul class="layui-nav layui-layout-left">
				<li class="layui-nav-item layadmin-flexible" lay-unselect>
					<a href="javascript:;" layadmin-event="flexible" title="侧边伸缩">
						<i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
					</a>
				</li>
				<c:if test="${user.USERNAME == 'admin'}">
					<li class="layui-nav-item" lay-unselect>
						<a lay-href="menu.do" title="菜单管理">
							<i class="layui-icon layui-icon-menu-fill"></i>
							菜单管理
						</a>
					</li>
				</c:if>

				<li class="layui-nav-item" lay-unselect>
					<a href="javascript:;" layadmin-event="refresh" title="刷新">
						<i class="layui-icon layui-icon-refresh-3"></i>
					</a>
				</li>
			</ul>
			<ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
				<li class="layui-nav-item layui-hide-xs" lay-unselect>
					<a href="javascript:;" layadmin-event="theme">
						<i class="layui-icon layui-icon-theme"></i>
					</a>
				</li>
				<li class="layui-nav-item layui-hide-xs" lay-unselect>
					<a href="javascript:;" layadmin-event="fullscreen">
						<i class="layui-icon layui-icon-screen-full"></i>
					</a>
				</li>
				<li class="layui-nav-item" lay-unselect>
					<a href="javascript:;">
						<cite>${user.USERNAME}</cite>
					</a>
					<dl class="layui-nav-child">
						<dd><a lay-href="user/goEditU.do?USER_ID=${user.USER_ID}&fx=head">修改资料</a></dd>
						<c:if test="${user.USERNAME == 'admin'}">
							<dd><a lay-href="head/goProductCode.do">代码生成</a></dd>
						</c:if>
						<hr>
						<dd style="text-align: center;"><a href="logout">退出</a></dd>
					</dl>
				</li>

				<li class="layui-nav-item layui-hide-xs" lay-unselect>
<%--					<a href="javascript:;" layadmin-event="about"><i class="layui-icon layui-icon-more-vertical"></i></a>--%>
					<a href="javascript:;"><i class="layui-icon"></i></a>
				</li>
<%--				<li class="layui-nav-item layui-show-xs-inline-block layui-hide-sm" lay-unselect>--%>
<%--					<a href="javascript:;" layadmin-event="more"><i class="layui-icon layui-icon-more-vertical"></i></a>--%>
<%--				</li>--%>
			</ul>
		</div>

		<!-- 侧边菜单 -->
		<div class="layui-side layui-side-menu">
			<div class="layui-side-scroll">
				<div class="layui-logo" lay-href="home/console.html">
					<span>${pd.SYSNAME}</span>
				</div>

				<ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
					<c:forEach items="${menuList}" var="menu">
						<c:if test="${menu.hasMenu}">
							<li data-name="template" class="layui-nav-item">
								<c:if test="${menu.MENU_URL != '#'}">
									<a lay-href="${menu.MENU_URL}" lay-tips="${menu.MENU_NAME }" lay-direction="2">
										<i class="layui-icon ${menu.MENU_ICON == null ? 'layui-icon-template' : menu.MENU_ICON}"></i>
										<cite>${menu.MENU_NAME }</cite>
									</a>
								</c:if>
								<c:if test="${menu.MENU_URL == '#'}">
									<a href="javascript:;" lay-tips="${menu.MENU_NAME }" lay-direction="2">
										<i class="layui-icon ${menu.MENU_ICON == null ? 'layui-icon-template' : menu.MENU_ICON}"></i>
										<cite>${menu.MENU_NAME }</cite>
									</a>
								</c:if>
								<dl class="layui-nav-child">
									<c:forEach items="${menu.subMenu}" var="sub">
										<c:if test="${sub.hasMenu}">
										<c:choose>
											<c:when test="${not empty sub.MENU_URL}">
												<dd><a lay-href="${sub.MENU_URL }">${sub.MENU_NAME }</a></dd>
											</c:when>
											<c:otherwise>
												<dd><a href="javascript:;">${sub.MENU_NAME }</a></dd>
											</c:otherwise>
										</c:choose>
										</c:if>
									</c:forEach>

								</dl>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>

		<!-- 页面标签 -->
		<div class="layadmin-pagetabs" id="LAY_app_tabs">
			<div class="layui-icon layadmin-tabs-control layui-icon-prev" layadmin-event="leftPage"></div>
			<div class="layui-icon layadmin-tabs-control layui-icon-next" layadmin-event="rightPage"></div>
			<div class="layui-icon layadmin-tabs-control layui-icon-down">
				<ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
					<li class="layui-nav-item" lay-unselect>
						<a href="javascript:;"></a>
						<dl class="layui-nav-child layui-anim-fadein">
							<dd layadmin-event="closeThisTabs"><a href="javascript:;">关闭当前标签页</a></dd>
							<dd layadmin-event="closeOtherTabs"><a href="javascript:;">关闭其它标签页</a></dd>
							<dd layadmin-event="closeAllTabs"><a href="javascript:;">关闭全部标签页</a></dd>
						</dl>
					</li>
				</ul>
			</div>
			<div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
				<ul class="layui-tab-title" id="LAY_app_tabsheader">
					<li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i class="layui-icon layui-icon-home"></i></li>
				</ul>
			</div>
		</div>


		<!-- 主体内容 -->
		<div class="layui-body" id="LAY_app_body">
			<div class="layadmin-tabsbody-item layui-show">
				<iframe src="home/console.html" frameborder="0" class="layadmin-iframe"></iframe>
			</div>
		</div>

		<!-- 辅助元素，一般用于移动设备下遮罩 -->
		<div class="layadmin-body-shade" layadmin-event="shade"></div>
	</div>
</div>

<script src="static/layuiadmin/layui/layui.js"></script>
<script>
	layui.config({
		base: 'static/layuiadmin/' //静态资源所在路径
	}).extend({
		index: 'lib/index' //主入口模块
	}).use('index');
</script>

</body>
</html>