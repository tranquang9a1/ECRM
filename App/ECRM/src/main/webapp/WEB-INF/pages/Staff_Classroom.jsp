<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${sessionScope.USER}"/>
<c:choose>
    <c:when test="${empty user}">
        <jsp:forward page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Hệ thống quản lý thiết bị phòng học</title>
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/layout.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/management.css"/>
            <link rel="stylesheet" href="/resource/css/roomtype-2.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/simplePagination.css"/>
            <link rel="stylesheet" href="/resource/css/jquery-ui.css"/>


            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/jquery-1.11.3.min.js"></script>
            <script src="/resource/js/socket.io.js"></script>
            <script src="/resource/js/jquery-ui.js"></script>

            <script src="/resource/js/socket-io.js"></script>
            <script src="/resource/js/staff-notify.js"></script>
            <script src="/resource/js/newTemplate.js"></script>
        </head>
        <body>
        <c:set var="tab" value="${requestScope.ACTIVETAB}"/>
        <c:set var="tab1" value="${requestScope.TABCONTROL}"/>
        <c:set var="category" value="${requestScope.CATEGORY}"/>
        <c:set value="${requestScope.ALLROOMTYPE}" var="roomtypes"/>
        <script>
            <c:set var="message" value="${requestScope.MESSAGE}"/>
            <c:if test="${message!= '0'}">
            showMessage('${message}');
            </c:if>
            function showMessage(message) {
                conformData(1, {message: message});
                var URLString = document.URL;
                window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", URLString.split("&MESSAGE")[0]+ "&MESSAGE=0");
            }
        </script>
        <div class="layout-background" style="height: 0px">
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
                            <div class="title" style="padding: 0; height: 70px">
                                <p>Quản lý phòng học</p>
                                <input type="button" id="createClassroomBtn" class="btn btn-orange" style="float:right; display: none" onclick="document.getElementById('ClassroomAction').value='create';showModal(1, 'modal-1');clearRoomName();
                                                    document.getElementById('classroom-create').value='Tạo phòng'" value="Tạo phòng học"/>
                                <input type="button" id="createRoomTypeBtn" class="btn btn-orange" style="float:right; display: none" onclick="document.getElementById('RoomTypeAction').value='create';showModal(1, 'modal-roomtypedetail')"
                                       value="Tạo loại phòng"/>
                                <div class="clear"></div>
                                <div class="title-category">
                                    <ul>
                                        <li id="head-tab1" onclick="changeManage('tab1', this)" data-tab="classroom-tab" class="active">Phòng học</li>
                                        <li id="head-tab2" onclick="changeManage('tab2', this)" data-tab="classroom-tab">Loại phòng</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="tab classroom-tab">
                                <div class="content-tab">
                                    <div id="tab1" class="body-tab">
                                        <jsp:include flush="false" page="Staff_ManageClassroom.jsp"/>
                                    </div>
                                    <div id="tab2" class="body-tab">
                                        <jsp:include flush="false" page="Staff_ManageRoomtype.jsp"/>
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
                        <i class="fa fa-times" onclick="showModal(0, 'modal-manageroomtype'); clearRoomType()"></i>
                    </div>
                    <div class="body-modal">
                        <div id="roomtype-map">

                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-manageroomtype');clearRoomType()"
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
                <input type="hidden" name="classroomId" id="classroomId" value="0">

                <div class="modal modal-small" id="modal-1">
                    <div class="content-modal" style="height: 217px;">
                        <div class="header-modal title">
                            <p id="classroomName">Tạo Phòng</p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-1'); clearRoomName();"></i>
                        </div>
                        <div class="body-modal">
                            <div class="group-control">
                                <div class="name" style="width: 135px;">Số phòng</div>
                                <div class="control">
                                    <input id="roomNameId"
                                           type="text" value="" name="RoomName" maxlength="4" placeholder="Nhập số phòng"/>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name" style="width: 135px;">Loại phòng</div>
                                <div class="value" id="loaiphong"></div>
                                <input type="hidden" name="RoomType" value="" id="roomtype">
                                <input type="button" class="btn btn-detail" style="float: left; margin: 0 0 0 0;"
                                       onclick="showModal(2, 'modal-1','modal-2'); chooseRoomType();"
                                       value="Chọn"/>
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(0, 'modal-1'); clearRoomName();"
                                   value="Thoát"/>
                            <input type="button" class="btn btn-orange" onclick="validateCreateClassroomForm();"
                                   value="Tạo Phòng" id="classroom-create"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
                <%--Modal hien len khi nhap vao nut chon kieu phong khi tao phong cua CLASSROOM--%>
            <div class="modal modal-medium" id="modal-2">
                <div class="content-modal" style="height: 625px;">
                    <div class="header-modal title">
                        <p>Loại phòng học</p>
                        <i class="fa fa-times" onclick="showModal(0, 'modal-2'); clearRoomName();"></i>
                    </div>
                    <div class="body-modal">
                        <div class="group-control">
                            <div class="name">Loại phòng</div>
                            <div class="control">
                                <select id="selectBox">
                                    <c:forEach var="r" items="${roomtypes}">
                                        <option value="${r.roomType.id}"
                                                data-value='{"id" :${r.roomType.id}, "verticalRows":${r.roomType.verticalRows},
                                                 "horizontalRows":"${r.roomType.horizontalRows}",
                                                 "noSlotsEachHRows":"${r.roomType.numberOfSlotsEachHRows}",
                           "name":"${r.roomType.name}", "equipment":${r.equipment}}'>
                                                ${r.roomType.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div id="roomtype-map2">
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-2'); clearRoomName();"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange"
                               onclick="showModal(2, 'modal-2','modal-1'); showRoomtype(); "
                               value="Chọn"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
                <%--Modal hien len khi nhap vao nut tao roomtype--%>
            <div class="modal modal-medium" id="modal-roomtypedetail">
                <div class="content-modal" style="height: 600px;">
                    <div class="header-modal title">
                        <p class="roomtypename">Tạo loại phòng </p>
                        <i class="fa fa-times" onclick="showModal(0, 'modal-roomtypedetail'); clearRoomType();"></i>
                    </div>
                    <div class="body-modal">
                        <div class="group-control">
                            <div class="name">Tên loại phòng</div>
                            <div class="control">
                                <input type="text" id="roomTypeName" maxlength="30"/>
                            </div>
                        </div>

                        <div style="margin: 15px 0 0 180px; font-weight: bold; height: 15px">
                            <div style="float:left; margin-right: 35px">Chọn</div>
                            <div style="float:left; margin-right: 35px">Số lượng</div>
                            <div>Độ ưu tiên</div>
                        </div>
                        <div style="width: 100%; height: 185px; margin: 10px 0 0; overflow-x: hidden; overflow-y: overlay">
                            <c:forEach items="${category}" var="c" varStatus="count">
                                <div class="group-control">
                                    <div class="name">${c.name}</div>
                                    <div class="control">
                                        <input class="check-box" style="margin: 11px 50px 11px 0" value="${c.id}-${c.imageUrl}" type="checkbox" id="thiet-bi${count.count}" onclick="checkQuantity(${count.count});"/>

                                        <div id="control-${count.count}" style="display: none" class="control-roomtype">
                                            <c:if test="${c.isManaged}">
                                                <input maxlength="2" id="quantity${count.count}" style="width: 60px; margin: 0 32px 0 0" class="quantity">
                                            </c:if>
                                            <c:if test="${!c.isManaged}">
                                                <p style="margin: 0 56px 0 0; float: left;">Không</p>
                                            </c:if>
                                            <select id="priority${count.count}" class="priority">
                                                <option value="1">Thấp</option>
                                                <option value="2">Trung Bình</option>
                                                <option value="3">Cao</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="group-control">
                            <div class="name">Số dãy bàn</div>
                            <div class="control">
                                <select id="vrow" onchange="createDetailMap()">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3" selected>3</option>
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
                                <div class="row" id="row-1"></div>
                                <div class="row" id="row-2"></div>
                                <div class="row" id="row-3" style="margin: 0 0 5px"></div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal"
                               onclick="showModal(0, 'modal-roomtypedetail'); clearRoomType()"
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
                    <input type="hidden" id="RoomtypeId" name="RoomtypeId" value="0">
                    <input type="hidden" id="name" name="RoomtypeName" value="" maxlength="30">
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
                    <input type="hidden" id="equip" name="equip" value="">


                    <div class="content-modal">
                        <div class="header-modal title">
                            <p id="title-roomtype-map">Tạo loại phòng </p>
                            <i class="fa fa-times"
                               onclick="showModal(2, 'modal-4', 'modal-roomtypedetail'); document.getElementById('equip').value = '';"></i>
                        </div>
                        <div class="body-modal">
                            <div class="map-container" id="idContainer">
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(2, 'modal-4', 'modal-roomtypedetail'); document.getElementById('equip').value = ''"
                                   value="Quay lại"/>
                            <input type="button" class="btn btn-orange"
                                   value="Tạo mẫu" onclick="validateCreateRoomType()" id="button-create-roomtype"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
                <%--Modal change room--%>
            <form id="form-getChangeRoom" name="form-getChangeRoom">
                <div class="modal modal-small" id="modal-changeRoom">
                    <div class="content-modal" style="height:216px;">
                        <input type="hidden" id="changeRoom-id">
                        <div class="header-modal title">
                            <p id="changeRoom-name"></p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-changeRoom');
                            document.getElementById('datepickerFrom').value = '';
                                   document.getElementById('datepickeTo').value = '';"></i>
                        </div>
                        <div class="body-modal">
                            <div class="group-control">
                                <div class="name">Từ ngày(*):</div>
                                <div class="control">
                                    <input type="text" id="datepickerFrom" name="timeFrom">
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Đến ngày:</div>
                                <div class="control">
                                    <input type="text" id="datepickeTo" name="timeTo">
                                </div>
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(0, 'modal-changeRoom'); document.getElementById('datepickerFrom').value = '';
                                   document.getElementById('datepickeTo').value = '';"
                                   value="Thoát"/>
                            <input type="button" class="btn btn-orange"
                                   onclick="getChangeRoom();"
                                   value="Tìm phòng trống"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
            <form id="changeRoom-form" name="changeRoom-form" action="/staff/changeRoomManually">
                <div class="modal modal-small" id="modal-room">
                    <input type="hidden" id="result-changeRoom-id" name="classroomId">
                    <input type="hidden" id="result-timeFrom" name="timeFrom">
                    <input type="hidden" id="result-timeTo" name="timeTo">
                    <div class="content-modal" style="height: 216px;">
                        <div class="header-modal title">
                            <p id="getchangeRoom-name">Phòng trống</p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-room');"></i>
                        </div>
                        <div class="body-modal">
                            <div id="changeRoomField">
                            </div>
                        </div>
                        <div class="footer-modal" style="border-top:0px">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(2,'modal-room', 'modal-changeRoom');"
                                   value="Quay lại"/>
                            <input type="button" class="btn btn-orange"
                                   onclick="changeRoomDate()"
                                   value="Đổi"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>

        </body>
        <script src="/resource/js/script.js"></script>
        <script src="/resource/js/staff_manageclassroom.js"></script>
        <script src="/resource/js/staff_manageroomtype.js"></script>
        <script src="/resource/js/roomtype-2.js"></script>
        <script src="/resource/js/jquery.simplePagination.js"></script>

        <script>
            window.onload = createDetailMap;
            function getRoomTypeId(roomtypeId) {
                document.getElementById('removeRoomtypeID').value = roomtypeId;
            }
            function getClassroomName(removeClassroomName) {
                document.getElementById('x').value = removeClassroomName;
            }

            function clearthietbi() {
                $('#selectBox option:first-child').attr("selected", "selected");
                var selectedValue = $('#selectBox option:first-child').data("value");
                document.getElementById('loaiphong').innerHTML = selectedValue.name;
                document.getElementById('roomtype').value = selectedValue.id;
                showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
                        , selectedValue.euipment);
                $(".thietBi").remove();
                document.getElementById("loaiphong").innerHTML = "";
                $('#vrow').selectedIndex = 2;
                createDetailMap();
                $(".check-box").prop("checked", false);
                var classroommap = document.getElementsByClassName('classroom-map');
                for (var i = 0; i < classroommap.length; i++) {
                    while (classroommap[i].hasChildNodes()) {
                        classroommap[i].removeChild(classroommap[i].lastChild);
                    }
                }
                document.getElementsByClassName('roomtypename')[1].innerHTML = "Tạo Loại Phòng";
                document.getElementById('RoomtypeId').innerHTML = "";
                document.getElementById('roomNameId').value = "";
                document.getElementById('classroomName').innerHTML = "Tạo Phòng";
                document.getElementById('roomNameId').value = "";
                document.getElementById('equip').value = "";
                document.getElementById('name').value = "";

            }

            document.getElementById("${tab}").className = "body-tab active";
            var tab = '${tab}';
            if (tab === 'tab1') {
                document.getElementById("head-tab1").className = "active";
                document.getElementById("head-tab2").className = "";
                document.getElementById("createClassroomBtn").style.display = "block";
            }
            else {
                document.getElementById("head-tab2").className = "active";
                document.getElementById("head-tab1").className = "";
                document.getElementById("createRoomTypeBtn").style.display = "block";
            }

            function changeManage(tab, thisE) {
                changeTabInTitle(tab, thisE);

                if(tab == 'tab1') {
                    document.getElementById("createClassroomBtn").style.display = "block";
                    document.getElementById("createRoomTypeBtn").style.display = "none";
                } else {
                    document.getElementById("createClassroomBtn").style.display = "none";
                    document.getElementById("createRoomTypeBtn").style.display = "block";
                }

                window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", "/staff/classroom?ACTIVETAB=" + tab + "&MESSAGE=0");
            }

            function validateCreateClassroomForm() {
                var roomName = document.forms["CreateClassroomForm"]["RoomName"].value;
                var roomType = document.forms["CreateClassroomForm"]["RoomType"].value;
                var action = document.forms["CreateClassroomForm"]["Action"].value;
                var classroomId = document.forms["CreateClassroomForm"]["classroomId"].value;
                $.ajax({
                    type: "get",
                    url: "/ajax/checkCreateClassroom",
                    cache: false,
                    data: 'roomName=' + roomName + '&roomType=' + roomType + '&action=' + action + '&classroomId=' + classroomId,
                    success: function (data) {
                        if (data.status == true) {
                            if (data.alert != '') {
                                document.getElementById("ClassroomAction").value = 'update';
                                showModal(0, 'modal-1');
                                conformData(3, {
                                    message: data.alert,
                                    btnName: 'Cập nhật',
                                    choose: 2,
                                    object: {id: 'createClassroomForm'}
                                });
                            } else {
                                showModal(0, 'modal-1');
                                document.getElementById('createClassroomForm').submit();
                                $(".loading-page").addClass("active");
                                $(".page").removeClass("active");
                            }

                        } else {
                            conformData(1, {message: data.alert});
                        }
                    },
                    error: function () {
                        conformData(1, {message: 'Xin Hãy Nhập Đầy Đủ Thông Tin!'});
                    }
                })
            }

            //display control in roomtype
            function checkQuantity(count) {
                var idEquip = "thiet-bi" + count;
                var checkBox = document.getElementById(idEquip);
                var control = "control-" + count;
                var idQuantity = "quantity" + count;
                var idPriority = "priority" + count;
                if (checkBox.checked) {
                    document.getElementById(control).style.display = 'inline-block';
                } else {
                    document.getElementById(control).style.display = 'none';
                    if (document.getElementById(idQuantity) != null) {
                        document.getElementById(idQuantity).value = '';
                    }
                    document.getElementById(idPriority).selectedIndex = 0;
                }
            }
            function checkNumber() {
                var quantity = document.getElementsByClassName("quantity");

                for (var i = 0; i < quantity.length; i++) {
                    var number = quantity[i].value;
                    var y = i + 1;
                    var idEquip = "thiet-bi" + y;
                    var checkBox = document.getElementById(idEquip);
                    if (checkBox.checked) {
                        if (isNaN(number)) {
                            conformData(1, {message: 'Số lượng không hợp lệ!'});
                            return false;
                        }
                        if (number <= 0) {
                            conformData(1, {message: 'Số lượng không hợp lệ!'})
                            return false;
                        }
                        if (number > 200) {
                            conformData(1, {message: 'Số phút không hợp lệ!'})
                            return false;
                        }
                    }

                }
                return true;
            }
            function validateCreateRoomType() {
                var roomtypeName = document.forms["CreateRoomType"]["RoomtypeName"].value;
                var action = document.forms["CreateRoomType"]["Action"].value;
                var roomtypeId = document.forms["CreateRoomType"]["RoomtypeId"].value;
                if (checkNumber()) {
                    $.ajax({
                        type: "get",
                        url: "/ajax/checkCreateRoomType",
                        cache: false,
                        data: 'roomtypeName=' + roomtypeName + '&action=' + action + '&roomtypeId=' + roomtypeId,
                        success: function (data) {
                            if (data.status == true) {
                                if (data.alert != '') {
                                    document.getElementById('RoomTypeAction').value = 'update';
                                    showModal(0, 'modal-4');
                                    conformData(3, {
                                        message: data.alert,
                                        btnName: 'Cập nhật',
                                        choose: 2,
                                        object: {id: 'createRoomType'}
                                    });
                                } else {
                                    showModal(0, 'modal-4');
                                    document.getElementById('createRoomType').submit();
                                    $(".loading-page").addClass("active");
                                    $(".page").removeClass("active");
                                }
                            } else {
                                conformData(1, {message: data.alert});

                            }
                        },
                        error: function () {
                            conformData(1, {message: 'Xin Hãy Nhập Đầy Đủ Thông Tin!'});
                        }
                    })
                }
            }

            function checkDeleteRoomType(roomTypeId, roomTypeName) {
                $.ajax({
                    type: "get",
                    url: "/ajax/checkDeleteRoomType",
                    cache: false,
                    data: 'roomTypeId=' + roomTypeId,
                    success: function (data) {
                        if (data.status == true) {
                            conformData(2, {
                                message: 'Bạn có muốn xóa loại phòng' + roomTypeName + '!',
                                btnName: 'Xóa',
                                link: '/staff/removeRoomType?roomTypeId=' + roomTypeId
                            })
                        } else {
                            conformData(1, {message: data.alert});
                        }
                    },
                    error: function () {
                        conformData(1, {message: 'Xin Hãy Nhập Đầy Đủ Thông Tin!'});
                    }
                })
            }

            function clearRoomType() {
                var control = document.getElementsByClassName('control-roomtype');
                for (i = 0; i < control.length; i++) {
                    control[i].style.display = 'none';
                }
                var checkbox = document.getElementsByClassName('check-box');
                for (i = 0; i < checkbox.length; i++) {
                    checkbox[i].checked = false;
                }
                var quantity = document.getElementsByClassName('quantity');
                for (i = 0; i < quantity.length; i++) {
                    quantity[i].value = '';
                }
                var priority = document.getElementsByClassName('priority');
                for (i = 0; i < priority.length; i++) {
                    priority[i].selectedIndex = 0;
                }
                document.getElementById('roomTypeName').value = '';
                document.getElementById('vrow').selectedIndex = 1;
                createDetailMap();
                document.getElementsByClassName('roomtypename')[1].innerHTML = "Tạo Loại Phòng";
                document.getElementById('title-roomtype-map').innerHTML = "Tạo Loại Phòng";
                document.getElementById('button-create-roomtype').value = "Tạo mẫu";
                document.getElementById('equip').value = "";
            }
            //phan trang
//            jQuery(function ($) {
//                var items = $("#removeClassroom > div");
//
//                var numItems = items.length;
//                var perPage = 9;
//
//                // only show the first 2 (or "first per_page") items initially
//                items.slice(perPage).hide();
//                // now setup pagination
//                $("#pagination").pagination({
//                    items: numItems,
//                    itemsOnPage: perPage,
//                    cssStyle: "compact-theme",
//                    onPageClick: function (pageNumber) { // this is where the magic happens
//                        // someone changed page, lets hide/show trs appropriately
//                        var showFrom = perPage * (pageNumber - 1);
//                        var showTo = showFrom + perPage;
//
//                        items.hide() // first hide everything, then show for the new page
//                                .slice(showFrom, showTo).show();
//                    }
//                });
//            });

            //phan trang
            jQuery(function ($) {
                var items = $("#removeRoomtype > div");

                var numItems = items.length;
                var perPage = 9;

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

            function clearRoomName() {
                if (document.getElementById('ClassroomAction').value == 'create') {
                    document.getElementById("roomNameId").value = "";
                    document.getElementById("loaiphong").innerHTML = "";
                    document.getElementById("classroomName").innerHTML = "Tạo Phòng";
                    $('#selectBox option:first-child').attr("selected", "selected");
                    var selectedValue = $('#selectBox option:first-child').data("value");
                    showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
                            , selectedValue.equipment);
                }
            }

            function showRoomtype() {
                var selectedValue = $('#selectBox').find(":selected").data("value");
                document.getElementById("loaiphong").innerHTML = selectedValue.name;

            }
            function chooseRoomType() {
                var action = document.getElementById('ClassroomAction').value;
                if (action == 'create') {
                    var selectedValue = $('#selectBox option:first-child').data("value");
                    document.getElementById('roomtype').value = selectedValue.id;
                }
            }

            $(function () {
                $("#datepickerFrom").datepicker({dateFormat: "dd-mm-yy"});
                $("#datepickeTo").datepicker({dateFormat: "dd-mm-yy"});
            });

            function checkDate(){
                re = /^\d{1,2}\-\d{1,2}\-\d{4}$/;
                var timeFrom = document.forms['form-getChangeRoom']['timeFrom'].value;
                var timeTo = document.forms['form-getChangeRoom']['timeTo'].value;
                if(timeFrom == '' || !timeFrom.match(re)) {
                    conformData(1, {message: 'Sai kiểu ngày tháng dd-mm-yyyy!'});
                    return false;
                }
                if(timeFrom == ''){
                    if(timeTo == '' || !timeTo.match(re)) {
                        conformData(1, {message: 'Sai kiểu ngày tháng dd-mm-yyyy!'});
                        return false;
                    }
                }

                var d1 = Date.parse(timeFrom);
                var d2 = Date.parse(timeTo);
                if(d1>d2){
                    conformData(1, {message: 'Ngày bắt đầu không được nhỏ hơn ngày kết thúc!'});
                    return false;
                }
                return true;
            }
            function getChangeRoom() {
                var classroomId = document.forms['form-getChangeRoom']['changeRoom-id'].value;
                var timeFrom = document.forms['form-getChangeRoom']['timeFrom'].value;
                var timeTo = document.forms['form-getChangeRoom']['timeTo'].value;
                if(checkDate()){
                    showModal(0,'modal-changeRoom');
                    $(".loading-page").addClass("active");
                    $.ajax({
                        type: "get",
                        url: "/ajax/getChangeRoom",
                        cache: false,
                        data: 'classroomId=' + classroomId + '&timeFrom=' + timeFrom + '&timeTo=' + timeTo,
                        success: function (data) {
                            $(".loading-page").removeClass("active");
                            $('#result-changeRoom-id').val(classroomId);
                            $('#result-timeFrom').val(timeFrom);
                            $('#result-timeTo').val(timeTo);
                            $('#changeRoomField').html(data);
                            showModal(1, 'modal-room');
                        },
                        error: function () {
                            conformData(1, {message: 'Sai kiểu ngày tháng dd-mm-yyyy!'});
                            $(".loading-page").removeClass("active");
                            showModal(1,'modal-changeRoom');
                        }
                    })
                }

            }

            function changeRoomDate(){
                showModal(0, 'modal-room');
                $(".loading-page").addClass("active");
                document.getElementById('changeRoom-form').submit();
            }

            $(".quantity").keydown(function (event) {
                if( event.ctrlKey || event.altKey
                        || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false)
                        || (95<event.keyCode && event.keyCode<106)
                        || (event.keyCode==8) || (event.keyCode==9)
                        || (event.keyCode>34 && event.keyCode<40)
                        || (event.keyCode==46) ) {
                    return;
                }
                else {
                    event.preventDefault();
                }
            });

            $("#roomNameId").keydown(function (event) {
                if( event.ctrlKey || event.altKey
                        || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false)
                        || (95<event.keyCode && event.keyCode<106)
                        || (event.keyCode==8) || (event.keyCode==9)
                        || (event.keyCode>34 && event.keyCode<40)
                        || (event.keyCode==46) ) {
                    return;
                }
                else {
                    event.preventDefault();
                }
            });

            document.getElementById("${tab1}").className += " active";
            document.getElementById("${tab1}").setAttribute("data-main", "1");
            connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});

        </script>
        </html>
    </c:otherwise>
</c:choose>