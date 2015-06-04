/**
 * Created by ChiDNMSE60717 on 5/26/2015.
 */
var listEquiment = [];
var soDayNgang = [];
var soChoNgoi = [];
var o = new Object();
var array = [];
var slots = 0;



function createDetailMap(horizontalRows,noSlotsEachRows) {
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
        if(horizontalRows != null){
            document.getElementById("SoDayNgang" + (i+1)).selectedIndex = horizontalRows[i]-1;
        }

        var item3 = document.createElement("div");
        item3.className = "item";
        var select2 = createSelect("SoChoNgoi" + (i+1));
        item3.appendChild(select2);
        row3.appendChild(item3);
        if(noSlotsEachRows != null){
            document.getElementById("SoChoNgoi" + (i+1)).selectedIndex = noSlotsEachRows[i]-1;
        }

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
        divProjector.className='equipment subThietBi projector';
        document.getElementsByClassName('thietBi')[0].appendChild(divProjector);
        document.getElementById("Projectors").value = true;
    }else{
        document.getElementById("Projectors").value = false;
    }
    //tivi
    if(document.getElementById('tivi').checked){
        var divTivi = document.createElement('div');
        divTivi.id = 'idTivi';
        divTivi.className='equipment subThietBi tivi';
        document.getElementsByClassName('thietBi')[0].appendChild(divTivi);
        document.getElementById("Television").value = true;
    }else{
        document.getElementById("Television").value = false;
    }
    //may lanh
    if(document.getElementById('mayLanh').checked){
        var divMayLanh = document.createElement('div');
        divMayLanh.id = 'idMayLanh';
        divMayLanh.className='equipment subThietBi mayLanh';
        document.getElementsByClassName('thietBi')[0].appendChild(divMayLanh);
        document.getElementById("AirConditioning").value = true;
    }else{
        document.getElementById("AirConditioning").value = false;
    }
    //quat
    if(document.getElementById('quat').checked){
        var divQuat = document.createElement('div');
        divQuat.id = 'idQuat';
        divQuat.className='equipment subThietBi quat';
        document.getElementsByClassName('thietBi')[0].appendChild(divQuat);
        document.getElementById("Fan").value = true;
    }else{
        document.getElementById("Fan").value = false;
    }
    //loa
    if(document.getElementById('loa').checked){
        var divLoa = document.createElement('div');
        divLoa.id = 'idLoa';
        divLoa.className='equipment subThietBi loa';
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

function showMap(mapId, typeId, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var array = [];
    var o = new Object();
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;
    var classroommap = document.getElementById(mapId);

    //while (classroommap.hasChildNodes()) {
    //    classroommap.removeChild(classroommap.lastChild);
    //}

    classroommap.innerHTML ="";

    var container = document.createElement('div');
    container.id = "roomtype-" + typeId;
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

function setChooseEquipment(){
    $(".equipment").click(function(){
        if($(this).attr("data-choose") === "true"){
            $(this).removeClass("choose");
            $(this).removeAttr("data-choose");
            document.getElementById("list-equipment").removeChild(document.getElementById("equipment-" + $(this).attr("data-position")));
        } else {
            $(this).addClass("choose");
            $(this).attr("data-choose", "true");
            document.getElementById("list-equipment").innerHTML += "<span id='equipment-" + $(this).attr("data-position") + "'>" + $(this).attr("data-equipment") + $(this).attr("data-position") + " </span>";
        }
    });
}
$("#selectBox").change(function(){
    var selectedValue = $(this).find(":selected").data("value");
    alert(selectedValue.id);
    document.getElementById('loaiphong').innerHTML = selectedValue.id;
    document.getElementById('roomtype').value = selectedValue.id;
    showMap( selectedValue.id, selectedValue.verticalRows, selectedValue.horizontalRows, selectedValue.noSlotsEachHRows
        , selectedValue.airConditioning, selectedValue.fan, selectedValue.projector, selectedValue.speaker,
        selectedValue.television);
});