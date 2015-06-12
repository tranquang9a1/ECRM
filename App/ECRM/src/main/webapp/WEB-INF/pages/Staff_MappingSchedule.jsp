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
<c:set var="tab" value="${requestScope.ACTIVETAB}"/>

<div class="contain-layout">
    <jsp:include flush="true" page="Header.jsp"/>
</div>
<div class="container">
    <div class="title page-title right-button">
        <p>Xếp lịch</p>

        <div class="clear"></div>
    </div>
    <div class="body-content">
        <div class="tab">
            <div class="tab-medium">
                <ul>
                    <li class="active" onclick="changeTab('tab1', this)">Upload schedule</li>
                    <li onclick="changeTab('tab2', this)">Import manually</li>
                </ul>
            </div>
            <div class="content-tab">
                <div id="tab1" class="body-tab">
                    <h3>File Upload:</h3>
                    Select a file to upload: <br />
                    <form action="/staff/import" method="post"
                          enctype="multipart/form-data">
                        <input type="file" name="scheduleFile" size="50" accept=".csv,
                        application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
                        <br />
                        <input type="submit" value="Upload File" />
                    </form>
                </div>
                <div id="tab2" class="body-tab">
                </div>
            </div>

        </div>
    </div>
</div>
</div>

<script src="../../resource/js/script.js"></script>

<script>
    function doAction(choose, object) {
        closeConform();
        switch (choose) {
            case 1:

        }
    }
    document.getElementById("${tab}").className = "body-tab active";
</script>
</body>
</html>
