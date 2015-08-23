<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/31/2015
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:choose>
  <c:when test="${empty user}">
    <jsp:forward page="Login.jsp"/>
  </c:when>
  <c:otherwise>
    <c:set value="${requestScope.TABCONTROL}" var="tab"/>
    <c:set value="${requestScope.LISTYEAR}" var="listYear"/>
    <html>
    <head>
      <title>ECRM - Equipment Classroom Management</title>
      <link rel="stylesheet" href="/resource/css/general.css"/>
      <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
      <script type="text/javascript" src="/resource/js/jquery-1.11.3.min.js"></script>
      <script type="text/javascript" src="/resource/js/highcharts.js"></script>
      <script type="text/javascript" src="/resource/js/newTemplate.js"></script>
    </head>
    <body>
    <div class="layout-background">
      <div class="container">
        <jsp:include page="/WEB-INF/pages/HeaderNew.jsp"/>
        <div class="content-body">
          <c:import url="/bao-cao/danh-muc"/>
          <div class="right-content">
            <div class="page active" id="statistic">
              <div class="title">
                <p>Thống kê</p>
                <c:if test="${listYear != null && listYear.size() > 0}">
                <div class="changeRoomChart">
                  <select onchange="loadDataStatistic(this)">
                    <c:forEach var="item" items="${listYear}">
                      <option value="${item}">${item}</option>
                    </c:forEach>
                  </select>
                  <p style="margin: 0 10px 0 0; float: right; text-transform: capitalize">Năm</p>
                </div>
                </c:if>
              </div>
              <div id="viewChart">
                <c:if test="${listYear != null && listYear.size() > 0}">
                  <c:import url="/staff/getDataStatistic?year=${listYear.get(0)}"/>
                </c:if>
              </div>
            </div>
            <c:import url="/bao-cao/thong-bao?little=false&quay-lai=statistic"/>
            <div class="loading-page">
              <img src="/resource/img/500.GIF">
              <div>Đang tải! Vui lòng chờ trong giây lát!</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </body>

    <script>
      document.getElementById("${tab}").className += " active";
      document.getElementById("${tab}").setAttribute("data-main", "1");

      connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});

      function loadDataStatistic(thisE) {
        $(".loading-page").addClass("active");
        $.ajax({
          method: "GET",
          url: "/staff/getDataStatistic",
          data: {year: $(thisE).val()},
          success: function(data) {
            $("#viewChart").html(data);
            $(".loading-page").removeClass("active");
          }
        });
      }
    </script>
    </html>
  </c:otherwise>
</c:choose>
