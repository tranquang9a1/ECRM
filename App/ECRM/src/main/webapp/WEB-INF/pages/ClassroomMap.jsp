<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 6/2/2015
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="../../resource/css/roomtype-2.css"/>
    <script src="../resource/js/jquery-1.11.3.js"></script>
    <script src="../../resource/js/quang.js"></script>

    <style>
        .subDIv {
            display: block !important;
        }
        .board {
            height: 20px;
            background-color: #999;
            color: #666;
            text-align: center;
            padding: 3px;
            border-radius: 5px;
            width: 50%;
            margin: 0px !important;
        }
    </style>
</head>
<c:set var="r" value="${requestScope.CLASSROOM}"/>
<body>
<div style="width: 240px;">
    <div class="group-control">
        <div class="name">Thiết bị</div>
        <div class="value" id="list-equipment"></div>
    </div>
    <div id="classroom-map">

    </div>
</div>
</body>
</html>
<script src="../../resource/js/roomtype-2.js"></script>
<script>
    window.onload = function(){
        showMap('classroom-map', ${r.roomTypeId}, ${r.tblRoomTypeByRoomTypeId.verticalRows},
                '${r.tblRoomTypeByRoomTypeId.horizontalRows}', '${r.tblRoomTypeByRoomTypeId.numberOfSlotsEachHRows}',
                ${r.tblRoomTypeByRoomTypeId.airConditioning}, ${r.tblRoomTypeByRoomTypeId.fan},
                ${r.tblRoomTypeByRoomTypeId.projector}, ${r.tblRoomTypeByRoomTypeId.speaker},
                ${r.tblRoomTypeByRoomTypeId.television});
        setChooseEquipment();

    }
</script>
