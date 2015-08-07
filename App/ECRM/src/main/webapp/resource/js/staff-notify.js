/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */

function showReportDetail(roomId){
    waitLoading();
    var room = $(".content-all-modal").attr("data-room");
    window.history.pushState({},"", "/thong-bao/hu-hai?phong=" + roomId);

    if(room != roomId) {
        $.ajax({
            method: "GET",
            url: "/bao-cao/chi-tiet",
            data: {roomId: roomId},
            success: function(result) {
                $(".content-all-modal").html(result);
                $(".content-all-modal script").remove();
                $(".content-all-modal").attr("data-room", roomId);
                closeLoading();
                showModal(1,'modal-1');
            }
        });
    } else {
        closeLoading();
        showModal(1,'modal-1');
    }
}

function sendResolve() {
    var listCate = "";
    var listEquip = "";

    for (var key in listResolve) {
        if (listResolve.hasOwnProperty(key) && listResolve[key].status == true){
            listCate += key + ",";

            var equipInCate = listResolve[key].equips;
            for(var key2 in equipInCate) {
                if(equipInCate.hasOwnProperty(key2)) {
                    listEquip += key + "-" + key2 + ",";
                }
            }
        }
    }

    if(listCate.length > 0) {
        document.getElementById("ListResolve").value = listCate.substring(0, listCate.length-1);
        document.getElementById("ListEquip").value = listEquip.substring(0, listEquip.length-1);
        document.getElementById("resolveForm").submit();
    } else {
        conformData(1, {message: "Bạn cần chọn ít nhất 1 thiết bị!"});
        $(".loading-page").removeClass("active");
    }
}

function chooseRealEquipment(equip, cate, checkBox){
    var category = listResolve[cate];
    if(category.equips[equip] != undefined) {
        delete category.equips[equip];
        checkBox.className = "real-equipment";
    } else {
        category.equips[equip] = true;
        checkBox.className += " active";
    }
}

function changeRoom() {
    waitLoading();
    $.ajax({
        method: "GET",
        url: "/bao-cao/doi-phong",
        data: {currentClassroom: $("#current-room").val(), changeClassroom: $("#new-room").val()},
        success: function(result) {
            $(".change-room-text").html("<div class='value' style='color:darkorange'><span>Đã đổi phòng!</span> Phòng hiện đang trống</div>");
            setTimeout(function(){
                $(".change-room-text .value").css("color", "black");
                $(".change-room-text .value span").remove();
            }, 10000);
            closeLoading();
            showModal(2, 'modal-3', 'modal-1');
        }
    });
}

function showMap(mapId, equipments, vrows, sDayNgang, sChoNgoi, listEquipment) {
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

                    if(equipments[position] != undefined && equipments[position].status == false) {
                        divBanGV.className = 'banGV damaged';
                    } else {
                        divBanGV.className = 'equipment banGV';
                    }
                    divBanGV.setAttribute('data-equipmentName', 'Bàn');
                    divBanGV.setAttribute("data-position", position);
                    divBanGV.setAttribute("data-categoryId", "7");

                    var divDayGheGV = document.createElement('div');
                    divDayGheGV.className = 'divDayGhe';
                    var divGheGV = document.createElement('div');
                    position = '[' + i + ',' + j + ',0]';

                    if(equipments[position] != undefined && equipments[position].status == false) {
                        divGheGV.className = 'ghe damaged';
                    } else {
                        divGheGV.className = 'equipment ghe';
                    }
                    divGheGV.setAttribute('data-equipmentName', 'Ghế');
                    divGheGV.setAttribute("data-position", position);
                    divGheGV.setAttribute("data-categoryId", "8");

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

                if(equipments[position] != undefined && equipments[position].status == false) {
                    divBan.className = 'ban damaged';
                } else {
                    divBan.className = 'equipment ban';
                }
                divBan.setAttribute('data-equipmentName','Bàn');
                divBan.setAttribute("data-position", position);
                divBan.setAttribute("data-categoryId", "7");

                var divDayGhe = document.createElement('div');
                divDayGhe.className = 'divDayGhe';
                divDay.appendChild(divBanGhe);
                divBanGhe.appendChild(divBan);
                divBanGhe.appendChild(divDayGhe);

                //chay vong for so cho ngoi moi ban
                for (var k = 0; k < array[i].soChoNgoi; k++) {
                    var divGhe = document.createElement('div');
                    position = '[' + i + ',' + j + ',' + k + ']';

                    if(equipments[position] != undefined && equipments[position].status == false) {
                        divGhe.className = 'ghe damaged';
                    } else {
                        divGhe.className = 'equipment ghe';
                    }
                    divGhe.setAttribute('data-equipmentName','Ghế');
                    divGhe.setAttribute("data-position", position);
                    divGhe.setAttribute("data-categoryId", "8");

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

    for(var i = 0; i< listEquipment.length; i++){
        var divEquip = document.createElement('div');
        if(equipments["["+ listEquipment[i].id+"]"] != undefined) {
            divEquip.className = 'subThietBi equip damaged';
        } else {
            divEquip.className = 'equipment subThietBi equip';
        }
        divEquip.style.backgroundImage = 'url("/resource/img/equipment/' + listEquipment[i].imageUrl + '")';
        divEquip.setAttribute('data-equipment', listEquipment[i].name);
        divEquip.setAttribute('data-position','['+ listEquipment[i].id+']');
        divEquip.setAttribute("data-categoryId", listEquipment[i].id);
        divT.appendChild(divEquip);
    }
}