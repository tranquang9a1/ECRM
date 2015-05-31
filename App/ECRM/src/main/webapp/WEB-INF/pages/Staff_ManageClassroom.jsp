<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/29/2015
  Time: 11:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="classrooms" value="${requestScope.ALLCLASSROOM}"/>
<div class="search" style="  margin: 0 0 15px;">
    <input type="text" placeholder="............."/>
    <input type="button" class="btn btn-orange" value="Tìm kiếm"/>
</div>
<div class="clear"></div>
<div class="table small-table">
    <div class="header-table">
        <div class="room-number">
            <div>Số phòng</div>
        </div>
        <div class="room-type">
            <div>Loại phòng</div>
        </div>
        <div class="control">
            <div>Quản lý</div>
        </div>
        <p class="clear"></p>
    </div>
    <div class="body-table">
        <c:forEach items="${classrooms}" var="cl">
            <div class="row">
                <div class="room-number">
                    <div>${cl.name}</div>
                </div>
                <div class="room-type">
                    <div>Loại ${cl.roomTypeId}</div>
                </div>
                <div class="control">
                    <div class="group-button">
                        <div class="btn btn-detail" title="Chỉnh sửa"><i class="fa fa-pencil"></i>
                        </div>
                        <div class="btn btn-view" title="Xem sơ đồ"
                             onclick="showModal(1, 'modal-manageclassroom'); showClassroomMap(${cl.name},${cl.roomTypeId},${cl.tblRoomTypeByRoomTypeId.verticalRows},'${cl.tblRoomTypeByRoomTypeId.horizontalRows}', '${cl.tblRoomTypeByRoomTypeId.noSlotsEachHRows}'
                                     , ${cl.tblRoomTypeByRoomTypeId.airConditioning}, ${cl.tblRoomTypeByRoomTypeId.fan}, ${cl.tblRoomTypeByRoomTypeId.projector}
                                     , ${cl.tblRoomTypeByRoomTypeId.speaker}, ${cl.tblRoomTypeByRoomTypeId.television})">
                            <i class="fa fa-eye"></i>
                        </div>

                        <div class="btn btn-remove" onclick="conform(2, {room: 101})" title="Xóa"><i
                                class="fa fa-times"></i>
                        </div>

                    </div>
                </div>
                <p class="clear"></p>
            </div>
        </c:forEach>
    </div>


</div>

