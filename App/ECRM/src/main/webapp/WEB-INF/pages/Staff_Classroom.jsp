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
            <link rel="stylesheet" href="/resource/css/layout.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/management.css"/>
            <link rel="stylesheet" href="/resource/css/roomtype-2.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/simplePagination.css"/>

            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/jquery-1.11.3.min.js"></script>
            <script src="/resource/js/socket.io.js"></script>

            <script src="/resource/js/socket-io.js"></script>
            <script src="/resource/js/staff-notify.js"></script>
            <script src="/resource/js/newTemplate.js"></script>
        </head>
        <body>
        <c:set var="tab" value="${requestScope.ACTIVETAB}"/>
        <c:set var="tab1" value="${requestScope.TABCONTROL}"/>

        <div class="layout-background">
            <div class="container">
                <div class="header">
                    <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
                    <div class="account-control">
                        <p>${user.tblUserInfoByUsername.fullName}</p>
                        <img src="../../resource/img/gears.png"/>
                    </div>
                </div>
                <div class="content-body">
                    <c:import url="/bao-cao/danh-muc"/>
                    <div class="right-content">
                        <div class="page active" id="classroom">
                            <div class="title"><p>Phòng Học</p></div>
                            <div class="body-content" style="background-color: whitesmoke;">
                                <div class="tab">
                                    <div class="tab-medium">
                                        <ul>
                                            <li id="tab1-1" onclick="changeTab('tab1', this)">Phòng học</li>
                                            <li id="tab2-2" onclick="changeTab('tab2', this)">Loại phòng</li>
                                        </ul>
                                    </div>
                                    <div class="content-tab">
                                        <div id="tab1" class="body-tab">
                                            <div><input type="button" class="btn btn-orange"
                                                        onclick="document.getElementById('ClassroomAction').value='create';showModal(1, 'modal-1')"
                                                        value="Tạo phòng học"/></div>
                                            <jsp:include flush="false" page="Staff_ManageClassroom.jsp"/>
                                        </div>
                                        <div id="tab2" class="body-tab">
                                            <div>
                                                <div></div>
                                                <input type="button" class="btn btn-orange" style="margin: 0"
                                                       onclick="document.getElementById('RoomTypeAction').value='update';showModal(1, 'modal-roomtypedetail')"
                                                       value="Tạo loại phòng"/></div>
                                            <jsp:include flush="false" page="Staff_ManageRoomtype.jsp"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:import url="/bao-cao/thong-bao?little=false&quay-lai=classroom"/>
                        <div class="loading-page">
                            <img src="/resource/img/500.GIF">
                            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="content-modal">
                <%--Modal hien len khi nhap vao nut xem cua CLASSROOM--%>
            <div class="modal modal-medium" id="modal-manageclassroom">
                <div class="content-modal">
                    <div class="header-modal title">
                        <p id="roomname"></p>
                        <i class="fa fa-times" onclick="showModal(0, 'modal-manageclassroom')"></i>
                    </div>
                    <div class="body-modal">
                        <div class="group-control">
                            <div class="name">Sơ đồ</div>
                        </div>
                        <div id="class-roommap">
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-manageclassroom');clearthietbi()"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange"
                               onclick="document.getElementById('ClassroomAction').value='update';showModal(2, 'modal-manageclassroom','modal-1'); "
                               value="Chỉnh Sửa"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
                <%--Modal hien len khi nhap vao nut xem cua ROOMTYPE--%>
            <div class="modal modal-medium" id="modal-manageroomtype">
                <div class="content-modal">
                    <div class="header-modal title">
                        <p class="roomtypename"></p>
                        <i class="fa fa-times" onclick="showModal(0, 'modal-view')"></i>
                    </div>
                    <div class="body-modal">
                        <div class="group-control">
                            <div class="name">Sơ đồ</div>
                        </div>
                        <div id="roomtype-map">
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-manageroomtype');clearthietbi()"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange"
                               onclick="document.getElementById('RoomTypeAction').value='update';showModal(2, 'modal-manageroomtype','modal-roomtypedetail')"
                               value="Chỉnh Sửa"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
                <%--Modal hien len khi nhap vao nut tao phong cua CLASSROOM--%>
            <form action="/staff/createClassroom" id="createClassroomForm" name="CreateClassroomForm" method="post">
                <input type="hidden" name="Action" id="ClassroomAction">

                <div class="modal modal-small" id="modal-1">
                    <div class="content-modal">
                        <div class="header-modal title">
                            <p id="classroomName">Tạo Phòng</p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
                        </div>
                        <div class="body-modal">
                            <div class="group-control">
                                <div class="name">Số phòng</div>
                                <div class="control">
                                    <input id="roomNameId" type="text" value="" name="RoomName"
                                           placeholder=".........................."/>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Loại phòng</div>
                                <div class="value" id="loaiphong"></div>
                                <input type="hidden" name="RoomType" value="" id="roomtype">
                                <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-1','modal-2')"
                                       value="Chọn"/>
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-1'); clearthietbi()"
                                   value="Thoát"/>
                            <input type="button" class="btn btn-orange" onclick="validateCreateClassroomForm();"
                                   value="Thêm"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
                <%--Modal hien len khi nhap vao nut chon kieu phong khi tao phong cua CLASSROOM--%>
            <div class="modal modal-medium" id="modal-2">
                <div class="content-modal" style="height: 660px;">
                    <div class="header-modal title">
                        <p>Loại phòng học</p>
                        <i class="fa fa-times" onclick="showModal(2, 'modal-2','modal-1')"></i>
                    </div>
                    <c:set value="${requestScope.ALLROOMTYPE}" var="roomtypes"/>
                    <div class="body-modal">
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Loại phòng</div>
                            <div class="control">
                                <select id="selectBox">
                                    <option value="" selected>...</option>
                                    <c:forEach var="r" items="${roomtypes}">
                                        <option data-value='{"id" :${r.id}, "verticalRows":${r.verticalRows}, "horizontalRows":"${r.horizontalRows}", "noSlotsEachHRows":"${r.numberOfSlotsEachHRows}",
                            "airConditioning":${r.airConditioning},
                            "fan":${r.fan}, "projector":${r.projector}, "speaker":${r.speaker}, "television":${r.television},"name":"${r.name}"}'>
                                            ${r.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="group-control">
                            <div class="name">Sơ đồ</div>
                        </div>
                        <div id="roomtype-map2">
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-2','modal-1')"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange" onclick="showModal(2, 'modal-2','modal-1') "
                               value="Chọn"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
                <%--Modal hien len khi nhap vao nut tao roomtype--%>
            <div class="modal modal-medium" id="modal-roomtypedetail">
                <div class="content-modal" style="height: 650px;">
                    <div class="header-modal title">
                        <p class="roomtypename">Tạo loại phòng </p>
                        <i class="fa fa-times" onclick="showModal(0, 'modal-roomtypedetail')"></i>
                    </div>
                    <div class="body-modal">
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Tên loại phòng</div>
                            <div class="control">
                                <input type="text" id="roomTypeName"/>
                            </div>
                        </div>
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Máy chiếu</div>
                            <div class="control">
                                <input class="check-box" type="checkbox" id="projector"/>
                            </div>
                        </div>
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Máy quạt</div>
                            <div class="control">
                                <input class="check-box" type="checkbox" id="quat"/>
                            </div>
                        </div>
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Máy lạnh</div>
                            <div class="control">
                                <input class="check-box" type="checkbox" id="mayLanh" onclick="checkMayLanh();"/>
                                Số lượng: <input disabled id="quantityAir" style="width: 20px" onkeydown="return ( event.ctrlKey || event.altKey
                    || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false)
                    || (95<event.keyCode && event.keyCode<106)
                    || (event.keyCode==8) || (event.keyCode==9)
                    || (event.keyCode>34 && event.keyCode<40)
                    || (event.keyCode==46) )">
                            </div>
                        </div>
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Tivi</div>
                            <div class="control">
                                <input class="check-box" type="checkbox" id="tivi"/>
                            </div>
                        </div>
                        <div class="group-control" style="margin: 15px 0 0">
                            <div class="name">Loa</div>
                            <div class="control">
                                <input class="check-box" type="checkbox" id="loa"/>
                            </div>
                        </div>
                        <div class="group-control">
                            <div class="name">Số dãy bàn</div>
                            <div class="control">
                                <select id="vrow" onchange="createDetailMap()">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                    <option value="10">10</option>
                                </select>
                            </div>
                        </div>
                        <div class="room-map">
                            <div class="title-map">
                                <div></div>
                                <div>Số hàng</div>
                                <div>Số ghế</div>
                                <p class="clear"></p>
                            </div>
                            <div id="detail">
                                <div class="row" id="row-1">

                                </div>
                                <div class="row" id="row-2">

                                </div>
                                <div class="row" id="row-3">

                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-roomtypedetail'); clearthietbi()"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange"
                               onclick="viewMap(); showModal(2, 'modal-roomtypedetail','modal-4')" value="Xem trước"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
                <%--Modal hien len khi nhap vao nut Xem truoc kieu phong khi tao ROOMTYPE--%>
            <form action="/staff/createRoomType" id="createRoomType" name="CreateRoomType" method="post">
                <div class="modal modal-medium" id="modal-4">
                    <input type="hidden" id="RoomtypeId" name="RoomtypeId" value="">
                    <input type="hidden" id="name" name="RoomtypeName" value="">
                    <input type="hidden" id="Slots" name="Slots" value="">
                    <input type="hidden" id="VerticalRows" name="VerticalRows" value="">
                    <input type="hidden" id="HorizontalRows" name="HorizontalRows" value="">
                    <input type="hidden" id="NumberOfSlotsEachHRows" name="NumberOfSlotsEachHRows" value="">
                    <input type="hidden" id="AirConditioning" name="AirConditioning" value="">
                    <input type="hidden" id="Fan" name="Fan" value="">
                    <input type="hidden" id="Projectors" name="Projector" value="">
                    <input type="hidden" id="Speaker" name="Speaker" value="">
                    <input type="hidden" id="Bulb" name="Bulb" value="">
                    <input type="hidden" id="Television" name="Television" value="">
                    <input type="hidden" id="RoomTypeAction" name="Action" value="">


                    <div class="content-modal">
                        <div class="header-modal title">
                            <p>Tạo loại phòng </p>
                            <i class="fa fa-times" onclick="showModal(2, 'modal-4', 'modal-roomtypedetail')"></i>
                        </div>
                        <div class="body-modal">
                            <div class="map-container" id="idContainer">
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(2, 'modal-4', 'modal-roomtypedetail')"
                                   value="Quay lại"/>
                            <input type="button" class="btn btn-orange" onclick="validateCreateRoomType();"
                                   value="Tạo mẫu"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
        </div>


        </body>
        <script src="/resource/js/script.js"></script>
        <script src="/resource/js/staff_manageclassroom.js"></script>
        <script src="/resource/js/staff_manageroomtype.js"></script>
        <script src="/resource/js/roomtype-2.js"></script>
        <script src="/resource/js/jquery.simplePagination.js"></script>

        <script>
            window.onload = createDetailMap;
            function doAction(choose, object) {
                closeConform();
                switch (choose) {
                    case 1:
                        showModal(0, 'modal-1');
                        document.getElementById('createClassroom').submit();
                        alert("Phong hoc đã được lưu thành công!");
                        clearthietbi();
                        break;
                    case 2:
                        showModal(0, 'modal-4');
                        document.getElementById('createRoomType').submit();
                        alert("Loại phòng đã được lưu thành công!");
                        clearthietbi();
                        break;
                    case 3:
                        getRoomTypeId(object);
                        document.getElementById('removeRoomtype').submit();
                        alert("Loai phong da xoa thanh cong!");
                        clearthietbi();
                        break;
                    case 4:
                        getClassroomName(object);
                        document.getElementById('removeClassroom').submit();
                        alert("Phong hoc da xoa thanh cong!");
                        clearthietbi();
                        break;
                }
            }
            function getRoomTypeId(roomtypeId) {
                document.getElementById('removeRoomtypeID').value = roomtypeId;
            }
            function getClassroomName(removeClassroomName) {
                document.getElementById('removeClassroomName').value = removeClassroomName;
            }

            function clearthietbi() {
                $(".thietBi").remove();
                document.getElementById("loaiphong").innerHTML = "";
                $('#selectBox option:first-child').attr("selected", "selected");
                $('#vrow option:first-child').attr("selected", "selected");
                createDetailMap();
                $(".check-box").prop("checked", false);
                var classroommap = document.getElementsByClassName('classroom-map');
                for (var i = 0; i < classroommap.length; i++) {
                    while (classroommap[i].hasChildNodes()) {
                        classroommap[i].removeChild(classroommap[i].lastChild);
                    }
                }
                document.getElementsByClassName('roomtypename')[1].innerHTML = "Tao Loai Phong";
                document.getElementById('RoomtypeId').innerHTML = "";
                document.getElementById('classroomName').innerHTML = "Tạo Phòng";
                document.getElementById('roomNameId').value = "";
                document.getElementById('name').value = "";
            }
            document.getElementById("${tab}").className = "body-tab active";
            var tab = '${tab}';
            if (tab === 'tab1') {
                document.getElementById("tab1-1").className = "active";
                document.getElementById("tab2-2").className = "";
            } else {
                document.getElementById("tab2-2").className = "active";
                document.getElementById("tab1-1").className = "";
            }

            function checkMayLanh() {
                if (document.getElementById('mayLanh').checked) {
                    document.getElementById('quantityAir').disabled = false;
                } else {
                    document.getElementById('quantityAir').disabled = true;
                    document.getElementById('quantityAir').value = "";
                }
            }


            function validateCreateClassroomForm() {
                var roomName = document.forms["CreateClassroomForm"]["RoomName"].value;
                var roomType = document.forms["CreateClassroomForm"]["RoomType"].value;
                var action = document.forms["CreateClassroomForm"]["Action"].value;
                $.ajax({
                    type: "get",
                    url: "/ajax/checkCreateClassroom",
                    cache: false,
                    data: 'roomName=' + roomName + '&roomType=' + roomType + '&action=' + action,
                    success: function (data) {
                        if (data.status == true) {
                            document.getElementById('createClassroomForm').submit();
                            clearthietbi();
                        } else {
                            alert(data.alert);
                        }
                    },
                    error: function () {
                        alert('Error while request..');
                    }
                })
            }

            function validateCreateRoomType() {
                var roomtypeName = document.forms["CreateRoomType"]["RoomtypeName"].value;
                var airConditioning = document.forms["CreateRoomType"]["AirConditioning"].value;
                var action = document.forms["CreateRoomType"]["Action"].value;
                $.ajax({
                    type: "get",
                    url: "/ajax/checkCreateRoomType",
                    cache: false,
                    data: 'roomtypeName=' + roomtypeName + '&airConditioning=' + airConditioning + '&action=' + action,
                    success: function (data) {
                        if (data.status == true) {
                            document.getElementById('createRoomType').submit();
                            clearthietbi();
                        } else {
                            alert(data.alert);
                        }
                    },
                    error: function () {
                        alert('Error while request..');
                    }
                })
            }

            //phan trang
            jQuery(function ($) {
                var items = $("#removeClassroom > div");

                var numItems = items.length;
                var perPage = 5;

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

            //phan trang
            jQuery(function ($) {
                var items = $("#removeRoomtype > div");

                var numItems = items.length;
                var perPage = 5;

                // only show the first 2 (or "first per_page") items initially
                items.slice(perPage).hide();
                // now setup pagination
                $("#pagination2").pagination({
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

            document.getElementById("${tab1}").className += " active";
            document.getElementById("${tab1}").setAttribute("data-main", "1");
        </script>
        </html>
    </c:otherwise>
</c:choose>