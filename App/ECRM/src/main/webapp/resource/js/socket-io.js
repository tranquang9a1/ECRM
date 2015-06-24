/**
 * Created by ChiDNMSE60717 on 6/16/2015.
 */
var socket = io.connect('http://192.168.43.163:8000');

function connectToSocket(username, role) {
    var jsonObject = {socketId: "", username: username, role: role};
    socket.emit('connectServer', jsonObject);
}

socket.on('NewReport', function(data) {
    alert(data.message);
});

socket.on('disconnect', function() {});

function sendDisconnect() {
    socket.disconnect();
}