<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/3/2015
  Time: 2:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.USER}"/>

<div class="header">
  <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
  <div class="account-control">
    <p>${user.tblUserInfoByUsername.fullName}</p>
    <img src="/resource/img/gears.png"/>
    <div class="account-setting">
      <img src="../../resource/img/pointer.png"/>
      <ul>
        <li>Thay đổi mật khẩu</li>
        <li>Thông tin tài khoản</li>
        <li><a href="/dang-xuat"> Đăng xuất</a></li>
      </ul>
    </div>
  </div>
</div>
