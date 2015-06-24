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
<div class="modal modal-medium" id="modal-1">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Tạo báo cáo</p>
      <i class="fa fa-times" onclick="showModal(0, 'modal-1')"></i>
    </div>
    <div class="body-modal" style="height: 480px; padding-top: 10px; overflow-y: overlay">
      <div class="group-control" style="margin: 0 0 20px">
        <div class="name">Đánh giá của bạn</div>
        <div class="control" style="width: 300px">
          <select id="report-evaluate" style="width: 200px; float: right">
            <option value="1">Phải đổi phòng</option>
            <%--<option value="2">Cẫn sửa ngay</option>--%>
            <option value="3" selected="selected">Vẫn dạy được</option>
          </select>
        </div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-orange" onclick="conform(1)" value="Gửi báo cáo"/>
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-1', 'modal-2')" value="Chọn vị trí"/>
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-2">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Sơ đồ phòng</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-2', 'modal-1')"></i>
    </div>
    <div class="body-modal">
      <div id="classroom-map">

      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-2', 'modal-1')" value="Quay lại"/>
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-3">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Lịch sử báo cáo</p>
      <i class="fa fa-times" onclick="showModal(0, 'modal-3')"></i>
    </div>
    <div class="body-modal" id="report-history" data-report="0"></div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-3')" value="Thoát"/>
    </div>
  </div>
  <div class="black-background"></div>
</div>
<script>
  positionEquipments = {};
  damageEquip = {};
  <c:if test="${equip.size() > 0}" >
    <c:forEach begin="0" end="${equip.size() -1}" var="i">
      <c:if test="${equip[i].position != null && equip[i].status == false}">
        positionEquipments["${equip[i].position.trim()}"] = {id: ${equip[i].id}, status: ${equip[i].status}};
      </c:if>
      if(damageEquip[${equip[i].categoryId}] == undefined) {
        damageEquip[${equip[i].categoryId}] = ${equip[i].status};
      } else if(damageEquip[${equip[i].categoryId}] == true) {
        damageEquip[${equip[i].categoryId}] = ${equip[i].status};
      }
    </c:forEach>
  </c:if>

  loadEquipment(damageEquip, ${rt.airConditioning}, ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
  showMap('classroom-map', positionEquipments, ${rt.verticalRows}, '${rt.horizontalRows}', '${rt.numberOfSlotsEachHRows}', ${rt.airConditioning},
  ${rt.fan}, ${rt.projector}, ${rt.speaker}, ${rt.television});
  setChooseEquipment('classroom-map');
</script>