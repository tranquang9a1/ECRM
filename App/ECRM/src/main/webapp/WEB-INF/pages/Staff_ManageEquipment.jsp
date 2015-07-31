<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 7/30/2015
  Time: 12:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="equipments" value="${requestScope.EQUIPMENTS}"/>
<div class="table" style="width:100%; height: 280px; margin-top: 10px">
    <div class="header-table">
        <div style="width:10%">
            <div>Loại</div>
        </div>
        <div style="width:12%;">
            <div>Tên</div>
        </div>
        <div style="width:20%;">
            <div>Số Serial</div>
        </div>
        <div style="width:100px;">
            <div>Giờ sử dụng</div>
        </div>
        <div style="width:100px;">
            <div>Giờ còn lại</div>
        </div>
        <div style="width:50px;">
            <div>Phòng</div>
        </div>
        <div style="width: 100px">
            <div></div>
        </div>
        <p class="clear"></p>
    </div>
    <div class="body-table">
        <form action="/staff/removeEqipment" id="removeClassroom">
            <input type="hidden" value="" id="removeClassroomName"
                   name="classroomName"/>
            <c:forEach items="${equipments}" var="e">
                <c:choose>
                    <c:when test="${e.timeRemain <=50 and e.usingTime>0}">
                        <div class="row">
                            <div style="width:10%;">
                                <div class="equip"
                                     style="background-image: url('../../resource/img/equipment/${e.tblEquipmentCategoryByCategoryId.imageUrl}')">
                                </div>
                            </div>
                            <div style="width:12%;">
                                <div>${e.name}</div>
                            </div>
                            <div style="width:20%;">
                                <div>${e.serialNumber}</div>
                            </div>
                            <div style="width:100px;">
                                <div>${e.usingTime}</div>
                            </div>
                            <div style="width:100px;">
                                <div>${e.timeRemain}</div>
                            </div>
                            <div style="width:50px;">
                                <div>${e.tblClassroomByClassroomId.name}</div>
                            </div>
                            <div style="width: 100px">
                                <div class="group-button">
                                    <c:choose>
                                        <c:when test="${e.classroomId != null}">
                                            <div class="btn btn-detail"
                                                 title="Chỉnh sửa" onclick="editEquipment(${e.classroomId},
                                                ${e.tblEquipmentCategoryByCategoryId.id}, '${e.name}', ${e.serialNumber},
                                                ${e.usingTime}, ${e.timeRemain},${e.id}); document.getElementById('time-remain').style.display = 'block';
                                                    $('#modal-1 > div.content-modal').css('height','360px');"><i
                                                    class="fa fa-pencil"></i>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="btn btn-detail"
                                                 title="Chỉnh sửa" onclick="editEquipment(0,
                                                ${e.tblEquipmentCategoryByCategoryId.id}, '${e.name}', ${e.serialNumber},
                                                ${e.usingTime}, ${e.timeRemain},${e.id}); document.getElementById('time-remain').style.display = 'block';
                                                    $('#modal-1 > div.content-modal').css('height','360px');"><i
                                                    class="fa fa-pencil"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${e.classroomId != null}">
                                            <div class="btn btn-remove"
                                                 onclick="conformData(2,{message:'Bạn có muốn gỡ thiết bị ${e.name} (serial: ${e.serialNumber}) ra khỏi phòng ${e.tblClassroomByClassroomId.name}?',
                                                         btnName:'Xóa',link:'/staff/removeEquipment?equipmentId=${e.id}&remove=false'})"
                                                 title="Xóa"><i
                                                    class="fa fa-times"></i>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="btn btn-remove"
                                                 onclick="conformData(2,{message:'Bạn có muốn xóa thiết bị ${e.name} (serial: ${e.serialNumber})?', btnName:'Xóa', link:'/staff/removeEquipment?equipmentId=${e.id}&remove=true'})"
                                                 title="Xóa"><i
                                                    class="fa fa-times"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <p class="clear"></p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <div style="width:10%;">
                                <div class="equip"
                                     style="background-image: url('../../resource/img/equipment/${e.tblEquipmentCategoryByCategoryId.imageUrl}')">
                                </div>
                            </div>
                            <div style="width:12%;">
                                <div>${e.name}</div>
                            </div>
                            <div style="width:20%;">
                                <div>${e.serialNumber}</div>
                            </div>
                            <div style="width:100px;">
                                <div>${e.usingTime}</div>
                            </div>
                            <div style="width:100px;">
                                <div>${e.timeRemain}</div>
                            </div>
                            <div style="width:50px;">
                                <div>${e.tblClassroomByClassroomId.name}</div>
                            </div>
                            <div style="width: 100px">
                                <div class="group-button">
                                    <c:choose>
                                        <c:when test="${e.classroomId != null}">
                                            <div class="btn btn-detail"
                                                 title="Chỉnh sửa" onclick="editEquipment(${e.classroomId},
                                                ${e.tblEquipmentCategoryByCategoryId.id}, '${e.name}', ${e.serialNumber},
                                                ${e.usingTime}, ${e.timeRemain},${e.id}); document.getElementById('time-remain').style.display = 'block';
                                                    $('#modal-1 > div.content-modal').css('height','360px');"><i
                                                    class="fa fa-pencil"></i>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="btn btn-detail"
                                                 title="Chỉnh sửa" onclick="editEquipment(0,
                                                ${e.tblEquipmentCategoryByCategoryId.id}, '${e.name}', ${e.serialNumber},
                                                ${e.usingTime}, ${e.timeRemain},${e.id});
                                                    document.getElementById('time-remain').style.display = 'block';
                                                    $('#modal-1 > div.content-modal').css('height','360px');"><i
                                                    class="fa fa-pencil"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${e.classroomId != null}">
                                            <div class="btn btn-remove"
                                                 onclick="conformData(2,{message:'Bạn có muốn gỡ thiết bị ${e.name} (serial: ${e.serialNumber}) ra khỏi phòng ${e.tblClassroomByClassroomId.name}?',
                                                         btnName:'Xóa',link:'/staff/removeEquipment?equipmentId=${e.id}&remove=false'})"
                                                 title="Xóa"><i
                                                    class="fa fa-times"></i>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="btn btn-remove"
                                                 onclick="conformData(2,{message:'Bạn có muốn xóa thiết bị ${e.name} (serial: ${e.serialNumber})?', btnName:'Xóa', link:'/staff/removeEquipment?equipmentId=${e.id}&remove=true'})"
                                                 title="Xóa"><i
                                                    class="fa fa-times"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <p class="clear"></p>
                        </div>
                    </c:otherwise>
                </c:choose>


            </c:forEach>
        </form>
    </div>
</div>
<div id="pagination" style="padding-left: 150px;"></div>

<script>
    function editEquipment(classroomId, category, name, serialNumber, usingTime, timeRemain, equipmentId) {
        document.getElementById('classroomName').innerHTML = 'Cập nhật thiết bị';
        document.getElementById('equipment-button').value = 'Cập nhật';
        document.getElementById('equipment-action').value = 'update';
        document.getElementById('equipment-classroomId').value = classroomId;
        document.getElementById('equipment-id').value = equipmentId;
        document.getElementById('equipment-name').value = name;
        document.getElementById('equipment-number').value = serialNumber;
        document.getElementById('equipment-category').value = category;
        document.getElementById('equipment-time').value = usingTime;
        document.getElementById('equipment-remain').value = timeRemain;
        document.getElementById('equipment-category-select').disabled = true;
        $('#equipment-category-select option[value="' + category + '"]').attr("selected", "selected");
        showModal(1, 'modal-1');
    }

    function clearEquipment() {
        document.getElementById('classroomName').innerHTML = 'Tạo thiết bị';
        document.getElementById('equipment-button').value = 'Tạo';
        document.getElementById('equipment-name').value = '';
        document.getElementById('equipment-number').value = '';
        document.getElementById('equipment-time').value = '';
        document.getElementById('equipment-remain').value = '';
        document.getElementById('equipment-category-select').selectedIndex = 0;
    }
</script>