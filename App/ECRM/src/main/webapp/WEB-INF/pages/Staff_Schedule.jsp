<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 7/6/2015
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="user" value="${sessionScope.USER}"/>

<c:choose>
    <c:when test="${empty user or user.roleId != 2}">
        <jsp:forward page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
        <head>
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
            <c:set var="scheduleConfig" value="${requestScope.SCHEDULECONFIG}"/>
            <c:set var="tab" value="${requestScope.TABCONTROL}"/>
            <meta charset="UTF-8"/>
            <title>ECRM - Equipment Classroom Management</title>
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/jquery-ui.css"/>
            <link rel="stylesheet" href="/resource/css/component.css"/>

            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/jquery-1.11.3.min.js"></script>
            <script src="/resource/js/jquery-ui.js"></script>
            <script src="/resource/js/newTemplate.js"></script>
            <script src="/resource/js/Staff-schedule.js"></script>
            <style>
                .inline div {
                    margin-bottom: 5px;
                }

                .input-search {
                    width: 180px;
                    height: 35px;
                    border-radius: 5px;
                }
            </style>
            <script>
                $(function () {
                    var teachers = [];
                    <c:forEach var="t" items="${teachers}" varStatus="count">
                    teachers[${count.count-1}] = '${t}';
                    </c:forEach>
                    $("#tags").autocomplete({
                        source: teachers
                    });
                    $("#ui-username").autocomplete({
                       source : teachers
                    });

                    var classrooms = [];
                    <c:forEach var="c" items="${classrooms}" varStatus="count">
                    classrooms[${count.count-1}] = '${c}';
                    </c:forEach>
                    $("#all").autocomplete({
                        source: classrooms
                    });

                });
                function checkSMS() {
                    if (document.getElementById('chckSMS').checked) {
                        document.getElementById('sms').value = "1";
                    } else {
                        document.getElementById('sms').value = "0";
                    }
                }
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
        <div class="layout-background">
            <div class="container">
                <jsp:include page="/WEB-INF/pages/HeaderNew.jsp"/>
                <div class="content-body">
                    <c:import url="/bao-cao/danh-muc"/>
                    <div class="right-content">
                        <div class="page active" id="schedule">
                            <div class="title"><p>Xếp lịch</p>

                                <div><input type="button" class="btn btn-primary" onclick="showModal(1,'Upload');"
                                            value="Nhập File"/>
                                </div>
                                <div><input type="button" class="btn btn-normal" onclick="showModal(1,'Manual');createNumberOfSlot();"
                                            value="Tạo mới"/></div>
                                <div class="clear"></div>
                            </div>
                            <form action="/staff/searchSchedule" onsubmit="return validateForm()" name="searchForm">
                                <div style="background-color: #ffffff; width: 490px;height: 50px;">

                                    <div class="search" style="display: inline-flex;padding-top: 7px">
                                        <a style="margin-right: 3px;background-color: #AAAAAA; border-radius: 3px; cursor: pointer;"
                                           title="Advance Search" onclick="showAdvance();"><img
                                                src="/resource/img/ic_expand_more_48px-256.png"
                                                style="width:25px;height: 25px; padding-top: 6px"></a>
                                        <select onchange="changeSearch();" id="selectSearch">
                                            <option value="0">Giáo viên</option>
                                            <option value="1">Phòng học</option>
                                        </select>

                                        <div style="display: block" id="teacherBox">
                                            <input id="tags" name="tags" class="input-search"
                                                   onkeypress="return pressEnter(event)">
                                        </div>
                                        <div>
                                            <div style="padding: 0px 0px 0px 0px;">
                                                <button type="button" class="btn btn-primary"
                                                        style="margin-left: 15px" id="search"
                                                        onclick="searchSchedule();">
                                                    Tìm Kiếm
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty datefrom or not empty dateto}">
                                        <div style="background-color: #ffffff; width: 490px;display: block"
                                             class="date">
                                            <div>Ngày:
                                                <input type="text" id="datefrom" name="datefrom"
                                                       value="${datefrom}" onkeypress="return pressEnter(event)"
                                                       class="input-search"><label>
                                                    ~ </label><input
                                                        type="text"
                                                        id="dateto"
                                                        name="dateto" value="${dateto}"
                                                         class="input-search">
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="background-color: #ffffff; width: 490px;display: none"
                                             class="date">
                                            <div>Ngày:
                                                <input type="text" id="datefrom" name="datefrom"
                                                       value="${datefrom}" onkeypress="return pressEnter(event)"
                                                       class="input-search"><label>
                                                    ~ </label><input
                                                        type="text"
                                                        id="dateto"
                                                        name="dateto" value="${dateto}"
                                                         class="input-search">
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                            <div class='component' style='max-height: 347px; padding:0 0; overflow: auto; overflow-y: hidden; position: relative;'></div>
                        </div>
                        <c:import url="/bao-cao/thong-bao?little=false&quay-lai=schedule"/>
                        <div class="loading-page">
                            <img src="/resource/img/500.GIF">

                            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="content-modal">
            <div class="modal modal-small" id="Upload">
                <div class="content-modal" style="height: 198px;">
                    <div class="header-modal title">
                        <p>Nhập Lịch</p>
                        <i class="fa fa-times" onclick="showModal(0, 'Upload')"></i>
                    </div>
                    <div class="body-modal" style="padding-top: 10px">
                        <a href="/staff/download">Bấm vào để tải mẫu!</a>

                        <div class="group-control">
                            <div class="name">Chọn file excel:</div>

                            <div class="control">
                                <form action="/staff/import" method="post" enctype="multipart/form-data"
                                      id="uploadSchedule">
                                    <input type="file" style="margin: 6px 0 0;" id="fileUpload" name="scheduleFile"
                                           size="50" accept=".csv,
                                     application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>

                                    <p id="lblError" style="color: red;"></p>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="footer-modal">
                        <input type="button" class="btn btn-normal" onclick="showModal(0, 'Upload')"
                               value="Thoát"/>
                        <input type="button" class="btn btn-primary" onclick="submitImport();"
                               value="Nhập lịch"/>
                    </div>
                </div>
                <div class="black-background"></div>
            </div>
            <div class="modal modal-medium" id="Manual">
                <div class="content-modal" style="height: 535px;">
                    <div class="header-modal title">
                        <p>Nhập Lịch Bằng Tay</p>
                        <i class="fa fa-times" onclick="showModal(0, 'Manual'); clearFormManual();"></i>
                    </div>
                    <form action="/staff/importManually" id="importManually"
                          name="ImportManually" method="post">
                        <div class="body-modal">
                            <div class="group-control">
                                <div class="name">Tài khoản(*):</div>
                                    <input id="ui-username" name="username" style="width: 200px; height: 35px">
                            </div>

                            <div class="group-control">
                                <div class="name">Tiết bắt đầu(*):</div>
                                <div class="control">
                                    <select name="slot" id="slot" onchange="createNumberOfSlot()">
                                        <c:forEach items="${scheduleConfig}" var="sc">
                                            <option value="${sc.slot}">${sc.slot}
                                                - ${sc.timeFrom}</option>
                                        </c:forEach>
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
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
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
                    || (event.keyCode==46) )" name="numberOfStudent" id="numberOfStudent">
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
                                    <input type="checkbox" onclick="findAvailableRoom()"
                                           id="chckBox"><label for="chckBox">Tìm phòng trống</label>

                                        <div id="classroom">
                                            <input name="all" id="all" style="width: 200px; height: 35px">
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
                            <div class="group-control">
                                <input type="hidden" id="sms" name="sms" value="0">

                                <div class="name">Gửi tin nhắn:</div>
                                <div class="control"><input type="checkbox" id="chckSMS" onclick="checkSMS();"><label
                                        for="chckSMS">Gửi tin nhắn</label></div>
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(0, 'Manual'); clearFormManual();"
                                   value="Thoát"/>
                            <button type="button" class="btn btn-primary"
                                    onclick="validateImportForm()">
                                Tạo
                            </button>
                        </div>
                    </form>
                </div>
                <div class="black-background"></div>
            </div>
        </div>
        </body>

        <script src="/resource/js/script.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-throttle-debounce/1.1/jquery.ba-throttle-debounce.min.js"></script>
        <script src="/resource/js/jquery.stickyheader.js"></script>
        <script>
            function createNumberOfSlot() {
                var select = document.getElementById('numberOfSlots');
                for (i = select.options.length - 1; i >= 0; i--) {
                    select.remove(i);
                }
                var size = ${fn:length(scheduleConfig)};
                var current = document.getElementById('slot').selectedIndex;
                for (var i = 1; i <= (size - current); i++) {
                    var option = document.createElement("option");
                    option.value = i;
                    option.text = i;
                    select.appendChild(option);
                }
            }

            if ('${teacher}' != 0) {
                var select = document.getElementById("selectSearch").selectedIndex = 0;

                changeSearch();
            }
            if ('${classroom}' != 0) {
                var select = document.getElementById("selectSearch").selectedIndex = 1;
                changeSearch();
            }

            function submitImport() {
                if (ValidateExtension()) {
                    showModal(0, 'Upload')
                    document.getElementById('uploadSchedule').submit();
                    $(".loading-page").addClass("active");
                    $(".page").removeClass("active");
                }
            }

            function ValidateExtension() {
                var fileUpload = document.getElementById("fileUpload");
                var lblError = document.getElementById("lblError");
                var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(.xls|.xlsx)$");
                if (!regex.test(fileUpload.value.toLowerCase())) {
                    lblError.innerHTML = "Hãy chọn tập tin có đuôi là: <b>" + ".xls|.xlsx" + "</b>";
                    return false;
                }
                lblError.innerHTML = "";
                return true;
            }

            function validateImportForm() {
                var username = document.forms["ImportManually"]["username"].value;
                var numberOfStudent = document.forms["ImportManually"]["numberOfStudent"].value;
                var dateFrom = document.forms["ImportManually"]["dateF"].value;
                var dateTo = document.forms["ImportManually"]["dateT"].value;
                var all = document.forms["ImportManually"]["all"].value;
                var avai = document.forms["ImportManually"]["avai"].value;
                $.ajax({
                    type: "get",
                    url: "/ajax/checkClassroom",
                    cache: false,
                    data: 'all=' + all + '&numberOfStudent=' + numberOfStudent + '&dateFrom=' + dateFrom + '&username=' + username +
                    '&avai=' + avai + '&dateTo=' + dateTo,
                    success: function (data) {
                        if (data.status == true) {
                            document.getElementById('importManually').submit();
                        } else {
                            conformData(1, {message:data.alert});
                        }
                    },
                    error: function () {
                        conformData(1, {message:'Error while request..'});
                    }
                })

            }
            function checkClassroom(classroomId) {
                $.ajax({
                    type: "get",
                    url: "/ajax/checkClassroom",
                    cache: false,
                    data: 'classroomId=' + classroomId,
                    success: function (data) {
                        console.log(data);
                    },
                    error: function () {
                        conformData(1, {message:'Error while request..'});
                    }
                })
            }

            function changeSearch() {
                var select = document.getElementById("selectSearch");
                var sub = select.options[select.selectedIndex].value;
                document.getElementById('tags').value = '';
                if (sub == '0') {
                    var teachers = [];
                    <c:forEach var="t" items="${teachers}" varStatus="count">
                    teachers[${count.count-1}] = '${t}';
                    </c:forEach>
                    $("#tags").autocomplete({
                        source: teachers
                    });
                } else {
                    var classrooms = [];
                    <c:forEach var="c" items="${classrooms}" varStatus="count">
                    classrooms[${count.count-1}] = '${c}';
                    </c:forEach>
                    $("#tags").autocomplete({
                        source: classrooms
                    });
                }
            }
            function pressEnter(e) {
                if (e.keyCode === 13) {
                    searchSchedule();
                }else{
                    var tag = document.getElementById('tags').value;
                    if (tag.length === 0 && e.keyCode >= 48 && e.keyCode <= 57) {
                        document.getElementById("selectSearch").selectedIndex = 1;
                        var classrooms = [];
                        <c:forEach var="c" items="${classrooms}" varStatus="count">
                        classrooms[${count.count-1}] = '${c}';
                        </c:forEach>
                        $("#tags").autocomplete({
                            source: classrooms
                        });

                    } else if(e.keyCode < 48 || e.keyCode > 57) {
                        document.getElementById("selectSearch").selectedIndex = 0;
                        var teachers = [];
                        <c:forEach var="t" items="${teachers}" varStatus="count">
                        teachers[${count.count-1}] = '${t}';
                        </c:forEach>
                        $("#tags").autocomplete({
                            source: teachers
                        });
                    }
                }

            }
            function showAdvance() {
                $('.date').toggle();
            }

            function clearFormManual() {
                document.getElementById('ui-username').selectedIndex = 0;
                document.getElementById('slot').selectedIndex = 0;
                document.getElementById('numberOfSlots').selectedIndex = 0;
                document.getElementById('numberOfStudent').value = "";
                document.getElementById('datepickeTo').value = "";
                document.getElementById('datepickerFrom').value = "";
                document.getElementById('sms').value = "";
                document.getElementById('all').selectedIndex = 0;
                document.getElementById('avai').selectedIndex = 0;
                document.getElementById('chckBox').checked = false;
                document.getElementById('chckSMS').checked = false;

            }

            document.getElementById("${tab}").className += " active";
            document.getElementById("${tab}").setAttribute("data-main", "1");
            connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});
        </script>
        </html>
    </c:otherwise>
</c:choose>