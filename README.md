# Thought-to-Note Lite Backend

# 📑 **Table of Contents**

1. **🔍 [Overview](#overview)**
2. **🚀 [Quick Start Guide](#quick-start-guide)**
   - 📂 **[Clone the Repository](#1-clone-the-repository)**
   - 🐳 **[Set Up Docker and PostgreSQL](#2-set-up-docker-and-postgresql)**
   - ☕ **[Set Up Maven Wrapper](#3-set-up-maven-wrapper)**
   - 🚦 **[Run the Spring Boot Application](#4-run-the-spring-boot-application)**
   - ✅ **[Verify the Application](#5-verify-the-application)**
3. **📌 [Prerequisites](#prerequisites)**
4. **🗂️ [Project Structure](#project-structure)**
5. **⚙️ [Docker and PostgreSQL Setup](#docker-and-postgresql-setup)**
6. **▶️ [Running the Project](#running-the-project)**
7. **📖 [API Documentation](#api-documentation)**
   -  🌐 **[Base URL](#base-url)**
   - 🔑 **[Authentication](#authentication)**
   - 📜 **[Swagger UI Access](#accessing-the-swagger-ui)**
   - 📌 **[Endpoints Overview](#endpoints-overview)**
   - 🚫 **[Disable Security for Development](#disable-security-for-development)**
   - 📬 **[Postman Collection](#postman-collection)**
8. **🏗️ [Code Structure and Design](#code-structure-and-design)**
9. **🧪 [Testing](#testing)**
10. **🔒 [Security Configuration](#security-configuration)**
11. **✨ [Final Notes](#final-notes)**

---

> [!TIP]
> For a comprehensive overview of how the Thought-to-Note Lite system works, including both backend and frontend components, please refer to the Central Documentation. This central guide explains the system architecture, setup, interaction, and testing in detail. To explore the frontend client, which interacts with this backend API, visit the [Frontend Repository](https://github.com/DimWebDev/thought-to-note-lite-fe/tree/develop)


## Overview

This project serves as the backend for the Thought-to-Note Lite application, built using Spring Boot. It acts as a core microservice, providing an API that manages note-related data and business logic. The backend uses PostgreSQL as the database, managed via Docker Compose, to ensure consistency and scalability.

The frontend of the Thought-to-Note Lite application is a React client, which communicates with this backend through RESTful API calls. Together, the frontend and backend offer a complete solution for managing and storing user notes, providing a seamless user experience.

## Quick Start Guide

Follow these steps to quickly set up and run the Thought-to-Note Lite backend on your local machine.

### 1. Clone the Repository

First, clone the repository to your local machine:

```bash
git clone <repository-url>
cd ThoughtToNoteLite-Backend
```

### 2. Set Up Docker and PostgreSQL

Ensure that Docker and Docker Compose are installed. Then, use Docker Compose to start the PostgreSQL database:

```bash
docker-compose -f docker/compose.yaml up -d
```

This command will create a containerized PostgreSQL database instance needed for the backend.

### 3. Set Up Maven Wrapper

Run the following command to generate and use the Maven Wrapper (this ensures consistent Maven versions):

```bash
mvn -N io.takari:maven:wrapper -Dmaven=3.9.8
```

### 4. Run the Spring Boot Application

Use the Maven Wrapper to start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

### 5. Verify the Application

After running the application, verify that it is working by accessing the base URL in your browser or through a tool like Postman:

```
http://localhost:8080/api/notes
```

You should see the application running successfully.

---

## Prerequisites

These technologies were used for the local development of the project. However, the project is compatible with multiple operating systems, including Windows and Linux, **as demonstrated by the GitHub Actions pipeline which runs successfully on a Linux environment.**

- **Java Version**: 21.0.2
- **Maven Version**: 3.9.8
- **Operating System**: macOS
- **Spring Boot Version**: 3.3.2
- **Docker** and **Docker Compose**
- **IntelliJ IDEA** (or any Java IDE)

---

## Project Structure

> This project adopts a **layered architecture** approach to organize the codebase into distinct layers, enhancing modularity, maintainability, and clarity. The structure includes key layers such as `controller`, `service`, `repository`, `model`, `config`, and `utils`, along with additional directories for API documentation, Docker configurations, and various build artifacts. This setup promotes clear separation of concerns, facilitating easier management and extension of the application.

```
ThoughtToNoteLite-Backend/
├── README.md                           # Project overview and setup instructions
├── api/
│   └── postman_collection.json         # Collection for API testing
├── docker/
│   └── compose.yaml                    # Docker Compose configuration
├── .github/
│   └── workflows/
│       └── ci.yml                      # GitHub Actions workflow for CI pipeline
├── mvnw                                # Maven wrapper script (Unix-based)
├── mvnw.cmd                            # Maven wrapper script (Windows)
├── pom.xml                             # Maven build file
├── project_docs.txt                    # Additional project documentation
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
│   │   │           │   └── NoteController.java      # REST controller for notes
│   │   │           ├── model/
│   │   │           │   └── Note.java                # Note entity definition
│   │   │           ├── repository/
│   │   │           │   └── NoteRepository.java      # Interface for note database operations
│   │   │           ├── service/
│   │   │           │   └── NoteService.java         # Business logic for note operations
│   │   │           └── utils/
│   │   │               └── PasswordEncoderUtil.java # Utility for password encoding
│   │   └── resources/
│   │       ├── application.properties               # Main configuration file
│   │       └── data/
│   │           └── notes-data.json                  # Sample notes data for initialization
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── thoughttonotelite/
│       │           ├── ThoughtToNoteLiteBeApplicationTests.java  # Application test class
│       │           ├── controller/
│       │           │   └── NoteControllerTest.java   # Unit tests for NoteController
│       │           ├── integration/
│       │           │   └── NoteIntegrationTest.java  # Integration tests for note operations
│       │           ├── model/
│       │           │   └── NoteEntityTest.java       # Tests for Note model behavior
│       │           └── service/
│       │               └── NoteServiceTest.java      # Unit tests for NoteService
│       └── resources/
│           └── application-test.properties           # Test configuration file
└── thought-to-note-lite-be.iml           # IntelliJ IDEA project file
```

### Description of Key Components

- **`api/`**: Contains the Postman collection used for testing the API endpoints.
- **`docker/`**: Includes Docker Compose configuration to manage dependencies like databases.
- **`.github/workflows/ci.yml`**: GitHub Actions workflow configuration for continuous integration (CI). The workflow includes automated testing and ensures compatibility across environments.
- **`mvnw`** and **`mvnw.cmd`**: Maven wrapper scripts for running Maven commands without requiring a local Maven installation.
- **`pom.xml`**: The Maven build file that manages project dependencies and build configurations.
- **`src/`**: Contains the source code and resources for the application.
  - **`main/java/com/thoughttonotelite/`**: The main application code organized into packages for configuration, controllers, models, repositories, services, and utilities.
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

The application integrates with a PostgreSQL database managed by Docker Compose. Key configuration details, such as database connection settings, are specified in the `application.properties` file.

For detailed configuration, refer to the `src/main/resources/application.properties` file.

---

## Running the Project

Before running the project, make sure you have installed the following:

- **Docker** and **Docker Compose** for managing containerized services.
- **Java** (version 21) for running the Spring Boot application.
- **Maven** for managing dependencies, though you can also use the Maven Wrapper.

### 1. **Set Up Maven Wrapper**

To ensure consistency across different environments, it's recommended to set up the Maven Wrapper. This ensures that everyone working on the project uses the exact same Maven version required by this project, ensuring compatibility and avoiding version conflicts. Specifically, use the command below to generate the Maven Wrapper with the required version:

- **Generate Maven Wrapper Files with Specific Version**:
  - Navigate to the root directory of your Spring Boot project and run the following command to generate the Maven Wrapper using the project's specified version (e.g., 3.9.8):
    ```bash
    mvn -N io.takari:maven:wrapper -Dmaven=3.9.8
    ```
  - This command will create a `.mvn/wrapper` directory containing the necessary Maven Wrapper files and ensure that the wrapper uses Maven version `3.9.8`, which is specified by this project.

The Maven Wrapper also makes it easier for those who do not have Maven installed globally or have a different version, as it will automatically download and use the version specified by the project when the repository is cloned. This makes it easy to run the project locally and avoids any compatibility issues.

- **Using Maven Wrapper**:

  - Once the Maven Wrapper is set up, replace the `mvn` command with `./mvnw` (on Unix/Linux/macOS) or `mvnw.cmd` (on Windows) in your commands. Note that to run all tests, ensure that the Docker container with the PostgreSQL database is running, as some tests use database transactions. For example, to run all tests:
    ```bash
    ./mvnw test
    ```

---

### 2. **Run the Spring Boot Application**

To run the Spring Boot application locally, follow these steps:

#### **1. Start Docker Services (Optional)**:

- In most cases, you do not need to manually start Docker Compose, as Spring Boot can handle database connectivity. However, if you want more control over the startup process or to ensure the database is running before the application starts, you can use the following command:
  ```bash
  docker-compose -f docker/compose.yaml up -d
  ```
  This command will start the PostgreSQL container in the background.

- **Verification**:
  To confirm that the PostgreSQL container is running correctly, use the following command:
  ```bash
  docker ps
  ```
  This will display the list of running containers. You should see the PostgreSQL container listed, indicating it has started successfully.

#### **2. Run the Spring Boot Application**:

- Use the Maven Wrapper to start the Spring Boot application directly:
  ```bash
  ./mvnw spring-boot:run
  ```
  The application will automatically connect to the PostgreSQL database managed by Docker Compose if the container is running.

#### **3. Verification**:

After running the application, you can verify that it is running successfully by:

- **Check the Logs**:
  Look for a message in the terminal indicating that the server has started, such as:
  ```
  Started ThoughtToNoteLiteBeApplication in ... seconds
  ```

- **Test the API**:
  Open a web browser and navigate to `http://localhost:8080` (or the configured port) to see if the API is accessible.

---

## API Documentation

The Thought-to-Note Lite API provides a comprehensive set of endpoints to manage `Note` entities. This API supports typical CRUD operations (Create, Read, Update, Delete) as well as a search functionality to retrieve notes by title. To enable Swagger documentation for the API, the following dependency was used in the `pom.xml` file:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

This dependency integrates Swagger with the Spring Boot application, generating an interactive user interface that documents all the available API endpoints. The following documentation outlines the key endpoints, authentication details, and how to explore the API using **Swagger**.

### Base URL

```
http://localhost:8080/api/notes
```

### Authentication

- **Basic Authentication**: All API endpoints require basic authentication.
- **Username**: `yourUsername`
- **Password**: `yourPassword`

### Accessing the Swagger UI

The API is documented and interactive via **Swagger** UI, which helps visualize the API documentation, making it more informative and user-friendly, allowing you to test all endpoints directly from your browser.

1. **Run the Application**: Start the backend by running:

    ```bash
    ./mvnw spring-boot:run
    ```

2. **Access Swagger**: Open your browser and navigate to:

    ```
    http://localhost:8080/swagger-ui.html
    ```

   This brings up an interactive Swagger UI where you can explore and test all the API endpoints.

3. **OpenAPI JSON Spec**: You can also access the raw OpenAPI documentation in JSON format at:

    ```
    http://localhost:8080/v3/api-docs
    ```

---

### Endpoints Overview

Below are the main API endpoints for managing notes, which are also accessible through the Swagger UI for live testing.

#### 1. **Get All Notes**

- **Endpoint**: `/api/notes`
- **Method**: `GET`
- **Description**: Retrieves a list of all notes.
- **Response**:
  - **200 OK**: Returns a JSON array of notes.

- **Example Request**:

```http
GET http://localhost:8080/api/notes
```

#### 2. **Get Note by ID**

- **Endpoint**: `/api/notes/{id}`
- **Method**: `GET`
- **Description**: Retrieves a specific note by its ID.
- **Path Variable**:
  - **`id`** (integer): The ID of the note to retrieve.
- **Response**:
  - **200 OK**: Returns the note corresponding to the specified ID.
  - **404 Not Found**: If the note does not exist.

- **Example Request**:

```http
GET http://localhost:8080/api/notes/1
```

#### 3. **Create Note**

- **Endpoint**: `/api/notes`
- **Method**: `POST`
- **Description**: Creates a new note.
- **Request Headers**:
  - **Content-Type**: `application/json`
- **Request Body**:
  - **`title`** (string): The title of the note.
  - **`content`** (string): The content of the note.
- **Response**:
  - **201 Created**: Returns the created note.
  - **400 Bad Request**: If the input data is invalid.

- **Example Request**:

```http
POST http://localhost:8080/api/notes
Content-Type: application/json

{
  "title": "New Note",
  "content": "This is a new note."
}
```

#### 4. **Update Note**

- **Endpoint**: `/api/notes/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing note.
- **Path Variable**:
  - **`id`** (integer): The ID of the note to update.
- **Request Headers**:
  - **Content-Type**: `application/json`
- **Request Body**:
  - **`title`** (string): The updated title of the note.
  - **`content`** (string): The updated content of the note.
- **Response**:
  - **200 OK**: Returns the updated note.
  - **404 Not Found**: If the note does not exist.
  - **400 Bad Request**: If the input data is invalid.

- **Example Request**:

```http
PUT http://localhost:8080/api/notes/1
Content-Type: application/json

{
  "title": "Updated Note",
  "content": "This is an updated note."
}
```

#### 5. **Delete Note**

- **Endpoint**: `/api/notes/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a note by its ID.
- **Path Variable**:
  - **`id`** (integer): The ID of the note to delete.
- **Response**:
  - **204 No Content**: If the note was successfully deleted.
  - **404 Not Found**: If the note does not exist.

- **Example Request**:

```http
DELETE http://localhost:8080/api/notes/1
```

#### 6. **Search Notes by Title**

- **Endpoint**: `/api/notes/search`
- **Method**: `GET`
- **Description**: Searches for notes by their title.
- **Query Parameter**:
  - **`title`** (string): The keyword to search for in the note titles.
- **Response**:
  - **200 OK**: Returns a JSON array of notes that match the search criteria.

- **Example Request**:

```http
GET http://localhost:8080/api/notes/search?title=Test
```

---

### Disable Security for Development

To make it easier to interact with the API during local development, you can disable authentication by adding the following snippet to the `application.properties` file. Note that disabling security is not recommended for production environments, as it can pose significant security risks:

```properties
# Disable Security (Development Only)
spring.security.user.name=yourUsername
spring.security.user.password=yourPassword
spring.security.user.roles=USER
```

This will bypass authentication requirements, allowing you to explore the API more freely during development.

> **Important**: This configuration is intended for **local development only**. Ensure that security is properly configured for any production environments.

---

### Postman Collection

To facilitate manual testing or integration with Postman, a pre-configured Postman collection is included in the repository:

- **File Location**: `api/postman_collection.json`

You can import this collection into Postman to quickly test all the API endpoints mentioned above.

---

By using the Swagger UI or Postman collection, you can explore the Thought-to-Note Lite API efficiently. The Swagger documentation provides a convenient, interactive interface for testing endpoints, while the Postman collection allows quick integration into your workflows.


## Code Structure and Design

#### 1. **`Note`**** Model Entity**

The `Note` entity is the core data model used in the Thought-to-Note Lite application to represent a note, mapped to the `notes` table in the PostgreSQL database using JPA annotations. It includes fields such as `id`, `title`, `content`, `createdAt`, and `updatedAt`, which correspond to the columns in the database table.

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

#### 2. **`NoteService`**** Class**

The `NoteService` class is responsible for implementing the business logic related to the `Note` entity. It serves as a mediator between the `NoteController` and the `NoteRepository`.

- **CRUD Operations:**
  - **Create:** Saves a new `Note` entity to the database, which automatically generates and assigns an `id`, `createdAt`, and `updatedAt` fields based on the current state of the entity.
  - **Read:** Retrieves notes either by their ID or as a list of all notes from the `notes` table.
  - **Update:** Modifies an existing `Note` entity and updates its `updatedAt` field before saving the changes back to the database.
  - **Delete:** Removes a `Note` from the `notes` table by its ID.

The service layer ensures that any business rules are enforced before interacting with the database through the repository. For example, it checks if a note exists before updating or deleting it, and ensures that a note has a title before saving.

- **Search Functionality:** The service includes a method to search for notes by their title, allowing users to find notes based on partial matches within the `title` column.

#### 3. **`NoteController`**** Class**

The `NoteController` is a REST controller that handles HTTP requests related to the `Note` entity. It exposes endpoints for the following operations:

- **Create a Note:** Handles `POST` requests to create a new note, which results in a new row in the `notes` table.
- **Retrieve Notes:** Handles `GET` requests to retrieve all notes or a specific note by its ID from the `notes` table.
- **Update a Note:** Handles `PUT` requests to update an existing note, modifying the corresponding row in the `notes` table.
- **Delete a Note:** Handles `DELETE` requests to remove a note by its ID from the `notes` table.
- **Search Notes:** Handles `GET` requests to search for notes by their title, querying the `notes` table for matches.

Each endpoint corresponds to a method in the `NoteService` class, and the controller is responsible for mapping the HTTP requests to these service methods. It also handles the construction of appropriate HTTP responses, such as returning `201 Created` for successful creation or `404 Not Found` when a note doesn't exist.

#### 4. **`DataInitializer`**** Class**

The `DataInitializer` class is a Spring component that implements `ApplicationRunner`. It is responsible for loading initial data into the `notes` table when the application starts, but only if the table is empty.

- **Data Initialization Steps:**
  - **Check for Existing Data:** The initializer first checks if the `notes` table is empty by counting the number of entries.
  - **Load JSON Data:** If the table is empty, it loads data from a JSON file (`notes-data.json`) located in the `resources/data` directory.
  - **Map JSON to Entities:** The JSON data is read and converted into a list of `Note` entities using Jackson's `ObjectMapper`.
  - **Insert into Database:** The list of `Note` entities is then saved to the database using the `NoteRepository`.

This mechanism ensures that the application starts with some sample data, which is useful for development and testing. If the database already contains data, the initializer skips the data loading step.

#### 5. **JPA, JDBC, and Database Schema in the Application**

The Thought-to-Note Lite backend application  integrates both JPA (Java Persistence API) and JDBC (Java Database Connectivity) to manage database interactions, leveraging the strengths of each to ensure efficient and reliable data operations.

##### **JPA (Java Persistence API)**

JPA is the primary tool used for interacting with the PostgreSQL database in this application. It simplifies mapping Java objects (entities) to database tables and managing their lifecycle. Key features include:

- **Entity Mapping:** The `Note` entity is annotated with JPA annotations like `@Entity`, `@Table`, and `@Id`, which map the class and fields directly to the `notes` table.
- **Repository Abstraction:** The `NoteRepository` extends `JpaRepository`, allowing CRUD operations without writing explicit SQL, which keeps the code cleaner and easier to maintain.
- **Hibernate Integration:** Hibernate, the default JPA implementation, automatically generates and executes SQL based on the JPA annotations. Properties like `spring.jpa.hibernate.ddl-auto=update` ensure that the `notes` table schema is updated to reflect the entity definitions.

##### **JDBC (Java Database Connectivity)**

While JPA handles most of the data interactions, JDBC is inherently employed in the background to execute the SQL queries generated by Hibernate (the JPA provider). The application explicitly defines JDBC properties within the `application.properties` file, ensuring that JDBC is configured and used correctly:

- **JDBC URL:** The `spring.datasource.url` property specifies the connection URL to the PostgreSQL database using JDBC. This property is critical as it directly influences how the application connects to the database.

- **Driver Configuration:** The `spring.datasource.driver-class-name` property points to the PostgreSQL JDBC driver, ensuring that JDBC can communicate with the PostgreSQL database.

- **Direct SQL Execution:** Although JPA abstracts most SQL operations, JDBC is still the underlying mechanism that performs the actual database interactions. When JPA methods are called, the SQL generated by Hibernate is executed via JDBC. Typically, developers do not need to interact directly with JDBC unless fine-tuning or custom optimizations are required, which helps simplify development.

- **Database Schema Integration:** JPA automatically manages the database schema, creating and updating tables like `notes` based on the entity definitions. JDBC ensures these operations are executed efficiently within the PostgreSQL database.

The application’s integration of JPA and JDBC allows for seamless and efficient interaction with the PostgreSQL database, ensuring that the database schema is properly managed and that data operations are executed reliably. The configuration ensures that the application’s entities, such as `Note`, are effectively mapped to the database, with Hibernate handling the ORM (Object-Relational Mapping) duties and JDBC executing the necessary SQL commands.

---

## Testing

The Thought-to-Note Lite backend includes a comprehensive suite of tests that cover the various layers of the application. These tests ensure that the application behaves as expected under different conditions and that the individual components work together correctly.

> **To run all the tests for the Thought-to-Note Lite backend, use the following command:**

```bash
./mvnw test
```

This command executes the entire test suite, encompassing controller, integration, entity, service, and application context tests. It ensures that the application functions correctly across all layers, providing confidence in the code's quality and robustness.

### 1. **Controller Tests (`NoteControllerTest`)**

These tests focus on the `NoteController` class, which handles HTTP requests related to the `Note` entity. The tests are designed to verify the correctness of the controller's behavior, including security aspects such as authentication and CSRF protection.

- **Framework:** Utilizes the `@WebMvcTest` annotation to configure the test environment, focusing solely on the web layer. `MockMvc` is employed to send mock HTTP requests and verify responses.

- **Security:** Employs the `@WithMockUser` annotation to simulate an authenticated user with specific roles during testing.

- **Key Tests:**
  - **Retrieving All Notes:** Ensures that a `GET` request returns the correct list of notes.
  - **Creating a Note:** Verifies that a `POST` request correctly creates a new note.
  - **Updating a Note:** Checks that a `PUT` request updates an existing note.
  - **Deleting a Note:** Confirms that a `DELETE` request successfully removes a note.
  - **Retrieving a Note by ID:** Ensures that a `GET` request fetches the correct note by its ID.
  - **Searching Notes by Title:** Validates that a `GET` request with a search parameter returns the correct list of matching notes.

### 2. **Integration Tests (`NoteIntegrationTest.java`)**

These tests assess the integration of various components within the application, particularly the interaction between the web layer and the database.

- **Framework:** Utilizes `@SpringBootTest` with a `RANDOM_PORT` web environment to start the application and an actual PostgreSQL database instance managed by Testcontainers.

- **Testcontainers:** Employs Testcontainers to create an isolated PostgreSQL environment for testing. The PostgreSQL container is defined with specific configurations and dynamically sets Spring Boot's datasource properties to connect to the Testcontainers-managed database.

- **TestRestTemplate:** Uses `TestRestTemplate` to send real HTTP requests to the running application, allowing for end-to-end testing of API endpoints.

- **Key Test:**
  - **Creating and Retrieving a Note:** Verifies that a note can be created via a `POST` request and subsequently retrieved via a `GET` request, ensuring that the data persists correctly in the database.

### 3. **Entity Tests (`NoteEntityTest.java`)**

These tests focus on the persistence and integrity of the `Note` entity within the database. They ensure that the entity's fields are correctly populated and persisted.

- **Framework:** Uses `@SpringBootTest` to load the full application context and `EntityManager` to directly interact with the persistence context.

- **Transactional Testing:** The tests are annotated with `@Transactional` to ensure that each test runs within its own transaction, which is rolled back at the end of the test. This guarantees that tests do not leave residual data in the database, maintaining a clean state for subsequent tests.

- **Testcontainers:** Integrates with Testcontainers to provide an isolated PostgreSQL environment, ensuring that tests are executed against a consistent and controlled database instance.

- **Key Test:**
  - **Entity Persistence:** Verifies that a `Note` entity is correctly persisted, with `id`, `createdAt`, and `updatedAt` fields set appropriately. It ensures that the entity's lifecycle events (`@PrePersist` and `@PreUpdate`) function as intended.

### 4. **Service Tests (`NoteServiceTest.java`)**

These tests focus on the business logic implemented in the `NoteService` class. They ensure that the service methods correctly interact with the `NoteRepository` and return the expected results.

- **Framework:** Utilizes the `MockitoExtension` to inject mocks into the service, isolating it from dependencies like the repository.

- **Mocking:** The `NoteRepository` is mocked to simulate database operations without performing real database interactions.

- **Key Tests:**
  - **Creating a Note:** Ensures that the service correctly saves a new note.
  - **Updating a Note:** Verifies that the service correctly updates an existing note.
  - **Deleting a Note:** Confirms that the service correctly deletes a note by its ID.
  - **Retrieving All Notes:** Checks that the service retrieves all notes.
  - **Retrieving a Note by ID:** Ensures that the service fetches the correct note by its ID.
  - **Searching Notes by Title:** Validates that the service returns the correct list of notes based on the title search.

### 5. **Application Context Test (`ThoughtToNoteLiteBeApplicationTests.java`)**

This test verifies that the Spring Boot application context loads successfully without any issues.

- **Framework:** Uses the `@SpringBootTest` annotation to load the full application context and integrates with Testcontainers to manage the PostgreSQL container.

- **Key Test:**
  - **Context Load:** Ensures that the application context starts up correctly, indicating that all components are properly configured.

These tests collectively ensure the reliability of the Thought-to-Note Lite backend, covering critical aspects from HTTP request handling and business logic to database interactions and application context configuration. By thoroughly testing each layer, the application is well-positioned to handle real-world usage scenarios effectively.

---

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

This security configuration is specifically tailored for the development phase. In a production environment, the following should be implemented:

- **Enable CSRF Protection:** Re-enable CSRF protection to safeguard against CSRF attacks.
- **Use Secure Password Encoders:** Replace `NoOpPasswordEncoder` with a secure password encoder like `BCryptPasswordEncoder` to ensure passwords are stored securely.
- **Secure Credential Storage:** Store credentials securely, avoiding hard-coded or exposed credentials in configuration files. Consider using environment variables or secret management tools.
- **Role-Based Access Control:** Implement fine-grained role-based access controls to restrict access to specific endpoints based on user roles and permissions.

By adhering to these best practices, the application can maintain robust security measures suitable for production environments.

---

## Final Notes

The Thought-to-Note Lite backend is meticulously structured with a layered architecture that promotes modularity, maintainability, and clarity. The comprehensive test suite, integrated with Testcontainers for an isolated testing environment, ensures that each component functions correctly both in isolation and in conjunction with others. Security configurations are thoughtfully set up to balance ease of development with the necessary safeguards, with clear guidelines for enhancing security in production settings.

By maintaining a robust testing strategy and adhering to best practices in security and application design, the Thought-to-Note Lite backend is well-equipped to deliver reliable and secure note-taking functionalities in real-world usage scenarios.