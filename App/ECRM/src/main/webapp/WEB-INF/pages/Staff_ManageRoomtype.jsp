<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/30/2015
  Time: 12:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/29/2015
  Time: 11:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value="${requestScope.ALLROOMTYPE}" var="roomtypes"/>
<div class="table small-table" style="height: 350px; width: 400px">
        <div class="header-table">
            <div class="width-40">
                <div>Loại Phòng</div>
            </div>
            <div class="width-30">
                <div>Số chỗ ngồi</div>
            </div>
            <div class="width-30">
                <div>Quản lý</div>
            </div>
            <p class="clear"></p>
        </div>
        <div class="body-table">
            <form action="/staff/removeRoomType" id="removeRoomtype" method="post">
                <input type="hidden" name="RoomtypeId" value="" id="removeRoomtypeID"/>
                <c:forEach items="${roomtypes}" var="r">
                    <div class="row">
                        <div class="width-40">
                            <div>${r.roomType.name}</div>
                        </div>
                        <div class="width-30">
                            <div>${r.roomType.slots}</div>
                        </div>
                        <div class="width-30">
                            <div class="group-button">
                                <div class="btn btn-view" title="Xem sơ đồ"
                                     onclick='showModal(1, "modal-manageroomtype"); showMapForEdit(${r.roomType.id},"${r.roomType.name}",
                                         ${r.roomType.verticalRows},"${r.roomType.horizontalRows}", "${r.roomType.numberOfSlotsEachHRows}"
                                             ,${r.equipment})'>
                                    <i class="fa fa-eye"></i>
                                </div>
                                <div class="btn btn-remove"
                                     onclick="checkDeleteRoomType(${r.roomType.id},'${r.roomType.name}');"
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
<div id="pagination2" style="padding-left: 150px;"></div>

