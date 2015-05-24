/**
 * Created by ChiDNMSE60717 on 5/24/2015.
 */
function changeTab(tab , thisElement) {
    var checkActive = thisElement.className;
    if(checkActive !== "active"){
        // change title tab
        var fatherElement = thisElement.parentNode;
        var listTab = fatherElement.childNodes;
        for(var i = 0; i < listTab.length; i++){
            listTab[i].className = "";
        }
        thisElement.className = "active";

        //change body tab
        fatherElement = thisElement.parentNode.parentNode.parentNode;
        listTab = fatherElement.getElementsByClassName("body-tab");
        for(var i = 0; i < listTab.length; i++){
            listTab[i].className = "body-tab";
        }
        document.getElementById(tab).className = "body-tab active";
    }
}

function showModal(show, modal1, modal2) {
    var bgA = document.getElementById(modal1).getElementsByClassName("black-background")[0];
    var modalA = document.getElementById(modal1).getElementsByClassName("content-modal")[0];

    if(show == 0) {
        bgA.className = "black-background";
        modalA.className = "content-modal";
    } else if(show == 1) {
        bgA.className += " active";
        modalA.className += " active";
    } else {
        bgA.className = "black-background";
        modalA.className = "content-modal";

        var bgB = document.getElementById(modal2).getElementsByClassName("black-background")[0];
        var modalB = document.getElementById(modal2).getElementsByClassName("content-modal")[0];
        bgB.className += " active";
        modalB.className += " active";
    }
}