function myFunction() {
    var table = document.getElementById("myTable");
    var vrows = document.getElementById("vrow");
    var soDayNgang = [];
    var soChoNgoi = [];
    var o = new Object();
    var array = [];

    document.getElementById("create").onclick = function () {
        table.innerHTML = '';
        var row = table.insertRow(0);
        for (var i = 0; i < vrows.value; i++) {
            var cell = row.insertCell(i);
            var j = i + 1;
            cell.innerHTML = "Day " + j;
        }
        var row = table.insertRow(1);
        for (var k = 0; k < vrows.value; k++) {
            cell = row.insertCell(k);
            var j = k + 1;
            cell.innerHTML = "So day ngang: <input type='text' id='SoDayNgang" + j + "'/><br/>" +
                "So cho ngoi mot day:  <input type='text' id='SoChoNgoi" + j + "'/><br/>";

            soDayNgang[k] = "SoDayNgang" + j;
            soChoNgoi[k] = "SoChoNgoi" + j;
        }


    }
    document.getElementById("view").onclick = function () {
        var chieuDaiBan = 0;
        var chieuDaiSubDiv = 0;
        var container = document.getElementById('idContainer');
        while(container.hasChildNodes()){
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
        divScreen.innerHTML='Board';
        divBang.appendChild(divScreen);
        for (var i = 0; i < vrows.value; i++) {
            var soDay = document.getElementById(soDayNgang[i]);
            var soCho = document.getElementById(soChoNgoi[i]);
            o = {
                soDay: soDay.value,
                soChoNgoi: soCho.value
            }
            array[i] = o;
        }
        //tao div con chua map
        var subDiv = document.createElement('div');
        subDiv.className = 'subDiv';
        subDiv.id='idSubDiv';
        div.appendChild(subDiv);
        //chay vong for day doc
        for (var i = 0; i < vrows.value; i++) {
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
            if(i==(vrows.value -1)){
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
            document.getElementById('idBang').style.width = "400px";
        }
        //projector
        if(document.getElementById('projector').checked){
            var divProjector = document.createElement('div');
            divProjector.id = 'idProjector';
            divProjector.className='subThietBi';
            document.getElementsByTagName('div')[1].appendChild(divProjector);
        }
        //tivi
        if(document.getElementById('tivi').checked){
            var divTivi = document.createElement('div');
            divTivi.id = 'idTivi';
            divTivi.className='subThietBi';
            document.getElementsByTagName('div')[1].appendChild(divTivi);
        }
        //may lanh
        if(document.getElementById('mayLanh').checked){
            var divMayLanh = document.createElement('div');
            divMayLanh.id = 'idMayLanh';
            divMayLanh.className='subThietBi';
            document.getElementsByTagName('div')[1].appendChild(divMayLanh);
        }
        //quat
        if(document.getElementById('quat').checked){
            var divQuat = document.createElement('div');
            divQuat.id = 'idQuat';
            divQuat.className='subThietBi';
            document.getElementsByTagName('div')[1].appendChild(divQuat);
        }
        //loa
        if(document.getElementById('loa').checked){
            var divLoa = document.createElement('div');
            divLoa.id = 'idLoa';
            divLoa.className='subThietBi';
            document.getElementsByTagName('div')[1].appendChild(divLoa);
        }
    }

}
