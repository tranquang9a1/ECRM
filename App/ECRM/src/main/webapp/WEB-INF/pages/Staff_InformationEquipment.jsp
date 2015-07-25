<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:choose>
    <c:when test="${empty user}">
        <jsp:forward page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
        <head>
            <c:set var="projector" value="${requestScope.PROJECTOR}"/>
            <c:set var="avai_projector" value="${requestScope.AVAILABLEPROJECTOR}"/>
            <c:set var="tivi" value="${requestScope.TIVI}"/>
            <c:set var="avai_tivi" value="${requestScope.AVAILABLETIVI}"/>
            <c:set var="air" value="${requestScope.AIR}"/>
            <c:set var="avai_air" value="${requestScope.AVAILABLEAIR}"/>
            <c:set var="classroomId" value="${requestScope.CLASSROOMID}"/>
            <c:set var="classroomName" value="${requestScope.CLASSROOMNAME}"/>
            <c:set var="tab" value="${requestScope.TABCONTROL}"/>
            <meta charset="UTF-8"/>
            <title>ECRM - Equipment Classroom Management</title>
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <script src="../../resource/js/jquery-1.11.3.js"></script>
            <script src="../../resource/js/jquery-1.11.3.min.js"></script>
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
                            <div class="title"><p>Cập Nhật Thông Tin Thiết Bị Cho Phòng ${classroomName}</p>
                                <a href="/staff/classroom?ACTIVETAB=1"><input class="btn btn-primary" type="button" value="Quay Lại"></a>
                            </div>
                            <form action="/staff/updateInformation" id="information" method="post">
                                <input hidden value="${classroomId}" name="classroomId"/>
                                <c:if test="${not empty projector }">
                                    <div class="group-control">
                                        <div class="name"> Chọn projector:
                                        </div>
                                        <div class="control"><select name="projector">
                                            <option value="0">---</option>
                                            <c:forEach items="${avai_projector}" var="p">
                                                <option value="${p.id}">${p.name}-${p.serialNumber}</option>
                                            </c:forEach>
                                        </select></div>
                                    </div>

                                </c:if>
                                <c:if test="${empty projector }">
                                    <input hidden value="0" name="projector"/>
                                </c:if>
                                <c:if test="${not empty tivi }">
                                    <div class="group-control">
                                        <div class="name">Chọn tivi:</div>
                                        <div class="control">
                                            <select name="tivi">
                                                <option value="0">---</option>
                                                <c:forEach items="${avai_tivi}" var="t">
                                                    <option value="${t.id}">${t.name}-${t.serialNumber}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${empty tivi }">
                                    <input hidden value="0" name="tivi"/>
                                </c:if>
                                <c:if test="${not empty air }">

                                    <c:forEach var="as" items="${air}" varStatus="i">
                                        <div class="group-control">
                                            <div class="name">Chọn máy lạnh ${i.count}:</div>
                                            <div class="control">
                                                <select id="air${i.count}">
                                                    <option value="0">---</option>
                                                    <c:forEach items="${avai_air}" var="a">
                                                        <option value="${a.id}">${a.name}-${a.serialNumber}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </c:if>
                                <input type="hidden" value="" id="airConditioning" name="airConditioning"/>
                                <button class="btn btn-primary" style="float: left; margin-left: 0px" onclick="clicks(${fn:length(air)});">Cập nhật</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </body>
        </html>
        <script src="../../resource/js/script.js"></script>
        <script>
            function clicks(size) {
                var information = "";
                for (var i = 1; i <= size; i++) {
                    information += document.getElementById('air' + i).value.toString() + "-";
                }
                document.getElementById("airConditioning").value = information;
                document.getElementById('information').submit();
            }

            document.getElementById("${tab}").className += " active";
            document.getElementById("${tab}").setAttribute("data-main", "1");
        </script>
    </c:otherwise>
</c:choose>