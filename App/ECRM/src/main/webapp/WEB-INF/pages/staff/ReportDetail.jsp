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
<div class="modal modal-medium" id="modal-1">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Chi tiết báo cáo</p>
      <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
    </div>
    <div class="body-modal">
      <input type="hidden" value="${room.roomId}" name="roomId"/>
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
      <input type="button" class="btn btn-orange" onclick="conform(1)" value="Khắc phục" />
      <input type="button" class="btn btn-orange" onclick="showModal(2, 'modal-1', 'modal-4')" value="Xem tất cả" />
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
      <p>Thiết bị hư hại phòng 203</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-4','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Máy chiếu</div>
        <div class="quantity">1 máy</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Đèn</div>
        <div class="quantity">3 cái</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Máy quạt</div>
        <div class="quantity">1 máy</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Ghế</div>
        <div class="quantity">5 cái</div>
        <div class="clear"></div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-4','modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(2)"  value="Khắc phục" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
