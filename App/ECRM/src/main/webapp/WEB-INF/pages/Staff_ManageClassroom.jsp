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
<c:set var="maxPage" value="${requestScope.MAXPAGE}"/>
<c:set var="page" value="${requestScope.PAGE}"/>
<c:set var="sort" value="${requestScope.SORT}"/>

<div class="table small-table" style="height: 360px;">
        <div class="header-table">
            <div class="width-20">
                <div style="cursor: pointer"><a style="text-decoration: none; color: #555" href="/staff/classroom?ACTIVETAB=tab1&MESSAGE=0&SORT=${sort}">Số phòng</a></div>
                <c:if test="${sort.equals('ASC')}">
                    <i class="fa fa-sort-desc" style="margin: 8px 0 13px"></i>
                </c:if>
                <c:if test="${!sort.equals('ASC')}">
                    <i class="fa fa-sort-asc" style="margin: 14px 0 7px;"></i>
                </c:if>
            </div>
            <div class="width-25">
                <div>Loại phòng</div>
            </div>
            <div class="width-30">
                <div>Trạng thái</div>
            </div>
            <div class="width-25">
                <div>Quản lý</div>
            </div>
            <p class="clear"></p>
        </div>
        <div class="body-table">
            <form action="/staff/removeClassroom" id="removeClassroom" method="post">
                <input type="hidden" value="" id="removeClassroomName" name="classroomName"/>
                <c:forEach items="${classrooms}" var="cl">
                    <div class="row">
                        <div class="width-20">
                            <div>${cl.classroom.name}</div>
                        </div>
                        <div class="width-25">
                            <div>${cl.roomType.name}</div>
                        </div>
                        <div class="width-30">
                            <c:if test="${not cl.classroom.isAllInformation}">
                                <div><a href="/staff/updateEquipment?classroomId=${cl.classroom.id}">Cập nhật thiết
                                    bị</a></div>
                            </c:if>
                            <c:if test="${cl.classroom.damagedLevel > 0}">
                                <div>Độ hư hại: ${cl.classroom.damagedLevel}</div>
                            </c:if>
                            <c:if test="${cl.classroom.damagedLevel == 0}">
                                <div>Bình thường</div>
                            </c:if>
                        </div>
                        <div class="width-25">
                            <div class="group-button">
                                <div class="btn btn-detail"
                                     title="Đổi phòng" onclick="showModal(1,'modal-changeRoom');
                                     document.getElementById('changeRoom-id').value=${cl.classroom.id};
                                        document.getElementById('changeRoom-name').innerHTML = 'Đổi phòng cho phòng ${cl.classroom.name}';
                                        document.getElementById('getchangeRoom-name').innerHTML = 'Phòng trống cho phòng ${cl.classroom.name}'"><i
                                        class="fa fa-refresh"></i>
                                </div>
                                <div class="btn btn-view" title="Xem sơ đồ"
                                     onclick='document.getElementById("ClassroomAction").value="update";
                                             document.getElementById("loaiphong").innerHTML = "${cl.roomType.name}" ;
                                             showModal(1, "modal-manageclassroom");
                                             showClassroomMap(${cl.classroom.name},${cl.classroom.roomTypeId},${cl.roomType.verticalRows},
                                             "${cl.roomType.horizontalRows}", "${cl.roomType.numberOfSlotsEachHRows}"
                                             ,${cl.equipment});
                                             editClassroom(${cl.classroom.roomTypeId}, ${cl.classroom.id});
                                             document.getElementById("classroom-create").value="Cập nhật"'>
                                    <i class="fa fa-eye"></i>
                                </div>
                                <div class="btn btn-remove"
                                     onclick="conformData(2, {message:'Bạn có muốn xóa phòng học ${cl.classroom.name}!', btnName:'Xóa',link:'/staff/removeClassroom?classroomName=${cl.classroom.name}'})"
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
<div id="pagination" style="width: 550px">
    <c:set var="partLink" value=""/>
    <c:if test="${sort.equals('ASC')}">
        <c:set var="partLink" value="&SORT=DESC"/>
    </c:if>

    <c:if test="${maxPage > 1}">
        <div class="paging" style="margin: 0">
            <c:if test="${page + 1 <= maxPage}">
                <div class="pagen" style="width: auto; padding: 0 5px"><a href="/staff/classroom?ACTIVETAB=tab1&MESSAGE=0&Page=${page+1}${partLink}">Sau</a></div>
                <div class="pagen"><a href="/staff/classroom?ACTIVETAB=tab1&MESSAGE=0&Page=${page+1}${partLink}">${page+1}</a></div>
            </c:if>
            <div class="pagen current">${page}</div>
            <c:if test="${page - 1 > 0}">
                <div class="pagen"><a href="/staff/classroom?ACTIVETAB=tab1&MESSAGE=0&Page=${page-1}${partLink}">${page-1}</a></div>
                <div class="pagen" style="width: auto; padding: 0 5px"><a href="/staff/classroom?ACTIVETAB=tab1&MESSAGE=0&Page=${page-1}${partLink}">Trước</a></div>
            </c:if>
        </div>
    </c:if>
</div>
<script>
    function editClassroom(id, classroomId) {
        $('#selectBox option[value="' + id + '"]').prop('selected', true);
        var selectedValue = $('#selectBox option[value="' + id + '"]').data("value");
        document.getElementById('loaiphong').innerHTML = selectedValue.name;
        document.getElementById('roomtype').value = selectedValue.id;
        showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
                , selectedValue.equipment);
        document.getElementById('classroomId').value = classroomId;
    }
</script>


