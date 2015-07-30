/**
 * Created by Htang on 5/30/2015.
 */
$("#selectBox").change(function () {
    var selectedValue = $(this).find(":selected").data("value");
    document.getElementById('loaiphong').innerHTML = selectedValue.name;
    document.getElementById('roomtype').value = selectedValue.id;
    showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
        , selectedValue.equipment);
});
function showMapForEdit(mapId, name, vrows, sDayNgang, sChoNgoi, r) {
    document.getElementsByClassName('roomtypename')[0].innerHTML = name;
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementById('roomtype-map');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }
    generateMap2(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, r);
    document.getElementById('name').value = name;
    document.getElementById('vrow').selectedIndex = vrows -1;
    createDetailMap(horizontalRows, noSlotsEachRows);
    document.getElementsByClassName('roomtypename')[1].innerHTML = name;
    document.getElementById('RoomtypeId').value = mapId;
    document.getElementById('title-roomtype-map').innerHTML = "Cập nhật " +name;
    document.getElementById('button-create-roomtype').value = "Cập nhật";
    document.getElementById('roomTypeName').value = name;
}

function showRoomtypeMapByDropDownList(mapId, vrows, sDayNgang, sChoNgoi, equipment) {

    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementById('roomtype-map2');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }

    generateMap2(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, equipment);
}

function generateMap2(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows,r) {

    var array = [];
    var o = new Object();
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;

    var container = document.createElement('div');
    container.id = "roomtype-" + mapId;
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
    divScreen.className = 'equipment board';
    divScreen.setAttribute("data-equipment","Bảng");
    divScreen.setAttribute("data-position","[0]");
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
    //chay vong for day doc
    for (var i = 0; i < vrows; i++) {
        var a = 0;
        //tao div day doc
        var divDay = document.createElement('div');
        divDay.id = 'day' + i;
        divDay.className = 'day';
        subDiv.appendChild(divDay);

        //chay vong for so day ngang moi day doc
        for (var j = 0; j <= array[i].soDay; j++) {
            //tao ban giao vien
            if (j === 0) {
                if(i == 0) {
                    var divBanGheGV = document.createElement('div');
                    var divBanGV = document.createElement('div');
                    divBanGV.className = 'equipment banGV';
                    divBanGV.setAttribute('data-equipment', 'Bàn');
                    divBanGV.setAttribute('data-position', '[' + i + ',' + j + ']');
                    var divDayGheGV = document.createElement('div');
                    divDayGheGV.className = 'divDayGhe';
                    var divGheGV = document.createElement('div');
                    divGheGV.className = 'equipment ghe';
                    divGheGV.setAttribute('data-equipment', 'Ghế');
                    divGheGV.setAttribute('data-position', '[' + i + ',' + j + ',0]');
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
                divBan.className = 'equipment ban';
                divBan.setAttribute('data-equipment','Bàn');
                divBan.setAttribute('data-position','[' + i + ',' + j + ']');
                var divDayGhe = document.createElement('div');
                divDayGhe.className = 'divDayGhe';
                divDay.appendChild(divBanGhe);
                divBanGhe.appendChild(divBan);
                divBanGhe.appendChild(divDayGhe);
                //chay vong for so cho ngoi moi ban
                for (var k = 0; k < array[i].soChoNgoi; k++) {
                    var divGhe = document.createElement('div');
                    divGhe.className = 'equipment ghe';
                    divGhe.setAttribute('data-equipment','Ghế');
                    divGhe.setAttribute('data-position','[' + i + ',' + j + ',' + k + ']');
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
    div.scrollLeft = chieuDaiSubDiv * 1 / 2;
    if (chieuDaiSubDiv > 400) {
        divBang.style.width = chieuDaiSubDiv + "px";
    } else {
        divBang.style.width = "100%";
    }


    for(var i = 0; i< r.length; i++){
        var divProjector = document.createElement('div');
        divProjector.className = 'equipment subThietBi equip';
        divProjector.style.backgroundImage = 'url("../resource/img/equipment/' + r[i].imageUrl + '")';
        divProjector.setAttribute('data-equipment',r[i].name);
        divProjector.setAttribute('data-position','['+ r[i].id+']');
        divT.appendChild(divProjector);
    }
}


