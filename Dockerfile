FROM openjdk:17-slim

WORKDIR /app

COPY pom.xml ./
COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java","-jar","/app/app.jar"]
