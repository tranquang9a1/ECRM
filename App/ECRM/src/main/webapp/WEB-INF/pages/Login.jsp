<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/25/2015
  Time: 10:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ECRM - Equipment Classroom Management</title>
    <link href="resource/css/layout.css" rel="stylesheet"/>
</head>
<body>
  <form class="login-form" action="/login" method="post">
    <p>ECRM</p>
    <p><input type="text" value="" name="username" placeholder="Tài khoản"/></p>
    <p><input type="password" value="" name="password" placeholder="Mật khẩu"/></p>
    <p><input type="checkbox" name="rememberAccount"/> Duy trì đăng nhập</p>
    <button type="submit">Đăng nhập</button>
  </form>
</body>
</html>
