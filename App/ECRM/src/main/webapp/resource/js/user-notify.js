/**
 * Created by ChiDNMSE60717 on 6/7/2015.
 */
var damagedEquipments = {};
var noDamagedEquipments = {};

function showMap(mapId, equipments, vrows, sDayNgang, sChoNgoi, mayLanh, quat, projector, loa, tivi) {
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

                    if(equipments[position] != undefined && equipments[position].status == true) {
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

                    if(equipments[position] != undefined && equipments[position].status == true) {
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

                if(equipments[position] != undefined && equipments[position].status == true) {
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

                    if(equipments[position] != undefined && equipments[position].status == true) {
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

    //projector
    if(projector>0){
        var divProjector = document.createElement('div');
        if(equipments["[1]"] != undefined && equipments["[1]"].status == true) {
            divProjector.className = 'subThietBi projector damaged';
        } else {
            divProjector.className = 'equipment subThietBi projector';
        }
        divProjector.setAttribute('data-equipmentName','Máy chiếu');
        divProjector.setAttribute("data-position", "[1]");
        divProjector.setAttribute("data-categoryId", "1");
        divT.appendChild(divProjector);
    }

    //tivi
    if(tivi>0){
        var divTivi = document.createElement('div');
        if(equipments["[2]"] != undefined && equipments["[2]"].status == true) {
            divTivi.className = 'subThietBi tivi damaged';
        } else {
            divTivi.className = 'equipment subThietBi tivi';
        }
        divTivi.setAttribute('data-equipmentName','Tivi');
        divTivi.setAttribute("data-position", "[2]");
        divTivi.setAttribute("data-categoryId", "2");
        divT.appendChild(divTivi);
    }

    //may lanh
    if(mayLanh>0){
        var divMayLanh = document.createElement('div');
        if(equipments["[3]"] != undefined && equipments["[3]"].status == true) {
            divMayLanh.className = 'subThietBi mayLanh damaged';
        } else {
            divMayLanh.className = 'equipment subThietBi mayLanh';
        }
        divMayLanh.setAttribute('data-equipmentName','Máy lạnh');
        divMayLanh.setAttribute("data-position", "[3]");
        divMayLanh.setAttribute("data-categoryId", "3");
        divT.appendChild(divMayLanh);
    }

    //quat
    if(quat>0){
        var divQuat = document.createElement('div');
        if(equipments["[4]"] != undefined && equipments["[4]"].status == true) {
            divQuat.className = 'subThietBi quat damaged';
        } else {
            divQuat.className = 'equipment subThietBi quat';
        }

        divQuat.setAttribute('data-equipmentName','Quạt');
        divQuat.setAttribute("data-position", "[4]");
        divQuat.setAttribute("data-categoryId", "4");
        divT.appendChild(divQuat);
    }

    //loa
    if(loa>0){
        var divLoa = document.createElement('div');
        if(equipments["[5]"] != undefined && equipments["[5]"].status == true) {
            divLoa.className = 'subThietBi loa damaged';
        } else {
            divLoa.className = 'equipment subThietBi loa';
        }
        divLoa.setAttribute('data-equipmentName','Loa');
        divLoa.setAttribute("data-position", "[5]");
        divLoa.setAttribute("data-categoryId", "5");
        divT.appendChild(divLoa);
    }
}

function setChooseEquipment(map){
    $("#" + map +" .equipment").click(function(){
        var category = $(this).attr("data-categoryId");
        var message = document.getElementById("category-"+category).parentNode.getElementsByClassName("message-text")[0];
        var inputT = document.getElementById("category-"+category).parentNode.getElementsByClassName("description-equipment")[0];

        if($(this).attr("data-choose") === "true"){
            $(this).removeClass("choose");
            $(this).removeAttr("data-choose");

            delete damagedEquipments[$(this).attr("data-position")];
            noDamagedEquipments[category].size--;
            if(noDamagedEquipments[category].size == 0) {
                document.getElementById("category-" + category).style.display = "none";
                document.getElementById("equipment-type-" + category).setAttribute("data-check", "false");
                document.getElementById("equipment-type-" + category).className = "check-damaged";
            }

            message.style.visibility = "visible";
            if(inputT != undefined) {
                inputT.className += " hidden";
            }
        } else {
            $(this).addClass("choose");
            $(this).attr("data-choose", "true");

            damagedEquipments[$(this).attr("data-position")] = {position: $(this).attr("data-position"), category: category};
            if(noDamagedEquipments[category] != undefined) {
                noDamagedEquipments[category].size++;
            } else {
                noDamagedEquipments[category] = {size: 1, evaluate: 4};
            }

            document.getElementById("category-" + category).style.display = "block";
            document.getElementById("equipment-type-" + category).setAttribute("data-check", "true");
            document.getElementById("equipment-type-" + category).className = "check-damaged check";

            message.style.visibility = "hidden";
            var valueE = document.getElementById("category-"+category).value;
            if(inputT != undefined && parseInt(valueE) == 1) {
                inputT.className = "description-equipment";
            }
        }
    });
}




function sentReport(){
    var listEquipment = "";
    var listEvaluate = "";
    var listDesc = [];

    for (var key in damagedEquipments) {
        if (damagedEquipments.hasOwnProperty(key)){
            listEquipment += damagedEquipments[key].category + "-" + damagedEquipments[key].position + "--";
        }
    }
    listEquipment = listEquipment.substring(0, listEquipment.length-2);

    for (var key in noDamagedEquipments) {
        if (noDamagedEquipments.hasOwnProperty(key) && noDamagedEquipments[key].size > 0){
            listEvaluate += key + "-" + noDamagedEquipments[key].evaluate + ",";
            if(noDamagedEquipments[key].evaluate == 1) {
                var desc = document.getElementById("category-"+key).parentNode.getElementsByClassName("description-equipment")[0];
                listDesc.push(desc.value);
            } else {
                listDesc.push("");
            }
        }
    }
    listEvaluate = listEvaluate.substring(0, listEvaluate.length-1);

    var room = document.getElementById("roomId");

    $.ajax({
        method: "POST",
        headers: {
            Accept : "text/plain; charset=utf-8",
            "Content-Type": "application/json; charset=utf-8"
        },
        dataType: 'json',
        url: "sentReport",
        data: JSON.stringify({"roomId": room.value, "evaluate": $("#report-evaluate").val(), "listDamaged": listEquipment, "listEvaluate": listEvaluate, "listDesc": listDesc}),
        success: function(result) {
            var data = result.split('-');
            var html = "<div class='width-10'><div>" + data[1] + "</div></div>";
            html += "<div class='width-25'><div id='list-20'>" + data[2] + "</div></div>";
            var createDate = new Date(parseInt(data[3]));
            html += "<div class='width-20'><div>" + (createDate.getDate()<10?("0"+createDate.getDate()):createDate.getDate())  + "/" + ((createDate.getMonth()+1)<10?("0" + (createDate.getMonth()+1)):(createDate.getMonth()+1)) + "/" + createDate.getFullYear() + "</div></div>";
            html += "<div class='width-15'><div>0%</div></div>";
            html += "<div class='width-15'><div><p class='label red'>Chưa sửa</p></div></div>";
            html += "<div class='width-15'><div class='group-button'><div onclick='loadReportHistory(" + data[0]+ ", " + data[1] + ")' class='btn btn-detail'><i class='fa fa-file'></i></div><div class='btn btn-remove'><i class='fa fa-times'></i></div></div></div>";
            html += "<p class='clear'></p>";
            var row = document.createElement("div");
            row.className = "row";
            row.innerHTML = html;
            var content = document.getElementById("content-report");
            content.appendChild(row);

            for (var key in noDamagedEquipments) {
                if (noDamagedEquipments.hasOwnProperty(key) && key < 7){
                    $("#row-type-" + key + " .width-50 p").text("Đã được báo cáo");
                }
            }

            for (var key in damagedEquipments) {
                if (damagedEquipments.hasOwnProperty(key)){
                    $(damagedEquipments[key].element).removeClass("equipment");
                    $(damagedEquipments[key].element).removeClass("choose");
                    $(damagedEquipments[key].element).removeAttr("data-choose");
                    $(damagedEquipments[key].element).addClass("damaged");
                    $(damagedEquipments[key].element).unbind( "click" );
                }
            }

            noDamagedEquipments = {};
            damagedEquipments = {};
            $(".check-damaged").attr("data-check","false");
            $(".check-damaged").removeClass("check");
            $("#modal-1 select").css("display", "none");
            $("#modal-1 select").val(4);
        }
    });
}

function loadEquipment(listDamage, mayLanh, quat, projector, loa, tivi){
    var listEquipment = new Array();
    if(listDamage[1] != undefined && projector > 0) {
        listEquipment.push({id: 1, classname: "projector", name: "Projector", message: (listDamage[1]?"Đã được báo cáo":"")});
    }
    if(listDamage[3] != undefined && mayLanh > 0) {
        listEquipment.push({id: 3, classname: "air-condition", name: "AirCondition", message: (listDamage[3]?"Đã được báo cáo":"")});
    }
    if(listDamage[5] != undefined && loa > 0) {
        listEquipment.push({id: 5, classname: "speaker", name: "Speaker", message: (listDamage[5]?"Đã được báo cáo":"")});
    }
    if(listDamage[2] != undefined && tivi > 0) {
        listEquipment.push({id: 2, classname: "tivi", name: "Television", message: (listDamage[2]?"Đã được báo cáo":"")});
    }
    if(listDamage[4] != undefined && quat > 0) {
        listEquipment.push({id: 4, classname: "fan", name: "Fan", message: (listDamage[4]?"Đã được báo cáo":"")});
    }
    listEquipment.push({id: 7, classname: "table-icon", name: "Table", message: (listDamage[7]?"Đã được báo cáo":"")});
    listEquipment.push({id: 8, classname: "chair", name: "Chair", message: (listDamage[8]?"Đã được báo cáo":"")});

    var contentDiv = document.getElementById("modal-1").getElementsByClassName("body-modal")[0];
    for(var i = 0; i < listEquipment.length; i++) {
        var img = document.createElement("div");
        img.className = "equipment-img " + listEquipment[i].classname;
        var div1 = document.createElement("div");
        div1.className = "width-30";
        div1.appendChild(img);

        var damageDiv = document.createElement("div");
        damageDiv.className = "check-damaged";
        damageDiv.innerText = "Hư hại";
        damageDiv.id = "equipment-type-" + listEquipment[i].id;
        damageDiv.setAttribute("data-check", "false");
        damageDiv.setAttribute("onclick", "changeDamaged(" + listEquipment[i].id + ")");
        //var hiddenInput = document.createElement("input");
        //hiddenInput.setAttribute("type", "hidden");
        //hiddenInput.setAttribute("name", listEquipment[i].name);
        var div2 = document.createElement("div");
        div2.className = "width-20";
        div2.appendChild(damageDiv);
        //div2.appendChild(hiddenInput);

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

function createSelect(id, contentDiv){
    var select = document.createElement("select");
    select.style.display = "none";
    select.setAttribute("id","category-"+id);
    select.setAttribute("onchange", "changeEvaluate(" + id + ")");

    var listEvaluate = ["Hư hại nặng", "Hư hại trung bình", "Hư hại nhẹ", "Không biết"];
    for(var i = 0; i < 4; i++){
        var option = document.createElement("option");
        option.setAttribute("value", (i+1));
        option.innerHTML = listEvaluate[i];
        select.appendChild(option);

        if(i == 3) {
            option.setAttribute("selected", "selected");
        }
    }

    contentDiv.appendChild(select);
}

function changeDamaged(id) {
    var element = document.getElementById("equipment-type-" + id);
    var message = document.getElementById("category-"+id).parentNode.getElementsByClassName("message-text")[0];
    var inputT = document.getElementById("category-"+id).parentNode.getElementsByClassName("description-equipment")[0];

    if(element.getAttribute("data-check") == "false") {
        if(id < 7) {
            damagedEquipments["[" + id + "]"] = {position: "[" + id + "]", category: id};
        }
        if(noDamagedEquipments[id] == undefined) {
            noDamagedEquipments[id] = {size: 1, evaluate: 4};
        } else {
            noDamagedEquipments[id].size = 1;
        }
        document.getElementById("category-" + id).style.display = "block";
        element.setAttribute("data-check", "true");
        element.className += " check";

        if(id < 7) {
            var thisEquip = $(".equipment[data-position='[" + id + "]']");
            $(thisEquip).addClass("choose");
            $(thisEquip).attr("data-choose", "true");
        }

        message.style.visibility = "hidden";
        var valueE = document.getElementById("category-"+id).value;
        if(inputT != undefined && parseInt(valueE) == 1) {
            inputT.className = "description-equipment";
        }
    } else {
        noDamagedEquipments[id] = {size: 0, evaluate: 4};
        document.getElementById("category-" + id).style.display = "none";
        element.setAttribute("data-check", "false");
        element.className = "check-damaged";

        for (var key in damagedEquipments) {
            if (damagedEquipments.hasOwnProperty(key) && damagedEquipments[key].category == id){
                delete damagedEquipments[key];
                var thisEquip = $(".equipment[data-position='" + key + "']");
                $(thisEquip).removeClass("choose");
                $(thisEquip).removeAttr("data-choose");
            }
        }

        message.style.visibility = "visible";
        if(inputT != undefined) {
            inputT.className += " hidden";
        }
    }
}

function changeEvaluate(category) {
    var element = document.getElementById("category-"+category);
    var inputT = element.parentNode.getElementsByClassName("description-equipment")[0];

    noDamagedEquipments[category].evaluate = parseInt(element.value);

    if(element.value == 1) {
        element.className = "small";

        if(inputT == undefined) {
            var inputT = document.createElement("input");
            inputT.className = "description-equipment";
            inputT.setAttribute("type", "text");
            inputT.setAttribute("placeholder", "Chi tiết hư hại");
            element.parentNode.appendChild(inputT);
        } else {
            inputT.className = "description-equipment"
        }
    } else {
        element.className = "";
        if(inputT != undefined) {
            inputT.className += " hidden";
        }
    }
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