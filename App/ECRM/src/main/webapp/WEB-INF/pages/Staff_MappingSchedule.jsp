<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            $("#datepicker").datepicker({dateFormat: "yy-mm-dd"});
        });

        function findAvailableRoom() {
            if (document.getElementById('chckBox').checked) {
                document.getElementById('classroom').style.display = 'none';
                document.getElementById('avai_classroom').style.display = 'block';
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
            }

        }
    </script>
</head>
<body>
<c:set var="tab" value="${requestScope.ACTIVETAB}"/>
<c:set var="classrooms" value="${requestScope.CLASSROOMS}"/>

<div class="contain-layout">
    <jsp:include flush="true" page="Header.jsp"/>
</div>
<div class="container">
    <div class="title page-title right-button">
        <p>Xếp lịch</p>

        <div class="clear"></div>
    </div>
    <div class="body-content">
        <div class="tab">
            <div class="tab-medium">
                <ul>
                    <li class="active" onclick="changeTab('tab1', this)">Upload schedule</li>
                    <li onclick="changeTab('tab2', this)">Import manually</li>
                </ul>
            </div>
            <div class="content-tab">
                <div id="tab1" class="body-tab">
                    <a href="/staff/download">Click here to download template!</a>

                    <h3>File Upload:</h3>
                    Select a file to upload: <br/>

                    <form action="/staff/import" method="post"
                          enctype="multipart/form-data">
                        <input type="file" name="scheduleFile" size="50" accept=".csv,
                        application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
                        <br/>
                        <input type="submit" value="Upload File"/>
                    </form>

                </div>
                <div id="tab2" class="body-tab">
                    <h3>Nhập lịch bằng tay</h3>

                    <form action="/staff/importManually">
                        <input hidden name="classroomId" id="classroomId">
                        <p>Username: <input type="text" name="username"></p><br/>

                        <p>Tiết bắt đầu: <select name="slot" id="slot">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                        </select></p>
                        <br/>

                        <p>Số tiết: <select name="numberOfSlots" id="numberOfSlots">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                        </select></p>
                        <br/>

                        <p>Số học sinh: <input type="text" name="numberOfStudent" id="numberOfStudent"></p>
                        </p><br/>

                        <p>Ngày: <input type="text" id="datepicker" name="date"></p><br/>

                        <p>Phòng học:<input type="checkbox" onclick="findAvailableRoom()" id="chckBox">Tìm phòng
                            trống<br/>

                        <div id="classroom">
                            <select id="all">
                                <option value="0">---</option>
                                <c:forEach items="${classrooms}" var="c">
                                    <option value="${c.id}">Phòng ${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div id="avai_classroom" style="display: none">
                            <select id="avai">
                                <option value="0">---</option>
                            </select>
                        </div>
                        </p><br/>
                        <button type="submit">Tạo</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
</div>

<script src="../../resource/js/script.js"></script>

<script>
    function doAction(choose, object) {
        closeConform();
        switch (choose) {
            case 1:

        }
    }
    document.getElementById("${tab}").className = "body-tab active";
</script>
</body>
</html>
