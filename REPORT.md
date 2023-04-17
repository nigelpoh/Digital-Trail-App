# 50.001 Digital Trails Project Report

This project has been split into two repositories for easy reading, app (the one you are currently looking at) and backend ([here](https://github.com/nigelpoh/Digital-Trails-Backend)). 
***
> **Important Note**
>
> The app code here works as is, but the Firebase keys have been excluded from the repository for obvious security reasons. You can get it to run by:
>
> 1. **App** Include your own secrets.xml file within the app's resources and include your Firebase Web Client ID, labeled web_client_id (`R.string.web_client_id`)
> 2. **Server** An `application.properties` file specifying `spring.data.mongodb.uri`, `spring.devtools.restart.additional-paths`, `spring.devtools.restart.poll-interval`, `server.address` and `server.port`
> 3. **Server** A `credentials_google.json` file generated with Google for Firebase Authentication
***

## Our Problem

Given the lack of clear recommendations of what to do when coming to Singapore for tours based on the current conditions (e.g., weather and time) and the lack of location specific information at all attractions since information boards are not available everywhere (causing tourists to have to Google for answers on their own), how might we make touring Singapore a more convenient, engaging and interactive experience?
