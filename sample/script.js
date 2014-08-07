function authFlow(){
	chrome.identity.getAuthToken({
    'interactive': true
	}, function (token) {
		if (chrome.runtime.lastError) {
			console.log("chrome.runtime.lastError : " + chrome.runtime.lastError);
		} else {
			console.log("token/else : " + token);
		}
		console.log("token : " + token);
	});
}

window.onload = authFlow;