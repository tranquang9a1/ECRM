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
    <link href="/resource/css/login.css" rel="stylesheet"/>
</head>
<body>
  <div class="top">
    <img src="/resource/img/logo.png">
    <p>Hệ thống quản lý thiết bị phòng học</p>
  </div>
  <div class="mid" style="z-index: 1;">
    <div class="introduction">
      <p>ECRM - Hệ thống quản lý thiết bị phòng học</p>
      <div></div>
      <p>Hệ thống được phát triển nhẳm tối ưu hóa thời gian báo cáo hư hại từ giảng viên, cùng việc quản lý thời hạn sử dụng thiết bị</p>
    </div>
    <form class="login-form" action="/login" method="post">
      <p style="text-align: center">Đăng nhập</p>
      <c:if test="${requestScope.MESSAGE!=null}"><div style="color: #d9534f; margin: 0 5px">${requestScope.MESSAGE}</div></c:if>
      <p class="old">Tài khoản</p>
      <p><input type="text" value="" name="username" placeholder="Tài khoản"/></p>
      <p class="old">Mật khẩu</p>
      <p><input type="password" value="" name="password" placeholder="Mật khẩu"/></p>
      <p><button type="submit">Đăng nhập</button></p>
    </form>
    <div class="content-bg" style="z-index: 1;">
      <div></div>
      <img src="/resource/img/background/LargeClassroomWindowView_0.JPG">
    </div>
    <div class="bottom" style="z-index: 2">
      <p><b>Group 10 - Capstone Project Summer 2015</b></p>
      <p><b>Địa chỉ: </b> Tòa nhà Inovation, công viên PM Quang Trung, Quận 12, TP Hồ Chí Minh</p>
    </div>
  </div>

</body>
</html>
