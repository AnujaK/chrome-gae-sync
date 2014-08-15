'use strict';


function listTodos(){
    var bootSimply = new BootSimply("Todo");
    bootSimply.listStore(null,null,successHandler);
}
function insertTodo(){
    loading.style.display="block";
    var todotext = document.getElementById("todotext");
    var bootSimply = new BootSimply("Todo");
    bootSimply.insertStore(todotext.value,listTodos);
}

function init(){
    var addtodo = document.getElementById("addtodo");
    addtodo.addEventListener("click", insertTodo);
    listTodos();
}

function successHandler(response){
    console.log("successHandler");
    
    var listtodo = document.getElementById("listtodo");
    listtodo.innerHTML = "";
    
    for(var i = 0; i < response.items.length; i++){
        var divtest = document.createElement("div");
        divtest.innerHTML = response.items[i].data;
        listtodo.appendChild(divtest);
    }
    var todotext = document.getElementById("todotext");
    todotext.value = "";
    var loading = document.getElementById("loading");
    loading.style.display="none";
}

function start() {
	//var bootSimply = new BootSimply("Todo");
    //var bootSimply = new BootSimply().entityRef("Todo");
    //var data="data from my chrome app";
	//bootSimply.insertStore(data,successHandler);
    //bootSimply.listStore(null,null,successHandler);
}
window.onload = init;