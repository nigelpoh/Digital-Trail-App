# Digital Trails App Repository

The **Digital Trails** project was created for the 1D Project of 50.001 Information Systems & Programming (SUTD 2023 Spring Term). This repository contains the implementation for the Android app component of the project.

***
**Important Note**

The app code here works as is, but the Firebase keys have been excluded from the repository for obvious security reasons. You can get it to run by:

1. **App** Include your own secrets.xml file within the app's resources and include your Firebase Web Client ID, labeled web_client_id (`R.string.web_client_id`)
2. **Server** An `application.properties` file specifying `spring.data.mongodb.uri`, `spring.devtools.restart.additional-paths`, `spring.devtools.restart.poll-interval`, `server.address` and `server.port`
3. **Server** A `credentials_google.json` file generated with Google for Firebase Authentication
***
