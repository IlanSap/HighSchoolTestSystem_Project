# Software Engineering Course Project, Haifa University 2023
## High School Test System - HSTS
The High School Test System is a computerized information system designed for managing exams in a high school. Its main objectives are to centralize all exam-related activities, enhance efficiency, and improve the reliability of exam processing. The system aims to achieve the following goals:

1. Create exams at two levels: (a) Manage a question bank for various subjects, and (b) Generate exams from the question bank.
2. Facilitate exam administration.
3. Conduct exam grading and record scores.
4. Data processing, analysis, and presentation, including generating various statistics on exams, scores, and student performance.
We utilized Java (OCSF server-clent framework, EVENTBUS), JavaFX GUI, SQL, Hibernate and Git.

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


Some examples:

Login Screen:
![Login Screen](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/f132c030-dc90-49b2-8ea2-1117b952e28c)

Student- Digital Exam Screen:
![Student- Digital Exam Screen](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/122ab368-c7eb-4fbf-a943-0bae984ea560)

Teacher- Main Screen:
![Teacher Main Screen Demo](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/2f9f98f8-b44c-4d80-ba21-58f8641b3665)

Teacher- View Questions Screen:
![Teacher- View Questions Screen](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/62c49dec-aa33-4a63-b35d-68375da843e0)

Teacher- Add Questions Screen:
![Teacher- Add Question Screen](https://github.com/IlanSap/HighSchoolTestSystem_Project/assets/106005589/a56d145e-e782-4ad9-a022-5037724bf733)



