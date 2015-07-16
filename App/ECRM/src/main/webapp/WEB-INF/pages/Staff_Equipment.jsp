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
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/simplePagination.css"/>

            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/jquery-1.11.3.min.js"></script>

        </head>
        <body>
        <c:set var="equipments" value="${requestScope.EQUIPMENTS}"/>
        <c:set var="categories" value="${requestScope.CATEGORIES}"/>
        <c:set var="tab" value="${requestScope.TABCONTROL}"/>
        <div class="layout-background">
            <div class="container">
                <div class="header">
                    <div class="logo">Quản lý thiết bị</div>
                    <div class="account-control">
                        <p>${user.tblUserInfoByUsername.fullName}</p>
                        <img src="/resource/img/gears.png"/>
                    </div>
                </div>
                <div class="content-body">
                    <c:import url="/bao-cao/danh-muc"/>
                    <div class="right-content">
                        <div class="page active">
                            <div class="title">
                                <p>Quản lý thiết bị</p>
                                <div><input type="button" class="btn btn-orange" value="Tạo thiết bị"
                                            onclick="showModal(1, 'modal-1')"/></div>
                                <div class="clear"></div>

                            </div>
                            <div class="body-content">
                                <div class="clear"></div>
                                <div class="table" style="width:100%; height: 315px">
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
                                        <form action="/staff/removeClassroom" id="removeClassroom">
                                            <input type="hidden" value="" id="removeClassroomName"
                                                   name="classroomName"/>
                                            <c:forEach items="${equipments}" var="e">
                                                <c:choose>
                                                    <c:when test="${e.timeRemain <=50}"><div class="row" style="background-color: #cd0a0a"></c:when>
                                                    <c:otherwise><div class="row"></c:otherwise>
                                                </c:choose>

                                                    <div style="width:10%;">
                                                        <div>${e.tblEquipmentCategoryByCategoryId.name}</div>
                                                    </div>
                                                    <div style="width:12%;">
                                                        <div>${e.name}</div>
                                                    </div>
                                                    <div style="width:20%;">
                                                        <div>${e.serialNumber}</div>
                                                    </div>
                                                    <div style="width:100px;">
                                                        <div>${e.tblEquipmentCategoryByCategoryId.usingTime}</div>
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
                                                                <c:when test="${e.timeRemain<=50 and e.tblEquipmentCategoryByCategoryId.id==1}">
                                                                    <div class="btn btn-detail" title="Chỉnh sửa"><i
                                                                            class="fa fa-pencil"></i>
                                                                    </div>
                                                                </c:when>
                                                            </c:choose>
                                                            <div class="btn btn-remove"
                                                                 onclick="conform(4, ${e.id})"
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
                            </div>
                        </div>
                    </div>
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
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-1')"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange" onclick="conform(1);"
                               value="Tạo"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
        </form>
        <script src="/resource/js/script.js"></script>
        <script src="/resource/js/jquery.simplePagination.js"></script>

        <script>
            function doAction(choose, object) {
                closeConform();
                switch (choose) {
                    case 1:
                        document.getElementById('createEquipment').submit();
                        break;
                }
            }

            //phan trang
            jQuery(function ($) {
                var items = $("#removeClassroom > div");

                var numItems = items.length;
                var perPage = 8;

                // only show the first 2 (or "first per_page") items initially
                items.slice(perPage).hide();
                // now setup pagination
                $("#pagination").pagination({
                    items: numItems,
                    itemsOnPage: perPage,
                    cssStyle: "compact-theme",
                    onPageClick: function (pageNumber) { // this is where the magic happens
                        // someone changed page, lets hide/show trs appropriately
                        var showFrom = perPage * (pageNumber - 1);
                        var showTo = showFrom + perPage;

                        items.hide() // first hide everything, then show for the new page
                                .slice(showFrom, showTo).show();
                    }
                });
            });

            document.getElementById("${tab}").className += " active";
            document.getElementById("${tab}").setAttribute("data-main", "1");


        </script>
        </body>
        </html>
    </c:otherwise>
</c:choose>