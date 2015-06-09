<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/8/2015
  Time: 3:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<c:set var="room" value="${requestScope.ROOM}"/>
<c:set var="report" value="${requestScope.REPORT}"/>
<c:set var="rt" value="${requestScope.ROOMTYPE}"/>
<c:set var="equipments" value="${requestScope.EQUIPMENTS}"/>
<div class="tab">
    <div class="tab-medium">
        <ul>
            <li class="active" onclick="changeTab('tab1', this)">Sơ đồ</li>
            <li onclick="changeTab('tab2', this)">Chi tiết</li>
        </ul>
    </div>
    <div class="content-tab">
        <div id="tab1" class="body-tab active">
            <div id="room-map"></div>
        </div>
        <div id="tab2" class="body-tab">
            <div class="group-control">
                <div class="name">Phòng</div>
                <div class="value">${room.name}</div>
            </div>
            <div class="group-control">
                <div class="name">Ngày báo cáo</div>
                <div class="value"><fmt:formatDate value="${report.createTime}" pattern="HH:mm dd/MM/yyyy" /></div>
            </div>
            <div class="group-control">
                <div class="name">Thiết bị</div>
                <div class="value" id="list-equipment-history"></div>
            </div>
            <div class="group-control">
                <div class="name">Mức độ hư hại</div>
                <div class="value">0%</div>
            </div>
            <div class="group-control">
                <div class="name">Trạng thái</div>
                <div class="value">
                    <c:if test="${report.status == true}">
                        <p class="label red">Chưa sửa</p>
                    </c:if>
                    <c:if test="${report.status == false}">
                        <p class="label green">Đã sửa</p>
                    </c:if>
                </div>
            </div>
            <div class="group-control">
                <div class="name">Mô tả hư hại</div>
                <div class="value">${report.description}</div>
            </div>
        </div>
    </div>
</div>
<script>
    var roomEquipments = {};
    <c:forEach var="i" begin="0" end="${equipments.size()-1}">
    roomEquipments["${equipments[i].position.trim()}"] = {
        "id": ${equipments[i].id},
        "category": ${equipments[i].categoryId},
        "status": "${equipments[i].status}"
    };
    </c:forEach>

    showMapForReport('room-map', roomEquipments, ${rt.id}, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},
            ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
    showModal(1, 'modal-3');
</script>