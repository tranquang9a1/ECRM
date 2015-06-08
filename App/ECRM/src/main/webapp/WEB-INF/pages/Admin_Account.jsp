<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>ECRM - Equipment Classroom Management</title>
  <link rel="stylesheet" href="../../resource/css/font-awesome.css"/>
  <link rel="stylesheet" href="../../resource/css/layout.css"/>
  <link rel="stylesheet" href="../../resource/css/general.css"/>
  <link rel="stylesheet" href="../../resource/css/management.css"/>
  <link rel="stylesheet" href="../../resource/css/roomtype-2.css"/>
  <script src="../../resource/js/jquery-1.11.3.js"></script>
  <script src="../../resource/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<c:set var="user" value="${sessionScope.USER}"/>
<c:set var="tab" value="${requestScope.ACTIVETAB}"/>
<c:set var="users" value="${requestScope.ALLUSERS}"/>

<div class="contain-layout">
  <jsp:include flush="true" page="Header.jsp"/>
</div>
<div class="container">
  <div class="title page-title right-button">
    <p>Quản lý tài khoản</p>

    <div class="clear"></div>
  </div>
  <div class="body-content">
    <div class="tab">
      <div class="tab-medium">
        <ul>
          <li class="active" onclick="changeTab('tab1', this)">Tài khoản</li>
          <li onclick="changeTab('tab2', this)">Tạo tài khoản</li>
        </ul>
      </div>
      <div class="content-tab">
        <div id="tab1" class="body-tab">
          <div class="table small-table">
            <div class="header-table">
              <div class="username">
                <div>Username</div>
              </div>
              <div class="phone">
                <div>Số điện thoại</div>
              </div>
              <div class="status">
                <div>Trạng thái</div>
              </div>
              <div class="check-box"></div>
              <p class="clear"></p>
            </div>
            <div class="body-table">
              <form action="/admin/editAccount" id="editAccount">
                <input type="hidden" value="" id="username" name="username"/>
                <c:forEach items="${users}" var="u">
                  <div class="row">
                    <div class="username">
                      <div>${u.username}</div>
                    </div>
                    <div class="phone">
                      <div>${u.tblUserInfoByUsername.phone}</div>
                    </div>
                    <div class="status">
                      <c:if test="${u.status}">
                        <div>Activated</div>
                      </c:if>
                      <c:otherwise>
                        <div>Deactivated</div>
                      </c:otherwise>
                    </div>
                    <div class="check-box">
                      <input type="checkbox" name="" />
                    </div>
                    <p class="clear"></p>
                  </div>
                </c:forEach>
              </form>
            </div>

          </div>
          <input type="button" value="Activate" name="Action" onclick="confirm(1)"/>
          <input type="button" value="Deactivate" name="Action" onclick="confirm(2)"/>
        </div>
        <div id="tab2" class="body-tab">

        </div>
      </div>
    </div>
  </div>
</div>
</div>

<script src="../../resource/js/script.js"></script>

<script>
  function doAction(choose, object) {
    closeConform();
    switch (choose) {
      case 1:
        showModal(0, 'modal-1');
        document.getElementById('createClassroom').submit();
        alert("Phong hoc đã được lưu thành công!");
        break;
    }
  }
  document.getElementById("${tab}").className = "body-tab active";
</script>
</body>
</html>
