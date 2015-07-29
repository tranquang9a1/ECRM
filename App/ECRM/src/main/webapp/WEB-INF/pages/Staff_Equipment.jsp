<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="user" value="${sessionScope.USER}"/>
<c:choose>
    <c:when test="${empty user}">
        <jsp:forward page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>ECRM - Equipment Classroom Management</title>
            <link rel="stylesheet" href="/resource/css/font-awesome.css"/>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <link rel="stylesheet" href="/resource/css/simplePagination.css"/>
            <link rel="stylesheet" href="/resource/css/management.css"/>

            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/jquery-1.11.3.min.js"></script>
            <script src="/resource/js/newTemplate.js"></script>

        </head>
        <style>
            .equip {
                width: 35px;
                height: 35px;
                background-size: 100% 100%;
            }
        </style>
        <c:set var="equipments" value="${requestScope.EQUIPMENTS}"/>
        <c:set var="categories" value="${requestScope.CATEGORIES}"/>
        <c:set var="tab" value="${requestScope.TABCONTROL}"/>
        <div class="layout-background" style="height: 0px">
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
                        <div class="page active" id="equipment">
                            <div class="title">
                                <p>Quản lý thiết bị</p>
                            </div>
                            <div class="tab">
                                <div class="tab-medium">
                                    <ul>
                                        <li id="tab1-1" onclick="changeTab('tab1', this)">Thiết bị</li>
                                        <li id="tab2-2" onclick="changeTab('tab2', this)">Loại thiết bị</li>
                                    </ul>
                                </div>
                                <div class="content-tab">
                                    <div id="tab1" class="body-tab">
                                        <div><input type="button" class="btn btn-orange" value="Tạo thiết bị"
                                                    onclick="showModal(1, 'modal-1')"/></div>
                                        <div class="search" style="  margin: 0 0 15px;">
                                            <input type="text" placeholder="Nhập thiết bị"/>
                                            <input type="button" class="btn btn-orange" value="Tìm kiếm"/>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="table" style="width:77%; height: 280px">
                                            <div class="header-table">
                                                <div style="width:10%">
                                                    <div>Loại</div>
                                                </div>
                                                <div style="width:12%;">
                                                    <div>Tên</div>
                                                </div>
                                                <div style="width:20%;">
                                                    <div>Số Serial</div>
                                                </div>
                                                <div style="width:100px;">
                                                    <div>Giờ sử dụng</div>
                                                </div>
                                                <div style="width:100px;">
                                                    <div>Giờ còn lại</div>
                                                </div>
                                                <div style="width:50px;">
                                                    <div>Phòng</div>
                                                </div>
                                                <div style="width: 100px">
                                                    <div></div>
                                                </div>
                                                <p class="clear"></p>
                                            </div>
                                            <div class="body-table">
                                                <form action="/staff/removeEqipment" id="removeClassroom">
                                                    <input type="hidden" value="" id="removeClassroomName"
                                                           name="classroomName"/>
                                                    <c:forEach items="${equipments}" var="e">
                                                        <c:choose>
                                                            <c:when test="${e.timeRemain <=50}">
                                                                <div class="row" style="background-color: #cd0a0a">
                                                                    <div style="width:10%;">
                                                                        <div>${e.tblEquipmentCategoryByCategoryId.name}</div>
                                                                    </div>
                                                                    <div style="width:12%;">
                                                                        <div>${e.name}</div>
                                                                    </div>
                                                                    <div style="width:20%;">
                                                                        <div>${e.serialNumber}</div>
                                                                    </div>
                                                                    <div style="width:100px;">
                                                                        <div>${e.tblEquipmentCategoryByCategoryId.usingTime}</div>
                                                                    </div>
                                                                    <div style="width:100px;">
                                                                        <div>${e.timeRemain}</div>
                                                                    </div>
                                                                    <div style="width:50px;">
                                                                        <div>${e.tblClassroomByClassroomId.name}</div>
                                                                    </div>
                                                                    <div style="width: 100px">
                                                                        <div class="group-button">
                                                                            <c:choose>
                                                                                <c:when test="${e.timeRemain<=50 and e.tblEquipmentCategoryByCategoryId.id==1}">
                                                                                    <div class="btn btn-detail"
                                                                                         title="Chỉnh sửa"><i
                                                                                            class="fa fa-pencil"></i>
                                                                                    </div>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <div class="btn btn-remove"
                                                                                 onclick="conform(4, ${e.id})"
                                                                                 title="Xóa"><i
                                                                                    class="fa fa-times"></i>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <p class="clear"></p>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="row">
                                                                    <div style="width:10%;">
                                                                        <div>${e.tblEquipmentCategoryByCategoryId.name}</div>
                                                                    </div>
                                                                    <div style="width:12%;">
                                                                        <div>${e.name}</div>
                                                                    </div>
                                                                    <div style="width:20%;">
                                                                        <div>${e.serialNumber}</div>
                                                                    </div>
                                                                    <div style="width:100px;">
                                                                        <div>${e.tblEquipmentCategoryByCategoryId.usingTime}</div>
                                                                    </div>
                                                                    <div style="width:100px;">
                                                                        <div>${e.timeRemain}</div>
                                                                    </div>
                                                                    <div style="width:50px;">
                                                                        <div>${e.tblClassroomByClassroomId.name}</div>
                                                                    </div>
                                                                    <div style="width: 100px">
                                                                        <div class="group-button">
                                                                            <c:choose>
                                                                                <c:when test="${e.timeRemain<=50 and e.tblEquipmentCategoryByCategoryId.id==1}">
                                                                                    <div class="btn btn-detail"
                                                                                         title="Chỉnh sửa"><i
                                                                                            class="fa fa-pencil"></i>
                                                                                    </div>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:choose>
                                                                                <c:when test="${e.classroomId != null}">
                                                                                    <div class="btn btn-remove"
                                                                                         onclick="conformData(2,{message:'Bạn có muốn gỡ thiết bị ${e.name} (serial: ${e.serialNumber}) ra khỏi phòng ${e.tblClassroomByClassroomId.name}?',
                                                                                                 btnName:'Xóa',link:'/staff/removeEquipment?equipmentId=${e.id}'})"
                                                                                         title="Xóa"><i
                                                                                            class="fa fa-times"></i>
                                                                                    </div>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <div class="btn btn-remove"
                                                                                         onclick="conformData(2,{message:'Bạn có muốn xóa thiết bị ${e.name} (serial: ${e.serialNumber})?', btnName:'Xóa', link:'/staff/removeEquipment?equipmentId=${e.id}&remove=true'})"
                                                                                         title="Xóa"><i
                                                                                            class="fa fa-times"></i>
                                                                                    </div>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </div>
                                                                    </div>
                                                                    <p class="clear"></p>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>


                                                    </c:forEach>
                                                </form>
                                            </div>
                                        </div>
                                        <div id="pagination" style="padding-left: 150px;"></div>
                                    </div>
                                    <div id="tab2" class="body-tab">
                                        <div>
                                            <div></div>
                                            <input type="button" class="btn btn-orange" style="margin: 0"
                                                   value="Tạo loại thiết bị" onclick="showModal(1, 'modal-category')"/></div>
                                        <div class="search" style="  margin: 0 0 15px;">
                                            <input type="text" placeholder="Nhập thiết bị"/>
                                            <input type="button" class="btn btn-orange" value="Tìm kiếm"/>
                                        </div>
                                        <div class="clear"></div>
                                        <div class="table" style="width:77%; height: 350px">
                                            <div class="header-table">
                                                <div style="width:100px">
                                                    <div>Hình ảnh</div>
                                                </div>
                                                <div style="width:100px;">
                                                    <div>Tên</div>
                                                </div>
                                                <div style="width:100px;">
                                                    <div>Quản lý</div>
                                                </div>
                                                <div style="width: 100px">
                                                    <div></div>
                                                </div>
                                                <p class="clear"></p>
                                            </div>
                                            <div class="body-table">
                                                <form action="/staff/removeEqipment" id="removeCategory">
                                                    <input type="hidden" value="" id="removeClassroomName"
                                                           name="classroomName"/>
                                                    <c:forEach items="${categories}" var="c">
                                                        <div class="row" style="height: 35px">
                                                            <div style="width: 100px">
                                                                <div class="equip"
                                                                     style="background-image: url('../../resource/img/equipment/${c.imageUrl}')">
                                                                </div>
                                                            </div>
                                                            <div style="width: 100px">${c.name}</div>
                                                                <div style="width: 100px"><c:choose>
                                                                    <c:when test="${c.isManaged}">
                                                                        Có
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        Không
                                                                    </c:otherwise>
                                                                </c:choose></div>
                                                            <div style="width: 100px">
                                                                <div class="control">
                                                                    <div class="group-button">
                                                                        <div class="btn btn-remove" title="Xóa"><i
                                                                                class="fa fa-times"></i>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </c:forEach>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:import url="/bao-cao/thong-bao?little=false&quay-lai=equipment"/>
                        <div class="loading-page">
                            <img src="/resource/img/500.GIF">
                            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content-modal">
            <form action="/staff/createEquipment" id="createEquipment" name="createEquipment">
                <div class="modal modal-medium" id="modal-1">
                    <div class="content-modal" style="height: 260px">
                        <div class="header-modal title">
                            <p id="classroomName">Tạo thiết bị</p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
                        </div>
                        <div class="body-modal" style="padding-top: 10px">
                            <div class="group-control">
                                <div class="name">Loại thiết bị</div>
                                <div class="control">
                                    <select name="categoryId">
                                        <c:forEach items="${categories}" var="ca">
                                            <option value="${ca.id}">${ca.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Tên thiết bị</div>
                                <input type="text" name="name">
                            </div>
                            <div class="group-control">
                                <div class="name">Số seri</div>
                                <input type="text" name="serialNumber">
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(0, 'modal-1')"
                                   value="Thoát"/>
                            <input type="button" class="btn btn-orange" onclick="validateEquipment();"
                                   value="Tạo"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>

            <form action="/staff/createCategory" id="createCategory" name="createCategory" enctype="multipart/form-data" method="post">
                <div class="modal modal-medium" id="modal-category">
                    <div class="content-modal" style="height: 260px">
                        <div class="header-modal title">
                            <p>Tạo thiết bị</p>
                            <i class="fa fa-times" onclick="showModal(0,'modal-category')"></i>
                        </div>
                        <div class="body-modal" style="padding-top: 10px">
                            <div class="group-control">
                                <div class="name">Tên loại</div>
                                <div class="control">
                                    <input name="name" type="text" id="categoryName">
                                </div>
                            </div>
                            <div class="group-control">
                                <div class="name">Hình ảnh</div>
                                <div class="control"> <input type="file" id="fileUpload" name="scheduleFile" size="50" accept="image/*"/>
                                    <p id="lblError" style="color: red;"></p>
                                </div>


                            </div>
                            <div class="group-control">
                                <div class="name">Quản lý</div>
                                <input type="checkbox"  id="checkManage" onclick="checkManaged();">
                                <input type="hidden" id="isManaged" name="isManaged" value="0">
                            </div>
                        </div>
                        <div class="footer-modal">
                            <input type="button" class="btn btn-normal"
                                   onclick="showModal(0, 'modal-category')"
                                   value="Thoát"/>
                            <input type="button" class="btn btn-orange"
                                   value="Tạo" onclick="validateCreateCategory();"/>
                        </div>
                    </div>
                    <div class="black-background"></div>
                </div>
            </form>
        </div>


        </body>
        <script src="/resource/js/script.js"></script>
        <script src="/resource/js/jquery.simplePagination.js"></script>

        <script>
            function validateEquipment() {
                var name = document.forms["createEquipment"]["name"].value;
                var serialNumber = document.forms["createEquipment"]["serialNumber"].value;

                $.ajax({
                    type: "get",
                    url: "/ajax/checkEquipment",
                    cache: false,
                    data: 'name=' + name + '&serialNumber=' + serialNumber,
                    success: function (data) {
                        if (data.status == true) {
                            showModal(0, 'modal-1');
                            document.getElementById('createEquipment').submit();
                            $(".loading-page").addClass("active");
                            $(".page").removeClass("active");
                        } else {
                            conformData(1, {message: data.alert});
                        }
                    },
                    error: function () {
                        conformData(1, {message: 'Xin Hãy Nhập Đầy Đủ Thông Tin!'});
                    }
                })
            }
            function ValidateExtension() {
                var fileUpload = document.getElementById("fileUpload");
                var lblError = document.getElementById("lblError");
                var regex = /^(([a-zA-Z]:)|(\\{2}\w+)\$?)(\\(\w[\w].*))+(.jpg|.jpeg|.png|.gif)$/;
                if (!regex.test(fileUpload.value.toLowerCase())) {
                    lblError.innerHTML = "Hãy chọn tập tin có đuôi là: <b>" + " .jpg, .jpeg,.png, .gif" + "</b>";
                    return false;
                }
                lblError.innerHTML = "";
                return true;
            }
            function validateCreateCategory() {
                if(ValidateExtension()){
                    var name = document.forms["createCategory"]["name"].value;
                    $.ajax({
                        type: "get",
                        url: "/ajax/checkCategory",
                        cache: false,
                        data: 'name=' + name,
                        success: function (data) {
                            if (data.status == true) {
                                showModal(0, 'modal-1');
                                document.getElementById('createCategory').submit();
                                $(".loading-page").addClass("active");
                                $(".page").removeClass("active");
                            } else {
                                conformData(1, {message: data.alert});
                            }
                        },
                        error: function () {
                            conformData(1, {message: 'Xin Hãy Nhập Đầy Đủ Thông Tin!'});
                        }
                    })
                }
            }

            function checkManaged(){
                if(document.getElementById('checkManage').checked){
                    document.getElementById('isManaged').value = "1";
                }else{
                    document.getElementById('isManaged').value = "0";

                }
            }
            //phan trang
            jQuery(function ($) {
                var items = $("#removeClassroom > div");

                var numItems = items.length;
                var perPage = 7;

                // only show the first 2 (or "first per_page") items initially
                items.slice(perPage).hide();
                // now setup pagination
                $("#pagination").pagination({
                    items: numItems,
                    itemsOnPage: perPage,
                    cssStyle: "compact-theme",
                    onPageClick: function (pageNumber) { // this is where the magic happens
                        // someone changed page, lets hide/show trs appropriately
                        var showFrom = perPage * (pageNumber - 1);
                        var showTo = showFrom + perPage;

                        items.hide() // first hide everything, then show for the new page
                                .slice(showFrom, showTo).show();
                    }
                });
            });

            document.getElementById("${tab}").className += " active";
            document.getElementById("${tab}").setAttribute("data-main", "1");


        </script>
        </html>
    </c:otherwise>
</c:choose>