# dbs-recruitment-task

This is a Java Spring Boot application using MongoDB as the application database.

# Setup
**Prerequisite**
1. Install Java 13 or above
2. Install Maven https://maven.apache.org/install.html
3. Install Lombok https://projectlombok.org/download

Following show the detail steps to run this application with **Eclipse**:
1. Clone this project into local machine
2. Open Eclipse and import this project as Maven project
3. Run it as **Spring Boot App**
4. Open Web Browser with URL: http://localhost:8080/swagger-ui/
5. You may test the APIs under **controller** section

# Troubleshooting
There is a existing connection issue between Java and MongoDB, If you encountered error/exception about 
>SSLHandshakeException : should not be presented in certificate_request
  
  
Please add the argument below into **Run Configuration** (RUN -> Run Configurations -> Arguments Tab -> VM arguments)
>-Djdk.tls.client.protocols=TLSv1.2
  
Run the application again
 
