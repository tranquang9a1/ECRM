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
                    <li onclick="changeTab('tab3', this)">Đã hủy</li>
                </ul>
            </div>
            <div class="content-tab">
                <div id="tab1" class="body-tab active">
                    <c:if test="${newReport.size() > 0}">
                        <c:forEach var="i" begin="0" end="${newReport.size() - 1}">
                        <div class="notify-active" onclick="showReportDetail(${newReport[i].roomId})">
                            <p class="room">Phòng ${newReport[i].roomName}</p>
                            <p class="description">${newReport[i].listEquipments}</p>
                            <p class="time">Người báo cáo(${newReport[i].reporters})</p>
                            <div class="clear"></div>
                        </div>
                    </c:forEach>
                    </c:if>
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
                                <option>Ngày sửa chửa</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="table">
                        <div class="header-table">
                            <div class="width-15">
                                <div>Phòng</div>
                            </div>
                            <div class="width-30">
                                <div>Thiết bị</div>
                            </div>
                            <div class="width-20">
                                <div>Ngày báo cáo</div>
                            </div>
                            <div class="width-20">
                                <div>Ngày sửa chửa</div>
                            </div>
                            <div class="width-15">
                                <div></div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <div class="row">
                                <div class="width-15">
                                    <div>Phòng 201</div>
                                </div>
                                <div class="width-30">
                                    <div>Máy lạnh</div>
                                </div>
                                <div class="width-20">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="width-20">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="width-15">
                                    <div class="group-button">
                                        <div title="Xem" onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-file"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="tab3" class="body-tab">
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
                                <option>Người báo cáo</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="table">
                        <div class="header-table">
                            <div class="width-15">
                                <div>Phòng</div>
                            </div>
                            <div class="width-30">
                                <div>Thiết bị</div>
                            </div>
                            <div class="width-20">
                                <div>Người báo cáo</div>
                            </div>
                            <div class="width-20">
                                <div>Ngày sửa chửa</div>
                            </div>
                            <div class="width-15">
                                <div></div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <div class="row">
                                <div class="width-15">
                                    <div>Phòng 201</div>
                                </div>
                                <div class="width-30">
                                    <div>Máy lạnh</div>
                                </div>
                                <div class="width-20">
                                    <div>Nguyễn Thái Bình</div>
                                </div>
                                <div class="width-20">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="width-15">
                                    <div class="group-button">
                                        <div title="Xem" onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-file"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="content-all-modal"></div>
<script>
    function doAction(choose, object) {
        closeConform();
        switch (choose) {
            case 1:
                showModal(0, 'modal-1');
                alert("Khắc phục thành công!");
                break;
            case 2:
                showModal(0, 'modal-4');
                alert("Khắc phục thành công!");
                break;
            case 3:
                showModal(2, 'modal-3', 'modal-1');
                alert("Đổi phòng thành công!");
                break;
        }
    }
    </
    body >
    < / html >
