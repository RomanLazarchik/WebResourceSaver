# WebResourceSaver

## Overview
WebResourceSaver is a Spring Boot application designed to download resources from the web asynchronously based on a URL request and store them locally. The details about the downloaded resources are stored in a PostgreSQL database. The app utilizes Apache Tika to identify the MIME type and consequently, the file extension of the downloaded resource.

## Requirements
- Java 15 or later
- Maven
- Docker
- Docker-compose

## Setup

### PostgreSQL Database

The application uses PostgreSQL as its database. The configuration of the database connection is specified in the `application.properties` file.
You can modify these properties according to your setup.

### File Storage

The application downloads and stores the web resources in a specified file storage directory. This path can be configured using the `file.storage.path` property in the `application.properties` file.

Please make sure the path exists on your machine, if not you need to create it. For Linux and MacOS you can use the command:

```bash
mkdir -p /downloads
```
For Windows:

```bash
md \downloads
```
### Application properties

- `spring.datasource.url` - The JDBC url for the database.
- `spring.datasource.username` - The username for the database.
- `spring.datasource.password` - The password for the database.
- `spring.jpa.hibernate.ddl-auto` - DDL mode. Here it's set to "update", meaning the schema will be automatically updated to reflect the entities.
- `spring.jpa.show-sql` - Enables logging of the SQL statements.
- `s'pring.jpa.properties.hibernate.format_sql` - If enabled, Hibernate will format SQL logs.
- `file.storage.path` - The directory where downloaded web resources are stored.

## Building and Running the App

You can build and run the application using Docker and Docker Compose. Here are the steps:

1. Navigate to the root directory of the project.
2. Build the Docker image and start the services with Docker Compose:
```bash
   docker-compose up --build
```
## Usage

To save a resource, make a POST request to the `/resource` endpoint with the URL of the resource:
```bash
   curl -X POST -H "Content-Type: application/json" -d '{"url":"http://example.com"}' http://localhost:8080/resource
```
Replace `"http://example.com"` with the URL of the resource you want to download.

## Async Processing

The application uses Spring's `@Async` annotation to enable asynchronous processing. A custom `Executor` bean named `asyncExecutor`
is defined in the `AsyncConfiguration` class and is used to execute these async tasks.

The `ResourceDownloadService` uses this executor to asynchronously download the web resources.

## Error Handling

The `GlobalExceptionHandler` class handles the `ResourceDownloadException` exceptions. 
These exceptions might occur during the downloading and storing of a resource. 
When such an exception occurs, an error log is created and a response with HTTP status 500 (Internal Server Error) is returned.