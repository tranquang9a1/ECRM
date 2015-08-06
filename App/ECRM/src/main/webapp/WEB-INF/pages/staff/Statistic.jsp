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
    <c:set value="${requestScope.LISTSTATISTIC}" var="list"/>
    <c:set value="${requestScope.TABCONTROL}" var="tab"/>
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
            <div class="page active">
              <div class="title">
                <p>Thống Kê</p>
              </div>
              <c:if test="${list == null}">Không có dữ liệu thống kê</c:if>
              <div id="chartContainer" style="width: 100%; height: 400px; margin: 35px 0 0 0;"></div>
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
    <c:if test="${list != null}">
    <script type="text/javascript">
      theme = 'sand-signika';
      $(function () {
        $('#chartContainer').highcharts({
          chart: {
            type: 'line'
          },
          title: {
            text: 'Số lượt đổi phòng các tháng trong năm 2015'
          },
          xAxis: {
            categories: [<c:forEach items="${list.listMonth}" var="item">'${item}'<c:if test="${item != list.listMonth[list.listMonth.size()-1]}">,</c:if></c:forEach>]
          },
          yAxis: {
            title: {
              text: ''
            }
          },
          plotOptions: {
            line: {
              dataLabels: {
                enabled: true
              },
              enableMouseTracking: false
            }
          },tooltip: {
            valueSuffix: 'lượt'
          },
          legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
          },
          series: [{
            name: 'Số lượt đổi',
            data: ${list.listNumber}
          }]
        });
      });
    </script>
    </c:if>
    <script>
      document.getElementById("${tab}").className += " active";
      document.getElementById("${tab}").setAttribute("data-main", "1");

      connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});
    </script>
    </html>
  </c:otherwise>
</c:choose>
