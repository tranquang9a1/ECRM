<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 5/30/2015
  Time: 10:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
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
  </head>
  <body>
    <c:set var="roomtype" value="${requestScope.ROOMTYPE}" />
    <div class="contain-layout">
        <jsp:include page="../Header.jsp" />
    </div>

    <div class="container">
        <div class="title page-title right-button">
            <p>Lịch sử báo cáo</p>
            <input type="button" class="btn btn-orange" onclick="showModal(1, 'modal-1')" value="Tạo báo cáo" />
            <div class="clear"></div>
        </div>
        <div class="body-content">
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
                    <div class="width-10"><div>Phòng</div></div>
                    <div class="width-25"><div>Thiết bị</div></div>
                    <div class="width-20"><div>Ngày báo cáo</div></div>
                    <div class="width-25"><div>Phụ trách</div></div>
                    <div class="width-20"><div>Quản lý</div></div>
                    <p class="clear"></p>
                </div>
                <div class="body-table">
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="control">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail"><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                    <div class="row">
                        <div class="width-10"><div>201</div></div>
                        <div class="width-25"><div>Máy quạt</div></div>
                        <div class="width-20"><div>25/3/2015</div></div>
                        <div class="width-25"><div>Trần Quốc An</div></div>
                        <div class="width-20">
                            <div class="group-button">
                                <div onclick="showModal(1, 'modal-3')" class="btn btn-detail" ><i class="fa fa-pencil"></i></div>
                                <div href="javascript:void(0)" class="btn btn-remove"><i class="fa fa-times"></i></div>
                            </div>
                        </div>
                        <p class="clear"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal modal-medium" id="modal-2">
        <div class="content-modal">
            <div class="header-modal title">
                <p>Tạo báo cáo - Chi tiết báo cáo</p>
                <i class="fa fa-times" onclick="showModal(0, 'modal-2')"></i>
            </div>
            <div class="body-modal">
                <div class="group-control" style="margin: 15px 0 0">
                    <div class="name">Phòng</div>
                    <div class="value">203</div>
                </div>
                <div class="group-control">
                    <div class="name">Thiết bị</div>
                    <div class="value">Máy chiếu - ECRM0251</div>
                </div>
                <div class="group-control" style="margin: 20px 0 0">
                    <div class="name">Độ hư hại</div>
                    <div class="control">
                        <select>
                            <option>Nặng, không thể dùng</option>
                            <option>Trung bình, khó sử dụng được</option>
                            <option>Bình thường, vẫn hoạt động được</option>
                        </select>
                    </div>
                </div>
                <div class="group-control" style="margin: 20px 0 0">
                    <div class="name">Đánh giá của bạn</div>
                    <div class="control">
                        <select>
                            <option>Phải đổi phòng</option>
                            <option>Cẫn sửa ngay</option>
                            <option>Vẫn dạy được</option>
                        </select>
                    </div>
                </div>
                <div class="group-control" style="margin: 25px 0 0">
                    <div class="name">Mô tả hư hại</div>
                    <div class="control">
                        <textarea style="height: 150px; width: 260px;"></textarea>
                    </div>
                </div>


            </div>
            <div class="footer-modal">
                <input type="button" class="btn btn-orange" onclick="conform(1)" value="Gửi báo cáo" />
                <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-2', 'modal-1')" value="Quay lại" />
            </div>
        </div>
        <div class="black-background"></div>
    </div>
    <div class="modal modal-medium" id="modal-1">
        <div class="content-modal">
            <div class="header-modal title">
                <p>Tạo báo cáo - Sơ đồ phòng học</p>
                <i class="fa fa-times" onclick="showModal(0, 'modal-1')"></i>
            </div>
            <div class="body-modal">
                <div class="group-control">
                    <div class="name">Thiết bị</div>
                    <div class="value" id="list-equipment"></div>
                </div>
                <div id="classroom-map">

                </div>
            </div>
            <div class="footer-modal">
                <input type="button" class="btn btn-orange" onclick="showModal(2, 'modal-1', 'modal-2')" value="Tiếp theo" />
                <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-1')" value="Thoát" />
            </div>
        </div>
        <div class="black-background"></div>
    </div>
    <div class="modal modal-medium" id="modal-3">
        <div class="content-modal">
            <div class="header-modal title">
                <p>Lịch sử báo cáo</p>
                <i class="fa fa-times" onclick="showModal(0, 'modal-3')"></i>
            </div>
            <div class="body-modal">
                <div class="tab">
                    <div class="tab-medium">
                        <ul>
                            <li class="active" onclick="changeTab('tab1', this)">Báo cáo</li>
                            <li onclick="changeTab('tab2', this)">Khắc phục</li>
                        </ul>
                    </div>
                    <div class="content-tab">
                        <div id="tab1" class="body-tab active">
                            <div class="group-control">
                                <div class="name">Phòng</div>
                                <div class="value">212</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Ngày báo cáo</div>
                                <div class="value">9h36 23/1/2015</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Thiết bị</div>
                                <div class="value">Máy chiếu - ECRM0251</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Mô tả hư hại</div>
                                <div class="value">.................................................</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Sơ đồ phòng</div>
                            </div>
                            <div style="width: 100%; height: 190px; background-color: #f1f1f1"></div>
                        </div>
                        <div id="tab2" class="body-tab">
                            <div class="group-control">
                                <div class="name">Nhân viên</div>
                                <div class="value">Dương Chí Bình</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Thời gian hoàn thành</div>
                                <div class="value">13h55 28/3/2015</div>
                            </div>
                            <div class="group-control">
                                <div class="name">Hành động</div>
                                <div class="value">
                                    .....................................................<br/>
                                    .....................................................
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Lý do hư hại</div>
                                <div class="value">....................................................................</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer-modal">
                <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-3')" value="Thoát" />
            </div>
        </div>
        <div class="black-background"></div>
    </div>

    <script src="../resource/js/script.js"></script>
    <script src="../resource/js/roomtype-2.js"></script>
    <script>
        window.onload = function(){
            showMap('classroom-map', ${roomtype.id}, ${roomtype.verticalRows}, '${roomtype.horizontalRows}', '${roomtype.noSlotsEachHRows}', ${roomtype.airConditioning},
                    ${roomtype.fan}, ${roomtype.projector}, ${roomtype.speaker}, ${roomtype.television});
            setChooseEquipment();
        }

        function doAction(choose, object){
            closeConform();
            switch (choose){
                case 1:
                    showModal(0, 'modal-2');
                    break;
            }

        }
    </script>
  </body>
</html>