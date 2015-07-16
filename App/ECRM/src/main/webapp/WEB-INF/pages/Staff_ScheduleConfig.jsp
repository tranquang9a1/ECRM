<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.USER}"/>

<html>
<head>
    <title></title>
    <c:set var="list" value="${requestScope.LIST}"/>
    <c:set var="counter" value="${requestScope.SIZE}"/>
    <c:set var="tab" value="${requestScope.TABCONTROL}"/>

    <script type="text/javascript" src="/resource/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resource/js/jquery.ui.timepicker.js"></script>
    <script type="text/javascript" src="/resource/js/jquery.ui.position.min.js"></script>
    <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
    <link type="text/css" rel="stylesheet" href="../../resource/css/jquery.ui.timepicker.css">
    <link type="text/css" rel="stylesheet" href="../../resource/css/jquery-ui-1.10.0.custom.min.css">
    <style type="text/css">
        div {
            padding: 8px;
        }
        label{
            font-size: medium;
            font-weight: 600;
        }
        .timepicker{
            border-radius: 5px;
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

                var newTextBoxDiv = $(document.createElement('div'))
                        .attr("id", 'TextBoxDiv' + counter);

                newTextBoxDiv.after().html('<label>Slot ' + counter + ' : </label>' +
                '<input type="text" class="timepicker" value="" /> ~ <input type="text"  class="timepicker" value=""/>');

                newTextBoxDiv.appendTo("#sub-text");


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
                    alert("Không còn slot nào để xóa!");
                    return false;
                }

                counter--;

                $("#TextBoxDiv" + counter).remove();

            });
            $('#update').click(function () {
                var y = document.getElementsByClassName("timepicker");
                for (i = 0; i < y.length; i++) {
                    y[i].disabled = false;
                }
                document.getElementById("edit").style.display = 'block';
                document.getElementById("action").style.display = 'block';
                document.getElementById("update").style.display = 'none';
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
            <div class="logo">Cấu Hình Tiết Học</div>
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
                        <p>Cấu Hình Tiết Học</p>

                        <div class="clear"></div>
                    </div>
                    <div class="body-content">
                        <div id="edit" style="display: none"><input type='button' class="btn btn-normal" value='Thêm Slot' id='addButton'>
                            <input type='button' class="btn btn-normal" value='Xóa Slot' id='removeButton'></div>

                        <input type='button' class="btn btn-primary" value='Cập Nhật' id='update'>

                        <form action="/staff/updateScheduleConfig" method="get" name="updateScheduleConfig"
                              id="updateScheduleConfig">
                            <input type="hidden" name="config" id="config" value="">
                            <input type="hidden" name="size" id="size" value="">

                            <div id='TextBoxesGroup' style="overflow-y: auto; height: 374px; width: 550px">
                                <div style="width: 500px; margin: 0 auto;" id="sub-text">
                                    <c:forEach var="l" items="${list}" varStatus="count">
                                        <div id="TextBoxDiv${count.count}">
                                            <label>Slot ${count.count} : </label><input type='text' class='timepicker' value="${l.timeFrom}"/>
                                            ~ <input type='text' class='timepicker' value="${l.timeTo}"/>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div style="display: none" id="action">
                                <input type='button' class="btn btn-primary" value='Hủy' id='cancel'>
                                <input type='button' class="btn btn-primary" value='Lưu' id='save' onclick="submitForm();">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
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
    document.getElementById("${tab}").className += " active";
    document.getElementById("${tab}").setAttribute("data-main", "1");

</script>
