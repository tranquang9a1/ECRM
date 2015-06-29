<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/28/2015
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>ECRM - Equipment Classroom Management</title>
    <link rel="stylesheet" href="../../resource/css/font-awesome.css"/>
    <link rel="stylesheet" href="../../resource/css/layout.css"/>
    <link rel="stylesheet" href="../../resource/css/general.css"/>
    <link rel="stylesheet" href="../../resource/css/management.css"/>
    <link rel="stylesheet" href="../../resource/css/roomtype-2.css"/>
    <script src="../../resource/js/jquery-1.11.3.js"></script>
    <script src="../../resource/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<c:set var="user" value="${sessionScope.USER}"/>
<c:set var="users" value="${requestScope.LSTUSERS}"/>

<div class="contain-layout">
    <jsp:include flush="true" page="Header.jsp"/>
</div>
<div class="container">
    <div class="title page-title right-button">
        <p>Quản lý tài khoản</p>

        <div><input type="button" class="btn btn-orange" onclick="showModal(1,'CreateAccount');" value="Tạo Tài Khoản"/>
        </div>
        <div class="clear"></div>
    </div>
    <div class="body-content">
        <div class="table" style="width: 720px">
            <div class="header-table">
                <div style="width:200px;">
                    <div>Username</div>
                </div>
                <div style="width:200px;">
                    <div>Số điện thoại</div>
                </div>
                <div class="status">
                    <div>Trạng thái</div>
                </div>
                <div class="control">
                    <div>Quản lý</div>
                </div>
                <p class="clear"></p>
            </div>
            <div class="body-table">
                <form action="/admin/editAccount" id="editAccount">
                    <input type="hidden" value="" id="username" name="Username"/>
                    <input type="hidden" value="" id="action" name="Action">
                    <c:forEach items="${users}" var="u">
                        <c:choose>
                            <c:when test="${u.status}">
                                <div class="row">
                            </c:when>
                            <c:otherwise>
                                <div class="row" style="background-color: hotpink">
                                </c:otherwise>
                        </c:choose>
                            <div style="width:200px;">
                                <div>${u.username}</div>
                            </div>
                            <div style="width:200px;">
                                <div>${u.tblUserInfoByUsername.phone}</div>
                            </div>
                            <div class="status">
                                <c:choose>
                                    <c:when test="${u.status}">
                                        <div>Activated</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>Deactivated</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="control">
                                <c:choose>
                                    <c:when test="${u.status}">
                                        <input type="button" class="btn btn-orange" value="Deactivate" onclick="Deactivate('${u.username}')"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="button" class="btn btn-orange" value="Activate" onclick="Activate('${u.username}')"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <p class="clear"></p>
                        </div>
                    </c:forEach>
                </form>
            </div>

        </div>

    </div>
    <div id="tab2" class="body-tab">

    </div>
</div>
<div class="modal modal-medium" id="CreateAccount">
    <div class="content-modal" style="height: 530px">
        <div class="header-modal title">
            <p>Tạo Tài Khoản</p>
            <i class="fa fa-times" onclick="showModal(0, 'CreateAccount')"></i>
        </div>
        <div class="body-modal">
            <form action="/admin/createAccount" id="AccountForm" onsubmit="return validateAccountForm()">
                <br/>

                <div class="group-control">
                    <div class="name">Username(*):</div>
                    <div class="control">
                        <input type="text" name="Username">
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
            <input type="button" class="btn btn-orange" onclick="validateAccountForm();"
                   value="Tạo"/>
        </div>
    </div>
    <div class="black-background"></div>
</div>

<script src="../../resource/js/script.js"></script>

<script>
    function Deactivate(username){
        document.getElementById('username').value = username;
        document.getElementById('action').value  = 'Deactivate';
        document.getElementById('editAccount').submit();
    }
    function Activate(username){
        document.getElementById('username').value = username;
        document.getElementById('action').value  = 'Activate';
        document.getElementById('editAccount').submit();
    }
    function validateAccountForm() {
        var username = document.forms["AccountForm"]["Username"].value;
        var phone = document.forms["AccountForm"]["Phone"].value;
        var fullName = document.forms["AccountForm"]["FullName"].value;
        var message = "";
        if (username == null || username == "") {
            alert("Username không được bỏ trống!");
            return false;
        }
        if(checkUsername(username)==="NO"){
            alert("Username đã tồn tại!");
            return false;
        }

        if (phone == null || phone == "") {
            alert("Số điện thoại không được bỏ trống!");
            return false;
        }
        if (fullName == null || fullName == "") {
            alert("Họ tên không được bỏ trống!");
            return false;
        }

    }
    function checkUsername(username){
        return $.ajax({
            type: "get",
            url: "/ajax/checkUsername",
            cache: false,
            data: 'Username='+username,
            success: function (data) {
                console.log(data);
                alert("Username đã tồn tại!");
            },
            error: function () {
                alert('Error while request..');
            }
        })
    }
</script>
</body>
</html>
