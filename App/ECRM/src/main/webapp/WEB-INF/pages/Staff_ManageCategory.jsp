<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 7/30/2015
  Time: 12:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <c:set var="categories" value="${requestScope.CATEGORIES}"/>

</head>
<body>
<div class="table" style="width:77%; height: 350px">
    <div class="header-table">
        <div style="width:100px">
            <div>Hình ảnh</div>
        </div>
        <div style="width:100px;">
            <div>Tên</div>
        </div>
        <div style="width:100px;">
            <div>Quản lý</div>
        </div>
        <div style="width: 100px">
            <div></div>
        </div>
        <p class="clear"></p>
    </div>
    <div class="body-table">
        <form action="/staff/removeEqipment" id="removeCategory">
            <input type="hidden" value="" id="removeClassroomName"
                   name="classroomName"/>
            <c:forEach items="${categories}" var="c">
                <div class="row" style="height: 35px">
                    <div style="width: 100px">
                        <div class="equip"
                             style="background-image: url('../../resource/img/equipment/${c.imageUrl}')">
                        </div>
                    </div>
                    <div style="width: 100px">${c.name}</div>
                    <div style="width: 100px"><c:choose>
                        <c:when test="${c.isManaged}">
                            Có
                        </c:when>
                        <c:otherwise>
                            Không
                        </c:otherwise>
                    </c:choose></div>
                    <div style="width: 100px">
                        <div class="control">
                            <div class="group-button">
                                <div class="btn btn-remove" title="Xóa"
                                     onclick="conformData(2,{message:'Bạn có muốn gỡ thiết bị ${e.name} (serial: ${e.serialNumber}) ra khỏi phòng ${e.tblClassroomByClassroomId.name}?',
                                             btnName:'Xóa',link:'/staff/removeEquipment?equipmentId=${e.id}'})"><i
                                        class="fa fa-times"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </c:forEach>
        </form>
    </div>
</div>
</body>
</html>
