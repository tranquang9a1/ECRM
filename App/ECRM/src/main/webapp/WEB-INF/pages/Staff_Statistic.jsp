<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 7/6/2015
  Time: 10:08 AM
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

        <html>
        <head>
            <c:set var="statistics" value="${requestScope.STATISTIC}"/>
            <c:set var="legendText" value="${requestScope.LEGENDTEXT}"/>
            <c:set var="text" value="${requestScope.TEXT}"/>
            <c:set var="time" value="${requestScope.TIME}"/>
            <c:set var="category" value="${requestScope.CATEGORY}"/>
            <c:set var="tab" value="${requestScope.TABCONTROL}"/>
            <title>ECRM - Equipment Classroom Management</title>
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/jquery-ui.css"/>
            <link rel="stylesheet" href="/resource/css/component.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <script type="text/javascript" src="/resource/js/canvasjs.min.js"></script>
        <script>

        </script>
        </head>
        <body>
        <div class="layout-background">
            <div class="container">
                <div class="header">
                    <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
                    <div class="account-control">
                        <p>${user.tblUserInfoByUsername.fullName}</p>
                        <img src="/resource/img/gears.png"/>
                    </div>
                </div>
                <div class="content-body">
                    <c:import url="/bao-cao/danh-muc"/>
                    <div class="right-content">
                        <div class="page active">
                            <div class="title"><p>Thống Kê</p>

                                <form action="/staff/statistic" id="searchStatistic">
                                    <select name="time" onchange="searchStatistic();" id="time">
                                        <option value="0">1 tháng</option>
                                        <option value="1">Tất cả</option>
                                    </select>
                                    <select name="category" onchange="searchStatistic();" id="category">
                                        <option value="0">Phòng học</option>
                                        <option value="1">Thiết bị</option>
                                    </select>
                                </form>
                            </div>
                            <div style="text-align: center; "><h2 style="margin-bottom: 1px">${text}</h2></div>
                            <div id="chartContainer" style="max-height: 47vh; width: 100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </body>
        <script type="text/javascript">
            function searchStatistic(){
                document.getElementById('searchStatistic').submit();
            }

            window.onload = function () {
                var chart = new CanvasJS.Chart("chartContainer",
                        {
                            title: {
                                text: ""
                            },
                            axisY: {
                                title: "Số lượng báo cáo"
                            },
                            legend: {
                                verticalAlign: "bottom",
                                horizontalAlign: "center"
                            },
                            theme: "theme2",
                            data: [

                                {
                                    type: "column",
                                    showInLegend: true,
                                    legendMarkerColor: "grey",
                                    legendText: "${legendText}",
                                    dataPoints: ${statistics}
                                },
                            ]
                        });

                chart.render();
            }

            if('${time}'==0){
                document.getElementById('time').selectedIndex = 0;
            }else{
                document.getElementById('time').selectedIndex = 1;
            }
            if('${category}'==0){
                document.getElementById('category').selectedIndex = 0;

            }else{
                document.getElementById('category').selectedIndex = 1;
            }

            document.getElementById("${tab}").className += " active";
            document.getElementById("${tab}").setAttribute("data-main", "1");
        </script>
        </html>
    </c:otherwise>
</c:choose>