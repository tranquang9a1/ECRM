<%@ page import="com.ecrm.Controller.UserController" %>
<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:set var="tab" value="${requestScope.ACTIVELEFTTAB}"/>
<script src="../resource/js/socket.io.js"></script>
<script src="../resource/js/socket-io.js"></script>
<div class="header-bar">
    <div class="logo">Quản lý thiết bị phòng học - Trường Đại Học FPT</div>
    <div class="account-notify">
        <div class="account-control">
            <p>${user.tblUserInfoByUsername.fullName}</p>
            <img src="../../resource/img/gears.png"/>
            <div class="clear"></div>
            <div class="account-setting">
                <img src="../../resource/img/pointer.png"/>
                <ul>
                    <li>Thiết lập</li>
                    <li>Cài đặt tài khoản</li>
                    <li><a href="/logout"> Đăng xuất</a></li>
                </ul>
            </div>
        </div>
        <c:import url="/thong-bao/all-notify" />
    </div>
</div>
<div class="list-category">
    <ul class="main-item">
        <%--<li>--%>
            <%--<a href="">--%>
                <%--<img src=""/>--%>
                <%--<p>Trang Chủ</p>--%>
            <%--</a>--%>
        <%--</li>--%>
        <c:if test="${user.tblRoleByRoleId.name == 'Staff'}">
            <li id="STAFF_NOTIFY">
                <a href="/thong-bao">
                    <img src=""/>
                    <p>Thông Báo</p>
                </a>
            </li>
            <li id="STAFF_EQUIP">
                <a href="/staff/equipment">
                    <img src=""/>
                    <p>Thiết Bị</p>
                </a>
            </li>
            <li id="STAFF_CLASSROOM">
                <a href="/staff/classroom?ACTIVETAB=tab1">
                    <img src=""/>
                    <p>Phòng Học</p>
                </a>
            </li>
            <li>
                <img src=""/>
                <p>Thống Kê</p>
            </li>
            <li id="STAFF_SCHEDULE">
                <a href="/staff/schedule">
                    <img src=""/>
                    <p>Xếp Lịch</p>
                </a>
            </li>
        </c:if>

        <c:if test="${user.tblRoleByRoleId.name == 'Admin'}">
            <li>
                <a href="/Admin_Account.jsp">
                    <img src=""/>
                    <p>Tài Khoản</p>
                </a>
            </li>
        </c:if>

        <c:if test="${user.tblRoleByRoleId.name == 'Teacher'}">
            <li id="USER_NOTIFY">
                <a href="/giang-vien/thong-bao">
                    <img src="" />
                    <p>Thông báo</p>
                </a>
            </li>
            <li id="USER_SCHEDULE">
                <a href="/giang-vien/lich-day">
                    <img src="" />
                    <p>Lịch dạy</p>
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="" />
                    <p>Tài khoản</p>
                </a>
            </li>
        </c:if>
    </ul>
    <script>
        document.getElementById("${tab}").className += " active";
        connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});
    </script>
</div>
