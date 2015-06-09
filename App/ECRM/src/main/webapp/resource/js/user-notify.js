/**
 * Created by ChiDNMSE60717 on 6/7/2015.
 */
var damagedEquipments = {};
var noDamagedEquipments = {};

function showMapForReport(mapId, equipments, typeId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var array = [];
    var o = new Object();
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;
    var classroommap = document.getElementById(mapId);

    classroommap.innerHTML ="";

    var container = document.createElement('div');
    container.className = 'map-container';
    classroommap.appendChild(container);

    var divT = document.createElement('div');
    divT.className = 'thietBi';
    container.appendChild(divT);
    //tao div map
    var div = document.createElement('div');
    div.className = 'map';
    container.appendChild(div);

    //tao div bang
    var divBang = document.createElement('div');
    divBang.className = 'bang';
    div.appendChild(divBang);
    var divScreen = document.createElement('div');
    divScreen.className = 'board';
    divScreen.setAttribute("data-equipmentName","Bảng");
    divScreen.innerHTML = 'Bảng';
    divBang.appendChild(divScreen);
    for (var i = 0; i < vrows; i++) {
        o = {
            soDay: horizontalRows[i],
            soChoNgoi: noSlotsEachRows[i]
        }
        array[i] = o;
    }

    //tao div con chua map
    var subDiv = document.createElement('div');
    subDiv.className = 'subDiv';
    div.appendChild(subDiv);
    var position;
    //chay vong for day doc
    for (var i = 0; i < vrows; i++) {
        var a = 0;
        //tao div day doc
        var divDay = document.createElement('div');
        divDay.className = 'day';
        subDiv.appendChild(divDay);

        //chay vong for so day ngang moi day doc
        for (var j = 0; j <= array[i].soDay; j++) {
            //tao ban giao vien
            if (j === 0) {
                if(i == 0) {
                    var divBanGheGV = document.createElement('div');
                    var divBanGV = document.createElement('div');
                    position = '[' + i + ',' + j + ']';

                    if(equipments[position].status == "DAMAGED") {
                        divBanGV.className = 'banGV damaged';
                    } else {
                        divBanGV.className = 'equipment banGV';
                    }
                    divBanGV.setAttribute('data-equipmentName', 'Bàn');
                    divBanGV.setAttribute("data-equipmentId", equipments[position].id);
                    divBanGV.setAttribute("data-categoryId", equipments[position].category);
                    divBanGV.setAttribute("data-status", equipments[position].status);

                    var divDayGheGV = document.createElement('div');
                    divDayGheGV.className = 'divDayGhe';
                    var divGheGV = document.createElement('div');
                    position = '[' + i + ',' + j + ',0]';

                    if(equipments[position].status == "DAMAGED") {
                        divGheGV.className = 'ghe damaged';
                    } else {
                        divGheGV.className = 'equipment ghe';
                    }
                    divGheGV.setAttribute('data-equipmentName', 'Ghế');
                    divGheGV.setAttribute("data-equipmentId", equipments[position].id);
                    divGheGV.setAttribute("data-categoryId", equipments[position].category);
                    divGheGV.setAttribute("data-status", equipments[position].status);

                    divDay.appendChild(divBanGheGV);
                    divBanGheGV.appendChild(divDayGheGV);
                    divBanGheGV.appendChild(divBanGV);
                    divDayGheGV.appendChild(divGheGV);
                } else {
                    var divBanGheGV = document.createElement('div');
                    divBanGheGV.style.height = 70 + "px";
                    divDay.appendChild(divBanGheGV);
                }
            } else {
                chieuDaiBan = 0;
                var divBanGhe = document.createElement('div');
                var divBan = document.createElement('div');
                position = '[' + i + ',' + j + ']';

                if(equipments[position].status == "DAMAGED") {
                    divBan.className = 'ban damaged';
                } else {
                    divBan.className = 'equipment ban';
                }
                divBan.setAttribute('data-equipmentName','Bàn');
                divBan.setAttribute("data-equipmentId", equipments[position].id);
                divBan.setAttribute("data-categoryId", equipments[position].category);
                divBan.setAttribute("data-status", equipments[position].status);

                var divDayGhe = document.createElement('div');
                divDayGhe.className = 'divDayGhe';
                divDay.appendChild(divBanGhe);
                divBanGhe.appendChild(divBan);
                divBanGhe.appendChild(divDayGhe);

                //chay vong for so cho ngoi moi ban
                for (var k = 0; k < array[i].soChoNgoi; k++) {
                    var divGhe = document.createElement('div');
                    position = '[' + i + ',' + j + ',' + k + ']';

                    if(equipments[position].status == "DAMAGED") {
                        divGhe.className = 'ghe damaged';
                    } else {
                        divGhe.className = 'equipment ghe';
                    }
                    divGhe.setAttribute('data-equipmentName','Ghế');
                    divGhe.setAttribute("data-equipmentId", equipments[position].id);
                    divGhe.setAttribute("data-categoryId", equipments[position].category);
                    divGhe.setAttribute("data-status", equipments[position].status);

                    divDayGhe.appendChild(divGhe);
                    chieuDaiBan += 34;
                }
                divBan.style.width = chieuDaiBan - 4 + "px";
            }
        }
        chieuDaiSubDiv += chieuDaiBan;
    }

    chieuDaiSubDiv = chieuDaiSubDiv + 15 * (array.length) - 15;
    subDiv.style.width = chieuDaiSubDiv + "px";
    if (chieuDaiSubDiv > 400) {
        divBang.style.width = chieuDaiSubDiv + "px";
    } else {
        divBang.style.width = "100%";
    }

    //projector
    if(projector>0){
        var divProjector = document.createElement('div');
        if(equipments["[1]"].status == "DAMAGED") {
            divProjector.className = 'subThietBi projector damaged';
        } else {
            divProjector.className = 'equipment subThietBi projector';
        }
        divProjector.setAttribute('data-equipmentName','Máy chiếu');
        divProjector.setAttribute("data-equipmentId", equipments["[1]"].id);
        divProjector.setAttribute("data-categoryId", equipments["[1]"].category);
        divProjector.setAttribute("data-status", equipments["[1]"].status);
        divT.appendChild(divProjector);
    }

    //tivi
    if(tivi>0){
        var divTivi = document.createElement('div');
        if(equipments["[2]"].status == "DAMAGED") {
            divTivi.className = 'subThietBi tivi damaged';
        } else {
            divTivi.className = 'equipment subThietBi tivi';
        }
        divTivi.setAttribute('data-equipmentName','Tivi');
        divTivi.setAttribute("data-equipmentId", equipments["[2]"].id);
        divTivi.setAttribute("data-categoryId", equipments["[2]"].category);
        divTivi.setAttribute("data-status", equipments["[2]"].status);
        divT.appendChild(divTivi);
    }

    //may lanh
    if(mayLanh>0){
        var divMayLanh = document.createElement('div');
        if(equipments["[3]"].status == "DAMAGED") {
            divMayLanh.className = 'subThietBi mayLanh damaged';
        } else {
            divMayLanh.className = 'equipment subThietBi mayLanh';
        }
        divMayLanh.setAttribute('data-equipmentName','Máy lạnh');
        divMayLanh.setAttribute("data-equipmentId", equipments["[3]"].id);
        divMayLanh.setAttribute("data-categoryId", equipments["[3]"].category);
        divMayLanh.setAttribute("data-status", equipments["[3]"].status);
        divT.appendChild(divMayLanh);
    }

    //quat
    if(quat>0){
        var divQuat = document.createElement('div');
        if(equipments["[4]"].status == "DAMAGED") {
            divQuat.className = 'subThietBi quat damaged';
        } else {
            divQuat.className = 'equipment subThietBi quat';
        }

        divQuat.setAttribute('data-equipmentName','Quạt');
        divQuat.setAttribute("data-equipmentId", equipments["[4]"].id);
        divQuat.setAttribute("data-categoryId", equipments["[4]"].category);
        divQuat.setAttribute("data-status", equipments["[4]"].status);
        divT.appendChild(divQuat);
    }

    //loa
    if(loa>0){
        var divLoa = document.createElement('div');
        if(equipments["[5]"].status == "DAMAGED") {
            divLoa.className = 'subThietBi loa damaged';
        } else {
            divLoa.className = 'equipment subThietBi loa';
        }
        divLoa.setAttribute('data-equipmentName','Loa');
        divLoa.setAttribute("data-equipmentId", equipments["[5]"].id);
        divLoa.setAttribute("data-categoryId", equipments["[5]"].category);
        divLoa.setAttribute("data-status", equipments["[5]"].status);
        divT.appendChild(divLoa);
    }
}

function setChooseEquipment(map){
    $("#" + map +" .equipment").click(function(){
        if($(this).attr("data-choose") === "true"){
            $(this).removeClass("choose");
            $(this).removeAttr("data-choose");
            delete damagedEquipments[$(this).attr("data-equipmentId")];
            noDamagedEquipments[$(this).attr("data-categoryId")].size--;
            if(noDamagedEquipments[$(this).attr("data-categoryId")].size == 0) {
                document.getElementById("category-" + $(this).attr("data-categoryId")).className = "group-control hidden";
                document.getElementById("list-equipment").removeChild(document.getElementById("equipment-" + $(this).attr("data-categoryId")));
            } else {
                document.getElementById("equipment-" + $(this).attr("data-categoryId")).innerHTML = $(this).attr("data-equipmentName") +"(" + noDamagedEquipments[$(this).attr("data-categoryId")].size + ")";
            }
        } else {
            $(this).addClass("choose");
            $(this).attr("data-choose", "true");
            damagedEquipments[$(this).attr("data-equipmentId")] = $(this).attr("data-equipmentId");
            var contentDiv = document.getElementById("equipment-" + $(this).attr("data-categoryId"));
            if(contentDiv != undefined && contentDiv!= null) {
                noDamagedEquipments[$(this).attr("data-categoryId")].size++;
                contentDiv.innerHTML = $(this).attr("data-equipmentName") +"(" + noDamagedEquipments[$(this).attr("data-categoryId")].size + ")";
            } else {
                noDamagedEquipments[$(this).attr("data-categoryId")] = {size: 1, evaluate: 3};
                document.getElementById("category-" + $(this).attr("data-categoryId")).className = "group-control";
                document.getElementById("list-equipment").innerHTML += "<span id='equipment-" + $(this).attr("data-categoryId") + "'>" + $(this).attr("data-equipmentName") + "(1) </span>";
            }
        }
    });
}

function checkEquipment(){
    if(Object.keys(damagedEquipments).length > 0){
        showModal(2, 'modal-1', 'modal-2');
    } else {
        alert("Bạn phải chọn ít nhất 1 thiết bị!");
    }
}

function changeEvaluate(category, thisElement) {
    noDamagedEquipments[category].evaluate = thisElement.value;

}

function submitForm(){
    var listEquipment = [];
    var listEvaluate = [];
    var i = 0;

    for (var key in damagedEquipments) {
        if (damagedEquipments.hasOwnProperty(key)){
            listEquipment[i] = damagedEquipments[key];
            i++;
        }
    }

    i = 0;
    for (var key in noDamagedEquipments) {
        if (noDamagedEquipments.hasOwnProperty(key)){
            listEvaluate[i] = key + "-" + noDamagedEquipments[key].evaluate;
            i++;
        }
    }

    document.getElementById("list-damaged").value = listEquipment;
    document.getElementById("list-evaluate").value = listEvaluate;
    document.getElementById("create-report").submit();
}

function loadReportHistory(reportId, roomId){
    $.ajax({
        method: "GET",
        url: "/giang-vien/viewHistory",
        data: { ReportId: reportId, RoomId: roomId },
        success: function (result) {
            $("#report-history").html(result);
            $("#list-equipment-history").text($("#list-"+reportId).html());
        }
    });
}