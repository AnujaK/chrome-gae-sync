'use strict';

var CgsClass = function () {

	var APPSPOT_URL = "https://chrome-gae-sync.appspot.com";

	var STORE_URL = APPSPOT_URL + "/_ah/api/storeendpoint/v1/store";

	var _name;
	return {
		init: function (_name) {
			this._name = _name;
			listStore();
		}
	}

	function listStore(cursor, limit, callback) {
		var apiUrl = STORE_URL + "/";
		makeApiCall(apiUrl, 'get', null, callback);
		return "";
	}

	function getStore(_id, callback) {
		var apiUrl = STORE_URL + "/" + _id;
		makeApiCall(apiUrl, 'get', null, callback);
		return "";
	}

	function insertStore(data, callback) {
		var apiUrl = STORE_URL + "/";
		makeApiCall(apiUrl, 'post', data, callback);
		return "";
	}

	function updateStore(_id, data, callback) {
		var apiUrl = STORE_URL + "/" + _id;
		makeApiCall(apiUrl, 'put', data, callback);
		return "";
	}

	function removeStore(_id, callback) {
		var apiUrl = STORE_URL + "/" + _id;
		makeApiCall(apiUrl, 'delete', null, callback);
		return "";
	}

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
					console.log("response : " + response);
					if (callback && callback != null) {
						callback(response);
					}

				}
			});
		}
	}
}

	function start() {
		CgsClass().init("test");
	}

window.onload = start;