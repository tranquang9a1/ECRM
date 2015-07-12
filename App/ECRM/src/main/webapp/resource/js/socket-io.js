/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
var socket = io.connect('http://192.168.1.107:3000');
//var socket = io.connect('http://10.82.134.241:3000');
function connectToSocket(username, role) {
    var jsonObject = {socketId: "", username: username, role: role, deviceType: 1};
    socket.emit('connectServer', jsonObject);
}

socket.on('ChangeRoom', function(data) {
    setDefaultDiv();

    var result = "Các tiết: <b>";
    var listTime = data.listTime;
    for (var i = 0; i < listTime.length; i++){
        result += listTime[i] + ", ";
    }
    result = result.substring(0, result.length-2);
    result += "</b>. Được đổi sang phòng " + data.changeRoom;

    var img = document.createElement("img");
    img.setAttribute("src", "/resource/img/user.png");
    var aDiv = document.createElement("a");
    aDiv.setAttribute("href", data.redirectLink);
    aDiv.innerHTML = result;

    var liDiv = document.createElement("li");
    liDiv.appendChild(img);
    liDiv.appendChild(aDiv);
    $(".list-notify ul").append(liDiv);

    var listRoom = document.getElementById("list-active-room");
    if(listRoom != undefined) {
        var rooms = listRoom.getElementsByTagName("option");
        for(var i = 0; i < rooms.length; i++) {
            if(rooms.item(i).getAttribute("value") == data.currentRoomId) {
                rooms.item(i).setAttribute("value", data.changeRoomId);
                rooms.item(i).innerHTML = data.changeRoom;
                rooms.item(i).setAttribute("checked", "checked");
                document.getElementById("roomId").value = data.changeRoomId;
                getRoom(listRoom);
            }
        }
    }
});

socket.on('NewReport', function(data) {
    setDefaultDiv();

    var img = document.createElement("img");
    img.setAttribute("src", "/resource/img/user.png");
    var aDiv = document.createElement("a");
    aDiv.setAttribute("href", data.redirectLink);
    aDiv.innerHTML = data.message;

    var liDiv = document.createElement("li");
    liDiv.appendChild(img);
    liDiv.appendChild(aDiv);
    $(".list-notify ul").append(liDiv);
});

socket.on('disconnect', function() {});

function sendDisconnect() {
    socket.disconnect();
}

function setDefaultDiv() {
    var numberOfNewNotify = $(".number-of-notify").attr("data-value");
    var numberOfNotify = $(".list-notify").attr("data-value");
    if(numberOfNotify == 0){
        $(".list-notify ul").html("");
    }

    numberOfNotify++;
    $(".list-notify").attr("data-value", numberOfNotify);

    numberOfNewNotify++;
    $(".number-of-notify").attr("data-value", numberOfNewNotify);
    $(".number-of-notify").html(numberOfNewNotify);
    $(".number-of-notify").removeClass("hidden");
}