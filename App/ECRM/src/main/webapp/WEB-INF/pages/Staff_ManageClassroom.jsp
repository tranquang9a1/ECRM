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
    <input type="text" placeholder="Nhập phòng học"/>
    <input type="button" class="btn btn-orange" value="Tìm kiếm"/>
</div>
<div class="clear"></div>
<div class="table small-table" style="height: 280px">
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
                        <c:if test="${cl.damagedLevel > 0}">
                            <div>Độ hư hại: ${cl.damagedLevel}</div>
                        </c:if>
                        <c:if test="${cl.damagedLevel == 0}">
                            <div>Bình thường</div>
                        </c:if>
                    </div>
                    <div class="control">
                        <div class="group-button">
                            <div class="btn btn-view" title="Xem sơ đồ"
                                 onclick="document.getElementById('ClassroomAction').value='update';document.getElementById('loaiphong').innerHTML = '${cl.tblRoomTypeByRoomTypeId.name}' ;showModal(1, 'modal-manageclassroom'); showClassroomMap(${cl.name},${cl.roomTypeId},${cl.tblRoomTypeByRoomTypeId.verticalRows},'${cl.tblRoomTypeByRoomTypeId.horizontalRows}', '${cl.tblRoomTypeByRoomTypeId.numberOfSlotsEachHRows}'
                                         , ${cl.tblRoomTypeByRoomTypeId.airConditioning}, ${cl.tblRoomTypeByRoomTypeId.fan}, ${cl.tblRoomTypeByRoomTypeId.projector}
                                         , ${cl.tblRoomTypeByRoomTypeId.speaker}, ${cl.tblRoomTypeByRoomTypeId.television});editClassroom(${cl.roomTypeId})">
                                <i class="fa fa-eye"></i>
                            </div>
                            <div class="btn btn-remove"
                                 onclick="conformData(2, {message:'Bạn có muốn xóa phòng học ${cl.name}!', btnName:'Xóa',link:'/staff/removeClassroom?classroomName=${cl.name}'})"
                                 title="Xóa"><i
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
<script>
    function editClassroom(id) {
        $('#selectBox option[value="' + id + '"]').prop('selected', true);
        var selectedValue = $('#selectBox option[value="' + id + '"]').data("value");
        document.getElementById('loaiphong').innerHTML = selectedValue.name;
        document.getElementById('roomtype').value = selectedValue.id;
        showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
                , selectedValue.airConditioning, selectedValue.fan, selectedValue.projector, selectedValue.speaker,
                selectedValue.television);

    }
</script>


