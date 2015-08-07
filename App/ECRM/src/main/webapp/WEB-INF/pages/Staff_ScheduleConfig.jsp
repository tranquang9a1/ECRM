<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.USER}"/>

<html>
<head>
    <title>ECRM - Equipment Classroom Management</title>
    <c:set var="list" value="${requestScope.LIST}"/>
    <c:set var="counter" value="${requestScope.SIZE}"/>
    <c:set var="tab" value="${requestScope.TABCONTROL}"/>
    <c:set var="duration" value="${requestScope.DURATION}"/>

    <script type="text/javascript" src="/resource/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resource/js/jquery.ui.timepicker.js"></script>
    <script type="text/javascript" src="/resource/js/jquery.ui.position.min.js"></script>
    <script type="text/javascript" src="/resource/js/newTemplate.js"></script>
    <script type="text/javascript" src="/resource/js/script.js"></script>
    <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
    <link type="text/css" rel="stylesheet" href="../../resource/css/jquery.ui.timepicker.css">
    <link type="text/css" rel="stylesheet" href="../../resource/css/jquery-ui-1.10.0.custom.min.css">
    <link type="text/css" rel="stylesheet" href="../../resource/css/jquery-ui-1.10.0.custom.min.css">


    <style type="text/css">
        .text-label {
            font-size: medium;
            font-weight: 600;
        }

        .ui-text {
            margin-top: 8px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('.timepicker').timepicker({
                hours: {starts: 7, ends: 20},
                minutes: {interval: 15},
                rows: 3,
                showPeriodLabels: true,
                minuteText: 'Min'
            });
        });
        $(document).ready(function () {

            var counter = ${counter};

            $("#addButton").click(function () {
                var oldTime = "";
                if (counter > 1) {
                    var i = counter - 1;
                    var id = "time-to" + i;
                    oldTime = document.getElementById(id).value;
                }
                var newTextBoxDiv = $(document.createElement('div'))
                        .attr("id", 'TextBoxDiv' + counter);
                newTextBoxDiv.attr("class", "ui-text");

                newTextBoxDiv.after().html('<label class="text-label">Tiết ' + counter + ' : </label>' +
                '<input type="text" class="timepicker" value="' + oldTime + '" id="time-from' + counter + '" onchange="changeText(' + counter + ')"/> ~ <input class="timepicker" type="text" value="" disabled id="time-to' + counter + '"/>');

                newTextBoxDiv.appendTo("#sub-text");
                if (counter > 1) {
                    changeText(counter);
                }
                counter++;
                $('.timepicker').timepicker({
                    hours: {starts: 7, ends: 20},
                    minutes: {interval: 15},
                    rows: 3,
                    showPeriodLabels: true,
                    minuteText: 'Min'
                });

            });

            $("#removeButton").click(function () {
                if (counter == 1) {
                    alert("Không còn tiết nào để xóa!");
                    return false;
                }

                counter--;

                $("#TextBoxDiv" + counter).remove();

            });


            $('#cancel').click(function () {
                location.reload();
            });
        });


    </script>
</head>

<body>
<div class="layout-background">
    <div class="container">
        <div class="header">
            <div class="logo">HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC</div>
            <div class="account-control">
                <p>${user.tblUserInfoByUsername.fullName}</p>
                <img src="/resource/img/gears.png"/>
            </div>
        </div>
        <div class="content-body">
            <c:import url="/bao-cao/danh-muc"/>
            <div class="right-content">
                <div class="page active" id="scheduleconfig">
                    <div class="title">
                        <p>Cấu Hình Tiết Học</p>

                        <div class="clear"></div>
                    </div>
                    <div class="body-content">
                        <div id="edit" style="display: none"><input type='button' class="btn btn-normal"
                                                                    value='Thêm Tiết' id='addButton'>
                            <input type='button' class="btn btn-normal" value='Xóa Tiết' id='removeButton'></div>

                        <input type='button' class="btn btn-primary" value='Cập Nhật' id='update'
                               onclick="showModal(1, 'Upload');">

                        <form action="/staff/updateScheduleConfig" method="get" name="updateScheduleConfig"
                              id="updateScheduleConfig">
                            <input type="hidden" name="config" id="config" value="">
                            <input type="hidden" name="size" id="size" value="">

                            <div id='TextBoxesGroup'
                                 style="overflow-y: auto; height: 374px; width: 550px; margin-top: 8px">
                                <div style="margin-left: 20%"><label class="text-label">Số phút mỗi tiết</label>

                                    <input type="text" disabled id="minutes" VALUE="${duration}" style="width: 50px">
                                </div>
                                <div style="width: 500px; margin: 0 auto; margin-top: 8px" id="sub-text">
                                    <c:forEach var="l" items="${list}" varStatus="count">
                                        <div id="TextBoxDiv${count.count}" class="ui-text">
                                            <label class="text-label">Tiết ${count.count} : </label><input type='text'
                                                                                                           class='timepicker'
                                                                                                           value="${l.timeFrom}"
                                                                                                           id="time-from${count.count}"
                                                                                                           onchange="changeText('${count.count}');"/>
                                            ~ <input class="timepicker" type='text' value="${l.timeTo}" disabled
                                                     id="time-to${count.count}"/>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div style="display: none" id="action">
                                <input type='button' class="btn btn-primary" value='Hủy' id='cancel'>
                                <input type='button' class="btn btn-primary" value='Lưu' id='save'
                                       onclick="submitForm();">
                            </div>
                        </form>
                    </div>
                </div>
                <c:import url="/bao-cao/thong-bao?little=false&quay-lai=scheduleconfig"/>
                <div class="loading-page">
                    <img src="/resource/img/500.GIF">

                    <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="content-all-modal">
    <div class="modal modal-small" id="Upload">
        <div class="content-modal" style="top: 30%">
            <div class="header-modal title">
                <p>Cấu hình tiết học</p>
            </div>
            <div class="body-modal" style="padding: 30px 10px">
                <div class="group-control">
                    <div class="name">Số phút mỗi tiết </div>
                    <div class="value"><input type="text" id="minute" maxlength="3"></div>
                </div>
            </div>
            <div class="footer-modal">
                <input type="button" class="btn btn-normal"
                       onclick="showModal(0, 'Upload'); document.getElementById('minute').value='';"
                       value="Thoát"/>
                <input type="button" class="btn btn-primary"
                       value="OK" onclick="showModal(0, 'Upload');getMinute();"/>
            </div>
        </div>
        <div class="black-background"></div>
    </div>
</div>
</body>
</html>
<script>
    var y = document.getElementsByClassName("timepicker");
    for (i = 0; i < y.length; i++) {
        y[i].disabled = true;
    }

    function validateHhMm(inputField) {
        var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(inputField.value);

        if (isValid) {
            inputField.style.backgroundColor = '#bfa';
        } else {
            inputField.style.backgroundColor = '#fba';
        }

        return isValid;
    }
    function validateScheduleConfig() {
        var bool = true;
        var timepicker = document.getElementsByClassName("timepicker");
        for (i = 0; i < timepicker.length; i++) {
            validateHhMm(timepicker[i]);
            if (validateHhMm(timepicker[i]) == false) {
                bool = false;
            }
        }
        return bool;
    }

    function submitForm() {
        if (validateScheduleConfig()) {
            var timepicker = document.getElementsByClassName("timepicker");
            var time = "";
            for (i = 0; i < timepicker.length; i++) {
                time += timepicker[i].value + "-";
            }
            document.getElementById("config").value = time;
            document.getElementById("size").value = timepicker.length / 2;
            document.getElementById('updateScheduleConfig').submit();
        } else {
            alert("Kiểu giờ phải là hh:MM:ss");
        }
    }

    function changeText(count) {
        var id = "time-from" + count;
        var timeFrom = document.getElementById(id).value;
        var date = new Date();
        var a = timeFrom.split(":");
        date.setHours(a[0], a[1], 0);
        var duration = document.getElementById("minutes").value;
        date.setTime(date.getTime() + duration * 60000);
        var time = date.getHours();
        var minute = date.getMinutes() + "";
        if (minute.length == 1) {
            minute = "0" + minute;
        }
        var timeTo = "time-to" + count;
        var result = time + ":" + minute + ":00";
        var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(result);
        if (isValid) {
            document.getElementById(timeTo).value = time + ":" + minute + ":00";
        }
    }

    function getMinute() {
        var minute = parseInt(document.getElementById('minute').value);
        if (checkNumber(minute)) {
            var y = document.getElementsByClassName("timepicker");
            for (i = 0; i < y.length; i++) {
                if(i%2==0){
                    y[i].disabled = false;
                }
            }
            document.getElementById("edit").style.display = 'block';
            document.getElementById("action").style.display = 'block';
            document.getElementById("update").style.display = 'none';
            document.getElementById("minutes").value = minute;
        } else {
            showModal(1, "Upload");
        }
    }


    function checkNumber(number) {
        if (isNaN(number)) {
            conformData(1, {message: 'Số phút không hợp lệ!'});
            return false;
        }
        if (number <= 0) {
            conformData(1, {message: 'Số phút không hợp lệ!'})
            return false;
        }
        if (number > 200) {
            conformData(1, {message: 'Số phút không hợp lệ!'})
            return false;
        }

        return true;
    }
    document.getElementById("${tab}").className += " active";
    document.getElementById("${tab}").setAttribute("data-main", "1");

</script>
