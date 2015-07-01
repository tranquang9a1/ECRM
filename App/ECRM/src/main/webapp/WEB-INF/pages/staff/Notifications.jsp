<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/8/2015
  Time: 6:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tmf" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>ECRM - Equipment Classroom Management</title>
    <link rel="stylesheet" href="../resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="../resource/css/layout.css"/>
    <link rel="stylesheet" href="../resource/css/general.css"/>
    <link rel="stylesheet" href="../resource/css/notify.css"/>
    <link rel="stylesheet" href="../resource/css/roomtype-2.css"/>

    <script src="../resource/js/jquery-1.11.3.js"></script>
    <script src="../resource/js/script.js"></script>
    <script src="../resource/js/staff-notify.js"></script>
</head>
<body>
<c:set var="newReport" value="${requestScope.NEWREPORT}"/>
<c:set var="finishReport" value="${requestScope.FINISHREPORT}"/>
<div class="contain-layout">
    <%@ include file="../Header.jsp" %>
</div>
<div class="container">
    <div class="title page-title">
        <p>Thông báo</p>
    </div>
    <div class="body-content">
        <div class="tab">
            <div class="tab-medium">
                <ul>
                    <li class="active" onclick="changeTab('tab1', this)">Chưa xử lý</li>
                    <li onclick="changeTab('tab2', this)">Đã xử lý</li>
                </ul>
            </div>
            <div class="content-tab">
                <div id="tab1" class="body-tab active">
                    <div class="table">
                        <div class="header-table">
                            <div class="width-10">
                                <div>Phòng</div>
                            </div>
                            <div class="width-35">
                                <div>Thiết bị</div>
                            </div>
                            <div class="width-30">
                                <div>Người báo cáo</div>
                            </div>
                            <div class="width-25">
                                <div></div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <c:if test="${newReport.size() > 0}">
                                <c:forEach var="i" begin="0" end="${newReport.size() - 1}">
                                    <div class="row">
                                        <div class="width-10">
                                            <div>${newReport[i].roomName}</div>
                                        </div>
                                        <div class="width-35">
                                            <div>${newReport[i].listEquipments}</div>
                                        </div>
                                        <div class="width-30">
                                            <div>${newReport[i].reporters}</div>
                                        </div>
                                        <div class="width-25">
                                            <div class="group-button">
                                                <div title="Xem" onclick="showReportDetail(${newReport[i].roomId})" class="btn btn-detail btn-text">Xem</div>
                                                <div title="Khắc phục tất cả" onclick="conform(2, ${newReport[i].roomId})" class="btn btn-orange btn-text">Khắc phục</div>
                                            </div>
                                        </div>
                                        <p class="clear"></p>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div id="tab2" class="body-tab">
                    <div class="search">
                        <div class="control">
                            <select>
                                <option>Phòng</option>
                                <option>101</option>
                                <option>102</option>
                                <option>103</option>
                                <option>205</option>
                                <option>213</option>
                            </select>
                        </div>
                        <div class="control">
                            <select>
                                <option>Thiết bị</option>
                                <option>Ghế</option>
                                <option>Bàn</option>
                                <option>Đèn</option>
                                <option>Máy chiếu</option>
                                <option>Máy lạnh</option>
                                <option>Máy quạt</option>
                            </select>
                        </div>
                        <div class="control">
                            <select>
                                <option>Ngày báo cáo</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="control">
                            <select>
                                <option>Ngày sửa chữa</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="table">
                        <div class="header-table">
                            <div class="width-10">
                                <div>Phòng</div>
                            </div>
                            <div class="width-35">
                                <div>Thiết bị</div>
                            </div>
                            <div class="width-20">
                                <div>Ngày báo cáo</div>
                            </div>
                            <div class="width-20">
                                <div>Người báo cáo</div>
                            </div>
                            <div class="width-15">
                                <div></div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <c:forEach items="${finishReport}" var="item">
                                <div class="row">
                                    <div class="width-10">
                                        <div>${item.room}</div>
                                    </div>
                                    <div class="width-35">
                                        <div>${item.listEquipment}</div>
                                    </div>
                                    <div class="width-20">
                                        <div>${item.createDate}</div>
                                    </div>
                                    <div class="width-20">
                                        <div>${item.reporter}</div>
                                    </div>
                                    <div class="width-15">
                                        <div class="group-button">
                                            <div title="Xem" style="line-height: 12px;" onclick="showFinishReport(${item.reportId})" class="btn btn-detail">Xem</div>
                                        </div>
                                    </div>
                                    <p class="clear"></p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="content-all-modal" data-room="0"></div>
<script>
    <c:if test="${requestScope.SHOWDETAIL != null}">
        showReportDetail(${requestScope.SHOWDETAIL});
    </c:if>

    function doAction(choose, object) {
        closeConform();
        switch (choose) {
            case 1:
                showModal(0, 'modal-4');
                sendResolve();
                break;
            case 2:
                window.location.href = "/thong-bao/sua-het?roomId="+object;
                break;
            case 3:
                changeRoom();
                break;
        }
    }
</script>
</body>
</html>
