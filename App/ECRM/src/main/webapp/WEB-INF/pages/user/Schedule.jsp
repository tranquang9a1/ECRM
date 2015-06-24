<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/8/2015
  Time: 5:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>ECRM - Equipment Classroom Management</title>
    <link rel="stylesheet" href="../resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="../resource/css/layout.css"/>
    <link rel="stylesheet" href="../resource/css/general.css"/>
</head>
<body>
<%@include file="../Header.jsp"%>
<c:set var="schedule" value="${requestScope.SCHEDULE}" />
<div class="container">
    <div class="title page-title">
        <p>Lịch dạy</p>
    </div>
    <div class="body-content">
        <div class="table" style="margin: 20px 0 0">
            <div class="header-table">
                <div class="width-15">
                    <div>Phòng</div>
                </div>
                <div class="width-20">
                    <div>Giờ bắt đầu</div>
                </div>
                <div class="width-20">
                    <div>Số tiết</div>
                </div>
                <div class="width-25">
                    <div>Ngày</div>
                </div>
                <div class="width-20">
                    <div>Báo cáo</div>
                </div>
                <p class="clear"></p>
            </div>
            <div class="body-table">
                <c:forEach var="i" begin="0" end="${schedule.size()-1}">
                    <div class="row">
                        <div class="width-15">
                            <div>${schedule[i].tblClassroomByClassroomId.name}</div>
                        </div>
                        <div class="width-20">
                            <div><fmt:formatDate value="${schedule[i].timeFrom}" pattern="HH:mm" /></div>
                        </div>
                        <div class="width-20">
                            <div>${schedule[i].slots}</div>
                        </div>
                        <div class="width-25">
                            <div><fmt:formatDate value="${schedule[i].date}" pattern="dd/MM/yyyy" /></div>
                        </div>
                        <div class="width-20">
                            <jsp:useBean id="currentDate" class="java.util.Date" />
                            <fmt:formatDate value="${currentDate}" pattern="HH:mm" var="formattedDate" />
                            <fmt:formatDate value="${schedule[i].timeFrom}" pattern="HH:mm" var="timeF" />
                            <div>
                                <c:if test="${formattedDate ge timeF}"><span style="color: #35b83a; font-weight: bold">Có thể báo cáo</span></c:if>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
