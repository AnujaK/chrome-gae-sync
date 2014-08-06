chrome-gae-sync
==========

##### What?

A ready to use and tested backend for your Chrome applications is chrome-gae-sync. As the name implies, it will connect your application to Google App Engine. 


##### Why?

We happen to spend so much time in coding and testing CRUD operations. Will it not be great if we get something to plug in and be able to access the Chrome app from where we had left it last time? 

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
