

# 💳 Wallet_System

**Wallet_System** is a **pure backend Java application** built using **Servlets, JDBC, and MySQL**.
It provides secure APIs for **user authentication, wallet transactions, and balance management**.

This project is designed **only from a backend/API perspective** and does **not include any UI or JSP pages**.
All APIs can be tested using **Postman** or any HTTP client.

---

## 🛠️ Tech Stack

* **Java 17**
* **Servlet API 4.0**
* **Apache Tomcat 9**
* **Maven (WAR packaging)**
* **MySQL**
* **JDBC**
* **HikariCP (Connection Pooling)**
* **Liquibase (DB migration)**
* **BCrypt (Password Hashing)**
* **SLF4J + Logback (Logging)**
* **JUnit 5, Mockito (Testing)**
* **JaCoCo (Code Coverage)**

---

## 🎯 Project Features

* User Registration & Login
* Secure password hashing using BCrypt
* Session-based authentication
* Wallet credit & debit transactions
* Wallet balance calculation
* User-specific data isolation
* Authentication filter for protected APIs
* Unit & integration test coverage
* Production-ready database connection pooling

---

## 🏗️ Architecture Overview

Wallet_System follows a **layered backend architecture** with clear separation of responsibilities.

```
Client (Postman / Browser)
        ↓
Servlet Layer (Controllers)
        ↓
DAO Layer (JDBC)
        ↓
MySQL Database
```

Supporting layers handle **security, configuration, logging, and testing**.

---

## 📁 Package Structure

```
org.wallet
 ├── controller   → HTTP request handling (Servlet APIs)
 ├── dao          → Database access logic
 ├── model        → Domain entities
 ├── dto          → Request / Response objects
 ├── filter       → Authentication & authorization
 ├── util         → Common utilities
 └── test         → Unit & integration tests
```

Each package has a **single responsibility**, improving readability and maintainability.

---

## 🔄 Application Flow (Start to End)

1. Client sends HTTP request (login, register, transaction, etc.)
2. Servlet validates input and session
3. DAO executes database operations using JDBC
4. Database stores or retrieves data
5. Servlet returns JSON response to client

---

## 🔐 Security Design

### Password Security

* Passwords are hashed using **BCrypt**
* Plain text passwords are never stored or logged

### Authentication

* On successful login, user identity is stored in `HttpSession`
* Session is used to identify authenticated users

### Authorization

* `AuthFilter` intercepts protected APIs
* Prevents unauthenticated access
* Ensures users can access **only their own data**

---

## 🗄️ Database & Persistence

* **MySQL** is used as the primary database
* **Liquibase** manages schema versions
* **JDBC** provides direct SQL control
* **HikariCP** ensures efficient connection pooling

All queries are **scoped by user ID** to prevent data leakage.

---

## 🔌 Connection Management

* `DataSourceUtil` provides a singleton **DataSource**
* DAOs receive `DataSource` via constructor injection
* Static DB connection usage is avoided

This design:

* Improves testability
* Enables mocking
* Avoids tight coupling

---

## 🧪 Testing Strategy

The project follows a **multi-level testing approach**.

### Unit Tests

* DAO logic tested using **Mockito**
* Servlet logic tested by mocking DAOs and HTTP objects
* No real database access

### Integration Tests

* Selected tests interact with the real database
* Ensures SQL and schema correctness

### Tools Used

* **JUnit 5**
* **Mockito**
* **JaCoCo**

---

## 📊 Code Coverage

* JaCoCo generates coverage reports after test execution
* Coverage report location:

  ```
  target/site/jacoco/index.html
  ```

---

## 📝 Logging

* Logging implemented using **SLF4J + Logback**
* Logs include:

    * Login attempts
    * Authentication failures
    * System and runtime errors

Logging helps in debugging and monitoring application behavior.

---

## 🚀 Build & Deployment

### Build the project

```bash
mvn clean install
```

### Run tests

```bash
mvn clean test
```

### Deployment

* Generates a WAR file
* Deployable on **Apache Tomcat 9**
* APIs accessible via browser or Postman

---

## 🔗 Sample API Endpoints

| Method | Endpoint            | Description            |
| ------ | ------------------- | ---------------------- |
| POST   | `/api/register`     | Register new user      |
| POST   | `/api/login`        | User login             |
| POST   | `/api/transaction`  | Add wallet transaction |
| GET    | `/api/transactions` | Get user transactions  |
| GET    | `/api/balance`      | Get wallet balance     |

---

## ✅ Key Highlights

* Clean layered architecture
* Secure authentication & authorization
* Proper database connection pooling
* Fully testable design
* Production-ready backend structure

---

## 📌 Future Enhancements (Optional)

* JWT-based authentication
* Pagination for transactions
* Standard API response wrapper
* Docker support
* SonarQube integration
* Migration to Spring Boot

---

## 👤 Author

**Guna**
Backend Java Developer (Servlets, JDBC, MySQL)

---



