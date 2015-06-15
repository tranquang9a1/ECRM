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
<script>
    var roomEquipments = {};
    <c:forEach items="${equipments}" var="item">
        <c:if test="${item.position != null && item.status == true}">
            roomEquipments["${item.position.trim()}"] = {id: ${item.id}, status: ${item.status}};
        </c:if>
    </c:forEach>
</script>
<div class="tab">
    <div class="tab-medium">
        <ul>
            <li class="active" onclick="changeTab('tab1', this)">Chi tiết</li>
            <li onclick="changeTab('tab2', this)">Sơ đồ</li>
        </ul>
    </div>
    <div class="content-tab">
        <div id="tab1" class="body-tab active">
            <div class="group-control">
                <div class="name">Phòng</div>
                <div class="value">${room.name}</div>
            </div>
            <div class="group-control">
                <div class="name">Trạng thái</div>
                <div class="value">
                    <c:if test="${report.status == 1}">
                        <p class="label red">Chưa sửa</p>
                    </c:if>
                    <c:if test="${report.status == 2}">
                        <p class="label blue">Đang sửa</p>
                    </c:if>
                    <c:if test="${report.status == 3}">
                        <p class="label green">Đã sửa</p>
                    </c:if>
                    <c:if test="${report.status == 4}">
                        <p class="label">Đã hủy</p>
                    </c:if>
                </div>
            </div>
            <div class="group-control">
                <div class="name">Danh sách thiết bị</div>
                <div class="value" id="list-equipment-history"></div>
            </div>
            <div class="group-control">
                <div class="name">Ngày báo cáo</div>
                <div class="value"><fmt:formatDate value="${report.createTime}" pattern="HH:mm dd/MM/yyyy" /></div>
            </div>
            <div class="group-control line">
                <div class="name">Đánh giá của bạn</div>
                <div class="value">
                    <c:if test="${report.evaluate == 1}">
                        <c:out value="Phải đổi phòng"/>
                    </c:if>
                    <c:if test="${report.evaluate == 2}">
                        <c:out value="Cần sửa ngay"/>
                    </c:if>
                    <c:if test="${report.evaluate == 3}">
                        <c:out value="Vẫn dạy được"/>
                    </c:if>
                </div>
            </div>
            <c:if test="${report.status < 3}">
                <div class="group-control">
                    <div class="name">Hư hại của phòng</div>
                    <div class="control">
                        <div class="process">
                            <p>${room.damagedLevel}%</p>
                            <div class="percent" style="left:${room.damagedLevel}%"></div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <div id="tab2" class="body-tab">
            <div id="room-map"></div>
        </div>
    </div>
</div>
<script>
    showMap('room-map', roomEquipments, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},
            ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
</script>