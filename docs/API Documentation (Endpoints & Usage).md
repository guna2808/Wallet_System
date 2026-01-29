

## 📘 API Documentation (Endpoints & Usage)

### 🔐 Authentication APIs

#### ➤ Register User

* **Method:** `POST`
* **URL:** `/api/register`
* **Description:** Registers a new user with a unique username.

**Request (JSON):**

```json
{
  "username": "guna",
  "password": "guna123"
}
```

**Success Response:**

```json
{
  "status": "success"
}
```

**Error Response (Duplicate User):**

```json
{
  "error": "user already exists"
}
```

---

#### ➤ Login User

* **Method:** `POST`
* **URL:** `/api/login`
* **Description:** Authenticates user and creates a session.

**Request (JSON):**

```json
{
  "username": "guna",
  "password": "guna123"
}
```

**Success Response:**

```json
{
  "status": "login success"
}
```

**Error Response:**

```json
{
  "error": "invalid credentials"
}
```

---

### 💳 Wallet APIs (Authentication Required)

#### ➤ Add Transaction

* **Method:** `POST`
* **URL:** `/api/transaction`
* **Description:** Adds a credit or debit transaction for the logged-in user.

**Request (JSON):**

```json
{
  "amount": 500,
  "type": "credit"
}
```

**Response:**

```json
{
  "status": "transaction added"
}
```

---

#### ➤ Get Transactions

* **Method:** `GET`
* **URL:** `/api/transactions`
* **Description:** Returns all transactions of the logged-in user.

**Response:**

```json
[
  {
    "id": 1,
    "amount": 500,
    "type": "credit",
    "createdAt": "2026-01-28T12:30:00"
  }
]
```

---

#### ➤ Get Wallet Balance

* **Method:** `GET`
* **URL:** `/api/balance`
* **Description:** Returns the current wallet balance of the logged-in user.

**Response:**

```json
{
  "balance": 500.0
}
```

---