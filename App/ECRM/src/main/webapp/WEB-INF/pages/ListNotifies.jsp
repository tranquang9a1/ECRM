<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 7/12/2015
  Time: 10:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="backLink" value="${requestScope.BACKLINK}"/>
<c:set var="numberOfUnreadNotify" value="${requestScope.NUMBEROFNOTIFY}"/>
<c:set var="notifies" value="${requestScope.LISTNOTIFY}"/>

<c:set var="unreadNotifies" value="${requestScope.UNREADNOTIFYS}"/>
<c:set var="readNotifies" value="${requestScope.READNOTIFYS}"/>

<c:if test="${not empty notifies}" >
  <c:forEach items="${notifies}" var="item">
    <c:choose>
      <c:when test="${item.tblNotificationById.status == true && item.tblNotificationById.messageType == 1}">
        <%--<li><img src="/resource/img/user.png"><a href="/thong-bao">${item.tblNotificationById.message}</a></li>--%>
        <div class="item" id="notify-${item.id}" title="${item.message}" onclick="conformData(1, {message: '${item.message}'})">${item.message}</div>
      </c:when>
      <c:otherwise>
        <c:if test="${item.status == true}">
          <%--<li><img src="/resource/img/user.png"><a href="/thong-bao/notify?link=${item.id}">${item.tblNotificationById.message}</a></li>--%>
          <div class="item" id="notify-${item.id}" title="${item.message}" onclick="conformData(1, {message: '${item.message}'})">${item.message}</div>
        </c:if>
        <c:if test="${item.status == false}">
          <%--<li style="background-color: #d5d5d5;"><img src="/resource/img/user.png"><a href="/thong-bao/notify?link=${item.id}">${item.tblNotificationById.message}</a></li>--%>
          <div class="item" id="notify-${item.id}" title="${item.message}" onclick="conformData(1, {message: '${item.message}'})"><b>${item.message}</b></div>
        </c:if>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</c:if>
<c:if test="${empty notifies}">
  <div class="page" id="list-notifies">
    <div class="title" style="padding: 0; height: 70px;">
      <p>Danh sách thông báo</p>
      <c:if test="${backLink != null}">
        <input type="button" class="btn btn-normal" onclick="showViewPage('${backLink}')" value="Quay lại"/>
      </c:if>
      <div class="clear"></div>
      <div class="title-category">
        <ul>
          <li class="active">Thông báo mới</li>
          <li>Lịch sử thông báo</li>
        </ul>
      </div>
    </div>
    <div class="notifies new-notifies active">
      <div class="left-col width-40">
        <div class="tab"><div class="tab-medium"><ul><li class="active">Giảng viên</li></ul></div></div>
        <div class="block-content">
          <div class="unread-notify">
          <c:if test="${unreadNotifies.size() > 0}">
            <c:forEach var="item" items="${unreadNotifies}">
              <div class="row">
                <a href="/bao-cao/notify?link=${item.id}">
                  <div class="time"><fmt:formatDate type="time" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div>
                  <div class="message">${item.tblNotificationById.message}</div>
                  <div class="clear"></div>
                </a>
              </div>
            </c:forEach>
          </c:if>
          </div>
          <c:if test="${unreadNotifies.size() <= 0}">
            <div class="none-message">Không có thông báo</div>
          </c:if>
        </div>
      </div>
      <%--<div class="right-col width-55">
        <div class="tab"><div class="tab-medium"><ul><li class="active">Hệ thống</li></ul></div></div>
        <div class="block-content">
        </div>
      </div>--%>
    </div>

    <div class="notifies old-notifies">
      <div class="right-col width-60">
      <div class="tab"><div class="tab-medium"><ul><li class="active">Lịch sử thông báo</li></ul></div></div>
      <div class="block-content">
      <c:if test="${readNotifies.size() <= 0}">
        <div class="none-message">Không có thông báo</div>
      </c:if>
      <c:if test="${readNotifies.size() > 0}">
        <div class="table">
          <div class="header-table">
            <div class="width-30">Thời gian</div>
            <div class="width-70">Nội dung</div>
          </div>
          <div class="body-table">
            <c:forEach items="${readNotifies}" var="item">
              <div class="row">
                <div class="width-30"><div><fmt:formatDate type="time" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div></div>
                <div class="width-70"><div>${item.tblNotificationById.message}</div></div>
              </div>
            </c:forEach>
          </div>
        </div>
      </c:if>
      </div>
    </div>
    </div>
  </div>
</c:if>
