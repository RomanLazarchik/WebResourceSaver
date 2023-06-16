# Web Resource Saver

Web Resource Saver is a Java-based, Spring Boot application that asynchronously downloads resources from the web given a valid URL and then saves them to a local file system. This application makes use of Spring's async capabilities, Reactor's project for non-blocking applications, JPA for data persistence, and Apache Tika for handling media types.

## Key Components

### 1. AsyncConfiguration:

A Spring `@Configuration` annotated class which creates a `ThreadPoolTaskExecutor` bean. This is utilized to execute methods annotated with `@Async`, allowing for asynchronous execution in separate threads.

### 2. ResourceController:

A `@RestController` annotated class that exposes a POST endpoint `/resource` which accepts a `UrlRequest` object. This object contains the URL from which the resource is to be downloaded. This controller uses `ResourceDownloadService` to download the resource asynchronously.

### 3. ResourceDownloadService:

A `@Service` annotated class that uses `WebClient` to send HTTP requests to download resources. It processes the response, identifies the content type, and extracts file extension based on the content type. The downloaded resource is then stored using `ResourceStorageService`, and the details (URL and file path) are saved into the database using `ResourceRepository`.

### 4. ResourceStorageService:

A `@Service` annotated class that is responsible for storing the downloaded data to the file system.

### 5. GlobalExceptionHandler:

A `@ControllerAdvice` annotated class that handles the `ResourceDownloadException`, allowing for centralized exception handling across the application.

### 6. ResourceDownloadException:

A custom `RuntimeException` to handle any errors that occur during resource download.

### 7. UrlRequest & Resource:

Model classes representing the incoming URL request and the resource details to be saved into the database respectively.

### 8. ResourceRepository:

A JPA Repository interface extending `JpaRepository` which provides methods for CRUD operations on `Resource` objects.

### 9. WebResourceSaverApplication:

The main application class that includes the `main` method which starts the Spring Boot application.

## Configuration

The application is configured to connect to a PostgreSQL database and uses Hibernate as the JPA provider. The database connection pool is managed by HikariCP. The thread pool settings for async execution and file storage path are also configurable.

To start the application, simply run the `main` method of `WebResourceSaverApplication`.

Please ensure to update the database configuration, file storage path in the properties file according to your setup.
