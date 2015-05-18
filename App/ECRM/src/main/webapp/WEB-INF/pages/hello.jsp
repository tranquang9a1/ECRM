<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cL" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
	<h1>${message}</h1>
    <c:set var="user" value="${sessionScope.user}" />
    <c:forEach var="u" items="${user}" varStatus="count">
    <tr>${u.name}</tr>
    </c:forEach>
</body>
</html>