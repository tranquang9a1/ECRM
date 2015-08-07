<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 7/2/2015
  Time: 3:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:set var="firstLogin" value="${sessionScope.FIRSTLOGIN}"/>

<c:set var="allSchedule" value="${requestScope.ALLSCHEDULE}"/>
<c:set var="finishSchedule" value="${requestScope.FINISHSCHEDULE}"/>

<c:set var="notifies" value="${requestScope.LISTNOTIFY}"/>
<c:set var="numberOfNotifies" value="${requestScope.NUMBEROFNOTIFY}"/>

<c:set var="page" value="${requestScope.CURRENTPAGE}"/>
<c:set var="max" value="${requestScope.MAXPAGE}"/>
<c:set var="reports" value="${requestScope.HISTORYREPORT}"/>
<html>
<head>
    <title>Hệ thống quản lý thiết bị</title>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/roomtype-2.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>

    <script src="/resource/js/jquery-1.11.3.min.js"></script>
    <script src="/resource/js/socket.io.js"></script>
    <script src="/resource/js/socket-io.js"></script>
    <script src="/resource/js/script.js"></script>
    <script src="/resource/js/user-notify.js"></script>
    <script src="/resource/js/newTemplate.js"></script>
</head>
<body>
    <c:if test="${firstLogin}">
        <div class="modal modal-small" id="modal-changePass">
          <div class="content-modal" style="height:264px;">
            <div class="header-modal title">Đổi mật khẩu</div>
            <div class="body-modal">
              <div>Chào, <b>${user.tblUserInfoByUsername.fullName}</b>. Bạn đang dùng mật khẩu mặt định, vui lòng thay đổi mật khẩu của bạn!</div>
              <div class="group-control">
                <div class="name">Mật khẩu mới</div>
                <div class="control">
                  <input type="password" id="password-first">
                </div>
              </div>
              <div class="group-control">
                <div class="name">Xác nhận lại</div>
                <div class="control">
                  <input type="password" id="rePassword-first">
                </div>
              </div>
            </div>
            <div class="footer-modal">
              <input type="button" class="btn btn-orange" onclick="changePassword('${user.username}', 1)" value="Đổi mật khẩu"/>
            </div>
          </div>
          <div class="black-background"></div>
        </div>
        <script>
          showModal(1, 'modal-changePass');
        </script>
      <c:remove var="FIRSTLOGIN" scope="session"/>
    </c:if>
    <div class="container">
      <div class="header">
        <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
        <div class="account-control">
          <p><a href="/dang-xuat" style="color: white; text-decoration: none">${user.tblUserInfoByUsername.fullName}</a></p>
          <img src="../../resource/img/gears.png"/>
        </div>
      </div>
      <div class="content-body">
        <div class="left-control">
          <div class="list-notification">
            <div class="title" style="cursor: pointer" onclick="showListNotify()">Thông báo gần đây</div>
            <div class="notifications" id="notifications">
              <c:import url="/bao-cao/danh-sach-thong-bao?little=true" />
            </div>
          </div>
          <div class="user-schedule">
            <div class="title">Lịch dạy trong ngày</div>
            <c:if test="${allSchedule != null}">
              <div class="number-of-schedule">${allSchedule.size()}</div>
            </c:if>
            <div class="content-schedule" onmouseover="stopUpdate = true" onmouseout="stopUpdate = false;">
              <div class="data-schedule">
                <div class="real-time">
                  <div id="now-time">
                    <div id="time-here"></div>
                  </div>
                  <div id="list-hour"></div>
                  <div id="list-schedule"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="right-content">
          <div class="page active" id="list-report">
            <div class="title">
              <p>Danh sách báo cáo đã gửi</p>
              <c:if test="${allSchedule != null && allSchedule.size() > 0}">
                <input type="button" class="btn btn-primary" value="Gửi báo cáo" onclick="changePage('list-room')"/>
              </c:if>
              <c:if test="${allSchedule == null || allSchedule.size() <= 0}">
                <input type="button" class="btn btn-normal" value="Gửi báo cáo" onclick="conformData(1,{message: 'Không thể tạo báo cáo, vì bạn chưa dạy tiết nào trong hôm nay!'})"/>
              </c:if>
            </div>
            <div class="content-data">
              <c:if test="${reports.size() > 0}">
              <div class="table" style="margin-top: 15px">
                </c:if>
                <c:if test="${reports.size() <= 0}">
                <p class="none-message">Bạn chưa có báo cáo!</p>
                <div class="table" style="margin-top: 15px; display: none">
                  </c:if>
                  <div class="header-table">
                    <div class="width-10">
                      <div>Phòng</div>
                    </div>
                    <div class="width-35">
                      <div>Thiết bị</div>
                    </div>
                    <div class="width-20">
                      <div>Ngày báo cáo</div>
                    </div>
                    <div class="width-20">
                      <div>Trạng thái</div>
                    </div>
                    <div class="width-15">
                      <div>Quản lý</div>
                    </div>
                    <p class="clear"></p>
                  </div>
                  <div class="body-table" id="content-report">
                    <c:if test="${reports.size() > 0}">
                      <c:forEach var="i" begin="0" end="${reports.size()-1}">
                        <div class="row" id="report-${reports[i].reportId}">
                          <div class="width-10">
                            <div>${reports[i].room}</div>
                          </div>
                          <div class="width-35">
                            <div id="list-${reports[i].reportId}" title="${reports[i].listEquipment}">${reports[i].listEquipment}</div>
                          </div>
                          <div class="width-20">
                            <div>${reports[i].createDate}</div>
                          </div>
                          <div class="width-20">
                            <div>
                              <c:if test="${reports[i].status == 1}">
                                <p class="label red">Chưa sửa</p>
                              </c:if>
                              <c:if test="${reports[i].status == 2}">
                                <p class="label blue">Đang sửa</p>
                              </c:if>
                              <c:if test="${reports[i].status == 3}">
                                <p class="label green">Đã sửa</p>
                              </c:if>
                            </div>
                          </div>
                          <div class="width-15">
                            <div class="group-button">
                              <div onclick="loadReportHistory(${reports[i].reportId})" class="btn btn-normal btn-text">Xem</div>
                            </div>
                          </div>
                          <p class="clear"></p>
                        </div>
                      </c:forEach>
                    </c:if>
                  </div>
                </div>
                <c:if test="${max > 1}">
                  <div class="paging">
                    <c:if test="${page + 1 <= max}">
                      <div class="pagen" style="width: auto; padding: 0 5px"><a href="/giang-vien?trang=${page+1}">Sau</a></div>
                      <div class="pagen"><a href="/giang-vien?trang=${page+1}">${page+1}</a></div>
                    </c:if>
                    <div class="pagen current">${page}</div>
                    <c:if test="${page - 1 > 0}">
                      <div class="pagen"><a href="/giang-vien?trang=${page-1}">${page-1}</a></div>
                      <div class="pagen" style="width: auto; padding: 0 5px"><a href="/giang-vien?trang=${page-1}">Trước</a></div>
                    </c:if>
                  </div>
                </c:if>
            </div>
          </div>
          <div class="page" id="list-room">
            <input id="roomId" type="hidden"/>
            <div class="title" style="padding: 0; height: 78px;">
              <p>Báo cáo phòng <span id="room"></span></p>
              <input type="button" class="btn btn-normal" value="Quay lại" onclick="changePage('list-report')" style="float: right;"/>
              <input type="button" class="btn btn-primary" value="Gửi báo cáo" onclick="sentReport()" style="float: right;"/>
              <div class="clear" style="padding: 0 0 7px;"></div>
              <div class="title-category" style="float: left;">
                <ul>
                  <li class="active" onclick="changeTabInfo(1, this, 'list-room')">Thông tin báo cáo</li>
                  <li onclick="changeTabInfo(2, this, 'list-room')">Sơ đồ phòng</li>
                </ul>
              </div>
              <c:if test="${finishSchedule.size() > 0}">
                <p style="float: right; padding: 0; text-transform: none"> Phòng
                  <select id="list-active-room" onchange="getRoomReport()" style="  background-color: white;  border: 1px solid #ddd;  padding: 6px 10px;  margin: 0 0 0 10px;">
                    <c:forEach items="${finishSchedule}" var="item">
                      <option value="${item.classId}">${item.className}</option>
                    </c:forEach>
                  </select>
                </p>
              </c:if>
            </div>
            <%--<div class="list-control-report">--%>
              <%--&lt;%&ndash;<div class="title-tab active" onclick="changeTabInfo(1, this)">Thông tin</div>&ndash;%&gt;--%>
              <%--&lt;%&ndash;<div class="title-tab" onclick="changeTabInfo(0, this)">Bản đồ</div>&ndash;%&gt;--%>

              <%--<div class="control-report-button">--%>
              <%--</div>--%>
            <%--</div>--%>
            <div class="list-room-content"></div>
          </div>
          <div class="page" id="history-report">
            <div class="title" style="padding: 0; height: 70px;">
              <p>Báo cáo đã gửi phòng <span id="room-name"></span></p>
              <input type="button" class="btn btn-normal" value="Quay lại" onclick="changePage('list-report')"/>
              <div class="clear"></div>
              <div class="title-category">
                <ul>
                  <li class="active" onclick="changeTabInfo(1, this, 'history-report')">Thông tin báo cáo</li>
                  <li onclick="changeTabInfo(2, this, 'history-report')">Sơ đồ phòng</li>
                </ul>
              </div>
            </div>
            <div class="list-data-report">

            </div>
          </div>
          <div class="loading-page">
            <img src="/resource/img/500.GIF">
            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
          </div>
        </div>
      </div>
    </div>
    <div class="need-remove">
      <script>
        var damagedEquipments = {};
        var noDamagedEquipments = {};

        var listSchedule = {};
        <c:forEach items="${allSchedule}" var="item">
        listSchedule[${item.id}] = {room: ${item.tblClassroomByClassroomId.name}, time: '${item.tblScheduleConfigByScheduleConfigId.timeFrom}'};
        </c:forEach>

        createTimes();
        <c:if test="${finishSchedule.size() > 0}">
          getRoomReport();
        </c:if>

        connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});
        $(".need-remove").remove();
      </script>
    </div>
</body>
</html>
