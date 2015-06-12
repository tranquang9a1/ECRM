function setChooseEquipment(){
    $(".equipment").click(function(){
        if($(this).attr("data-choose") === "true"){
            $(this).removeClass("choose");
            $(this).removeAttr("data-choose");
            document.getElementById("list-equipment").removeChild(document.getElementById("equipment-" + $(this).attr("data-position")));
            Android.removeEquipment($(this).attr("data-equipment"), $(this).attr("data-position"));
        } else {
            $(this).addClass("choose");
            $(this).attr("data-choose", "true");
            document.getElementById("list-equipment").innerHTML += "<span id='equipment-" + $(this).attr("data-position") + "'>" + $(this).attr("data-equipment") + $(this).attr("data-position") + " </span>";
            Android.addEquipment($(this).attr("data-equipment"), $(this).attr("data-position"));
        }
    });
}