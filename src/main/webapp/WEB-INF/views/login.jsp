<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小码哥客户关系管理系统</title>
    <link rel="stylesheet" href="/static/css/style.css">
    <%@include file="common.jsp"%>
    <script type="text/javascript">
        $(function(){
            $("#submitBtn").on("click",function(){
                $("#loginBtn").form("submit",{
                    url:'/login',
                    success:function(data){
                        //把返回的json数据封装成一个json对象
                        //需要的数据{"success":true,"msg":'登录成功'}
                        console.log(data);
                        data=$.parseJSON(data);
                        if(data.success){
                            //如果登录成功,跳转到/main
                            window.location.href="/main";
                        }else {
                            $.messager.alert("温馨提示",data.msg,"error");
                        }
                    }
                })
            })
        })
    </script>
</head>
<body>
  <section class="container">
    <div class="login">
      <h1>用户登录</h1>
      <form id="loginBtn" method="post">
        <p><input type="text" name="username" value="" placeholder="账号"></p>
        <p><input type="password" name="password" value="" placeholder="密码"></p>
        <p class="submit">
        	<input id="submitBtn" type="button" value="登录">
        	<input id="resetBtn" type="button" value="重置">
        </p>
      </form>
    </div>
  </section>
<div style="text-align:center;" class="login-help">
	<p>Copyright ©2015 广州小码哥教育科技有限公司</p>
</div>
</html>