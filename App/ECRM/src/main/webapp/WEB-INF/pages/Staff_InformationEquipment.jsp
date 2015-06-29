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
        <c:set var="projector" value="${requestScope.PROJECTOR}"/>
        <c:set var="avai_projector" value="${requestScope.AVAILABLEPROJECTOR}"/>
        <c:set var="tivi" value="${requestScope.TIVI}"/>
        <c:set var="avai_tivi" value="${requestScope.AVAILABLETIVI}"/>
        <c:set var="air" value="${requestScope.AIR}"/>
        <c:set var="avai_air" value="${requestScope.AVAILABLEAIR}"/>
        <c:set var="classroomId" value="${requestScope.CLASSROOMID}"/>

        <div class="contain-layout">
            <jsp:include flush="true" page="Header.jsp"/>
        </div>
        <div class="container">
            <div class="title page-title right-button">
                <p>Quản lý Thiết Bị</p>

                <div class="clear"></div>
            </div>
            <form action="/staff/updateInformation" id="information">
                <input hidden value="${classroomId}" name="classroomId"/>
                <c:if test="${not empty projector }">
                    Chọn projector trong kho:
                    <select name="projector">
                        <option value="0">---</option>
                        <c:forEach items="${avai_projector}" var="p">
                            <option value="${p.id}">${p.name}-${p.serialNumber}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${empty projector }">
                    <input hidden value="0" name="projector"/>
                </c:if>
                <br/>
                <c:if test="${not empty tivi }">
                    Chọn tivi trong kho:
                    <select name="tivi">
                        <option value="0">---</option>
                        <c:forEach items="${avai_tivi}" var="t">
                            <option value="${t.id}">${t.name}-${t.serialNumber}</option>
                        </c:forEach>
                    </select>

                </c:if>
                <c:if test="${empty tivi }">
                    <input hidden value="0" name="tivi"/>
                </c:if>
                <br/>
                <c:if test="${not empty air }">
                    <c:forEach var="as" items="${air}" varStatus="i">
                        Chọn may lanh thu ${i.count} trong kho:
                        <select id="air${i.count}">
                            <option value="0">---</option>
                            <c:forEach items="${avai_air}" var="a">
                                <option value="${a.id}">${a.name}-${a.serialNumber}</option>
                            </c:forEach>
                        </select>
                        <br/>
                    </c:forEach>
                </c:if>
                <input type="hidden" value="" id="airConditioning" name="airConditioning"/>

            </form>
            <button onclick="clicks(${fn:length(air)});">OK</button>
        </div>

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
        </script>

        </body>
        </html>
    </c:otherwise>
</c:choose>