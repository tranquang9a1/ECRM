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
    <script>
        $(function () {
            $("#datepickerFrom").datepicker({dateFormat: "yy-mm-dd"});
            $("#datepickeTo").datepicker({dateFormat: "yy-mm-dd"});
            $("#datefrom").datepicker({dateFormat: "yy-mm-dd"});
            $("#dateto").datepicker({dateFormat: "yy-mm-dd"});
        });

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
                    "&numberOfStudent=" + $("#numberOfStudent").val() + "&date=" + $("#datepicker").val(),
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

<div class="contain-layout">
    <jsp:include flush="true" page="Header.jsp"/>
</div>
<div class="container">
    <div class="title page-title right-button">
        <p>Xếp lịch</p>

        <div><input type="button" class="btn btn-orange" onclick="showModal(1,'Upload');" value="Import Schedule"/>
        </div>
        <div><input type="button" class="btn btn-orange" onclick="showModal(1,'Manual');"
                    value="Import Schedule Manually"/></div>
        <div class="clear"></div>
    </div>


    <form action="/staff/searchSchedule" onsubmit="return validateForm()" name="searchForm">
        <div style=" margin-top: 20px;margin-left: 20px; background-color: #ffffff; width: 511px;">
            <header style="  border-bottom: 1px solid #EBEEF3; padding: 10px;">
                TÌM KIẾM LỊCH
            </header>
        </div>
        <div style="margin-left: 20px; background-color: #ffffff; width: 511px;">
            <table>
                <tr>
                    <td><label>Ngày: </label></td>
                    <td><input type="text" id="datefrom" name="datefrom" value="${datefrom}"><label> ~ </label><input
                            type="text"
                            id="dateto"
                            name="dateto" value="${dateto}"></td>
                </tr>
                <tr>
                    <td><label>Phòng học: </label></td>
                    <td><select name="classroomId" style="width: 100px">
                        <option value="0">---</option>
                        <c:if test="${not empty classroom}">
                            <c:forEach items="${classrooms}" var="c">
                                <c:if test="${classroom == c.id}">
                                    <option value="${c.id}" selected>Phòng ${c.name}</option>
                                </c:if>
                                <c:if test="${classroom != c.id}">
                                    <option value="${c.id}">Phòng ${c.name}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty classroom}">
                            <c:forEach items="${classrooms}" var="c">
                                <option value="${c.id}">Phòng ${c.name}</option>
                            </c:forEach>
                        </c:if>
                    </select></td>
                </tr>
                <tr>
                    <td><label>Giáo viên: </label></td>
                    <td><select name="username" style="width: 100px">
                        <option value="0">---</option>
                        <c:forEach items="${teachers}" var="t">
                            <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
                        </c:forEach>
                        <c:if test="${empty teacher}">
                            <c:forEach items="${teachers}" var="t">
                                <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty teacher}">
                            <c:forEach items="${teachers}" var="t">
                                <c:if test="${teacher == t.username}">
                                    <option value="${t.username}" selected>${t.tblUserInfoByUsername.fullName}</option>
                                </c:if>
                                <c:if test="${teacher != t.username}">
                                    <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </select></td>
                </tr>
                <tr>
                    <td></td>
                    <td style="padding: 10px 0px 5px 0px;">
                        <button type="submit" class="btn btn-orange" style="float: right">Search</button>
                    </td>
                </tr>
            </table>
        </div>
    </form>

    <div style=" margin-top: 20px;margin-left: 20px; background-color: #ffffff; width: 1000px;">
        <header style="  border-bottom: 1px solid #EBEEF3; padding: 10px;">
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
                <c:forEach var="ci" items="${classroomId}">
                    <tr>
                        <td rowspan="6">${ci}</td>
                        <td bgcolor="yellow">7:00-8:30</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='07:00:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>

                    </tr>
                    <tr>
                        <td bgcolor="yellow">8:45-10:15</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='08:45:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td bgcolor="yellow">10:30-12:00</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='10:30:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td bgcolor="yellow">12:30-14:00</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='12:30:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td bgcolor="yellow">14:15-15:45</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='14:15:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td bgcolor="yellow">16:00-17:30</td>
                        <c:forEach items="${teachingDate}" var="td">
                            <td>
                                <c:forEach var="s" items="${schedules}">
                                    <c:choose>
                                        <c:when test="${s.timeFrom=='16:00:00' and s.tblClassroomByClassroomId.name==ci and s.date == td.toString()}">
                                            ${s.username}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <div class="modal modal-small" id="Upload">
        <div class="content-modal">
            <div class="header-modal title">
                <p>Upload Schedule</p>
                <i class="fa fa-times" onclick="showModal(0, 'Upload')"></i>
            </div>
            <div class="body-modal">
                <a href="/staff/download">Click here to download template!</a>

                <p>Select a file to upload:</p>

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
                       value="Upload"/>
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
            <div class="body-modal">
                <form action="/staff/importManually" id="importManually">
                    <br/>

                    <div class="group-control">
                        <div class="name">Username:</div>
                        <div class="control">
                            <input type="text" name="username">
                        </div>
                    </div>

                    <div class="group-control">
                        <div class="name">Tiết bắt đầu:</div>
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
                        <div class="name">Số tiết:</div>
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
                        <div class="name">Số học sinh:</div>
                        <div class="control">
                            <input type="text" name="numberOfStudent" id="numberOfStudent">
                        </div>
                    </div>
                    <div class="group-control">
                        <div class="name">Từ ngày:</div>
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
                        <div class="name">Phòng học:</div>
                        <div class="control">
                            <input type="checkbox" onclick="findAvailableRoom()" id="chckBox">Tìm phòng trống
                            <div id="classroom">
                                <select id="all" name="all">
                                    <option value="0">---</option>
                                    <c:forEach items="${classrooms}" var="c">
                                        <option value="${c.id}">Phòng ${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="avai_classroom" style="display: none">
                                <select id="avai" name="avai">
                                    <option value="0">---</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="footer-modal">
                <input type="button" class="btn btn-normal" onclick="showModal(0, 'Manual')"
                       value="Thoát"/>
                <input type="button" class="btn btn-orange" onclick="conform(2);"
                       value="Tạo"/>
            </div>
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
            case 2:
                document.getElementById('importManually').submit();
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
</script>
</body>
</html>
