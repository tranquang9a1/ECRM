/**
 * Created by Htang on 5/30/2015.
 */
$("#selectBox").change(function () {
    var selectedValue = $(this).find(":selected").data("value");
    document.getElementById('loaiphong').innerHTML = selectedValue.id;
    document.getElementById('roomtype').value = selectedValue.id;
    showRoomtypeMapByDropDownList(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
        , selectedValue.airConditioning, selectedValue.fan, selectedValue.projector, selectedValue.speaker,
        selectedValue.television);
});
function showMapForEdit(mapId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
    document.getElementById('roomtypename').innerHTML = "Loai " + mapId;
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementById('roomtype-map');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }
    generateMap(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, mayLanh, quat, projector, loa, tivi);
    if(projector){
        document.getElementById('projector').checked = true;
    }else {
        document.getElementById('projector').checked = false;
    }
    if(quat){
        document.getElementById('quat').checked = true;
    }else {
        document.getElementById('quat').checked = false;
    }
    if(mayLanh){
        document.getElementById('mayLanh').checked = true;
    }else {
        document.getElementById('mayLanh').checked = false;
    }
    if(tivi){
        document.getElementById('tivi').checked = true;
    }else {
        document.getElementById('tivi').checked = false;
    }
    if(loa){
        document.getElementById('loa').checked = true;
    }else {
        document.getElementById('loa').checked = false;
    }
    document.getElementById('vrow').selectedIndex = vrows -1;
    createDetailMap(horizontalRows, noSlotsEachRows);

}

function showRoomtypeMapByDropDownList(mapId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {

    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementById('roomtype-map2');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }


    generateMap(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, mayLanh, quat, projector, loa, tivi);
}
function generateMap(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, mayLanh, quat, projector, loa, tivi) {

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

    //projector
    if(projector){
        var divProjector = document.createElement('div');
        divProjector.className = 'equipment subThietBi projector';
        divProjector.setAttribute('data-equipment','Máy chiếu');
        divProjector.setAttribute('data-position','[1]');
        divT.appendChild(divProjector);
    }

    //tivi
    if(tivi){
        var divTivi = document.createElement('div');
        divTivi.className = 'equipment subThietBi tivi';
        divTivi.setAttribute('data-equipment','Tivi');
        divTivi.setAttribute('data-position','[2]');
        divT.appendChild(divTivi);
    }

    //may lanh
    if(mayLanh){
        var divMayLanh = document.createElement('div');
        divMayLanh.className = 'equipment subThietBi mayLanh';
        divMayLanh.setAttribute('data-equipment','Máy lạnh');
        divMayLanh.setAttribute('data-position','[3]');
        divT.appendChild(divMayLanh);
    }

    //quat
    if(quat){
        var divQuat = document.createElement('div');
        divQuat.className = 'equipment subThietBi quat';
        divQuat.setAttribute('data-equipment','Quạt');
        divQuat.setAttribute('data-position','[4]');
        divT.appendChild(divQuat);
    }

    //loa
    if(loa){
        var divLoa = document.createElement('div');
        divLoa.className = 'equipment subThietBi loa';
        divLoa.setAttribute('data-equipment','Loa');
        divLoa.setAttribute('data-position','[5]');
        divT.appendChild(divLoa);
    }
}

