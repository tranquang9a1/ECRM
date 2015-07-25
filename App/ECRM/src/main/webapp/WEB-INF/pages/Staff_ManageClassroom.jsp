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
<div class="table small-table" style="height: 210px">
    <div class="header-table">
        <div class="room-number">
            <div>Số phòng</div>
        </div>
        <div class="room-type">
            <div>Loại phòng</div>
        </div>
        <div class="statusS">
            <div>Trạng thái</div>
        </div>
        <div class="control">
            <div>Quản lý</div>
        </div>
        <p class="clear"></p>
    </div>
    <div class="body-table">
        <form action="/staff/removeClassroom" id="removeClassroom" method="post">
            <input type="hidden" value="" id="removeClassroomName" name="classroomName"/>
            <c:forEach items="${classrooms}" var="cl">
                <div class="row">
                    <div class="room-number">
                        <div>${cl.name}</div>
                    </div>
                    <div class="room-type">
                        <div>${cl.tblRoomTypeByRoomTypeId.name}</div>
                    </div>
                    <div class="statusS">
                    <c:if test="${ not cl.isAllInformation}">

                            <a href="/staff/EquipmentInformation?ClassroomId=${cl.id}">Cập nhật thiết bị!</a>

                    </c:if>
                    </div>
                    <div class="control">
                        <div class="group-button">
                            <div class="btn btn-view" title="Xem sơ đồ"
                                 onclick="document.getElementById('loaiphong').innerHTML = '${cl.tblRoomTypeByRoomTypeId.name}' ;showModal(1, 'modal-manageclassroom'); showClassroomMap(${cl.name},${cl.roomTypeId},${cl.tblRoomTypeByRoomTypeId.verticalRows},'${cl.tblRoomTypeByRoomTypeId.horizontalRows}', '${cl.tblRoomTypeByRoomTypeId.numberOfSlotsEachHRows}'
                                         , ${cl.tblRoomTypeByRoomTypeId.airConditioning}, ${cl.tblRoomTypeByRoomTypeId.fan}, ${cl.tblRoomTypeByRoomTypeId.projector}
                                         , ${cl.tblRoomTypeByRoomTypeId.speaker}, ${cl.tblRoomTypeByRoomTypeId.television});">
                                <i class="fa fa-eye"></i>
                            </div>
                            <div class="btn btn-remove" onclick="conform(4, ${cl.name})" title="Xóa"><i
                                    class="fa fa-times"></i>
                            </div>
                        </div>
                    </div>
                    <p class="clear"></p>
                </div>
            </c:forEach>
        </form>
    </div>

</div>
<div id="pagination" style="padding-left: 150px;"></div>


