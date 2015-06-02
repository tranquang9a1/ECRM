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
    <script src="../../resource/js/roomtype-2.js"></script>
</head>
<c:set var="r" value="${requestScope.ROOMTYPE}"/>
<body onload="showMap('classroom-map', ${r.id}, ${r.verticalRows},'${r.horizontalRows}', '${r.noSlotsEachHRows}',
${r.airConditioning}, ${r.fan}, ${r.projector}, ${r.speaker}, ${r.television})">
<div style="width: 400px;">
    <div id="classroom-map">

    </div>
</div>
</body>
</html>
