/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
var socket = io.connect('http://192.168.1.139:3000');
//var socket = io.connect('http://10.82.134.241:3000');
function connectToSocket(username, role) {
    var jsonObject = {socketId: [], username: username, role: role, deviceType: 1};
    socket.emit('connectServer', jsonObject);
}

socket.on('ChangeRoom', function(data) {
    var htmlText = "Đổi từ phòng " + data.currentRoom + " sang phòng <b>" + data.changeRoom + "</b> vào các tiết: <b>";
    var titleText = "Đổi từ phòng " + data.currentRoom + " sang phòng " + data.changeRoom + " vào các tiết: ";
    var listTime = data.listTime;
    for (var i = 0; i < listTime.length; i++){
        htmlText += listTime[i] + ", ";
        titleText += listTime[i] + ", ";
    }
    htmlText = htmlText.substring(0, htmlText.length-2);
    htmlText += "</b>";
    titleText = titleText.substring(0, titleText.length-2);

    var itemDiv = document.createElement("div");
    itemDiv.style.fontWeight = "bold";
    itemDiv.style.fontStyle = "italic";
    itemDiv.className = "item";
    itemDiv.setAttribute("id", data.notifyId);
    itemDiv.setAttribute("title", titleText);
    itemDiv.innerHTML = htmlText;
    itemDiv.setAttribute("onclick", "viewNotify(" + data.notifyId + ", '" + htmlText + "')");

    document.getElementById("notifications").innerHTML = itemDiv.outerHTML + document.getElementById("notifications").innerHTML;
    var number = $("#numberOfnotify").attr("data-value");
    number++;
    $("#numberOfnotify").attr("data-value", number);
    $("#numberOfnotify").html(number);
    if(number > 0) {
        $("#numberOfnotify").removeClass("hidden");
    }

    var listRoom = document.getElementById("list-active-room");
    if(listRoom != undefined) {
        listRoom.innerHTML = "";
        var listNewRoom = data.listRoom;

        if(listNewRoom.length > 0) {
            for(var i = 0; i < listNewRoom.length; i++) {
                var room = document.createElement("option");
                room.setAttribute("value", listNewRoom[i].roomId);
                room.innerHTML = listNewRoom[i].roomName;

                listRoom.appendChild(room);
                if(i == (listNewRoom.length - 1)) {
                    room.setAttribute("checked", "checked");
                }
            }

            getRoomReport();
        } else {

        }
    }
});

socket.on('NewReport', function(data) {
    setDefaultDiv();

    var timeDiv = document.createElement("div");
    timeDiv.className = "time";
    var nowTime = new Date();
    timeDiv.innerHTML = formatAMPM(nowTime);

    var msgDiv = document.createElement("div");
    msgDiv.className = "message";
    msgDiv.innerHTML = data.message;

    var clearDiv = document.createElement("div");
    clearDiv.className = "clear";
    var aDiv = document.createElement("a");
    aDiv.setAttribute("href", data.redirectLink);
    aDiv.appendChild(timeDiv);
    aDiv.appendChild(msgDiv);
    aDiv.appendChild(clearDiv);

    var rowDiv = document.createElement("div");
    rowDiv.className = "row";
    rowDiv.appendChild(aDiv);

    var content = document.getElementById("unread-notify");
    content.innerHTML = rowDiv.outerHTML + content.innerHTML;

    var removeMessage = content.getElementsByClassName("none-message")[0];
    if(removeMessage != undefined) {
        removeMessage.parentNode.removeChild(removeMessage);
    }
});

socket.on('disconnect', function() {});

function sendDisconnect() {
    socket.disconnect();
}

function setDefaultDiv() {
    var numberOfNotify = $(".number-of-notifies").attr("data-value");
    if(numberOfNotify == 0){
        $(".number-of-notifies").addClass("active")
    }

    numberOfNotify++;
    $(".number-of-notifies").attr("data-value", numberOfNotify);
    $(".number-of-notifies").html(numberOfNotify);
}

function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}