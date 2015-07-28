/**
 * Created by Htang on 5/30/2015.
 */
function showClassroomMap(roomname, mapId, vrows, sDayNgang, sChoNgoi, equipment) {
    var a = document.get
    //add roomname
    document.getElementById('roomname').innerHTML = "Sơ đồ phòng " + roomname;
    //
    var horizontalRows = sDayNgang.split('-');
    var noSlotsEachRows = sChoNgoi.split('-');
    var classroommap = document.getElementById('class-roommap');

    while (classroommap.hasChildNodes()) {
        classroommap.removeChild(classroommap.lastChild);
    }

    generateMap2(classroommap, horizontalRows, noSlotsEachRows, mapId, vrows, equipment);
    document.getElementById('classroomName').innerHTML = "Phòng "+roomname;
    document.getElementById('roomNameId').value = roomname;
}
