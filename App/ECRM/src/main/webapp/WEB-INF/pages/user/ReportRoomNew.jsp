<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/17/2015
  Time: 5:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<c:set var="room" value="${requestScope.ROOM}"/>
<c:set var="rt" value="${requestScope.ROOMTYPE}" />
<c:set var="equip" value="${requestScope.EQUIPMENTS}" />
<div class="room-content" id="room-detail-${room.id}">
  <div class="data-tab main-info active">
      <div class="group-control" style="margin: 0 0 5px; padding: 0 0 15px; border-bottom: 1px solid #ddd">
        <div class="name">Đánh giá của bạn</div>
        <div class="control" style="width: 270px">
          <select id="report-evaluate" style="width: 200px; float: right">
            <option value="1">Đổi phòng</option>
            <option value="2" selected="selected">Không cần đổi phòng</option>
          </select>
        </div>
      </div>
    <div class="data-room-content"></div>
  </div>

  <div class="data-tab map-info">
    <div id="classroom-map-${room.id}">

    </div>
  </div>
</div>
<script>
  positionEquipments = {};
  damageEquip = {};
  <c:if test="${equip.size() > 0}" >
    <c:forEach begin="0" end="${equip.size()-1}" var="i">
      <c:if test="${(equip[i].position != null || '[0]'.equals(equip[i].position) != false) && equip[i].status == false}">
        positionEquipments["${equip[i].position.trim()}"] = {id: ${equip[i].id}, status: ${equip[i].status}};
      </c:if>
      if(damageEquip[${equip[i].categoryId}] == undefined) {
        damageEquip[${equip[i].categoryId}] = ${equip[i].status};
      } else if(damageEquip[${equip[i].categoryId}] == true) {
        damageEquip[${equip[i].categoryId}] = ${equip[i].status};
      }
    </c:forEach>
  </c:if>

  loadRoomEquipment(damageEquip, ${rt.airConditioning}, ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
  showMap('classroom-map-${room.id}', positionEquipments, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},
  ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
  setChooseEquipment('classroom-map-${room.id}');
</script>