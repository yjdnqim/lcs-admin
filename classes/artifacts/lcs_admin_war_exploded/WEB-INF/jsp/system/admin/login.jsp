<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- jsp文件头和头部 -->
    <%@ include file="top_layui.jsp"%>

    <link rel="stylesheet" href="static/layuiadmin/style/login.css" media="all">
</head>
<body>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>${pd.SYSNAME}</h2>
            <p>${pd.SYSNAME}</p>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username"
                       for="LAY-user-login-username"></label>
                <input type="text" name="username" id="LAY-user-login-username" lay-verify="required" placeholder="用户名"
                       class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-password"></label>
                <input type="password" name="password" id="LAY-user-login-password" lay-verify="required"
                       placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"
                               for="LAY-user-login-vercode"></label>
                        <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required"
                               placeholder="图形验证码" class="layui-input">
                    </div>
                    <div class="layui-col-xs5">
                        <div style="margin-left: 10px;">
                            <img src="code.do" class="layadmin-user-login-codeimg"
                                 id="LAY-user-get-vercode">
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-form-item" style="margin-bottom: 20px;">
                <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-login-submit">登 入</button>
            </div>

        </div>
    </div>

</div>

<script src="static/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: 'static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function () {
        var admin = layui.admin, form = layui.form;
        form.render();
        //提交
        form.on('submit(LAY-user-login-submit)', function (obj) {

            var loginname = obj.field.username;
            var password = obj.field.password;
            var vercode = "qq313596790fh" + loginname + ",fh," + password + "QQ978336446fh" + ",fh," + obj.field.vercode;
            //请求登入接口
            admin.req({
                url: "login_login" //实际使用请改成服务端真实接口
                , data: {KEYDATA: vercode, tm: new Date().getTime()}
                , done: function (res) {
                    if("success" == res.result){
                        //saveCookie();
                        //登入成功的提示与跳转
                        layer.msg('登录成功', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            location.href = 'main/index'; //后台主页
                        });
                    }else if("usererror" == res.result){
                        layer.msg('用户名或密码有误', {
                            offset: '15px'
                            , icon: 2
                            , time: 1000
                        })
                    }else if("codeerror" == res.result){
                        layer.msg('验证码输入有误', {
                            offset: '15px'
                            , icon: 2
                            , time: 1000
                        })
                    }else{
                        layer.msg('缺少参数', {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        })
                    }
                }
            });

        });

    });
</script>
</body>
</html>