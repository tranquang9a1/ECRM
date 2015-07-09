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

function conformData(message) {
    var body = document.getElementsByTagName("body").item(0);
    var conformDiv = document.createElement("div");
    conformDiv.className = "conform";
    var contentDiv = document.createElement("div");
    contentDiv.className = "content-conform";

    var titleDiv = document.createElement("div");
    titleDiv.className = "title-conform";
    titleDiv.innerHTML = message;

    var btnConform = document.createElement("input");
    btnConform.className = "btn btn-orange";
    btnConform.type = "button";
    btnConform.value = "Đóng";
    btnConform.addEventListener("click", closeConformData, false);

    contentDiv.appendChild(titleDiv);
    contentDiv.appendChild(btnConform);

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

function createTimes(){
    var element = document.getElementById("list-hour");
    var hour = 6;

    for(var i = 0; i < 14; i++) {
        var div = document.createElement("div");
        div.className = "hour-item";
        div.style.top = (i*60)+"px";
        div.innerHTML = (hour+0) + ":00";
        element.appendChild(div);
        hour++;
    }

   updateTime();

}

function updateTime() {
    var d = new Date();
    var nowHour = d.getHours();
    var nowMinute = d.getMinutes();
    var time = ((nowHour - 6) * 60) + nowMinute - 832;
    document.getElementById("now-time").style.top  = time + "px";
    document.getElementById("time-here").innerHTML = nowHour + ":" + nowMinute;

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

function changePage(page){
    $(".loading-page").addClass("active");

    setTimeout(function(){
        $(".page").removeClass("active");
        $("#" + page).addClass("active");
        $(".loading-page").removeClass("active");
    }, 300);
}