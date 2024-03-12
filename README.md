# Software Engineering Course Project, Haifa University 2023
## High School Test System - HSTS
### Group 1


## Based on an OCSF Mediator Example

## Structure
Pay attention to the three modules:
1. **client** - a simple client built using JavaFX and OCSF. We use EventBus (which implements the mediator pattern) in order to pass events between classes (in this case: between Client.SimpleClient and PrimaryController).
2. **server** - a simple server built using OCSF.
3. **entities** - a shared module where all the entities of the project live.

## How to run the project, via JetBrains IntelliJ
1. Run Maven clean:install **in the parent project**.
2. Run the server using the exec:java goal in the server module.
3. Run the client using the javafx:run goal in the client module.
4. Press the button and see what happens!


Ideally the project will be fully runnable via jar files..

Login Screen:
![Login Screen](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/f132c030-dc90-49b2-8ea2-1117b952e28c)

Teacher Main Screen Demo:
![Teacher Main Screen Demo](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/2f9f98f8-b44c-4d80-ba21-58f8641b3665)

