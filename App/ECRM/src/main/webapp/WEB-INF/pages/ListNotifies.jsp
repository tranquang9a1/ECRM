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

<c:set var="systemUnreads" value="${requestScope.SYSTEMUNREADS}"/>
<c:set var="newReportUnreads" value="${requestScope.NEWREPORTUNREADS}"/>
<c:set var="readNotifies" value="${requestScope.READNOTIFYS}"/>
<c:set var="check" value="${requestScope.USERPAGE}"/>

<c:if test="${not empty check}" >
  <c:forEach items="${notifies}" var="item">
    <c:choose>
      <c:when test="${item.tblNotificationById.status == true && item.tblNotificationById.messageType == 1}">
        <div class="item" id="notify-${item.id}" title="${item.tblNotificationById.message}" onclick="conformData(1, {message: '${item.tblNotificationById.message}'})">${item.tblNotificationById.message}</div>
      </c:when>
      <c:otherwise>
        <c:if test="${item.status == true}">
          <div class="item" id="notify-${item.id}" title="${item.tblNotificationById.message}" onclick="conformData(1, {message: '${item.tblNotificationById.message}'})">${item.tblNotificationById.message}</div>
        </c:if>
        <c:if test="${item.status == false}">
          <div class="item" style="font-weight: bold; font-style: italic" id="notify-${item.id}" title="${item.tblNotificationById.message}" onclick="viewNotify(${item.id}, '${item.tblNotificationById.message}')">${item.tblNotificationById.message}</div>
        </c:if>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</c:if>
<c:if test="${empty check}">
  <div class="page" id="list-notifies">
    <div class="title" style="padding: 0; height: 70px;">
      <p>Danh sách thông báo</p>
      <c:if test="${backLink != null}">
        <input type="button" class="btn btn-normal" onclick="showViewPage('${backLink}')" value="Quay lại"/>
      </c:if>
      <div class="clear"></div>
      <div class="title-category">
        <ul>
          <li id="head-tab10" onclick="changeTabInTitle('tab10', this)" data-tab="notify-tab" class="active">Thông báo mới</li>
          <li id="head-tab20" onclick="changeTabInTitle('tab20', this)" data-tab="notify-tab">Lịch sử thông báo</li>
        </ul>
      </div>
    </div>
    <div class="tab notify-tab">
      <div class="content-tab">
        <div class="body-tab active" id="tab10">
          <div class="left-col width-40">
            <div class="tab"><div class="tab-medium"><ul><li class="active">Giảng viên</li></ul></div></div>
            <div class="block-content unread-notify" id="unread-notify">
              <c:if test="${newReportUnreads.size() > 0}">
                <c:forEach var="item" items="${newReportUnreads}">
                  <div class="row">
                    <a href="/bao-cao/notify?link=${item.id}">
                      <div class="time"><fmt:formatDate type="time" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div>
                      <div class="message">${item.tblNotificationById.message}</div>
                      <div class="clear"></div>
                    </a>
                  </div>
                </c:forEach>
              </c:if>
              <c:if test="${newReportUnreads.size() == 0}">
                <div class="none-message" style="color: #888888; font-style: italic">Không có thông báo</div>
              </c:if>
            </div>
          </div>
          <div class="right-col width-55">
            <div class="tab"><div class="tab-medium"><ul><li class="active">Hệ thống</li></ul></div></div>
            <div class="block-content">
              <c:if test="${systemUnreads.size() > 0}">
                <c:forEach var="item" items="${systemUnreads}">
                  <div class="row">
                    <a href="/bao-cao/notify?link=${item.id}">
                      <div class="time"><fmt:formatDate type="time" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div>
                      <div class="message">${item.tblNotificationById.message}</div>
                      <div class="clear"></div>
                    </a>
                  </div>
                </c:forEach>
              </c:if>
              <c:if test="${systemUnreads.size() <= 0}">
                <div class="none-message" style="color: #888888; font-style: italic">Không có thông báo</div>
              </c:if>
            </div>
          </div>
        </div>
        <div class="body-tab" id="tab20">
          <c:if test="${readNotifies.size() <= 0}">
            <div class="none-message">Không có thông báo</div>
          </c:if>
          <c:if test="${readNotifies.size() > 0}">
            <div class="table">
              <div class="header-table">
                <div class="width-15"><div>Thời gian</div></div>
                <div class="width-15"><div>Ngày</div></div>
                <div class="width-15"><div>Loại</div></div>
                <div class="width-55"><div>Nội dung</div></div>
              </div>
              <div class="body-table">
                <c:forEach items="${readNotifies}" var="item">
                  <div class="row">
                    <div class="width-15"><div><fmt:formatDate type="time" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div></div>
                    <div class="width-15"><div><fmt:formatDate type="date" timeStyle="short" value="${item.tblNotificationById.createTime}"/></div></div>
                    <div class="width-15" style="text-transform: uppercase">
                      <c:if test="${item.tblNotificationById.messageType == 1}">Giảng viên</c:if>
                      <c:if test="${item.tblNotificationById.messageType == 3}">Hệ thống</c:if>
                    </div>
                    <div class="width-55"><div>${item.tblNotificationById.message}</div></div>
                    <p class="clear"></p>
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
