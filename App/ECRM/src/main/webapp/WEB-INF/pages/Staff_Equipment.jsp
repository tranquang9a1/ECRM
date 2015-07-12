<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:choose>
    <c:when test="${empty user}">
        <jsp:forward page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>ECRM - Equipment Classroom Management</title>
            <link rel="stylesheet" href="../../resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="../../resource/css/layout.css"/>
            <link rel="stylesheet" href="../../resource/css/general.css"/>
            <link rel="stylesheet" href="../../resource/css/management.css"/>

            <script src="../../resource/js/jquery-1.11.3.js"></script>
            <script src="../../resource/js/jquery-1.11.3.min.js"></script>

        </head>
        <body style="background: url('../../resource/img/background/LargeClassroomWindowView_0.JPG') no-repeat; background-size: cover">
        <c:set var="tab" value="${requestScope.ACTIVETAB}"/>
        <c:set var="equipments" value="${requestScope.EQUIPMENTS}"/>
        <c:set var="categories" value="${requestScope.CATEGORIES}"/>

        <div class="contain-layout">
            <jsp:include flush="true" page="Header.jsp"/>
        </div>
        <div class="container">
            <div class="title page-title right-button">
                <p>Quản lý thiết bị</p>

                <div class="clear"></div>
            </div>
            <div class="body-content">
                <div id="tab1" class="body-tab">
                    <div><input type="button" class="btn btn-orange" value="Tạo thiết bị"
                                onclick="showModal(1, 'modal-1')"/></div>

                    <div class="search" style="  margin: 0 0 15px;">
                        <input type="text" placeholder="............."/>
                        <input type="button" class="btn btn-orange" value="Tìm kiếm"/>
                    </div>
                    <div class="clear"></div>
                    <div class="table small-table" style="width:860px;">
                        <div class="header-table">
                            <div style="width:110px;">
                                <div>Loại thiết bị</div>
                            </div>
                            <div style="width:150px;">
                                <div>Tên</div>
                            </div>
                            <div style="width:150px;">
                                <div>Số Serial</div>
                            </div>
                            <div style="width:150px;">
                                <div>Thời gian sử dụng</div>
                            </div>
                            <div style="width:150px;">
                                <div>Thời gian còn lại</div>
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
                            <form action="/staff/removeClassroom" id="removeClassroom">
                                <input type="hidden" value="" id="removeClassroomName" name="classroomName"/>
                                <c:forEach items="${equipments}" var="e">
                                    <div class="row">
                                        <div style="width:110px;">
                                            <div>${e.tblEquipmentCategoryByCategoryId.name}</div>
                                        </div>
                                        <div style="width:150px;">
                                            <div>${e.name}</div>
                                        </div>
                                        <div style="width:150px;">
                                            <div>${e.serialNumber}</div>
                                        </div>
                                        <div style="width:150px;">
                                            <div>${e.tblEquipmentCategoryByCategoryId.usingTime}</div>
                                        </div>
                                        <div style="width:150px;">
                                            <div>${e.timeRemain}</div>
                                        </div>
                                        <div style="width:50px;">
                                            <div>${e.tblClassroomByClassroomId.name}</div>
                                        </div>
                                        <div style="width: 100px">
                                            <div class="group-button">
                                                <div class="btn btn-detail" title="Chỉnh sửa"><i
                                                        class="fa fa-pencil"></i>
                                                </div>
                                                <div class="btn btn-remove" onclick="conform(4, ${e.id})"
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

                </div>
                <form action="/staff/createEquipment" id="createEquipment">
                    <div class="modal modal-large" id="modal-1">
                        <div class="content-modal">
                            <div class="header-modal title">
                                <p id="classroomName">Tạo thiết bị</p>
                                <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
                            </div>
                            <div class="body-modal">
                                <div class="group-control">
                                    <div class="name">Loại thiết bị</div>
                                    <div class="control">
                                        <select name="categoryId">
                                            <c:forEach items="${categories}" var="ca">
                                                <option value="${ca.id}">${ca.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="group-control">
                                    <div class="name">Tên thiết bị</div>
                                    <input type="text" name="name">
                                </div>
                                <div class="group-control">
                                    <div class="name">Số seri</div>
                                    <input type="text" name="serialNumber">
                                </div>
                            </div>
                            <div class="footer-modal">
                                <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-1')"
                                       value="Thoát"/>
                                <input type="button" class="btn btn-orange" onclick="conform(1);" value="Tạo"/>
                            </div>
                        </div>
                        <div class="black-background"></div>
                    </div>
                </form>
            </div>
        </div>


        <script src="../../resource/js/script.js"></script>

        <script>
            function doAction(choose, object) {
                closeConform();
                switch (choose) {
                    case 1:
                        document.getElementById('createEquipment').submit();
                        break;
                }
            }
        </script>
        </body>
        </html>
    </c:otherwise>
</c:choose>