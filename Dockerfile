# Use OpenJDK for base image
FROM openjdk:17-jdk-buster

# The application's .jar file
ARG JAR_FILE=target/*.jar

# Add Maintainer Info
LABEL maintainer="example@example.com"

# Change directory in docker image
WORKDIR /usr/src/app

# Copy the application's .jar to the container
COPY ${JAR_FILE} app.jar

# Run the .jar file
ENTRYPOINT ["java","-jar","/app.jar"]