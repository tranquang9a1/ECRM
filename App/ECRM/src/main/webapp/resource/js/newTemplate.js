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
            btnConform.className = "btn btn-orange";
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
    }
}



function createTimes(){
    var elementHour = document.getElementById("list-hour");
    var elementSchedule = document.getElementById("list-schedule");
    var hour = 6;

    for(var i = 0; i < 14; i++) {
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
            div.innerHTML = "Phòng " + listSchedule[key].room;
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
    var time = ((nowHour - 6) * 60) + nowMinute - 832;
    document.getElementById("now-time").style.top  = time + "px";
    document.getElementById("time-here").innerHTML = (nowHour<10?'0'+nowHour:nowHour) + ":" + (nowMinute<10?'0'+nowMinute:nowMinute);

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

    setTimeout(function() {updateTime()}, 6000);
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

function loadRoomEquipment(listDamage, mayLanh, quat, projector, loa, tivi){
    var listEquipment = new Array();
    if(projector > 0) {
        listEquipment.push({id: 1, classname: "projector", name: "Projector", message: (listDamage[1]==false?"Đã được báo cáo":"")});
    }
    if(mayLanh > 0) {
        listEquipment.push({id: 3, classname: "air-condition", name: "AirCondition", message: (listDamage[3]==false?"Đã được báo cáo":"")});
    }
    if(loa > 0) {
        listEquipment.push({id: 5, classname: "speaker", name: "Speaker", message: (listDamage[5]==false?"Đã được báo cáo":"")});
    }
    if(tivi > 0) {
        listEquipment.push({id: 2, classname: "tivi", name: "Television", message: (listDamage[2]==false?"Đã được báo cáo":"")});
    }
    if(quat > 0) {
        listEquipment.push({id: 4, classname: "fan", name: "Fan", message: (listDamage[4]==false?"Đã được báo cáo":"")});
    }
    listEquipment.push({id: 7, classname: "table-icon", name: "Table", message: (listDamage[7]==false?"Đã được báo cáo":"")});
    listEquipment.push({id: 8, classname: "chair", name: "Chair", message: (listDamage[8]==false?"Đã được báo cáo":"")});

    var contentDiv = $(".data-room-content")[0];
    for(var i = 0; i < listEquipment.length; i++) {
        var img = document.createElement("div");
        img.className = "equipment-img " + listEquipment[i].classname;
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



function changeTabInfo(type, element){
    $(".title-tab").removeClass("active");
    $(element).addClass("active");
    if(type == 1) {
        $('.main-info').addClass('active');
        $('.map-info').removeClass('active');
    } else {
        $('.main-info').removeClass('active');
        $('.map-info').addClass('active');
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
    window.history.pushState({},"", "/bao-cao/hu-hai?phong=" + roomId);

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

function resetURL() {
    window.history.pushState({},"", "/bao-cao");
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
