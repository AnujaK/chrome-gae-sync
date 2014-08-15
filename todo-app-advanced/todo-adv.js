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
function deleteTodo(entityId){
    loading.style.display="block";
    var bootSimply = new BootSimply("Todo");
    bootSimply.removeStore(entityId,listTodos);
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
    
    if(response.items!=null || response.items!=undefined){
        for(var i = 0; i < response.items.length; i++){
            var divtest = document.createElement("div");
            divtest.innerHTML = "<hr>" + response.items[i].data+"      ";
            
            var button = document.createElement("button");
            button.setAttribute("id", i+"_deletetodo");
            var entityId = response.items[i]._id;
            button.setAttribute("entityId", entityId);
            button.setAttribute("style", "float:right;");
            button.innerHTML = "Delete";
            
            divtest.appendChild(button);
            listtodo.appendChild(divtest);
            
            (function () {
                var ii = i;
                var entityIdLocal = entityId;
                button.addEventListener('click', function(){
                    deleteTodo(entityIdLocal);
                    console.log(ii+" -- "+entityIdLocal);
                });
            })();
        }
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