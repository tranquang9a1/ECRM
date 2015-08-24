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
<c:set var="tabActive" value="${requestScope.ACTIVETAB}"/>

<c:set var="newReport" value="${requestScope.NEWREPORT}"/>
<c:set var="page" value="${requestScope.PAGE}"/>
<c:set var="maxPage" value="${requestScope.SIZE}"/>
<c:set var="finishReport" value="${requestScope.FINISHREPORT}"/>

<html>
<head>
    <title>Hệ thống quản lý thiết bị phòng học</title>
    <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/roomtype-2.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>

    <script src="/resource/js/jquery-1.11.3.js"></script>
    <script src="/resource/js/script.js"></script>
    <script src="/resource/js/newTemplate.js"></script>
</head>
<body>
  <div class="layout-background">
    <div class="container">
      <jsp:include page="/WEB-INF/pages/HeaderNew.jsp"/>
      <div class="content-body">
        <c:import url="/bao-cao/danh-muc"/>
        <div class="right-content">
          <div class="page active" id="list-report">
            <div class="title" style="height: 70px; padding: 0">
              <p>Danh sách báo cáo</p>
              <div class="clear"></div>
              <div class="title-category">
                <ul>
                  <li id="head-tab1" onclick="changeTabInTitle('tab1', this); changeURL(1)" data-tab="report-tab" class="active">Báo cáo mới</li>
                  <li id="head-tab2" onclick="changeTabInTitle('tab2', this); changeURL(2)" data-tab="report-tab">Lịch sử báo cáo</li>
                </ul>
              </div>
            </div>
            <div class="tab report-tab">
              <div class="content-tab">
                <div class="body-tab active" id="tab1">
                  <c:if test="${newReport.size() > 0}">
                    <div class="table">
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
                                <div title="Khắc phục tất cả" onclick="conformData(2, {message:'Bạn muốn khắc phục tất cả hư hại phòng ${item.roomName}!', btnName: 'Khắc phục', link: '/bao-cao/sua-het?roomId=${item.roomId}'})" class="btn btn-primary btn-text">Khắc phục</div>
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
                <div class="body-tab" id="tab2">
                  <c:if test="${finishReport.size() > 0}">
                    <div class="table">
                      <div class="header-table">
                        <div class="width-10">
                          <div>Phòng</div>
                        </div>
                        <div class="width-35">
                          <div>Thiết bị</div>
                        </div>
                        <div class="width-20">
                          <div>Người báo cáo</div>
                        </div>
                        <div class="width-20">
                          <div>Ngày báo cáo</div>
                        </div>
                        <div class="width-15">
                          <div></div>
                        </div>
                        <p class="clear"></p>
                      </div>
                      <div class="body-table">
                        <c:forEach var="item" items="${finishReport}">
                          <div class="row">
                            <div class="width-10">
                              <div>${item.room}</div>
                            </div>
                            <div class="width-35">
                              <div>${item.listEquipment}</div>
                            </div>
                            <div class="width-20">
                              <div>${item.reporter}</div>
                            </div>
                            <div class="width-20">
                              <div>${item.createDate}</div>
                            </div>
                            <div class="width-15">
                              <div class="group-button">
                                <div title="Xem" onclick="showHistoryReport(${item.reportId})" class="btn btn-normal btn-text">Xem</div>
                              </div>
                            </div>
                            <p class="clear"></p>
                          </div>
                        </c:forEach>
                      </div>
                    </div>
                    <div id="pagination">
                      <c:if test="${maxPage > 1}">
                        <div class="paging" style="margin: 0">
                          <c:if test="${page + 1 <= maxPage}">
                            <div class="pagen" style="width: auto; padding: 0 5px"><a href="/bao-cao/lich-su?trang=${page+1}">Sau</a></div>
                            <div class="pagen"><a href="/bao-cao/lich-su?trang=${page+1}">${page+1}</a></div>
                          </c:if>
                          <div class="pagen current">${page}</div>
                          <c:if test="${page - 1 > 0}">
                            <div class="pagen"><a href="/bao-cao/lich-su?trang=${page-1}">${page-1}</a></div>
                            <div class="pagen" style="width: auto; padding: 0 5px"><a href="/bao-cao/lich-su?trang=${page-1}">Trước</a></div>
                          </c:if>
                        </div>
                      </c:if>
                    </div>
                  </c:if>
                  <c:if test="${finishReport.size() <= 0}">
                    <p>Lịch sử báo cáo còn trống!</p>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
          <div class="page" id="history-report">
            <div class="title" style="padding: 0; height: 70px;">
              <p>Chi tiết báo cáo phòng <span></span></p>
              <input type="button" class="btn btn-normal" value="Quay lại" onclick="changePage('list-report');"/>
              <div class="clear"></div>
              <div class="title-category">
                <ul>
                  <li class="active" onclick="changeTabInfo(1, this, 'history-report')">Thông tin báo cáo</li>
                  <li onclick="changeTabInfo(2, this, 'history-report')">Sơ đồ phòng</li>
                </ul>
              </div>
            </div>
            <div class="page-content"></div>
          </div>
          <div class="page" id="report-details" data-room="0">
            <div class="title">
              <p>Chi tiết báo cáo phòng <span></span></p>
              <input type="button" class="btn btn-normal" value="Quay lại" onclick="changePage('list-report'); resetURL();"/>
            </div>
            <div class="page-content"></div>
          </div>
          <c:import url="/bao-cao/thong-bao?little=false&quay-lai=list-report"/>
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
      document.getElementById("${tab}").setAttribute("data-main", "1");

      connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});

      <c:if test="${requestScope.SHOWDETAIL != null}">
        showDetailReport(${requestScope.SHOWDETAIL});
      </c:if>

      <c:if test="${tabActive.equals('tab2')}">
        changeTabInTitle('tab2', $("#head-tab2")[0]);
      </c:if>
      var urlHistory = "/bao-cao/lich-su?trang=${page}";
      function changeURL(tab) {
        if(tab == 1) {
          window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", "/bao-cao");
        } else {
          window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", urlHistory);
        }
      }

      $("#need-remove").remove();
    </script>
  </div>
  </div>
</body>
</html>
