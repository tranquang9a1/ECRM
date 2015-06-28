<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/26/2015
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="list" value="${requestScope.LISTNOTIFY}" />
<c:if test="${list.size() == 0}">
  <div class="notifications fa fa-bell">
    <p class="number-of-notify hidden" data-value="0">0</p>
    <div class="list-notify">
      <img src="../../resource/img/pointer.png"/>
      <ul>
        <li><a href="javascript:void(0)">Không có thông báo mới</a></li>
      </ul>
    </div>
  </div>
</c:if>
<c:if test="${list.size() > 0}">
  <div class="notifications fa fa-bell">
    <p class="number-of-notify" data-value="${list.size()}">${list.size()}</p>
    <div class="list-notify">
      <img src="../../resource/img/pointer.png"/>
      <ul>
        <c:forEach items="${list}" var="item">
          <li><img src="/resource/img/user.png"><a href="${item.redirectLink}">${item.message}</a></li>
        </c:forEach>
      </ul>
    </div>
  </div>
</c:if>