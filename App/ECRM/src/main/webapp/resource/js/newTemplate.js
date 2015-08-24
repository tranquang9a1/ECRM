/**
 * Created by ChiDNMSE60717 on 7/2/2015.
 */
function showListNotify(element) {
    var height = document.getElementById("notifications").clientHeight;
    if(height > 0) {
        document.getElementById("notifications").style.maxHeight = "0px";
    } else {
        document.getElementById("notifications").style.maxHeight = "180px"
    }
}

function conformData(type, object) {
    var body = document.getElementsByTagName("body").item(0);
    var conformDiv = document.createElement("div");
    conformDiv.className = "conform";
    var contentDiv = document.createElement("div");
    contentDiv.className = "content-conform";

    var titleDiv = document.createElement("div");
    titleDiv.className = "title-conform";
    titleDiv.innerHTML = object.message;
    contentDiv.appendChild(titleDiv);


    switch (type) {
        case 1:
            var btnConform = document.createElement("input");
            btnConform.className = "btn btn-normal";
            btnConform.type = "button";
            btnConform.value = "Đóng";
            btnConform.addEventListener("click", closeConformData, false);
            contentDiv.appendChild(btnConform);
            break;

        case 2:
            var btnNo = document.createElement("input");
            btnNo.className = "btn btn-normal";
            btnNo.type = "button";
            btnNo.value = "Đóng";
            btnNo.addEventListener("click", closeConformData, false);

            var btnYes = document.createElement("a");
            btnYes.className = "btn btn-primary";
            btnYes.href = object.link;
            btnYes.innerHTML = object.btnName;

            contentDiv.appendChild(btnNo);
            contentDiv.appendChild(btnYes);
            break;

        case 3:
            var btnNo = document.createElement("input");
            btnNo.className = "btn btn-normal";
            btnNo.type = "button";
            btnNo.value = "Đóng";
            btnNo.addEventListener("click", closeConformData, false);

            var btnYes = document.createElement("a");
            btnYes.className = "btn btn-primary";
            btnYes.type = "button";
            btnYes.innerHTML = object.btnName;
            btnYes.addEventListener("click", function(){ doAction(object.choose, object.object)}, false);

            contentDiv.appendChild(btnNo);
            contentDiv.appendChild(btnYes);
            break;

    }

    var bgDiv = document.createElement("div");
    bgDiv.className = "white-background";
    conformDiv.appendChild(contentDiv);
    conformDiv.appendChild(bgDiv);
    body.appendChild(conformDiv);

    setTimeout(function(){
        contentDiv.className += " active";
    }, 10);
}

function closeConformData() {
    var conformDiv = document.getElementsByClassName("conform")[0];
    conformDiv.parentNode.removeChild(conformDiv);
}

function doAction(choose, object) {
    closeConformData();
    switch (choose) {
        case 1:
            $(".loading-page").addClass("active");
            sendResolve();
            break;
        case 2:
            document.getElementById(object.id).submit();
            $(".loading-page").addClass("active");
            $(".page").removeClass("active");
    }

}



function createTimes(){
    var elementHour = document.getElementById("list-hour");
    var elementSchedule = document.getElementById("list-schedule");
    var hour = 6;

    for(var i = 0; i < 16; i++) {
        var div = document.createElement("div");
        div.className = "hour-item";
        div.style.top = (i*60)+"px";
        div.innerHTML = hour + ":00";
        elementHour.appendChild(div);
        hour++;
    }

    for(var key in listSchedule) {
        if (listSchedule.hasOwnProperty(key)){
            var times = listSchedule[key].time.split(":");
            var hours = parseInt(times[0]);
            var minutes = parseInt(times[1]);

            var div = document.createElement("div");
            div.className = "schedule-item";
            div.style.top = ((hours-6)*60 + minutes - 23)+"px";
            div.innerHTML = (hours<10?"0"+hours:hours) + ":" + (minutes<10?"0"+minutes:minutes) + " - <b>Phòng " + listSchedule[key].room + "</b>";
            elementSchedule.appendChild(div);
        }
    }

   updateTime();

}

var stopUpdate = false;
function updateTime() {
    var d = new Date();
    var nowHour = d.getHours();
    var nowMinute = d.getMinutes();

    var time = ((nowHour - 6) * 60) + nowMinute - 952;
    document.getElementById("time-here").innerHTML = (nowHour < 10 ? '0' + nowHour : nowHour) + ":" + (nowMinute < 10 ? '0' + nowMinute : nowMinute);

    var overtime = false;
    if(nowHour >= 21) {
        overtime = true;
        document.getElementById("now-time").style.top = "0px";
    }
    else {
        document.getElementById("now-time").style.top = time + "px";
    }

    if(stopUpdate == false) {
        var minTime = 100000;
        for(var key in listSchedule) {
            if (listSchedule.hasOwnProperty(key)) {
                var times = listSchedule[key].time.split(":");
                var hours = parseInt(times[0]);
                var minutes = parseInt(times[1]);

                var timeTmp = ((nowHour*60)+nowMinute) - ((hours*60)+minutes);
                if(((nowHour*60)+nowMinute) >= ((hours*60)+minutes)) {
                    if(timeTmp > 0 && timeTmp < minTime){
                        $(".content-schedule").scrollTop(((hours - 6) * 60) + minutes - 23);
                        minTime = timeTmp;
                    }
                }
            }
        }
    }

    if(overtime == false) {
        setTimeout(function() {updateTime()}, 6000);
    }
}

function getRoomReport() {
    $(".loading-page").addClass("active");
    var room = $("#list-active-room").val();

    $.ajax({
            method: "GET",
            url: "/giang-vien/phong-bao-cao",
            data: {roomId: room},
            success: function(data) {
                $(".list-room-content").html(data);
                $(".list-room-content script").remove();
                $("#roomId").val(room);
                $("#room").html($("#list-active-room option:selected").text());
                noDamagedEquipments = {};
                damagedEquipments = {};

                $(".loading-page").removeClass("active");
            }
    });
}

function loadRoomEquipment(listDamage, listEquip){
    var listEquipment = new Array();
    var listId = "";

    for(var i = 0; i < listEquip.length; i++) {
        if(listId.indexOf(listEquip[i].id) > -1){}
        else {
            listId += listEquip[i].id;
            if(listDamage[listEquip[i].id] != null) {
                listEquipment.push({
                    id: listEquip[i].id,
                    name: listEquip[i].name,
                    imageUrl: listEquip[i].imageUrl,
                    message: "Đã được báo cáo"
                });
            } else {
                listEquipment.push({
                    id: listEquip[i].id,
                    name: listEquip[i].name,
                    imageUrl: listEquip[i].imageUrl,
                    message: ""
                });
            }
        }
    }

    var contentDiv = $(".data-room-content")[0];
    for(var i = 0; i < listEquipment.length; i++) {
        var img = document.createElement("div");
        img.className = "equipment-img"
        img.style.backgroundImage = "url('/resource/img/equipment/" + listEquipment[i].imageUrl + "')";
        var div1 = document.createElement("div");
        div1.className = "width-30";
        div1.appendChild(img);

        var damageDiv = document.createElement("div");
        damageDiv.className = "check-damaged";
        damageDiv.innerHTML = "Hư hại";
        damageDiv.id = "equipment-type-" + listEquipment[i].id;
        damageDiv.setAttribute("data-check", "false");
        damageDiv.setAttribute("onclick", "changeDamaged(" + listEquipment[i].id + ")");
        var div2 = document.createElement("div");
        div2.className = "width-20";
        div2.appendChild(damageDiv);

        var div3 = document.createElement("div");
        div3.className = "width-50";
        var textP = document.createElement("p");
        textP.innerHTML = listEquipment[i].message;
        textP.className = "message-text";
        createSelect(listEquipment[i].id, div3);
        div3.appendChild(textP);

        var clear = document.createElement("p");
        clear.className = "clear";
        var row = document.createElement("div");
        row.className = "row";
        row.id = "row-type-" + listEquipment[i].id;
        row.appendChild(div1);
        row.appendChild(div2);
        row.appendChild(div3);
        row.appendChild(clear);
        contentDiv.appendChild(row);
    }
}



function changeTabInfo(type, element, parent){
    var listTitle = element.parentNode.childNodes;
    for(var i = 0; i < listTitle.length; i++) {
        listTitle[i].className = "";
    }

    $(element).addClass("active");
    if(type == 1) {
        $("#" + parent + ' .main-info').addClass('active');
        $("#" + parent + ' .map-info').removeClass('active');
    } else {
        $("#" + parent + ' .main-info').removeClass('active');
        $("#" + parent + ' .map-info').addClass('active');
    }
}

function changeTabTitle(type, element){
    $(".title-category ul li").removeClass("active");
    $(element).addClass("active");

    if(type == 1) {
        $('.main-info').addClass('active');
        $('.map-info').removeClass('active');
    } else {
        $('.main-info').removeClass('active');
        $('.map-info').addClass('active');
    }
}

function changePage(page){
    $(".loading-page").addClass("active");

    setTimeout(function(){
        $(".page").removeClass("active");
        $("#" + page).addClass("active");
        $(".loading-page").removeClass("active");
    }, 300);
}


function showDetailReport(roomId){
    $(".loading-page").addClass("active");
    var room = $("#report-details").attr("data-room");
    window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", "/bao-cao/hu-hai?phong=" + roomId);

    if(room != roomId) {
        $.ajax({
            method: "GET",
            url: "/bao-cao/chi-tiet",
            data: {roomId: roomId},
            success: function(result) {
                $("#report-details .page-content").html(result);
                $("#report-details script").remove();
                $("#report-details").attr("data-room", roomId);

                changePage('report-details');
            }
        });
    } else {
        changePage('report-details');
    }
}

function showHistoryReport(reportId){
    $(".loading-page").addClass("active");
    $.ajax({
        method: "GET",
        url: "/bao-cao/historyReport",
        data: {reportId: reportId},
        success: function(result) {
            $("#history-report .page-content").html(result);
            $("#history-report script").remove();
            changePage('history-report');
        }
    });
}

function resetURL() {
    window.history.pushState({"html":'',"pageTitle":'Hệ thống quản lý thiết bị phòng học'},"", "/bao-cao");
}

function showViewPage(page) {
    changePage(page);

    if("list-notifies" == page) {
        $(".left-category ul li").removeClass("active");
        $("#STAFF_NOTIFICATION").addClass("active");
    } else {
        $(".left-category ul li").removeClass("active");
        $("[data-main=1]").addClass("active");
    }
}

function changeNewRoom() {
    showModal(0, 'change-room');
    $(".loading-page").addClass("active");
    $.ajax({
        method: "GET",
        url: "/bao-cao/doi-phong",
        data: {currentClassroom: $("#current-room").val(), changeClassroom: $("#new-room").val()},
        success: function(result) {
            $(".change-room-text").html("<div class='value'>Phòng hiện đang trống</div>");
            conformData(1,{message: "Phòng đã được đổi thành công"});
            //conformData(1,{message: "Phòng đã được chuyển sang " + $("#new-room option:checked").text()})
            $(".loading-page").removeClass("active");
        }
    });
}

function changePassword(username, th) {
    var password = $("#password").val();
    var rePassword = $("#rePassword").val();

    if(th==1) {
        password = $("#password-first").val();
        rePassword = $("#rePassword-first").val();
    }

    if(password.length < 6) {
        conformData(1, {message: 'Mật khẩu tối thiểu 6 ký tự!'});
    } else if (password != rePassword) {
        conformData(1, {message: 'Mật khẩu xác nhận chưa trùng khớp!'});
    } else if(password == '123456') {
        conformData(1, {message: 'Không được dùng mật khẩu mặc định!'});
    } else {
        if(th==1) {
            showModal(0, 'modal-changePass');
        } else {
            showModal(0, 'modal-updatePass');
        }
        conformData(1, {message: 'Đổi mật khẩu thành công!'});

        $.ajax({
            method: "POST",
            url: "/giang-vien/doi-mat-khau",
            data: {username: username, password: password},
            success: function(data) {}
        });
    }
}