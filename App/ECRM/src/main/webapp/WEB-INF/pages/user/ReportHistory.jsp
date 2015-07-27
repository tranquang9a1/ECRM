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
        <%--<c:if test="${'[0]'.equals(item.position) != false}">--%>
            roomEquipments["${item.position.trim()}"] = {id: ${item.id}, status: false};
        <%--</c:if>--%>
    </c:forEach>
</script>
<div id="hitory-report-${report.id}">
    <input type="hidden" value="${room.name}" id="room-name-${report.id}"/>
    <div class="room-content" style="position: relative; float: left; margin: 15px 0 0 15px;">
        <div class="data-tab main-info active">
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
                <div class="value">
                    <c:forEach items="${equipments}" var="item">
                        ${item.tblEquipmentCategoryByCategoryId.name}
                        <c:if test="${item.id != equipments.get(equipments.size()-1).id}">,</c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="group-control">
                <div class="name">Ngày báo cáo</div>
                <div class="value"><fmt:formatDate value="${report.createTime}" pattern="HH:mm dd/MM/yyyy" /></div>
            </div>
            <div class="group-control line">
                <div class="name">Đánh giá của bạn</div>
                <div class="value">${report.evaluate}</div>
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
        <div class="data-tab map-info">
            <div id="report-map-${report.id}"></div>
        </div>
    </div>
</div>
<script>
    showMap('report-map-${report.id}', roomEquipments, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},
            ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
</script>