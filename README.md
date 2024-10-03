# Thought-to-Note Lite Backend

## Overview

This project is the backend for the Thought-to-Note Lite application, built using Spring Boot. It uses PostgreSQL as the database, managed via Docker Compose.

## Prerequisites

- **Java Version**: 21.0.2
- **Maven Version**: 3.9.8
- **Operating System**: macOS 
- **Spring Boot Version**: 3.3.2
- **Docker** and **Docker Compose**
- **IntelliJ IDEA** (or any Java IDE)


---

## Project Structure

> This project adopts a **layered architecture** approach to organize the codebase into distinct layers, enhancing modularity, maintainability, and clarity. The structure includes key layers such as `controller`, `service`, `repository`, `model`, `config`, `dto`, and `exception`, along with additional directories for API documentation, Docker configurations, and various build artifacts. This setup promotes clear separation of concerns, facilitating easier management and extension of the application.

```
ThoughtToNoteLite-Backend/
├── api/
│   └── postman_collection.json         # Postman collection for API testing
├── docker/
│   └── compose.yaml                     # Docker Compose configuration
├── documentation.md                     # Project documentation
├── mvnw                                 # Maven wrapper script (Unix-based)
├── mvnw.cmd                             # Maven wrapper script (Windows)
├── pom.xml                              # Maven build file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── thoughttonotelite/
│   │   │           ├── DataInitializer.java        # Initializer for application data
│   │   │           ├── ThoughtToNoteLiteBeApplication.java  # Main application class
│   │   │           ├── config/
│   │   │           │   └── SecurityConfig.java     # Security configuration
│   │   │           ├── controller/
│   │   │           │   └── NoteController.java      # Controller for managing notes
│   │   │           ├── dto/
│   │   │           ├── exception/
│   │   │           ├── model/
│   │   │           │   └── Note.java               # Model representing a note
│   │   │           ├── repository/
│   │   │           │   └── NoteRepository.java      # Repository interface for note operations
│   │   │           ├── service/
│   │   │           │   └── NoteService.java         # Service class for note-related business logic
│   │   │           └── utils/
│   │   │               └── PasswordEncoderUtil.java # Utility class for password encoding
│   │   └── resources/
│   │       ├── application.properties             # Main configuration file
│   │       ├── data/
│   │       │   └── notes-data.json                # Sample data for notes
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── thoughttonotelite/
│                   ├── ThoughtToNoteLiteBeApplicationTests.java  # Main application test class
│                   ├── controller/
│                   │   └── NoteControllerTest.java   # Tests for NoteController
│                   ├── model/
│                   │   └── NoteEntityTest.java      # Tests for Note model
│                   └── service/
│                       └── NoteServiceTest.java     # Tests for NoteService
├── thought-to-note-lite-be.iml
```

### Description of Key Components

- **`api/`**: Contains the Postman collection used for testing the API endpoints.
- **`docker/`**: Includes Docker Compose configuration to manage dependencies like databases.
- **`documentation.md`**: Provides detailed documentation for the project, including setup and usage instructions.
- **`mvnw` and `mvnw.cmd`**: Maven wrapper scripts for running Maven commands without requiring a local Maven installation.
- **`pom.xml`**: The Maven build file that manages project dependencies and build configurations.
- **`src/`**: Contains the source code and resources for the application.
   - **`main/java/com/thoughttonotelite/`**: The main application code organized into packages for configuration, controllers, data transfer objects (DTOs), exceptions, models, repositories, services, and utilities.
   - **`main/resources/`**: Resource files such as configuration files and static data.
   - **`test/java/com/thoughttonotelite/`**: Unit and integration tests organized similarly to the main source code.
- **`thought-to-note-lite-be.iml`**: IntelliJ IDEA project file.


## Docker and PostgreSQL Setup

### Docker Compose Configuration

The `docker/compose.yaml` file is used to manage the PostgreSQL database service for this project. It creates a containerized PSQL Database. This file is configured to pull and run PostgreSQL version 16.

Here is a snippet of the `compose.yaml` file:

```yaml
services:
  postgres:
    image: 'postgres:16'  
    environment:
      - 'POSTGRES_DB=thoughttonotelitedb'
      - 'POSTGRES_USER=postgres'
      - 'POSTGRES_PASSWORD=postgres'
    ports:
      - '5432:5432'
```

### Spring Boot Integration

To ensure that Spring Boot automatically manages the Docker Compose services, the following configuration has been added to the `application.properties` file:

```properties
spring.application.name=thought-to-note-lite-be

# Docker Compose file location
spring.docker.compose.file=docker/compose.yaml

# Database connection settings
spring.datasource.url=jdbc:postgresql://localhost:5432/thoughttnotelitedb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA and Hibernate settings
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```


---

## Running the Project

### 1. **Set Up Maven Wrapper**

To ensure consistency across different environments, it's recommended to set up the Maven Wrapper. This allows everyone working on the project to use the same Maven version.

- **Generate Maven Wrapper Files**:
  - Navigate to the root directory of your Spring Boot project and run the following command:
    ```bash
    mvn -N io.takari:maven:wrapper
    ```
  - This command will create a `.mvn/wrapper` directory containing the necessary Maven Wrapper files.

- **Using Maven Wrapper**:
  - Once the Maven Wrapper is set up, replace the `mvn` command with `./mvnw` (on Unix/Linux/macOS) or `mvnw.cmd` (on Windows) in your commands. Take into consideration that to run all tests the database inside the docker container must have been initialized because some tests use transactions. For example, to run all tests:
    ```bash
    ./mvnw test
    ```

### 2. **Run the Spring Boot Application**

You can start the Spring Boot application using Maven, which will automatically start the Docker container alongside the application server.

- **Run the Application**:
  ```bash
  ./mvnw spring-boot:run
  ```
  - The application will connect to the PostgreSQL database managed by Docker Compose.

### 3. **Start Docker Compose Services Manually (Optional)**

The Docker Compose services are automatically started when you run the application using the Maven command above. However, if you prefer to start them manually, you can do so with the following command:

- **Start Docker Compose Services**:
  ```bash
  docker-compose -f docker/compose.yaml up -d
  ```


By following these steps, you ensure that your project is set up with a consistent Maven environment and that the application and its dependencies are correctly started, either automatically or manually, depending on your preference.

---


## API Documentation

The Thought-to-Note Lite API provides a set of endpoints to manage `Note` entities. This API supports typical CRUD operations (Create, Read, Update, Delete) as well as a search functionality to retrieve notes by title. The following documentation is based on the provided Postman collection.

### Base URL

```
http://localhost:8080/api/notes
```

### Authentication

- **Basic Authentication:** All API endpoints require basic authentication.
- **Username:** `yourUsername`
- **Password:** `yourPassword`

### Endpoints

#### 1. **Get All Notes**

- **Endpoint:** `/api/notes`
- **Method:** `GET`
- **Description:** Retrieves a list of all notes.
- **Response:**
   - **200 OK:** Returns a JSON array of notes.

- **Example Request:**

```http
GET http://localhost:8080/api/notes
```

#### 2. **Get Note by ID**

- **Endpoint:** `/api/notes/{id}`
- **Method:** `GET`
- **Description:** Retrieves a specific note by its ID.
- **Path Variable:**
   - **`id`** (integer): The ID of the note to retrieve.
- **Response:**
   - **200 OK:** Returns the note corresponding to the specified ID.
   - **404 Not Found:** If the note does not exist.

- **Example Request:**

```http
GET http://localhost:8080/api/notes/1
```

#### 3. **Create Note**

- **Endpoint:** `/api/notes`
- **Method:** `POST`
- **Description:** Creates a new note.
- **Request Headers:**
   - **Content-Type:** `application/json`
- **Request Body:**
   - **`title`** (string): The title of the note.
   - **`content`** (string): The content of the note.
- **Response:**
   - **201 Created:** Returns the created note.
   - **400 Bad Request:** If the input data is invalid.

- **Example Request:**

```http
POST http://localhost:8080/api/notes
Content-Type: application/json

{
  "title": "New Note",
  "content": "This is a new note."
}
```

#### 4. **Update Note**

- **Endpoint:** `/api/notes/{id}`
- **Method:** `PUT`
- **Description:** Updates an existing note.
- **Path Variable:**
   - **`id`** (integer): The ID of the note to update.
- **Request Headers:**
   - **Content-Type:** `application/json`
- **Request Body:**
   - **`title`** (string): The updated title of the note.
   - **`content`** (string): The updated content of the note.
- **Response:**
   - **200 OK:** Returns the updated note.
   - **404 Not Found:** If the note does not exist.
   - **400 Bad Request:** If the input data is invalid.

- **Example Request:**

```http
PUT http://localhost:8080/api/notes/1
Content-Type: application/json

{
  "title": "Updated Note",
  "content": "This is an updated note."
}
```

#### 5. **Delete Note**

- **Endpoint:** `/api/notes/{id}`
- **Method:** `DELETE`
- **Description:** Deletes a note by its ID.
- **Path Variable:**
   - **`id`** (integer): The ID of the note to delete.
- **Response:**
   - **204 No Content:** If the note was successfully deleted.
   - **404 Not Found:** If the note does not exist.

- **Example Request:**

```http
DELETE http://localhost:8080/api/notes/1
```

#### 6. **Search Notes by Title**

- **Endpoint:** `/api/notes/search`
- **Method:** `GET`
- **Description:** Searches for notes by their title.
- **Query Parameter:**
   - **`title`** (string): The keyword to search for in the note titles.
- **Response:**
   - **200 OK:** Returns a JSON array of notes that match the search criteria.

- **Example Request:**

```http
GET http://localhost:8080/api/notes/search?title=Test
```

### Notes

- Ensure that each request includes the correct authentication credentials using **Basic Auth** in tools like Postman.
- All requests and responses use `application/json` as the content type.

### Postman Collection

To facilitate testing and development, a Postman collection (`postman_collection.json`) is included. This collection contains pre-configured requests for all the endpoints mentioned above. You can import this collection into Postman to quickly start testing the API.

---


## Code Structure and Design

#### 1. **`Note` Model Entity**

The `Note` entity is the core data model used in the Thought-to-Note Lite application to represent a note. It is mapped to the `notes` table in the PostgreSQL database using JPA annotations. The entity includes fields such as `id`, `title`, `content`, `createdAt`, and `updatedAt`, which correspond to the columns in the database table.

- **Database Schema:**
   - **Table Name:** `notes`
   - **Columns:**
      - **`id`** (BIGINT, Primary Key, Auto-Increment): The unique identifier for each note.
      - **`title`** (VARCHAR): Stores the title of the note.
      - **`content`** (TEXT): Holds the content of the note, allowing for larger text entries.
      - **`createdAt`** (TIMESTAMP): Records when the note was created, automatically set upon creation.
      - **`updatedAt`** (TIMESTAMP): Logs when the note was last updated, automatically set upon creation and update.

- **Automatic Timestamps:**
   - The `@PrePersist` method `onCreate()` automatically sets the `createdAt` and `updatedAt` timestamps when a new note is created.
   - The `@PreUpdate` method `onUpdate()` updates the `updatedAt` timestamp whenever an existing note is modified.

This entity allows for seamless interaction with the database, where each instance of `Note` corresponds to a row in the `notes` table.

#### 2. **`NoteService` Class**

The `NoteService` class is responsible for implementing the business logic related to the `Note` entity. It serves as a mediator between the `NoteController` and the `NoteRepository`.

- **CRUD Operations:**
   - **Create:** Saves a new `Note` entity to the database, which automatically generates and assigns an `id`, `createdAt`, and `updatedAt` fields based on the current state of the entity.
   - **Read:** Retrieves notes either by their ID or as a list of all notes from the `notes` table.
   - **Update:** Modifies an existing `Note` entity and updates its `updatedAt` field before saving the changes back to the database.
   - **Delete:** Removes a `Note` from the `notes` table by its ID.

The service layer ensures that any business rules are enforced before interacting with the database through the repository. For example, it checks if a note exists before updating or deleting it.

- **Search Functionality:** The service includes a method to search for notes by their title, allowing users to find notes based on partial matches within the `title` column.

#### 3. **`NoteController` Class**

The `NoteController` is a REST controller that handles HTTP requests related to the `Note` entity. It exposes endpoints for the following operations:

- **Create a Note:** Handles `POST` requests to create a new note, which results in a new row in the `notes` table.
- **Retrieve Notes:** Handles `GET` requests to retrieve all notes or a specific note by its ID from the `notes` table.
- **Update a Note:** Handles `PUT` requests to update an existing note, modifying the corresponding row in the `notes` table.
- **Delete a Note:** Handles `DELETE` requests to remove a note by its ID from the `notes` table.
- **Search Notes:** Handles `GET` requests to search for notes by their title, querying the `notes` table for matches.

Each endpoint corresponds to a method in the `NoteService` class, and the controller is responsible for mapping the HTTP requests to these service methods. It also handles the construction of appropriate HTTP responses, such as returning `201 Created` for successful creation or `404 Not Found` when a note doesn't exist.

#### 4. **`DataInitializer` Class**

The `DataInitializer` class is a Spring component that implements `ApplicationRunner`. This class is used to load initial data into the `notes` table in the database when the application starts, but only if the table is empty.

- **Data Initialization:**
   - **JSON Data Loading:** The initializer checks if the `notes` table is empty by counting the number of entries. If it’s empty, it loads data from a JSON file (`notes-data.json`) located in the `resources/data` directory.
   - **Object Mapping:** The JSON data is read and converted into a list of `Note` entities using Jackson's `ObjectMapper`.
   - **Database Insertion:** The list of `Note` entities is then saved to the database using the `NoteRepository`, populating the `notes` table.

This mechanism ensures that the application starts with some sample data, which is useful for development and testing. If the database already contains data, the initializer skips the data loading step.

#### 5. **JPA, JDBC, and Database Schema in the Application**

The Thought-to-Note Lite backend application effectively integrates both JPA (Java Persistence API) and JDBC (Java Database Connectivity) to manage database interactions, leveraging the strengths of each to ensure efficient and reliable data operations.

##### **JPA (Java Persistence API)**
JPA is the primary tool used in this application for interacting with the PostgreSQL database. It simplifies the process of mapping Java objects (entities) to database tables and managing the lifecycle of these objects within the database. Key features include:

- **Entity Mapping:** The `Note` entity is annotated with JPA annotations such as `@Entity`, `@Table`, and `@Id`, which map the class and its fields directly to the corresponding database table and columns (`notes` table).
- **Repository Abstraction:** The `NoteRepository` interface extends `JpaRepository`, providing a high-level abstraction for CRUD operations on the `notes` table. This allows developers to perform database operations without writing explicit SQL queries, making the code cleaner and easier to maintain.
- **Hibernate Integration:** Hibernate, the default JPA implementation used in this Spring Boot application, automatically handles the SQL generation and execution based on the JPA annotations. Configuration properties like `spring.jpa.hibernate.ddl-auto=update` ensure that the database schema, including the `notes` table, is automatically updated to match the current state of the entities.

##### **JDBC (Java Database Connectivity)**
While JPA handles most of the data interactions, JDBC is inherently employed in the background to execute the SQL queries generated by Hibernate (the JPA provider). The application explicitly defines JDBC properties within the `application.properties` file, ensuring that JDBC is configured and used correctly:

- **JDBC URL:** The `spring.datasource.url` property specifies the connection URL to the PostgreSQL database using JDBC. This property is critical as it directly influences how the application connects to the database.
- **Driver Configuration:** The `spring.datasource.driver-class-name` property points to the PostgreSQL JDBC driver, ensuring that JDBC can communicate with the PostgreSQL database.
- **Direct SQL Execution:** Although JPA abstracts most SQL operations, JDBC is still the underlying mechanism that performs the actual database interactions. When JPA methods are called, the SQL generated by Hibernate is executed via JDBC.

- **Database Schema Integration:** JPA automatically manages the database schema, creating and updating tables like `notes` based on the entity definitions. JDBC ensures these operations are executed efficiently within the PostgreSQL database.

In summary, the application’s integration of JPA and JDBC allows for seamless and efficient interaction with the PostgreSQL database, ensuring that the database schema is properly managed and that data operations are executed reliably. The configuration ensures that the application’s entities, such as `Note`, are effectively mapped to the database, with Hibernate handling the ORM (Object-Relational Mapping) duties and JDBC executing the necessary SQL commands.

---


## Testing

The Thought-to-Note Lite backend includes a comprehensive suite of tests that cover the various layers of the application. These tests ensure that the application behaves as expected under different conditions and that the individual components work together correctly.

>To run all the tests for the Thought-to-Note Lite backend, you can use the following command:

```bash
./mvnw test
```

This command will execute the entire test suite, covering controller, entity, service, and application context tests. It ensures that the application behaves as expected across all layers, providing confidence in the code's quality and robustness.

#
### 1. **Controller Tests (`NoteControllerTest`)**

These tests focus on the `NoteController` class, which handles HTTP requests related to the `Note` entity. The tests are designed to verify the correctness of the controller's behavior, including security aspects such as authentication and CSRF protection.

- **Framework:** The `WebMvcTest` annotation is used to configure the test environment, focusing only on the web layer. `MockMvc` is utilized to send mock HTTP requests and verify responses.
- **Security:** The `WithMockUser` annotation is used to simulate an authenticated user with specific roles during testing.
- **Key Tests:**
   - **Retrieving all notes:** Ensures that a `GET` request returns the correct list of notes.
   - **Creating a note:** Verifies that a `POST` request correctly creates a new note.
   - **Updating a note:** Checks that a `PUT` request updates an existing note.
   - **Deleting a note:** Confirms that a `DELETE` request successfully removes a note.
   - **Retrieving a note by ID:** Ensures that a `GET` request fetches the correct note by its ID.
   - **Searching notes by title:** Validates that a `GET` request with a search parameter returns the correct list of matching notes.

### 2. **Entity Tests (`NoteEntityTest`)**

These tests focus on the persistence and integrity of the `Note` entity within the database. The tests ensure that the entity's fields are correctly populated and persisted in the database.

- **Framework:** The `SpringBootTest` annotation is used to load the full application context, and `EntityManager` is used to directly interact with the persistence context.
- **Transactional Testing:** Tests are run within a transaction to ensure database integrity and to roll back changes after each test.
- **Key Test:**
   - **Entity Persistence:** Verifies that a `Note` entity is correctly persisted, with `id`, `createdAt`, and `updatedAt` fields set appropriately.

### 3. **Service Tests (`NoteServiceTest`)**

These tests focus on the business logic implemented in the `NoteService` class. The tests ensure that the service methods correctly interact with the `NoteRepository` and return the expected results.

- **Framework:** The `MockitoExtension` is used to inject mocks into the service, isolating it from dependencies like the repository.
- **Key Tests:**
   - **Creating a note:** Ensures that the service correctly saves a new note.
   - **Updating a note:** Verifies that the service correctly updates an existing note.
   - **Deleting a note:** Confirms that the service correctly deletes a note by its ID.
   - **Retrieving all notes:** Checks that the service retrieves all notes.
   - **Retrieving a note by ID:** Ensures that the service fetches the correct note by its ID.
   - **Searching notes by title:** Validates that the service returns the correct list of notes based on the title search.

### 4. **Application Context Test (`ThoughtToNoteLiteBeApplicationTests`)**

This test verifies that the Spring Boot application context loads successfully without any issues.

- **Framework:** The `SpringBootTest` annotation is used to load the full application context.
- **Key Test:**
   - **Context Load:** Ensures that the application context starts up correctly, indicating that all components are properly configured.


These tests collectively ensure the reliability of the Thought-to-Note Lite backend, covering critical aspects from HTTP request handling and business logic to database interactions and application context configuration. By thoroughly testing each layer, the application is well-positioned to handle real-world usage scenarios effectively.



## Security Configuration

The Thought-to-Note Lite backend application incorporates **Spring Security** to manage authentication and secure the application's endpoints. However, during development, certain configurations have been simplified to ease testing and development workflows.

### Spring Security Overview

- **Authentication:** The application requires basic authentication for all HTTP requests. This ensures that only authenticated users can access the API endpoints.
- **Authorization:** All requests are secured, meaning that users must be authenticated before they can interact with any part of the application.
- **Password Handling:** For simplicity during development, the application uses `NoOpPasswordEncoder`, which stores passwords in plain text. This approach is not secure for production environments but is convenient for development and testing purposes.

### Security Configuration Details

The `SecurityConfig` class is responsible for configuring the security settings:

- **CSRF Protection:** CSRF (Cross-Site Request Forgery) protection is disabled (`csrf.disable()`) to simplify testing, particularly when using tools like Postman. In production, it is recommended to enable CSRF protection to secure the application against CSRF attacks.
- **Basic Authentication:** The application uses basic authentication, which requires the user to provide a username and password with each request. This is configured with the `httpBasic()` method.
- **Password Encoding:** The `NoOpPasswordEncoder` is used, which does not apply any hashing or encryption to passwords. This encoder is suitable only for development and testing; in production, a stronger encoder like `BCryptPasswordEncoder` should be used.

### Development Credentials

To simplify development, default user credentials have been defined in the `application.properties` file:

- **Username:** `yourUsername`
- **Password:** `yourPassword`
- **Role:** `USER`

These credentials allow access to all API endpoints. When testing the application with tools like Postman, you must include these credentials in the request:

- **In Postman:**
   - Use the **Basic Auth** option under the **Authorization** tab.
   - Enter `yourUsername` as the username and `yourPassword` as the password.

This setup ensures that the application is secure enough for development purposes while still being accessible and easy to test.

### Important Note

This security configuration is specifically tailored for the development phase. In a production environment the following should be implemented:
- Enable CSRF protection.
- Replace `NoOpPasswordEncoder` with a secure password encoder like `BCryptPasswordEncoder`.
- Store credentials securely, and ensure they are not hard-coded or exposed in configuration files.

