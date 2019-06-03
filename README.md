# A Simple Chat App with JWT authentication

## General description
The aim of this project is to create a web app where multiple users can exchange text messages through websocket. 

## Requirements
* Java 8+ backend with Spring Boot 2
* Use webflux/reactor
* Use functional style where possible
* Web UI should be delivered as a html page from Spring Boot, so no separate repo for the frontend
* Implement a basic auth (username/password) for security, create a JSON Web Token (JWT) for the user who logged in.
* After the first login, the user should be able to use the JWT token to send/receive messages
* The web UI should show username and timestamp for each message

## Bonuses 
* Persist all messages in Mongodb
* Restore users’ messages 
* Let users create private groups, messages posted in a group are not visible to users outside the group
* Go beyond text messages, send an image, for example

## Time
You have 7 working days to finish the project. Try to get some of the bonuses if you have time.

----------------------------------------------------------------------------------------------------------

## Features
1. Signup
2. Login
3. Delete account (no frontend)
4. Add group with members
5. See group members (only the newly created group)
6. Send message to specific group


## How to Develop
### Frontend
- `npm install`
- `ng serve --aot`
- `ng build --aot --prod` (dist is inside the `resources/static` folder of Spring Boot)

### Backend
- `Run App.java class inside IDE`
- `mvn clean install`
- `java -jar ./target/chatapp-1.0-SNAPSHOT.jar`

## Missing features
- Why no WebFlux and Stomp:
  - https://github.com/spring-projects/spring-boot/issues/14069
  - https://github.com/spring-projects/spring-boot/issues/12932

- No Mongo, because too little experience and no time to look stuff up.
- No Bonuses, because I didn't have time left.
