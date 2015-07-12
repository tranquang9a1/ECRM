<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 7/12/2015
  Time: 6:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tmf" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="user" value="${sessionScope.USER}"/>
<c:set var="tab" value="${requestScope.TABCONTROL}"/>

<c:set var="numberOfNotify" value="${requestScope.NUMBEROFNOTIFY}"/>
<c:set var="newReport" value="${requestScope.NEWREPORT}"/>

<html>
<head>
    <title>Hệ thống quản lý thiết bị phòng học</title>
    <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/roomtype-2.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>

    <script src="/resource/js/jquery-1.11.3.js"></script>
    <script src="/resource/js/socket.io.js"></script>

    <script src="/resource/js/socket-io.js"></script>
    <script src="/resource/js/staff-notify.js"></script>
    <script src="/resource/js/newTemplate.js"></script>
</head>
<body>
  <div class="layout-background">
    <div class="container">
      <div class="header">
        <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
        <div class="account-control">
          <p>${user.tblUserInfoByUsername.fullName}</p>
          <img src="../../resource/img/gears.png"/>
        </div>
      </div>
      <div class="content-body">
        <div class="left-category">
          <ul>
            <li id="STAFF_REPORT">
              <a href="/bao-cao">
                <p>Báo cáo</p>
              </a>
            </li>
            <li id="STAFF_NOTIFICATION">
              <a href="javascript:void(0)" onclick="showNotifies()">
                <p class="part-float">Thông báo</p>
                <c:if test="${numberOfNotify > 0}">
                  <p class="part-float right active">${numberOfNotify}</p>
                </c:if>
                <c:if test="${numberOfNotify == 0}">
                  <p class="part-float right">${numberOfNotify}</p>
                </c:if>
                <p class="clear"></p>
              </a>
            </li>
            <li id="STAFF_EQUIP">
              <a href="/staff/equipment">
                <p>Thiết Bị</p>
              </a>
            </li>
            <li id="STAFF_CLASSROOM">
              <a href="/staff/classroom?ACTIVETAB=tab1">
                <p>Phòng Học</p>
              </a>
            </li>
            <li>
              <p>Thống Kê</p>
            </li>
            <li id="STAFF_SCHEDULE">
              <a href="/staff/schedule">
                <p>Xếp Lịch</p>
              </a>
            </li>
          </ul>
        </div>
        <div class="right-content">
          <div class="page active" id="list-report">
            <div class="title"><p>Danh sách báo cáo</p></div>
            <c:if test="${newReport.size() > 0}">
              <div class="table" style="margin: 15px 0 0">
                <div class="header-table">
                  <div class="width-10">
                    <div>Phòng</div>
                  </div>
                  <div class="width-35">
                    <div>Thiết bị</div>
                  </div>
                  <div class="width-30">
                    <div>Người báo cáo</div>
                  </div>
                  <div class="width-25">
                    <div></div>
                  </div>
                  <p class="clear"></p>
                </div>
                <div class="body-table">
                  <c:forEach var="item" items="${newReport}">
                    <div class="row">
                      <div class="width-10">
                        <div>${item.roomName}</div>
                      </div>
                      <div class="width-35">
                        <div>${item.listEquipments}</div>
                      </div>
                      <div class="width-30">
                        <div>${item.reporters}</div>
                      </div>
                      <div class="width-25">
                        <div class="group-button">
                          <div title="Xem" onclick="showDetailReport(${item.roomId})" class="btn btn-normal btn-text">Xem</div>
                          <div title="Khắc phục tất cả" onclick="conformData(2, {message:'Bạn muốn khắc phục hư hại phòng ${item.roomName}!', btnName: 'Khắc phục', link: '/bao-cao/sua-het?roomId=${item.roomId}'})" class="btn btn-primary btn-text">Khắc phục</div>
                        </div>
                      </div>
                      <p class="clear"></p>
                    </div>
                  </c:forEach>
                </div>
              </div>
            </c:if>
            <c:if test="${newReport.size() <= 0}">
              <p>Không có báo cáo mới!</p>
            </c:if>
          </div>
          <div class="page" id="report-details" data-room="0">
            <div class="title">
              <p>Chi tiết báo cáo phòng <span></span></p>
              <input type="button" class="btn btn-normal" value="Quay lại" onclick="changePage('list-report'); resetURL();"/>
            </div>
            <div class="page-content"></div>
          </div>
          <c:import url="/bao-cao/danh-sach-thong-bao?little=false"/>
          <div class="loading-page">
            <img src="/resource/img/500.GIF">
            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
          </div>
        </div>
      </div>
    </div>
    <div id="need-remove">
    <script>
      document.getElementById("${tab}").className += " active";
      connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});

      <c:if test="${requestScope.SHOWDETAIL != null}">
        showDetailReport(${requestScope.SHOWDETAIL});
      </c:if>

      $("#need-remove").remove();
    </script>
  </div>
  </div>
</body>
</html>