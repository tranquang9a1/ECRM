/**
 * Created by Htang on 5/30/2015.
 */
$("#selectBox").change(function () {
    var selectedValue = $(this).find(":selected").data("value");
    document.getElementById('loaiphong').innerHTML = selectedValue.id;
    document.getElementById('roomtype').value = selectedValue.id;
    showMap(selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
        , selectedValue.airConditioning, selectedValue.fan, selectedValue.projector, selectedValue.speaker,
        selectedValue.television);
});
function showMapForEdit(mapId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
    document.getElementById('roomtypename').innerHTML = "Loai " + mapId;
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementsByClassName('classroom-map')[1];

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

function showMap(mapId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {

    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementsByClassName('classroom-map')[2];

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
    container.id = mapId;
    container.className = 'map-container';
    classroommap.appendChild(container);


    var divT = document.createElement('div');
    divT.className = 'thietBi';
    container.appendChild(divT);
    //tao div map
    var div = document.createElement('div');
    div.className = 'map';
    div.id = 'idMap';
    container.appendChild(div);
    div.style.height = "300px";
    //tao div bang
    var divBang = document.createElement('div');
    divBang.className = 'bang';
    divBang.id = 'idBang';
    div.appendChild(divBang);
    var divScreen = document.createElement('div');
    divScreen.className = 'board';
    divScreen.innerHTML = 'Board';
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
    subDiv.id = 'idSubDiv';
    div.appendChild(subDiv);
    //chay vong for day doc
    for (var i = 0; i < vrows; i++) {
        var a = 0;
        //tao div day doc
        var divDay = document.createElement('div');
        divDay.id = 'day' + i;
        divDay.className = 'day';
        subDiv.appendChild(divDay);
        //tao ban giao vien

        //chay vong for so day ngang moi day doc
        for (var j = 0; j < array[i].soDay; j++) {
            //tao ban giao vien
            if (j === 0) {
                var divBanGheGV = document.createElement('div');
                var divBanGV = document.createElement('div');
                divBanGV.id = 'banGV' + i;
                divBanGV.className = 'banGV';
                var divDayGheGV = document.createElement('div');
                divDayGheGV.className = 'divDayGhe';
                var divGheGV = document.createElement('div');
                divGheGV.id = 'gheGV' + i;
                divGheGV.className = 'ghe';
                divDay.appendChild(divBanGheGV);
                divBanGheGV.appendChild(divDayGheGV);
                divBanGheGV.appendChild(divBanGV);
                divDayGheGV.appendChild(divGheGV);

            }
            if (i != 0) {
                document.getElementById('banGV' + i).style.visibility = 'hidden';
                document.getElementById('gheGV' + i).style.visibility = 'hidden';
            }
            chieuDaiBan = 0;
            var divBanGhe = document.createElement('div');
            var divBan = document.createElement('div');
            divBan.id = 'ban' + i + 'day' + j;
            divBan.className = 'ban';
            var divDayGhe = document.createElement('div');
            divDayGhe.className = 'divDayGhe';
            divDay.appendChild(divBanGhe);
            divBanGhe.appendChild(divBan);
            divBanGhe.appendChild(divDayGhe);
            //chay vong for so cho ngoi moi ban
            for (var k = 0; k < array[i].soChoNgoi; k++) {
                var divGhe = document.createElement('div');
                divGhe.id = 'ban' + i + 'day' + j + 'ghe' + k;
                divGhe.className = 'ghe';
                divDayGhe.appendChild(divGhe);
                chieuDaiBan += 34;
            }
            if (j == (array.length - 1)) {
                document.getElementById('ban' + i + 'day' + j).style.marginRight = '0px';

            }
            document.getElementById('ban' + i + 'day' + j).style.width = chieuDaiBan - 4 + "px";
        }
        if (i == (vrows - 1)) {
            document.getElementById('day' + i).style.marginRight = '0px';

        }
        chieuDaiSubDiv += chieuDaiBan;


    }
    chieuDaiSubDiv = chieuDaiSubDiv + 15 * (array.length) - 15;
    document.getElementById('idSubDiv').style.width = chieuDaiSubDiv + "px";
    var objDiv = document.getElementById("idMap");
    objDiv.scrollLeft = chieuDaiSubDiv * 1 / 2;
    if (chieuDaiSubDiv > 400) {
        document.getElementById('idBang').style.width = chieuDaiSubDiv + "px";
    } else {
        document.getElementById('idBang').style.width = "100%";
    }
    //projector
    if (projector) {
        var divProjector = document.createElement('div');
        divProjector.id = 'idProjector';
        divProjector.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divProjector);
    }

    //tivi
    if (tivi) {
        var divTivi = document.createElement('div');
        divTivi.id = 'idTivi';
        divTivi.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divTivi);
    }

    //may lanh
    if (mayLanh) {
        var divMayLanh = document.createElement('div');
        divMayLanh.id = 'idMayLanh';
        divMayLanh.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divMayLanh);
    }

    //quat
    if (quat) {
        var divQuat = document.createElement('div');
        divQuat.id = 'idQuat';
        divQuat.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divQuat);
    }

    //loa
    if (loa) {
        var divLoa = document.createElement('div');
        divLoa.id = 'idLoa';
        divLoa.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divLoa);
    }
}

