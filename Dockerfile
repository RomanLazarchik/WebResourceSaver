# Use OpenJDK 17 base image
FROM openjdk:17-jdk-alpine

# Create a directory in the container where the app will be placed
RUN mkdir -p /app

# Set this as the working directory (where the .jar file will be copied to)
WORKDIR /app

# Copy the .jar file into the working directory
COPY ./target/WebResourceSaver-0.0.1-SNAPSHOT.jar /app

# Copy the application properties into the container. It will be picked up by Spring Boot
COPY ./src/main/resources/application.properties /app

# If you have additional configuration files, copy them into the container
# COPY ./additional-config.file /app

# Specify the command to run when the container is started
CMD ["java", "-jar", "WebResourceSaver-0.0.1-SNAPSHOT.jar"]

# The container will listen on port 8080 at runtime
EXPOSE 8080
