'use strict';


function listSampleData(){
    var bootSimply = new BootSimply("SampleData");
    bootSimply.listStore(null,null,successHandler);
}
function insertSampleData(){
    loading.style.display="block";
    var sampletext = document.getElementById("sampletext");
    var bootSimply = new BootSimply("SampleData");
    bootSimply.insertStore(sampletext.value,listSampleData);
}

function init(){
    var addsample = document.getElementById("addsample");
    addsample.addEventListener("click", insertSampleData);
    listSampleData();
}

function successHandler(response){
    console.log("successHandler");
    
    var listsample = document.getElementById("listsample");
    listsample.innerHTML = "";
   
    if(response.items!=null || response.items!=undefined){
        for(var i = 0; i < response.items.length; i++){
            var divtest = document.createElement("div");
            divtest.innerHTML = "<hr>" + response.items[i].data;
            listsample.appendChild(divtest);
        }
    }
    var sampletext = document.getElementById("sampletext");
    sampletext.value = "";
    var loading = document.getElementById("loading");
    loading.style.display="none";
}

function start() {
	//var bootSimply = new BootSimply("SampleData");
    //var bootSimply = new BootSimply().entityRef("SampleData");
    //var data="data from my chrome app";
	//bootSimply.insertStore(data,successHandler);
    //bootSimply.listStore(null,null,successHandler);
}
window.onload = init;