<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/8/2015
  Time: 7:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="room" value="${requestScope.DAMAGEDROOM}"/>
<c:set var="rt" value="${room.roomtype}" />
<script>
  <%--var positionEquipments = {};--%>
<%--//  var damageEquip = {};--%>
  <%--<c:forEach items="${equip}" var="item">--%>
    <%--positionEquipments["${item.position.trim()}"] = {id: ${item.id}, status: ${item.status}};--%>
    <%--<c:if test="${item.status == true}">--%>
    <%--if(damageEquip[${item.categoryId}] == undefined) {--%>
      <%--damageEquip[${item.categoryId}] = {name: ${item.tblEquipmentCategoryByCategoryId.name}, status: ${item.status}, size: 1};--%>
    <%--} else {--%>
      <%--damageEquip[${item.categoryId}].size += 1;--%>
    <%--}--%>
    <%--</c:if>--%>
  <%--</c:forEach>--%>
</script>
<div class="modal modal-medium" id="modal-1">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Chi tiết báo cáo</p>
      <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="group-control">
        <div class="name">Phòng</div>
        <div class="value">${room.roomName}</div>
      </div>
      <div class="group-control">
        <div class="name">Người báo cáo</div>
        <div class="value">${room.reporters}</div>
      </div>
      <div class="group-control">
        <div class="name">Báo cáo mới nhất</div>
        <div class="value"><fmt:formatDate value="${room.reportDate}" pattern="HH:mm dd/MM/yyyy" /></div>
      </div>
      <div class="group-control line">
        <div class="name">Mức độ hư hại</div>
        <div class="value">${room.evaluate}</div>
      </div>
      <c:if test="${room.damagedLevel >= 50 && room.suggestRooms != null}" >
        <div class="group-control">
          <div class="name">Đề xuất</div>
          <div class="value">Đổi sang phòng <b>${room.suggestRooms[0]}</b></div>
          <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-1','modal-3')" value="Đổi phòng" />
        </div>
      </c:if>
      <div class="group-control">
        <div class="name">Hư hại của phòng</div>
        <div class="control">
          <div class="process">
            <p>${room.damagedLevel}%</p>
            <div class="percent" style="left:${room.damagedLevel}%"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="showModal(2, 'modal-1', 'modal-4')" value="Khắc phục" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-2">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Sơ đồ phòng học</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-2','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="map-container" id="idContainer" style="margin: 15px 0 0">

      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-2','modal-1')" value="Thoát" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-small" id="modal-3">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Đổi phòng</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-3','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="group-control">
        <div class="name">Phòng hiện tại</div>
        <div class="value">${room.roomName}</div>
      </div>
      <div class="group-control">
        <div class="name">Phòng trống</div>
        <div class="control">
          <c:if test="${room.suggestRooms != null}">
            <select>
              <c:forEach items="${room.suggestRooms}" var="item" >
                <option value="${item}">${item}</option>
              </c:forEach>
            </select>
          </c:if>
        </div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-3','modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(3)" value="Đổi phòng" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-4">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Thiết bị hư hại phòng ${room.roomName}</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-4','modal-1')"></i>
    </div>
    <div class="body-modal">
      <form action="/thong-bao/sua-chua" id="resolveForm" method="post" style="margin: 0;">
        <input type="hidden" value="${room.roomId}" name="RoomId"/>
        <input type="hidden" id="ListResolve" value="" name="ListResolve">
      </form>
        <c:forEach items="${room.equipments}" var="item" >
          <div class="group-equipment">
            <input type="checkbox" class="equipment-type" value="${item.id}"/>
            <div class="equip ${item.className}"></div>
            <div class="equipment">${item.name}</div>
            <div class="quantity">Số lượng: ${item.size}</div>
            <div class="clear"></div>
          </div>
        </c:forEach>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-4','modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(1)"  value="Khắc phục" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<script>
  var listResolve = {};
  $(".equipment-type").click(function(){
    if(listResolve[$(this).attr("value")] != undefined) {
      delete listResolve[$(this).attr("value")];
    } else {
      listResolve[$(this).attr("value")] = true;
    }
  });

  <%--showMap('idContainer', positionEquipments, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},--%>
            <%--${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});--%>
</script>