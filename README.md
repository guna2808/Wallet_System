# 💼 Wallet_System – Secure Backend Wallet API

A **pure backend wallet system** built using **Java Servlets + JDBC + MySQL**, focusing on **security, clean architecture, and real-world backend practices**.

❌ No JSP  
❌ No UI  
✅ API-only backend  
---

## 📌 Project Overview

**Wallet_System** provides secure REST-style APIs for:

- User registration & login
- Secure password storage (BCrypt)
- Wallet transactions (credit / debit)
- Dynamic wallet balance calculation
- Session-based authentication & authorization

The project is deployed as a **WAR** on **Apache Tomcat 9** and tested using **Postman**.

---

## 🛠️ Tech Stack

| Layer | Technology          |
|---|---------------------|
Language | Java 17             |
Backend | Java Servlets       |
Build Tool | Maven               |
Server | Apache Tomcat 9     |
Database | MySQL               |
Security | BCrypt + HttpSession |
Data Format | JSON                |
Testing | Postman             |

---

## 🔐 Security Features

- Password hashing using **BCrypt**
- Session-based authentication
- Servlet **Auth Filter** for API protection
- Logout API with session invalidation
- SQL Injection prevention using PreparedStatement
- Database-level UNIQUE constraints

---

## 🌐 Base URL
http://localhost:8080/Wallet_System-1.0-SNAPSHOT


---

## 📚 API Endpoints

### 1️⃣ Register User
POST /api/register

**Request (x-www-form-urlencoded)**

username="Your Username"
password="Your Password"


**Success Response**
```json
{
  "status": "registered successfully"
}
```
**Error Response**
```json
{
"error": "username already exists"
}
```


