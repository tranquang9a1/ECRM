<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/19/2015
  Time: 7:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="listConfig" value="${requestScope.LISTCONFIG}"/>
<html>
<head>
    <title>Hệ thống quản lý thiết bị phòng học</title>
    <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="/resource/css/general.css"/>
    <link rel="stylesheet" href="/resource/css/newTemplate.css"/>

    <script src="/resource/js/jquery-1.11.3.js"></script>
    <script src="/resource/js/script.js"></script>
    <script src="/resource/js/newTemplate.js"></script>
</head>
<body>
<div class="layout-background">
    <div class="container">
        <jsp:include page="/WEB-INF/pages/HeaderNew.jsp"/>
        <div class="content-body">
            <c:import url="/bao-cao/danh-muc"/>
            <div class="right-content">
                <div class="page active" id="system-configuration">
                    <div class="title">
                        <p>Cấu hình hệ thống</p>
                    </div>
                    <div class="left-col width-55">
                        <c:if test="${listConfig.size() > 5}">
                            <div class="tab">
                                <div class="tab-medium">
                                    <ul>
                                        <li class="active">Cấu hình thiết bị chung</li>
                                    </ul>
                                </div>
                                <div class="content-tab">
                                    <c:forEach begin="5" end="${listConfig.size() - 1}" var="i">
                                        <div class="group-control" style="position:relative;">
                                            <div class="name"
                                                 style="width: 320px">${listConfig.get(i).description}</div>
                                            <div class="value"
                                                 id="value-${listConfig.get(i).key}">${listConfig.get(i).value}</div>
                                            <div style="float: right;">
                                                <div class="btn" style="margin: 0; line-height: 19px; height: 19px;"
                                                     onclick="$('#content-${listConfig.get(i).key}').css('display','block')">
                                                    <i class="fa fa-pencil"></i></div>
                                            </div>
                                            <div class="update-div" id="content-${listConfig.get(i).key}"
                                                 style="position:absolute; width: 140px; left: 300px; height: 35px; display: none; z-index: 100; background-color: white">
                                                <input class="number-text" type="text" maxlength="2"
                                                       id="${listConfig.get(i).key}" style="width: 75px"
                                                       value="${listConfig.get(i).value}"/>

                                                <div class="btn btn-normal"
                                                     style="height: 19px; line-height: 19px; margin: 0"
                                                     onclick="updateConfig('${listConfig.get(i).key}')">Lưu
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="right-col width-40">
                        <div class="tab">
                            <div class="tab-medium">
                                <ul>
                                    <li class="active">Thiết bị mặt định</li>
                                </ul>
                            </div>
                            <div class="content-tab">
                                <c:if test="${listConfig.size() > 0}">
                                    <c:forEach begin="0" end="${listConfig.size() - 1}" var="i">
                                        <c:if test="${i < 5 && !listConfig.get(i).key.equals('SERIOUSLYDAMGED') }">
                                            <div class="group-control">
                                                <div class="name">${listConfig.get(i).description}</div>
                                                <div class="value"
                                                     id="value-${listConfig.get(i).key}">${listConfig.get(i).value}</div>
                                                <div style="float: right;">
                                                    <div class="btn" style="margin: 0; line-height: 19px; height: 19px;"
                                                         onclick="$('#content-${listConfig.get(i).key}').css('display','block')">
                                                        <i class="fa fa-pencil"></i></div>
                                                </div>
                                                <div class="update-div" id="content-${listConfig.get(i).key}"
                                                     style="position:absolute; width: 140px; left: 180px; height: 35px; display: none; z-index: 100; background-color: white">
                                                    <input class="number-text" type="text" maxlength="2"
                                                           id="${listConfig.get(i).key}" style="width: 75px"
                                                           value="${listConfig.get(i).value}"/>

                                                    <div class="btn btn-normal"
                                                         style="height: 19px; line-height: 19px; margin: 0"
                                                         onclick="updateConfig('${listConfig.get(i).key}')">Lưu
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach begin="0" end="${listConfig.size() - 1}" var="i">
                                        <c:if test="${listConfig.get(i).key.equals('SERIOUSLYDAMGED') || listConfig.get(i).key.equals('')}" >
                                            <div class="group-control line">
                                                <div class="name"
                                                     style="text-transform: uppercase;">${listConfig.get(2).description}</div>
                                                <div class="value"
                                                     id="value-${listConfig.get(2).key}">${listConfig.get(2).value}</div>
                                                <div style="float: right;">
                                                    <div class="btn" style="margin: 0; line-height: 19px; height: 19px;"
                                                         onclick="$('#content-${listConfig.get(2).key}').css('display','block')">
                                                        <i class="fa fa-pencil"></i></div>
                                                </div>
                                                <div class="update-div" id="content-${listConfig.get(2).key}"
                                                     style="position:absolute; width: 140px; left: 180px; height: 35px; display: none; z-index: 100; background-color: white">
                                                    <input class="number-text" type="text" maxlength="2"
                                                           id="${listConfig.get(2).key}" style="width: 75px"
                                                           value="${listConfig.get(2).value}"/>

                                                    <div class="btn btn-normal"
                                                         style="height: 19px; line-height: 19px; margin: 0"
                                                         onclick="updateConfig('${listConfig.get(2).key}')">Lưu
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <c:import url="/bao-cao/thong-bao?little=false&quay-lai=system-configuration"/>
                <div class="loading-page">
                    <img src="/resource/img/500.GIF">

                    <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(".number-text").keydown(function (event) {
        if (event.ctrlKey || event.altKey
                || (47 < event.keyCode && event.keyCode < 58 && event.shiftKey == false)
                || (95 < event.keyCode && event.keyCode < 106)
                || (event.keyCode == 8) || (event.keyCode == 9)
                || (event.keyCode > 34 && event.keyCode < 40)
                || (event.keyCode == 46)) {
            return;
        }
        else {
            event.preventDefault();
        }
    });

    function updateConfig(key) {
        var valueK = $("#" + key).val();
        if (valueK == "") {
            conformData(1, {message: "Giá trị cấu hình là bắt buộc! Vui lòng nhập lại!"});
        } else {
            $(".loading-page").addClass("active");
            $.ajax({
                type: "POST",
                url: "/bao-cao/cau-hinh",
                data: {key: key, value: valueK},
                success: function (data) {
                    $("#value-" + key).html(valueK);
                    $("#content-" + key).css('display', 'none');
                    $(".loading-page").removeClass("active");
                },
                error: function () {
                    $(".loading-page").removeClass("active");
                    conformData(1, {message: "Lỗi kết nối! Vui lòng thử lại!"});
                }
            });
        }
    }

    connectToSocket('${sessionScope.USER.username}', ${sessionScope.USER.roleId});
</script>
</div>
</body>
</html>
