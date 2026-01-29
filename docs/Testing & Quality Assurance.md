## 🧪 Testing & Quality Assurance

Wallet_System follows a **clean and layered testing strategy** to ensure correctness and reliability.

### ✔ Unit Testing

* DAO layer tested using **Mockito**
* Servlet layer tested by mocking:

    * DAOs
    * `HttpServletRequest`
    * `HttpServletResponse`
    * `HttpSession`
* No real database connection is used in unit tests

### ✔ Integration Testing

* Selected tests use the real database
* Validates SQL queries and Liquibase schema
* Ensures correct interaction with MySQL

### ✔ Test Tools

* **JUnit 5** – test framework
* **Mockito** – dependency mocking
* **JaCoCo** – code coverage reporting

### ✔ Code Coverage

JaCoCo generates coverage reports after running tests.

**Coverage Report Location:**

```
target/site/jacoco/index.html
```

This ensures:

* Core business logic is tested
* APIs behave correctly
* Code quality remains high

---

## ✅ Why This Matters

* Prevents regressions
* Ensures secure and correct behavior
* Makes the project CI/CD ready
* Improves maintainability and confidence

---