<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/31/2015
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${requestScope.LISTSTATISTIC}" var="list"/>
<c:set value="${requestScope.YEAR}" var="year"/>

<c:if test="${list == null}">Không có dữ liệu thống kê</c:if>
<div id="chartContainer" style="width: 100%; height: 400px; margin: 35px 0 0 0;"></div>

<c:if test="${list != null}">
  <script type="text/javascript">
  $(function () {
    $('#chartContainer').highcharts({
      chart: {
        type: 'line'
      },
      title: {
        text: 'Số lượt đổi phòng các tháng trong năm ${year}'
      },
      xAxis: {
        categories: [<c:forEach items="${list.listMonth}" var="item">'${item}'<c:if test="${item != list.listMonth[list.listMonth.size()-1]}">,</c:if></c:forEach>]
      },
      yAxis: {
        title: {
          text: ''
        }
      },
      plotOptions: {
        line: {
          dataLabels: {
            enabled: true
          },
          enableMouseTracking: false
        }
      },tooltip: {
        valueSuffix: 'lượt'
      },
      legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
      },
      series: [{
        name: 'Số lượt đổi',
        data: ${list.listNumber}
      }]
    });
  });
  </script>
</c:if>
