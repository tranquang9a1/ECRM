<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 7/6/2015
  Time: 6:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/resource/js/socket.io.js"></script>

<script src="/resource/js/socket-io.js"></script>
<script src="/resource/js/staff-notify.js"></script>
<c:set var="numberOfNotify" value="${requestScope.NUMBEROFNOTIFY}"/>

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
            <a href="/staff/statistic?time=0&category=0">
                <p>Thống Kê</p>
            </a>
        </li>
        <li id="STAFF_SCHEDULE">
            <a href="/staff/schedule">
                <p>Xếp Lịch</p>
            </a>
        </li>
    </ul>
</div>
<c:import url="/bao-cao/danh-sach-thong-bao?little=false"/>
