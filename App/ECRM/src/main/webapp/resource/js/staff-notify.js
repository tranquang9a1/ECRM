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