# BLOG API

code challenge for SOLVEDEX by Felipe Díaz C. felipediazc@gmail.com

### SOFTWARE REQUIREMENTS

You need MAVEN and JAVA 1.17 installed in your PC (MAC/LINUX).

### HOW TO PACKAGE ?

    mvn clean install

### HOW TO TEST CHANGES ?

Please run the following command:

      mvn clean install -DskipTests


### HOW TO DO UNIT TESTING ?

If you want to do the unit and integration test, please use the following instruction:

    mvn test

### HOW TO PACKAGE WITHOUT TESTING ?

    mvn clean install -DskipTests


### HOW TO DOWNLOAD ALL DEPENDENCIES

    mvn install dependency:copy-dependencies

### HOW TO RUN (Default port is 8080)
There is two alternatives to run de application

    mvn spring-boot:run

Or 

    java -jar target/blogapi-0.0.1-SNAPSHOT.jar

### DOCUMENTATION -> SWAGGER UI

Please enter to this URL, you will find all endpoints 

http://localhost:8080/swagger-ui/index.html

Each one, has the required documentation to use it, keep in mind:

1. Most of the services requires the authentication token. Then, you need to use the signIn endpoint in order to get authenticated.  Copy the authentication token and use it in the endpoints you need
2. There are default data for using this project, the following users and passwords are available:

username/password

    test1/test1
    test2/test2
    test3/test3

### CODE COVERAGE

Please look into the target/site/jacoco folder for de index.html file

### H2 CONSOLE

http://localhost:8080/h2-console/


