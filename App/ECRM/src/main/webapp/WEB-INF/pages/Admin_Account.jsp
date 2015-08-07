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
        <jsp:include page="Login.jsp"/>
    </c:when>
    <c:otherwise>
        <html>
        <head>
            <meta charset="UTF-8"/>
            <title>Hệ thống quản lý thiết bị phòng học</title>
            <link rel="stylesheet" href="/resource/css/general.css"/>
            <link rel="stylesheet" href="/resource/css/newTemplate.css"/>
            <script src="/resource/js/jquery-1.11.3.js"></script>
            <script src="/resource/js/newTemplate.js"></script>
        </head>
        <body>
        <c:set var="user" value="${sessionScope.USER}"/>
        <c:set var="users" value="${requestScope.LSTUSERS}"/>
        <c:set var="page" value="${requestScope.CURRENTPAGE}"/>
        <c:set var="max" value="${requestScope.MAXPAGE}"/>

        <div class="layout-background">
            <div class="container">
                <jsp:include page="/WEB-INF/pages/HeaderNew.jsp"/>
                <div class="content-body">
                    <div class="left-category">
                        <ul>
                            <li id="ADMIN_NOTIFICATION">
                                <a href="javascript:void(0)">
                                    <p>Thông báo</p>
                                </a>
                            </li>
                            <li id="STAFF_REPORT" class=" active">
                                <a href="/admin/account">
                                    <p>Tài khoản</p>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="right-content">
                        <div class="page active" id="list-account">
                            <div class="title">
                                <p>Quản lý tài khoản</p>
                                <input type="button" class="btn btn-orange" onclick="showModal(1,'CreateAccount');" value="Tạo Tài Khoản"/>
                            </div>
                            <c:if test="${users.size() <= 0}"> <p class="none-message">Bạn chưa có báo cáo!</p></c:if>
                            <c:if test="${users.size() > 0}">
                            <div class="table" style="margin: 15px 0 0">
                                <div class="header-table">
                                    <div class="width-25">
                                        <div>Tên người dùng</div>
                                    </div>
                                    <div class="width-25">
                                        <div>Số điện thoại</div>
                                    </div>
                                    <div class="width-25">
                                        <div>Trạng thái</div>
                                    </div>
                                    <div class="width-25">
                                        <div>Quản lý</div>
                                    </div>
                                    <p class="clear"></p>
                                </div>
                                <div class="body-table">
                                    <form action="/quan-ly/editAccount" id="editAccount">
                                        <input type="hidden" value="" id="username" name="Username"/>
                                        <input type="hidden" value="" id="action" name="Action">
                                        <input type="hidden" value="${page}" name="Page" />
                                        <c:forEach items="${users}" var="u">
                                        <c:choose>
                                        <c:when test="${u.status}">
                                            <div class="row">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="row" style="color: #aaa">
                                        </c:otherwise>
                                        </c:choose>
                                                <div class="width-25">
                                                    <div>${u.username}</div>
                                                </div>
                                                <div class="width-25">
                                                    <div>${u.tblUserInfoByUsername.phone}</div>
                                                </div>
                                                <div class="status width-25">
                                                    <c:choose>
                                                        <c:when test="${u.status}">
                                                            <div style="color: #35b83a;">Hoạt động</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div>Khóa</div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="width-25">
                                                    <c:choose>
                                                        <c:when test="${u.status}">
                                                            <input type="button" class="btn btn-normal" style="float: left; padding: 6px 15px; height: 29px; margin: 3px 0 0 10px;" value="Khóa"
                                                                   onclick="Deactivate('${u.username}')"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="button" class="btn btn-orange" style="float: left; padding: 6px 15px; height: 29px; margin: 3px 0 0 10px;" value="Kích hoạt"
                                                                   onclick="Activate('${u.username}')"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <p class="clear"></p>
                                            </div>
                                            </c:forEach>
                                    </form>
                                </div>
                            </div>
                            <c:if test="${max > 1}">
                                    <div class="paging">
                                        <c:if test="${page + 1 <= max}">
                                            <div class="pagen" style="width: auto; padding: 0 5px"><a href="/quan-ly?trang=${page+1}">Sau</a></div>
                                            <div class="pagen"><a href="/quan-ly?trang=${page+1}">${page+1}</a></div>
                                        </c:if>
                                        <div class="pagen current">${page}</div>
                                        <c:if test="${page - 1 > 0}">
                                            <div class="pagen"><a href="/quan-ly?trang=${page-1}">${page-1}</a></div>
                                            <div class="pagen" style="width: auto; padding: 0 5px"><a href="/quan-ly?trang=${page-1}">Trước</a></div>
                                        </c:if>
                                    </div>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="loading-page">
                            <img src="/resource/img/500.GIF">
                            <div>Đang tải! Vui lòng chờ trong giây lát!</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal modal-medium" id="CreateAccount">
            <div class="content-modal" style="height: 267px">
                <div class="header-modal title">
                    <p>Tạo Tài Khoản</p>
                    <i class="fa fa-times" onclick="showModal(0, 'CreateAccount')"></i>
                </div>
                <div class="body-modal">
                    <form action="/quan-ly/createAccount" id="AccountForm" style="margin: 0">
                        <div class="group-control">
                            <div class="name">Username(*):</div>
                            <div class="control">
                                <input type="text" name="Username" onchange="checkUsername();">
                            </div>
                        </div>
                        <div class="group-control">
                            <div class="name">Họ và tên(*):</div>
                            <div class="control">
                                <input type="text" name="FullName">
                            </div>
                        </div>
                        <div class="group-control">
                            <div class="name">Số điện thoại(*):</div>
                            <div class="control">
                                <input type="text"
                                       onkeydown="return ( event.ctrlKey || event.altKey
                    || (47<event.keyCode && event.keyCode<58 && event.shiftKey==false)
                    || (95<event.keyCode && event.keyCode<106)
                    || (event.keyCode==8) || (event.keyCode==9)
                    || (event.keyCode>34 && event.keyCode<40)
                    || (event.keyCode==46) )" name="Phone">
                            </div>
                        </div>

                    </form>
                </div>
                <div class="footer-modal">
                    <input type="button" class="btn btn-normal" onclick="showModal(0, 'CreateAccount')"
                           value="Thoát"/>
                    <input type="button" class="btn btn-orange" onclick="submit();"
                           value="Tạo"/>
                </div>
            </div>
            <div class="black-background"></div>
        </div>
        <input type="hidden" id="validateUsername">
        <script src="/resource/js/script.js"></script>

        <script>
            function Deactivate(username) {
                document.getElementById('username').value = username;
                document.getElementById('action').value = 'Deactivate';
                document.getElementById('editAccount').submit();
            }
            function Activate(username) {
                document.getElementById('username').value = username;
                document.getElementById('action').value = 'Activate';
                document.getElementById('editAccount').submit();
            }
            function submit(){
                if(validateAccountForm() == true){
                    document.getElementById("AccountForm").submit();
                }
            }
            function validateAccountForm() {
                var username = document.forms["AccountForm"]["Username"].value;
                checkUsername(username);
                var phone = document.forms["AccountForm"]["Phone"].value;
                var fullName = document.forms["AccountForm"]["FullName"].value;
                var message = "";
                if (username == null || username == "") {
                    conformData(1, {message: "Username không được bỏ trống!"});
                    return false;
                }else{
                    if(document.getElementById("validateUsername").value==="NO"){
                        conformData(1, {message: "Username đã tồn tại!"});
                        return false;
                    }
                }


                if (phone == null || phone == "") {
                    conformData(1, {message: "Số điện thoại không được bỏ trống!"});
                    return false;
                }
                if (fullName == null || fullName == "") {
                    conformData(1, {message: "Họ tên không được bỏ trống!"});
                    return false;
                }
                return true;
            }

            function checkUsername(username) {
                return $.ajax({
                    type: "get",
                    url: "/ajax/checkUsername",
                    cache: false,
                    data: 'Username=' + username,
                    success: function (data) {
                        if(data === "OK"){
                            document.getElementById("validateUsername").value = "OK";
                        }else{
                            document.getElementById("validateUsername").value = "NO";
                        }
                        console.log(data);
                    },
                    error: function () {
                        conformData(1, {message: 'Error while request..'});
                    }
                })
            }
        </script>
        </body>
        </html>
    </c:otherwise>
</c:choose>