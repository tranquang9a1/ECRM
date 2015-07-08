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
<c:set var="size" value="${requestScope.NUMBEROFNOTIFY}" />
<c:if test="${list.size() == 0}">
  <div class="notifications fa fa-bell">
    <p class="number-of-notify hidden" data-value="0">0</p>
    <div class="list-notify" data-value="${list.size()}">
      <img src="../../resource/img/pointer.png"/>
      <ul>
        <li><a href="javascript:void(0)" style="line-height: 40px">Không có thông báo mới</a></li>
      </ul>
    </div>
  </div>
</c:if>
<c:if test="${list.size() > 0}">
  <div class="notifications fa fa-bell">
    <c:if test="${size > 0}">
      <p class="number-of-notify" data-value="${size}">${size}</p>
    </c:if>
    <c:if test="${size <= 0}">
      <p class="number-of-notify hidden" data-value="0">0</p>
    </c:if>
    <div class="list-notify" data-value="${list.size()}">
      <img src="../../resource/img/pointer.png"/>
      <ul>
        <c:forEach items="${list}" var="item">
          <c:choose>
            <c:when test="${item.tblNotificationById.status == true && item.tblNotificationById.messageType == 1}">
              <li><img src="/resource/img/user.png"><a href="/thong-bao">${item.tblNotificationById.message}</a></li>
            </c:when>
            <c:otherwise>
              <c:if test="${item.status == true}">
                <li><img src="/resource/img/user.png"><a href="/thong-bao/notify?link=${item.id}">${item.tblNotificationById.message}</a></li>
              </c:if>
              <c:if test="${item.status == false}">
                <li style="background-color: #d5d5d5;"><img src="/resource/img/user.png"><a href="/thong-bao/notify?link=${item.id}">${item.tblNotificationById.message}</a></li>
              </c:if>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </ul>
    </div>
  </div>
</c:if>