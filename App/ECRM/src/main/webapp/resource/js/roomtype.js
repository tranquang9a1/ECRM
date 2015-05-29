/**
 * Created by ChiDNMSE60717 on 5/26/2015.
 */

var soDayNgang = [];
var soChoNgoi = [];
var o = new Object();
var array = [];
var slots = 0;

function changeFunc() {
    var selectBox = document.getElementById("selectBox");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    alert(selectedValue);
    document.getElementById('loaiphong').innerHTML = selectedValue;
    document.getElementById('roomtype').value = selectedValue;
}

function createDetailMap() {
    var colElement = document.getElementById("vrow");
    var col = colElement.options[colElement.selectedIndex].value;
    var row1 = document.getElementById("row-1");
    row1.innerHTML = "";
    row1.style.width = col*115;
    var row2 = document.getElementById("row-2");
    row2.innerHTML = "";
    row2.style.width = col*115;
    var row3 = document.getElementById("row-3");
    row3.innerHTML = "";
    row3.style.width = col*115;

    for (var i = 0; i < col; i++) {
        var item1 = document.createElement("div");
        item1.className = "item";
        item1.innerHTML = "Dãy " + (i+1);
        row1.appendChild(item1);

        var item2 = document.createElement("div");
        item2.className = "item";
        var select1 = createSelect("SoDayNgang" + (i+1));
        item2.appendChild(select1);
        row2.appendChild(item2);

        var item3 = document.createElement("div");
        item3.className = "item";
        var select2 = createSelect("SoChoNgoi" + (i+1));
        item3.appendChild(select2);
        row3.appendChild(item3);

        soDayNgang[i] = "SoDayNgang" + (i+1);
        soChoNgoi[i] = "SoChoNgoi" + (i+1);
    }

    var clearDiv = document.createElement("div");
    clearDiv.className = "clear";
    row1.appendChild(clearDiv);
    row2.appendChild(clearDiv);
    row3.appendChild(clearDiv);
}

function createSelect(id){
    var selectList = document.createElement("select");
    selectList.id = id;

    for (var i = 1; i <= 10; i++) {
        var option = document.createElement("option");
        option.value = i;
        option.text = i;
        selectList.appendChild(option);
    }

    return selectList;
}

function viewMap() {
    var vrows = document.getElementById("vrow").value;
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;
    var container = document.getElementById('idContainer');
    while(container.hasChildNodes()){
        container.removeChild(container.lastChild);
    }
    var divT = document.createElement('div');
    divT.className = 'thietBi';
    container.appendChild(divT);
    //tao div map
    var div = document.createElement('div');
    div.className = 'map';
    div.id = 'idMap';
    container.appendChild(div);
    //tao div bang
    var divBang = document.createElement('div');
    divBang.className = 'bang';
    divBang.id = 'idBang';
    div.appendChild(divBang);
    var divScreen = document.createElement('div');
    divScreen.className = 'board';
    divScreen.innerHTML='Board';
    divBang.appendChild(divScreen);
    for (var i = 0; i < vrows; i++) {
        var soDay = document.getElementById(soDayNgang[i]).value;
        var soCho = document.getElementById(soChoNgoi[i]).value;
        o = {
            soDay: soDay,
            soChoNgoi: soCho
        }
        array[i] = o;
        slots += soDay*soCho;
    }

    //tao div con chua map
    var subDiv = document.createElement('div');
    subDiv.className = 'subDiv';
    subDiv.id='idSubDiv';
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
            if(j===0){
                var divBanGheGV = document.createElement('div');
                var divBanGV = document.createElement('div');
                divBanGV.id = 'banGV'+i;
                divBanGV.className = 'banGV';
                var divDayGheGV = document.createElement('div');
                divDayGheGV.className = 'divDayGhe';
                var divGheGV = document.createElement('div');
                divGheGV.id = 'gheGV'+i;
                divGheGV.className = 'ghe';
                divDay.appendChild(divBanGheGV);
                divBanGheGV.appendChild(divDayGheGV);
                divBanGheGV.appendChild(divBanGV);
                divDayGheGV.appendChild(divGheGV);

            }
            if(i!=0){
                document.getElementById('banGV'+i).style.visibility='hidden';
                document.getElementById('gheGV'+i).style.visibility='hidden';
            }

            //
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
            if(j==(array.length-1)){
                document.getElementById('ban' + i + 'day' + j).style.marginRight='0px';

            }
            document.getElementById('ban' + i + 'day' + j).style.width = chieuDaiBan -4 + "px";
        }
        if(i==(vrows -1)){
            document.getElementById('day'+i).style.marginRight='0px';

        }
        chieuDaiSubDiv  += chieuDaiBan;


    }
    chieuDaiSubDiv = chieuDaiSubDiv + 15*(array.length) -15;
    document.getElementById('idSubDiv').style.width = chieuDaiSubDiv + "px";
    var objDiv = document.getElementById("idMap");
    objDiv.scrollLeft= chieuDaiSubDiv*1/2;
    if(chieuDaiSubDiv>400){
        document.getElementById('idBang').style.width = chieuDaiSubDiv + "px";
    }else{
        document.getElementById('idBang').style.width = "100%";
    }
    //projector
    if(document.getElementById('projector').checked){
        var divProjector = document.createElement('div');
        divProjector.id = 'idProjector';
        divProjector.className='subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divProjector);
        document.getElementById("Projectors").value = true;
    }else{
        document.getElementById("Projectors").value = false;
    }
    //tivi
    if(document.getElementById('tivi').checked){
        var divTivi = document.createElement('div');
        divTivi.id = 'idTivi';
        divTivi.className='subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divTivi);
        document.getElementById("Television").value = true;
    }else{
        document.getElementById("Television").value = false;
    }
    //may lanh
    if(document.getElementById('mayLanh').checked){
        var divMayLanh = document.createElement('div');
        divMayLanh.id = 'idMayLanh';
        divMayLanh.className='subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divMayLanh);
        document.getElementById("AirConditioning").value = true;
    }else{
        document.getElementById("AirConditioning").value = false;
    }
    //quat
    if(document.getElementById('quat').checked){
        var divQuat = document.createElement('div');
        divQuat.id = 'idQuat';
        divQuat.className='subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divQuat);
        document.getElementById("Fan").value = true;
    }else{
        document.getElementById("Fan").value = false;
    }
    //loa
    if(document.getElementById('loa').checked){
        var divLoa = document.createElement('div');
        divLoa.id = 'idLoa';
        divLoa.className='subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divLoa);
        document.getElementById("Speaker").value = true;
    }else{
        document.getElementById("Speaker").value = false;
    }
    var temp1 = "";
    var temp2 = "";
    document.getElementById("Slots").value = slots;
    document.getElementById("VerticalRows").value = vrows;
    for(i = 0; i<vrows; i++){
        temp1+= document.getElementById(soDayNgang[i]).value+'-';
    }
    document.getElementById("HorizontalRows").value = temp1;
    for(i = 0; i<vrows; i++){
        temp2+= document.getElementById(soChoNgoi[i]).value+'-';
    }
    document.getElementById("NoSlotsEachHRows").value = temp2;

}

function showMap( mapId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
    /*var vrows = 2;
     var soDayNgang = [5, 5];
     var soChoNgoi = [3, 3];*/
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var array = [];
    var o = new Object();
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;
    var classroommap = document.getElementById('classroommap');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }

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
        /*var soDay = document.getElementById(soDayNgang[i]);
         var soCho = document.getElementById(soChoNgoi[i]);*/
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

            //
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
    if(projector){
        var divProjector = document.createElement('div');
        divProjector.id = 'idProjector';
        divProjector.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divProjector);
    }

    //tivi
    if(tivi){
        var divTivi = document.createElement('div');
        divTivi.id = 'idTivi';
        divTivi.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divTivi);
    }

    //may lanh
    if(mayLanh){
        var divMayLanh = document.createElement('div');
        divMayLanh.id = 'idMayLanh';
        divMayLanh.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divMayLanh);
    }

    //quat
    if(quat){
        var divQuat = document.createElement('div');
        divQuat.id = 'idQuat';
        divQuat.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divQuat);
    }

    //loa
    if(loa){
        var divLoa = document.createElement('div');
        divLoa.id = 'idLoa';
        divLoa.className = 'subThietBi';
        document.getElementsByClassName('thietBi')[0].appendChild(divLoa);
    }


}