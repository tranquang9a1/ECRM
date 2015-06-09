/**
 * Created by ChiDNMSE60717 on 6/8/2015.
 */

function showReportDetail(reportId){
    $.ajax({
        method: "GET",
        url: "/thong-bao/chi-tiet",
        data: {reportId: reportId},
        success: function(result) {
            $(".content-all-modal").html(result);
            showModal(1,'modal-1');
        }
    });
}