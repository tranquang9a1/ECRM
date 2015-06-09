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
                    <c:forEach var="i" begin="0" end="${newReport.size() - 1}">
                        <div class="notify-active" onclick="showReportDetail(${newReport[i].reportId})">
                            <p class="room">Phòng ${newReport[i].tblClassroomByClassRoomId.name}</p>
                            <p class="description">
                                <c:if test="${newReport[i].evaluate == 1}">
                                    Hư hại nghiêm trọng, cần đổi phòng
                                </c:if>
                                <c:if test="${newReport[i].evaluate == 2}">
                                    Hư hại trung bình, cần sửa gấp
                                </c:if>
                                <c:if test="${newReport[i].evaluate == 3}">
                                    Hư hại nhẹ, vẫn dùng được
                                </c:if>
                            </p>
                            <p class="time"><tmf:formatDate value="${newReport[i].createTime}" pattern="HH:mm dd/MM/yyyy"/></p>
                            <div class="clear"></div>
                        </div>
                    </c:forEach>
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
                                <option>Phụ trách</option>
                                <option>Lại Văn Sâm</option>
                                <option>Trần Lạc Hầu</option>
                                <option>Cung Thiên Toản</option>
                                <option>Dương Chí Bình</option>
                                <option>Trần Quốc An</option>
                            </select>
                        </div>
                        <div class="control">
                            <select>
                                <option>Ngày báo cáo</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="table">
                        <div class="header-table">
                            <div class="room">
                                <div>Phòng</div>
                            </div>
                            <div class="equipment">
                                <div>Thiết bị</div>
                            </div>
                            <div class="report-time">
                                <div>Ngày báo cáo</div>
                            </div>
                            <div class="staff">
                                <div>Phụ trách</div>
                            </div>
                            <div class="control">
                                <div>Quản lý</div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy lạnh</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>101</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>08/2/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Lạc Hồng Quân</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>315</div>
                                </div>
                                <div class="equipment">
                                    <div>Ghế</div>
                                </div>
                                <div class="report-time">
                                    <div>20/4/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Lạc Hồng Quân</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>1/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Bàn</div>
                                </div>
                                <div class="report-time">
                                    <div>15/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Lạc Hồng Quân</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>507</div>
                                </div>
                                <div class="equipment">
                                    <div>Đèn</div>
                                </div>
                                <div class="report-time">
                                    <div>28/1/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>07/4/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                        <div href="javascript:void(0)" class="btn btn-remove"><i
                                                class="fa fa-times"></i></div>
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
                                <option>Phụ trách</option>
                                <option>Lại Văn Sâm</option>
                                <option>Trần Lạc Hầu</option>
                                <option>Cung Thiên Toản</option>
                                <option>Dương Chí Bình</option>
                                <option>Trần Quốc An</option>
                            </select>
                        </div>
                        <div class="control">
                            <select>
                                <option>Ngày báo cáo</option>
                                <option>02/03/2015</option>
                            </select>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="table">
                        <div class="header-table">
                            <div class="room">
                                <div>Phòng</div>
                            </div>
                            <div class="equipment">
                                <div>Thiết bị</div>
                            </div>
                            <div class="report-time">
                                <div>Ngày báo cáo</div>
                            </div>
                            <div class="staff">
                                <div>Phụ trách</div>
                            </div>
                            <div class="control">
                                <div>Quản lý</div>
                            </div>
                            <p class="clear"></p>
                        </div>
                        <div class="body-table">
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
                                    </div>
                                </div>
                                <p class="clear"></p>
                            </div>
                            <div class="row">
                                <div class="room">
                                    <div>201</div>
                                </div>
                                <div class="equipment">
                                    <div>Máy quạt</div>
                                </div>
                                <div class="report-time">
                                    <div>25/3/2015</div>
                                </div>
                                <div class="staff">
                                    <div>Trần Quốc An</div>
                                </div>
                                <div class="control">
                                    <div class="group-button">
                                        <div onclick="showModal(1, 'modal-5')" class="btn btn-detail"><i
                                                class="fa fa-pencil"></i></div>
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
