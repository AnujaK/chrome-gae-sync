chrome-gae-sync
==========

I realized that a lot of Chrome developers, including me, struggle to store their app data online and there is no quick and easy way to solve this problem. The open source project 'chrome-gae-sync' is a result of that.
![alt text](https://raw.githubusercontent.com/AnujaK/chrome-gae-sync/master/extras/Chrome-GAE-Sync_img.png "Initial Design")
##### What?

Supercharge your Chrome applications with a ready-to-use powerful Google App Engine backend. 


##### Why?

We happen to spend so much time in coding and testing data storage and fetching operations. Will it not be great if we get something to plug in and be able to access the Chrome app in the same state from anywhere as that of where we had left it?

You can be more productive in building the functionality of the app by using this project in your app.

##### How?

Just do some simple configurations and plug this project into yours. Using OAuth 2.0, your Chrome app will connect to Google App Engine and access its data.

Getting Started
==========

##### 1. Google App Engine


Go to https://appengine.google.com and create a new application (Enter application identifier, title and create it). I will use “chrome-gae-sync” as my application identifier in this documentation.

TODO : Add More details

##### 2. Google Developers Console


Go to https://console.developers.google.com and click on the GAE application identifier you created above. 

2.1. In the left navigation bar, under "APIS & AUTH" section, click on APIs and enable Google+API. Once done you will see Google+API at the top with status ON (ON button is displayed in green).

2.2. Generate Client ID for OAuth - TODO
In Google Developers Console, click on "Create new Client ID" under "APIS & AUTH". A pop-up appears which contains options to select type of application for which client ID is to be generated. We will need client ID for web application and Chrome application. Click on "Web application" as APPLICATION TYPE and in the textbox labeled as AUTHORIZED JAVASCRIPT ORIGINS, enter URL of your application. In this case we entered http://chrome-gae-sync.appspot.com, which is the deployment URL and http://localhost:8888 for local testing. You can enter multiple URLs. AUTHORIZED REDIRECT URI will get updated accordingly. Click on "Create Client ID".
We can even revisit the project later in the console and authorized origins.

To generate client ID for Chrome application, again click on "Create new Client ID" and select "Installed application" as APPLICATION TYPE and INSTALLED APPLICATION TYPE as "Chrome Application". Click on "Create Client ID".

2.3. Consent Screen - Select Email address, enter product name and click Save. You can also enter optional fields like home page url, logo etc.


##### 3. Chrome Application

3.1. Add/update the following in your manifest.json file:

```
"permissions": ["identity"],
"oauth2": {
    "client_id": "580486400136-3cthtr07f028k1f2cbfrt8ddbvhta0kv.apps.googleusercontent.com",
    "scopes": ["https://www.googleapis.com/auth/plus.login"]
}
```

Use the client ID you generated in section 2.2 above (for chrome application).

3.2. Publish your chrome application to web store (with the modified manifest file). This is mandatory to get the OAuth flow working (even locally).

3.3. Get chrome app key and update manifest.json again.

TODO : Add More details


Deployment
==========

TODO

Technology Stack
==========

TODO

Contribute
==========

You're interested in contributing to chrome-gae-sync? AWESOME. Here are the basic steps:

- Make sure you have a [GitHub Account](https://github.com/signup/free)
- Fork it from here : https://github.com/AnujaK/chrome-gae-sync
- Clone your fork  
- Make your changes
- Make sure everything is working fine
- Format your code (see below)
- Submit a pull request

##### GitHub help : 

- Forking a repo - https://help.github.com/articles/fork-a-repo
- Creating a pull request - https://help.github.com/articles/creating-a-pull-request
- Syncing a fork - https://help.github.com/articles/syncing-a-fork
 

Release Date
==========

Version 1.0 is coming soon.

Copyright and License
==========

Copyright 2014 AnujaK (a.for.anuja@gmail.com)

Licensed under Apache License, Version 2.0
