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

    if (show == 0) {
        bgA.className = "black-background";
        modalA.className = "content-modal";
    } else if (show == 1) {
        var lock = document.getElementById(modal1).getAttribute("data-lock");
        if(lock != "true") {
            bgA.className += " active";
            modalA.className += " active";
        }
    } else {
        bgA.className = "black-background";
        modalA.className = "content-modal";

        var bgB = document.getElementById(modal2).getElementsByClassName("black-background")[0];
        var modalB = document.getElementById(modal2).getElementsByClassName("content-modal")[0];
        bgB.className += " active";
        modalB.className += " active";
    }
}

function conform(choose, object){
    var body = document.getElementsByTagName("body").item(0);
    var conformDiv = document.createElement("div");
    conformDiv.className = "conform";
    var contentDiv = document.createElement("div");
    contentDiv.className = "content-conform small";

    var titleDiv = document.createElement("div");
    titleDiv.className = "title-conform";
    titleDiv.innerHTML = "BẠN CÓ CHẮC KHÔNG?";
    var controlDiv = document.createElement("div");
    controlDiv.className = "control-button";

    var btnYes = document.createElement("input");
    btnYes.className = "btn btn-orange";
    btnYes.type = "button";
    btnYes.value = "CÓ! TÔI CHẮC";
    btnYes.addEventListener("click", function() { doAction(choose, object)}, false);
    var btnNo = document.createElement("input");
    btnNo.className = "btn btn-normal";
    btnNo.type = "button";
    btnNo.value = "KHÔNG";
    btnNo.addEventListener("click", closeConform, false);
    controlDiv.appendChild(btnYes);
    controlDiv.appendChild(btnNo);

    contentDiv.appendChild(titleDiv);
    contentDiv.appendChild(controlDiv);

    var bgDiv = document.createElement("div");
    bgDiv.className = "white-background";
    conformDiv.appendChild(contentDiv);
    conformDiv.appendChild(bgDiv);
    body.appendChild(conformDiv);

    setTimeout(function(){
        contentDiv.className += " active";
    }, 10);
}

function closeConform(){
    var conformDiv = document.getElementsByClassName("conform")[0];
    conformDiv.parentNode.removeChild(conformDiv);
}

function waitLoading() {
    var body = document.getElementsByTagName("body").item(0);
    var loadingDiv = document.createElement("div");
    loadingDiv.className = "loading";

    var contentDiv = document.createElement("div");
    contentDiv.className = "loading-content";
    var imgP = document.createElement("img");
    imgP.setAttribute("src", "/resource/img/loadingAnimation.gif");
    contentDiv.appendChild(imgP);

    var bgDiv = document.createElement("div");
    bgDiv.className = "black-background";
    loadingDiv.appendChild(contentDiv);
    loadingDiv.appendChild(bgDiv);
    body.appendChild(loadingDiv);

    setTimeout(function(){
        contentDiv.className += " active";
    }, 10);
}

function closeLoading(){
    var loadingDiv = document.getElementsByClassName("loading")[0];
    loadingDiv.parentNode.removeChild(loadingDiv);
}