```mermaid
flowchart TD
    Client[Client / Postman] --> Servlet[Servlet Layer]
    Servlet --> DAO[DAO Layer]
    DAO --> DB[(MySQL Database)]

    Servlet --> Filter[Auth Filter]
    Servlet --> Session[HTTP Session]

    DAO --> DataSource[HikariCP DataSource]
    DataSource --> DB
