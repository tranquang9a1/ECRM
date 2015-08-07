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
        <li onclick="showModal(1, 'modal-updatePass')">Thay đổi mật khẩu</li>
        <li>Thông tin tài khoản</li>
        <li><a href="/dang-xuat"> Đăng xuất</a></li>
      </ul>
    </div>
  </div>
  <div class="modal modal-small" id="modal-updatePass">
    <div class="content-modal" style="height:217px">
      <div class="header-modal title" style="margin: 0">Đổi mật khẩu</div>
      <div class="body-modal">
        <div class="group-control">
          <div class="name">Mật khẩu mới</div>
          <div class="control">
            <input type="password" id="password">
          </div>
        </div>
        <div class="group-control">
          <div class="name">Xác nhận lại</div>
          <div class="control">
            <input type="password" id="rePassword">
          </div>
        </div>
      </div>
      <div class="footer-modal" style="border-top: 1px solid #ccc">
        <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-updatePass')" value="Thoát"/>
        <input type="button" class="btn btn-primary" onclick="changePassword('${user.username}', 0)" value="Đổi mật khẩu"/>
      </div>
    </div>
    <div class="black-background"></div>
  </div>
</div>
