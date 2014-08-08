'use strict';

function start() {
	var bootSimply = new BootSimply("Todo");
	var data="data from my chrome app";

	bootSimply.insertStore(data);
    
    bootSimply.listStore();
}

window.onload = start;