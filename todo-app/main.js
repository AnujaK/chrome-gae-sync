chrome.app.runtime.onLaunched.addListener(function() {
  chrome.app.window.create('index.html', {"id": "ChromeGaeSync", "bounds": { "width": 1024, "height": 768 } });
});