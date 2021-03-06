'use strict';

var BootSimply = (function () {

	var _name;

	var BootSimply = function (_name) {
		this._name = _name;
	};

	function makeApiCall(url, type, data, callback) {

		var accessToken;

		getAccessToken();

		function getAccessToken() {
			chrome.identity.getAuthToken({
				'interactive': true
			}, function (token) {
				if (chrome.runtime.lastError) {
					console.log("chrome.runtime.lastError : " + chrome.runtime.lastError);
				} else {
					console.log("token : " + token);

					accessToken = token;
					invokeApi();
				}
			});
		}

		function invokeApi() {
			$.ajax({
				url: url,
				type: type,
				dataType: 'json',
				data: data,
                
				contentType: "application/json",
				headers: {
					'Authorization': 'Bearer ' + accessToken
				},
				success: function (response) {
					console.log("response : " + JSON.stringify(response));
					if (callback && callback != null) {
						callback(response);
					}

				}
			});
		}
	}

	BootSimply.prototype.listStore = function (cursor, limit, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/findAll/" + this._name;
		makeApiCall(apiUrl, 'post', null, callback);
		return "";
	}

	BootSimply.prototype.getStore = function (_id, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/findById/" + this._name + "/" +_id;
		makeApiCall(apiUrl, 'get', null, callback);
		return "";
	}

	BootSimply.prototype.insertStore = function (data, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/add/" + this._name;        
        var requestObject = { "data": data};
        console.log("Data in insertStore method: "+requestObject);
		makeApiCall(apiUrl, 'post', JSON.stringify(requestObject), callback);
		return "";
	}

	BootSimply.prototype.updateStore = function (_id, data, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/modify/" +this._name +"/"+ _id;
		makeApiCall(apiUrl, 'put', data, callback);
		return "";
	}

	BootSimply.prototype.removeStore = function (_id, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/remove/" +this._name +"/"+ _id;
		makeApiCall(apiUrl, 'delete', null, callback);
		return "";
	}
    
    BootSimply.prototype.archive = function (_id, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/archive/" +this._name +"/"+ _id;
		makeApiCall(apiUrl, 'post', null, callback);
		return "";
	}
    
    BootSimply.prototype.softDelete = function (_id, callback) {
		var apiUrl = APPSPOT_ENDPOINT + "/softDelete/" +this._name +"/"+ _id;
		makeApiCall(apiUrl, 'post', null, callback);
		return "";
	}

	return BootSimply;

}());