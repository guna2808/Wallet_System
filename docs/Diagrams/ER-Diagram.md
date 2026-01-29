```mermaid
---
config:
  theme: neo
  look: classic
---
erDiagram
    USERS {
        int id PK
        string username
        string password
    }

    TRANSACTIONS {
        int id PK
        int user_id FK
        double amount
        string type
        timestamp created_at
    }

    USERS ||--o{ TRANSACTIONS : has