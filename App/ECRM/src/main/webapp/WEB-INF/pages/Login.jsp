<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/25/2015
  Time: 10:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>ECRM - Equipment Classroom Management</title>
    <link href="resource/css/login.css" rel="stylesheet"/>
</head>
<body>
  <div class="top">
    <img src="/resource/img/logo.png">
    <p>Quản lý thiết bị phòng học - Trường Đại Học FPT</p>
  </div>
  <div class="mid">
    <form class="login-form" action="/login" method="post">
      <p>Đăng nhập</p>
      <c:if test="${requestScope.MESSAGE!=null}"><div style="color: #d9534f; margin: 0 5px">${requestScope.MESSAGE}</div></c:if>
      <p class="old">Tài khoản</p>
      <p><input type="text" value="" name="username" placeholder="Tài khoản"/></p>
      <p class="old">Mật khẩu</p>
      <p><input type="password" value="" name="password" placeholder="Mật khẩu"/></p>
      <p><input type="checkbox" name="rememberAccount"/> Duy trì đăng nhập</p>
      <button type="submit">Đăng nhập</button>
    </form>
    <img src="/resource/img/img1.png">
  </div>
  <div class="bottom">
    <p><b>Địa chỉ: </b> Tòa nhà Inovation, công viên PM Quang Trung, Quận 12, TP Hồ Chí Minh</p>
    <p><b>Group 10 - Capstone Project Summer 2015</b></p>
  </div>
</body>
</html>
