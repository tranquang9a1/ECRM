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
<c:set var="schedules" value="${requestScope.SCHEDULES}"/>

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


    <form action="staff/searchSchedule">
        <div>Ngày bắt đầu: <input type="text" id="datefrom" name="datefrom"></div>
        <div>Ngày kết thúc: <input type="text" id="dateto" name="dateto"></div>
        <div>Phòng học:<select name="classroomId">
            <option value="0">---</option>
            <c:forEach items="${classrooms}" var="c">
                <option value="${c.id}">Phòng ${c.name}</option>
            </c:forEach>
        </select></div>
        <div>Giáo viên:<select name="username">
            <option value="0">---</option>
            <c:forEach items="${teachers}" var="t">
                <option value="${t.username}">${t.tblUserInfoByUsername.fullName}</option>
            </c:forEach>
        </select></div>
        <button type="submit">Search</button>
    </form>

    <div id="result" style="width: 1000px; height: 300px; overflow: scroll; margin-left: 20px;">
        <div style="width: 100%">
            <div class="table">
                <div class="header-table">
                    <div class="room-number">
                        <div>Ngày</div>
                    </div>
                    <c:forEach var="c" items="${schedules}">
                        <div class="room-number">
                            <fmt:formatDate value="${c.date}" type="date" dateStyle="short"/>
                        </div>
                    </c:forEach>

                    <p class="clear"></p>
                </div>
                <div class="body-table">
                    <div class="row">


                        <p class="clear"></p>
                    </div>
                </div>

            </div>

        </div>
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
</script>
</body>
</html>
