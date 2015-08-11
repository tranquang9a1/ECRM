/**
 * Created by Htang on 6/30/2015.
 */


$(function () {
    $("#datepickerFrom").datepicker({dateFormat: "dd-mm-yy"});
    $("#datepickeTo").datepicker({dateFormat: "dd-mm-yy"});
    $("#datefrom").datepicker({dateFormat: "dd-mm-yy"});
    $("#dateto").datepicker({dateFormat: "dd-mm-yy"});
});



function searchSchedule() {
    var tags = document.getElementById('tags').value;
    var dateFrom = document.getElementById('datefrom').value;
    var dateTo = document.getElementById('dateto').value;
    var action = document.getElementById("selectSearch").selectedIndex;
    $.ajax({
        type: "get",
        url: "/ajax/searchSchedule",
        cache: false,
        data: 'tags=' + tags +'&action=' + action +
        "&dateFrom=" + dateFrom + "&dateTo=" + dateTo,
        success: function (responseText) {
            $('.component').html("");
            $('.component').html(responseText);
            initHeader();
        },
        error: function () {
            alert('Error while request..');
        }
    })

}


