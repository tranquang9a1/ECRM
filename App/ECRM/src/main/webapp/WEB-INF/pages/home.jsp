<%--
  Created by IntelliJ IDEA.
  User: Htang
  Date: 5/26/2015
  Time: 3:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>home</title>
</head>
<c:set value="${requestScope.VROWS}" var="vrows"/>
<body>
<form action="/print" method="get">
  <button type="submit">map</button>
</form>
</body>
</html>
