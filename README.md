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

### SWAGGER UI

http://localhost:8080/swagger-ui/index.html

### CODE COVERAGE

Please look into the target/site/jacoco folder for de index.html file

