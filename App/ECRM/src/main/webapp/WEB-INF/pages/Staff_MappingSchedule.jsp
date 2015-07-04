<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <link rel="stylesheet" href="../../resource/css/jquery-ui.css"/>

            <script src="../../resource/js/jquery-1.11.3.js"></script>
            <script src="../../resource/js/jquery-1.11.3.min.js"></script>
            <script src="../../resource/js/jquery-ui.js"></script>
            <script src="../../resource/js/Staff-schedule.js"></script>
            <style>
                .custom-combobox {
                    position: relative;
                    display: inline-block;
                }

                .custom-combobox-toggle {
                    position: absolute;
                    top: 0;
                    bottom: 0;
                    margin-left: -1px;
                    padding: 0;
                }

                .custom-combobox-input {
                    margin: 0;
                    padding: 5px 10px;
                    width: 170px;
                }
                tr.header
                {
                    cursor:pointer;
                }
                .header .sign:after{
                    content:">>";
                    display:inline-block;
                }
                .header.expand .sign:after{
                    content:"<<";
                }
            </style>
            <script>
                function findAvailableRoom() {
                    if (document.getElementById('chckBox').checked) {
                        document.getElementById('classroom').style.display = 'none';
                        document.getElementById('avai_classroom').style.display = 'block';
                        document.getElementById("all").selectedIndex = 0;
                        $.ajax({
                            type: "get",
                            url: "/ajax/findClassroom",
                            cache: false,
                            data: 'slot=' + $("#slot").val() + "&numberOfSlots=" + $("#numberOfSlots").val() +
                            "&numberOfStudent=" + $("#numberOfStudent").val() + "&date=" + $("#datepickerFrom").val(),
                            success: function (responseText) {
                                $('#avai').html(responseText);
                            },
                            error: function () {
                                alert('Error while request..');
                            }
                        })
                    } else {
                        document.getElementById('avai_classroom').style.display = 'none';
                        document.getElementById('classroom').style.display = 'block';
                        document.getElementById("avai").selectedIndex = 0;

                    }

                }
            </script>
        </head>
        <body>
        <c:set var="tab" value="${requestScope.ACTIVETAB}"/>
        <c:set var="classrooms" value="${requestScope.CLASSROOMS}"/>
        <c:set var="teachers" value="${requestScope.TEACHERS}"/>
        <c:set var="teachingDate" value="${requestScope.TEACHINGDATE}"/>
        <c:set var="classroomId" value="${requestScope.CLASSROOMID}"/>
        <c:set var="schedules" value="${requestScope.SCHEDULES}"/>
        <c:set var="datefrom" value="${requestScope.DATEFROM}"/>
        <c:set var="dateto" value="${requestScope.DATETO}"/>
        <c:set var="teacher" value="${requestScope.TEACHER}"/>
        <c:set var="classroom" value="${requestScope.CLASSROOM}"/>
        <c:set var="isEmpty" value="${requestScope.ISEMPTY}"/>

        <div class="contain-layout">
            <jsp:include flush="true" page="Header.jsp"/>
        </div>
        <div class="container">
            <div class="title page-title right-button">
                <p>Xếp lịch</p>

                <div><input type="button" class="btn btn-orange" onclick="showModal(1,'Upload');" value="Nhập File"/>
                </div>
                <div><input type="button" class="btn btn-orange" onclick="showModal(1,'Manual');"
                            value="Tạo mới"/></div>
                <div class="clear"></div>
            </div>


            <form action="/staff/searchSchedule" onsubmit="return validateForm()" name="searchForm">
                <div style=" margin-top: 20px;margin-left: 20px; background-color: #ffffff; width: 511px;">
                    <header style="  border-bottom: 1px solid #EBEEF3; padding: 10px; font-size: medium;
  font-weight: bold;">
                        TÌM KIẾM
                    </header>
                </div>
                <div style="margin-left: 20px; background-color: #ffffff; width: 511px;">
                    <table>
                        <tr>
                            <td><label>Ngày: </label></td>
                            <td><input type="text" id="datefrom" name="datefrom" value="${datefrom}"><label>
                                ~ </label><input
                                    type="text"
                                    id="dateto"
                                    name="dateto" value="${dateto}"></td>
                        </tr>
                        <tr>
                            <td><label>Phòng học: </label></td>
                            <td>
                                <div class="ui-widget">
                                    <select name="classroomId" id="combobox1">
                                        <option value="0"></option>
                                        <c:choose>
                                            <c:when test="${not empty classroom}">
                                                <c:forEach items="${classrooms}" var="c">
                                                    <c:choose>
                                                        <c:when test="${classroom == c.id}">
                                                            <option value="${c.id}" selected>${c.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${c.id}">${c.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${classrooms}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Giáo viên: </label></td>
                            <td>
                                <div class="ui-widget">
                                    <select name="username" style="width: 150px" id="combobox2">
                                        <option value="0"></option>
                                        <c:choose>
                                            <c:when test="${not empty teacher}">
                                                <c:forEach items="${teachers}" var="t">
                                                    <c:choose>
                                                        <c:when test="${teacher == t.username}">
                                                            <option value="${t.username}"
                                                                    selected>${t.tblUserInfoByUsername.fullName}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${teachers}" var="t">
                                                    <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="padding: 10px 0px 5px 0px;">
                                <button type="submit" class="btn btn-orange" style="float: right">Tìm Kiếm</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
            <c:if test="${not isEmpty}">
                <div style=" margin-top: 20px;margin-left: 20px; background-color: #ffffff; width: 1000px;">
                    <header style="  border-bottom: 1px solid #EBEEF3; padding: 10px;font-size: medium;
  font-weight: bold;">
                        LỊCH
                    </header>
                </div>
                <div id="result"
                     style="width: 1000px; height: 300px; overflow: scroll; margin-left: 20px; background-color: #ffffff">

                    <c:if test="${not empty schedules}">
                        <table border="1px">
                            <tr>

                                <th bgcolor="yellow">Phòng</th>
                                <th bgcolor="yellow">Ngày</th>

                                <c:forEach items="${teachingDate}" var="td">
                                    <th bgcolor="#32cd32">${td}</th>
                                </c:forEach>

                            </tr>
                            <c:forEach var="cs" items="${schedules}">
                                <tr class="header" style="clear: both  ">
                                    <td rowspan="${cs.rowspan}" >${cs.roomName}<span class="sign"></span></td>
                                </tr>

                                <c:forEach var="tis" items="${cs.timeSchedules}">
                                    <c:if test="${ not empty tis.teacherSchedules}">
                                        <tr>
                                            <td bgcolor="yellow">${tis.time}</td>

                                            <c:forEach items="${teachingDate}" var="td">
                                                <td>
                                                    <c:forEach var="tes" items="${tis.teacherSchedules}">
                                                        <c:if test="${tes.date == td.toString()}">
                                                            ${tes.teacher}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </c:forEach>
                                        </tr>

                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </c:if>
            <div class="modal modal-small" id="Upload">
                <div class="content-modal">
                    <div class="header-modal title">
                        <p>Nhập Lịch</p>
                        <i class="fa fa-times" onclick="showModal(0, 'Upload')"></i>
                    </div>
                    <div class="body-modal">
                        <a href="/staff/download">Bấm vào để tải mẫu!</a>

                        <p>Chọn file excel:</p>

                        <form action="/staff/import" method="post"
                              enctype="multipart/form-data" id="uploadSchedule">
                            <input type="file" name="scheduleFile" size="50" accept=".csv,
                        application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
                            <br/>
                        </form>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal" onclick="showModal(0, 'Upload')"
                               value="Thoát"/>
                        <input type="button" class="btn btn-orange" onclick="conform(1);"
                               value="Nhập lịch"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>

            <div class="modal modal-medium" id="Manual">
                <div class="content-modal" style="height: 530px">
                    <div class="header-modal title">
                        <p>Nhập Lịch Bằng Tay</p>
                        <i class="fa fa-times" onclick="showModal(0, 'Manual')"></i>
                    </div>
                    <form action="/staff/importManually" id="importManually" onsubmit="return validateImportForm()"
                          name="ImportManually">
                        <div class="body-modal">

                            <br/>

                            <div class="group-control">
                                <div class="name">Tài khoản(*):</div>
                                <div class="control">
                                    <input type="text" name="username">
                                </div>
                            </div>

                            <div class="group-control">
                                <div class="name">Tiết bắt đầu(*):</div>
                                <div class="control">
                                    <select name="slot" id="slot">
                                        <option value="1">1: 07:00:00</option>
                                        <option value="2">2: 08:45:00</option>
                                        <option value="3">3: 10:30:00</option>
                                        <option value="4">4: 12:30:00</option>
                                        <option value="5">5: 14:15:00</option>
                                        <option value="6">6: 16:00:00</option>
                                    </select>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Số tiết(*):</div>
                                <div class="control">
                                    <select name="numberOfSlots" id="numberOfSlots">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                        <option value="6">6</option>
                                    </select>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Số học sinh(*):</div>
                                <div class="control">
                                    <input type="text"
                                           onkeydown="return ( event.ctrlKey || event.altKey
                    || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false)
                    || (95<event.keyCode && event.keyCode<106)
                    || (event.keyCode==8) || (event.keyCode==9)
                    || (event.keyCode>34 && event.keyCode<40)
                    || (event.keyCode==46) )" name="numberOfStudent" id="numberOfStuden"t>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Từ ngày(*):</div>
                                <div class="control">
                                    <input type="text" id="datepickerFrom" name="dateF">
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Đến ngày:</div>
                                <div class="control">
                                    <input type="text" id="datepickeTo" name="dateT">
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Phòng học(*):</div>
                                <div class="control">
                                    <input type="checkbox" onclick="findAvailableRoom()" id="chckBox">Tìm phòng trống
                                    <div id="classroom">
                                        <div class="ui-widget">
                                            <select id="all" name="all">
                                                <option value="0"></option>
                                                <c:forEach items="${classrooms}" var="c">
                                                    <option value="${c.id}">${c.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div id="avai_classroom" style="display: none">
                                        <div class="ui-widget">
                                            <select id="avai" name="avai">
                                                <option value="0"></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal" onclick="showModal(0, 'Manual')"
                                   value="Thoát"/>
                            <button type="submit" class="btn btn-orange">Tạo</button>
                        </div>
                    </form>
                </div>
                <div class="black-background"></div>
            </div>


        </div>
        </div>

        <script src="../../resource/js/script.js"></script>

        <script>
            function doAction(choose, object) {
                closeConform();
                switch (choose) {
                    case 1:
                        document.getElementById('uploadSchedule').submit();
                        break;
                }
            }
            function validateForm() {
                var dateFrom = document.forms["searchForm"]["datefrom"].value;
                var dateTo = document.forms["searchForm"]["dateto"].value;
                if (dateFrom == null || dateFrom == "") {
                    alert("Phải nhập ngày bắt đầu!");
                    return false;
                }
                if (dateTo == null || dateTo == "") {
                    alert("Phải nhập ngày kết thúc!");
                    return false;
                }
            }
            function validateImportForm() {
                var username = document.forms["ImportManually"]["username"].value;
                var numberOfStudent = document.forms["ImportManually"]["numberOfStudent"].value;
                var dateFrom = document.forms["ImportManually"]["dateF"].value;
                var dateTo = document.forms["ImportManually"]["dateT"].value;
                var all = document.forms["ImportManually"]["all"].value;
                var avai = document.forms["ImportManually"]["avai"].value;
                if (username == null || username == "") {
                    alert("Phải nhập tài khoản!");
                    return false;
                }
                if (numberOfStudent == null || numberOfStudent == "") {
                    alert("Phải nhập số lượng học sinh!");
                    return false;
                }
                if (dateFrom == null || dateFrom == "") {
                    alert("Phải nhập ngày bắt đầu!");
                    return false;
                }
                if (avai == 0 && all == 0) {
                    alert("Phải chọn phòng học!");
                    return false;
                }else{
                    if(all!=0 && checkClassroom(all)==="1"){
                        alert("Phòng học hiện không khả dụng!");
                        return false;
                    }
                }
                if (dateTo != null && dateFrom != null) {
                    var date1 = new Date(dateFrom);
                    var date2 = new Date(dateTo);
                    if (date2 < date1) {
                        alert("Ngày kết thúc không được nhỏ hơn ngày bắt đầu!");
                        return false;
                    }
                }
            }
            function checkClassroom(classroomId) {
                return $.ajax({
                    type: "get",
                    url: "/ajax/checkClassroom",
                    cache: false,
                    data: 'classroomId=' + classroomId,
                    success: function (data) {
                        console.log(data);
                        alert("Phòng học hiện không khả dụng!");
                    },
                    error: function () {
                        alert('Error while request..');
                    }
                })
            }
            $('.header').click(function(){
                $(this).toggleClass('expand').nextUntil('tr.header').slideToggle(100);
            });
        </script>
        </body>
        </html>
    </c:otherwise>
</c:choose>