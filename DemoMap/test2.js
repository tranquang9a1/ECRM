/**
 * Created by Htang on 5/26/2015.
 */
function myFunction() {
    var vrows = 2;
    var soDayNgang = [5, 5];
    var soChoNgoi = [3, 3];
    var array = [];
    var o = new Object();
    var chieuDaiBan = 0;
    var chieuDaiSubDiv = 0;
    var container = document.getElementById('idContainer');
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }
    var divT = document.createElement('div');
    divT.className = 'thietBi';
    document.getElementsByTagName('div')[0].appendChild(divT);
    //tao div map
    var div = document.createElement('div');
    div.className = 'map';
    div.id = 'idMap';
    document.getElementsByTagName('div')[0].appendChild(div);
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
            soDay: soDayNgang[i],
            soChoNgoi: soChoNgoi[i]
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
        document.getElementById('idBang').style.width = "400px";
    }
    //projector
    var divProjector = document.createElement('div');
    divProjector.id = 'idProjector';
    divProjector.className = 'subThietBi';
    document.getElementsByTagName('div')[1].appendChild(divProjector);
    //tivi
    var divTivi = document.createElement('div');
    divTivi.id = 'idTivi';
    divTivi.className = 'subThietBi';
    document.getElementsByTagName('div')[1].appendChild(divTivi);
    //may lanh
    var divMayLanh = document.createElement('div');
    divMayLanh.id = 'idMayLanh';
    divMayLanh.className = 'subThietBi';
    document.getElementsByTagName('div')[1].appendChild(divMayLanh);
    //quat
    var divQuat = document.createElement('div');
    divQuat.id = 'idQuat';
    divQuat.className = 'subThietBi';
    document.getElementsByTagName('div')[1].appendChild(divQuat);
    //loa
    var divLoa = document.createElement('div');
    divLoa.id = 'idLoa';
    divLoa.className = 'subThietBi';
    document.getElementsByTagName('div')[1].appendChild(divLoa);
}