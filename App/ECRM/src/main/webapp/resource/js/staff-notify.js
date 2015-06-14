/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */

function showReportDetail(roomId){
    $.ajax({
        method: "GET",
        url: "/thong-bao/chi-tiet",
        data: {roomId: roomId},
        success: function(result) {
            $(".content-all-modal").html(result);
            showModal(1,'modal-1');
        }
    });
}

function sendResolve() {
    var list = "";

    for (var key in listResolve) {
        if (listResolve.hasOwnProperty(key)){
            list += key + ",";
        }
    }

    if(list.length > 0) {
        document.getElementById("ListResolve").value = list.substring(0, list.length-1);
        document.getElementById("resolveForm").submit();
    } else {
        alert("Bạn cần chọn thiết bị!");
    }
}