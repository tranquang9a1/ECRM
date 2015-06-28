/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
var socket = io.connect('http://128.199.208.93:3000');

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
    result += "</b>. Được chuyền sang dạy phòng <b>" + data.changeRoom + "</b>";

    var img = document.createElement("img");
    img.setAttribute("src", "/resource/img/user.png");
    var aDiv = document.createElement("a");
    aDiv.setAttribute("href", "/giang-vien/lich-day");
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
    var numberOfNotify = $(".number-of-notify").attr("data-value");
    if(numberOfNotify == 0){
        $(".list-notify ul").html("");
    }
    numberOfNotify++;
    $(".number-of-notify").attr("data-value", numberOfNotify);
    $(".number-of-notify").html(numberOfNotify);
    $(".number-of-notify").removeClass("hidden");
}