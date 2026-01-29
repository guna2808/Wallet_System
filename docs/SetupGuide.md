## ▶️ Project Usage & Setup Guide (Run on Your Local Machine)

This section explains **how to download, configure, and run the Wallet_System project on your own computer**.

---

## 🧰 Prerequisites

Before starting, make sure the following are installed on your system:

* **Java 17**

  ```bash
  java -version
  ```
* **Maven 3.8+**

  ```bash
  mvn -version
  ```
* **MySQL 8+**
* **Apache Tomcat 9**
* **Git**
* **Postman** (for API testing)

---

## 📥 Step 1: Clone the Repository

```bash
git clone https://github.com/<your-username>/Wallet_System.git
cd Wallet_System
```

(Or download the ZIP from GitHub and extract it.)

---

## 🗄️ Step 2: Create Database in MySQL

Login to MySQL:

```bash
mysql -u root -p
```

Create database:

```sql
CREATE DATABASE walletdb;
```

> ⚠️ Do **not** create tables manually.
> Liquibase will handle table creation automatically.

---

## ⚙️ Step 3: Configure Database Properties

Open the file:

```
src/main/resources/application.properties
```

Update the following values according to your MySQL setup:

```properties
db.url=jdbc:mysql://localhost:3306/walletdb
db.username=root
db.password=your_mysql_password
db.driver=com.mysql.cj.jdbc.Driver
db.pool.size=10
```

Save the file.

---

## 🧩 Step 4: Run Liquibase (Database Migration)

This step will **automatically create tables**.

```bash
mvn liquibase:update
```

After this step:

* `users` table is created
* `transactions` table is created

---

## 🧪 Step 5: Run Tests (Optional but Recommended)

Run all unit and integration tests:

```bash
mvn clean test
```

You should see:

```
BUILD SUCCESS
```

---

## 📦 Step 6: Build the Project (WAR File)

```bash
mvn clean package
```

This will generate:

```
target/Wallet_System-1.0-SNAPSHOT.war
```

---

## 🚀 Step 7: Deploy on Tomcat 9

### Option A: Copy WAR File

1. Copy the WAR file:

   ```
   target/Wallet_System-1.0-SNAPSHOT.war
   ```
2. Paste it into:

   ```
   <tomcat-folder>/webapps/
   ```
3. Start Tomcat:

   ```bash
   startup.bat   (Windows)
   ./startup.sh  (Linux/Mac)
   ```

---

## 🌐 Step 8: Verify Application is Running

Open browser:

```
http://localhost:8080/Wallet_System-1.0-SNAPSHOT/
```

If Tomcat is running, the application is deployed successfully.

---

## 🧪 Step 9: Use APIs via Postman

### 1️⃣ Register User

```
POST http://localhost:8080/Wallet_System-1.0-SNAPSHOT/api/register
```

```json
{
  "username": "guna",
  "password": "guna123"
}
```

---

### 2️⃣ Login User

```
POST http://localhost:8080/Wallet_System-1.0-SNAPSHOT/api/login
```

```json
{
  "username": "guna",
  "password": "guna123"
}
```

> ⚠️ Keep the session/cookies enabled in Postman.

---

### 3️⃣ Add Transaction

```
POST http://localhost:8080/Wallet_System-1.0-SNAPSHOT/api/transaction
```

```json
{
  "amount": 500,
  "type": "credit"
}
```

---

### 4️⃣ Get Transactions

```
GET http://localhost:8080/Wallet_System-1.0-SNAPSHOT/api/transactions
```

---

### 5️⃣ Get Wallet Balance

```
GET http://localhost:8080/Wallet_System-1.0-SNAPSHOT/api/balance
```

---

## 🔒 Notes on Authentication

* Session-based authentication is used
* User must login before accessing wallet APIs
* Unauthorized requests will be blocked by `AuthFilter`

---

## 🛑 Common Issues & Fixes

### ❌ MySQL Access Denied

✔ Check username/password in `application.properties`

### ❌ 404 Error

✔ Verify WAR name and context path

### ❌ Unauthorized API Access

✔ Ensure login API is called first (session must exist)

---

## ✅ You’re All Set!

At this point:

* Application is running
* Database is connected
* APIs are working
* Wallet_System is fully functional

---

