<%--
  Created by IntelliJ IDEA.
  User: ChiDNMSE60717
  Date: 6/8/2015
  Time: 7:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal modal-medium" id="modal-1">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Chi tiết báo cáo</p>
      <i class="fa fa-times" onclick="showModal(0,'modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="tab">
        <div class="tab-medium">
          <ul>
            <li class="active" onclick="changeTab('tab4', this)">Báo cáo</li>
            <li onclick="changeTab('tab5', this)">Lịch sử</li>
          </ul>
        </div>
        <div class="content-tab">
          <div id="tab4" class="body-tab active">
            <div class="group-control">
              <div class="name">Phòng</div>
              <div class="value">203</div>
            </div>
            <div class="group-control">
              <div class="name">Người báo cáo</div>
              <div class="value">Phạm Văn Khánh</div>
            </div>
            <div class="group-control">
              <div class="name">Thời gian báo cáo</div>
              <div class="value">7h04, 24/4/2015</div>
            </div>
            <div class="group-control">
              <div class="name">Thiết bị hư hại</div>
              <div class="value">Máy chiếu - ECRM0251</div>
              <input type="button" class="btn btn-normal" onclick="showMap(2, [5,5], [3,2], 'idContainer');showModal(2, 'modal-1','modal-2')" value="Vị trí" />
            </div>
            <div class="group-control">
              <div class="name">Chi tiết hư hại</div>
              <div class="value">.........................................</div>
            </div>
            <div class="group-control line">
              <div class="name">Đề xuất</div>
              <div class="value">Đổi sang phòng <b>213</b></div>
              <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-1','modal-3')" value="Đổi phòng" />
            </div>
            <div class="group-control">
              <div class="name">Mức độ hư hại</div>
              <div class="value">Nặng, không thể dùng được</div>
            </div>
            <div class="group-control">
              <div class="name">Hư hại của phòng</div>
              <div class="control">
                <div class="process">
                  <p>73%</p>
                  <div class="percent" style="left:73%"></div>
                </div>
              </div>
            </div>
          </div>
          <div id="tab5" class="body-tab">
            <div class="row top-control">
              <p>Máy chiếu - ECRM0251</p>
              <select class="select">
                <option>Máy chiếu</option>
                <option>Máy quạt</option>
                <option>Đèn</option>
              </select>
              <div class="clear"></div>
            </div>
            <div class="table">
              <div class="header-table">
                <div class="date-report"><div>Ngày báo cáo</div></div>
                <div class="reporter"><div>Người báo cáo</div></div>
                <div class="staff"><div>Nhân viên</div></div>
                <p class="clear"></p>
              </div>
              <div class="body-table">
                <div class="row">
                  <div class="date-report"><div>10h20 20/3/2015</div></div>
                  <div class="reporter"><div>Phạm Thế Vinh</div></div>
                  <div class="staff"><div>Trương Hồng Thái</div></div>
                  <p class="clear"></p>
                </div>
                <div class="row">
                  <div class="date-report"><div>7h01 15/3/2015</div></div>
                  <div class="reporter"><div>Trương Thế Hiển</div></div>
                  <div class="staff"><div>Trương Hồng Thái</div></div>
                  <p class="clear"></p>
                </div>
                <div class="row">
                  <div class="date-report"><div>8h37 21/2/2015</div></div>
                  <div class="reporter"><div>Trương Thế Hiển</div></div>
                  <div class="staff"><div>Lê Quang Hạnh</div></div>
                  <p class="clear"></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(0, 'modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(1)" value="Khắc phục" />
      <input type="button" class="btn btn-orange" onclick="showModal(2, 'modal-1', 'modal-4')" value="Xem tất cả" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-2">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Sơ đồ phòng học</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-2','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="map-container" id="idContainer" style="margin: 15px 0 0">

      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-2','modal-1')" value="Thoát" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-small" id="modal-3">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Đổi phòng</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-3','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="group-control">
        <div class="name">Phòng hiện tại</div>
        <div class="value">203</div>
      </div>
      <div class="group-control">
        <div class="name">Phòng trống</div>
        <div class="control">
          <select>
            <option>213</option>
            <option>301</option>
            <option>413</option>
            <option>212</option>
            <option>108</option>
          </select>
        </div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-3','modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(3)" value="Đổi phòng" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
<div class="modal modal-medium" id="modal-4">
  <div class="content-modal">
    <div class="header-modal title">
      <p>Thiết bị hư hại phòng 203</p>
      <i class="fa fa-times" onclick="showModal(2, 'modal-4','modal-1')"></i>
    </div>
    <div class="body-modal">
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Máy chiếu</div>
        <div class="quantity">1 máy</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Đèn</div>
        <div class="quantity">3 cái</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Máy quạt</div>
        <div class="quantity">1 máy</div>
        <div class="clear"></div>
      </div>
      <div class="group-equipment">
        <input type="checkbox" />
        <div style=" float:left; width: 50px; height: 50px; background-color: #f1f1f1"></div>
        <div class="equipment">Ghế</div>
        <div class="quantity">5 cái</div>
        <div class="clear"></div>
      </div>
    </div>
    <div class="footer-modal">
      <input type="button" class="btn btn-normal" onclick="showModal(2, 'modal-4','modal-1')" value="Thoát" />
      <input type="button" class="btn btn-orange" onclick="conform(2)"  value="Khắc phục" />
    </div>
  </div>
  <div class="black-background"></div>
</div>
