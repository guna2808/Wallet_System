```mermaid
sequenceDiagram
    autonumber

    %% ===============================
    %% ACTORS
    %% ===============================
    participant C as Client (Postman/Browser)
    participant F as AuthFilter
    participant RS as RegisterServlet
    participant LS as LoginServlet
    participant TS as TransactionServlet
    participant GTS as GetTransactionsServlet
    participant BS as WalletBalanceServlet
    participant UDAO as UserDAO
    participant TDAO as TransactionDAO
    participant DS as DataSource (HikariCP)
    participant DB as MySQL Database
    participant SESS as HttpSession

    %% ===============================
    %% USER REGISTRATION
    %% ===============================
    rect rgb(235, 245, 255)
    C->>RS: POST /api/register (username, password)
    RS->>UDAO: register(username, password)
    UDAO->>UDAO: BCrypt.hash(password)
    UDAO->>DS: getConnection()
    DS->>DB: INSERT user
    DB-->>DS: success / duplicate error
    DS-->>UDAO: result
    UDAO-->>RS: true / false
    RS-->>C: success / user already exists
    end

    %% ===============================
    %% USER LOGIN
    %% ===============================
    rect rgb(235, 255, 235)
    C->>LS: POST /api/login (username, password)
    LS->>UDAO: login(username, password)
    UDAO->>DS: getConnection()
    DS->>DB: SELECT user by username
    DB-->>DS: hashed password
    DS-->>UDAO: result
    UDAO->>UDAO: BCrypt.verify(password)
    UDAO-->>LS: true / false

    alt Login success
        LS->>SESS: create session
        LS->>SESS: setAttribute(username)
        LS-->>C: login success
    else Login failure
        LS-->>C: invalid credentials
    end
    end

    %% ===============================
    %% ADD TRANSACTION
    %% ===============================
    rect rgb(255, 245, 235)
    C->>F: POST /api/transaction
    F->>SESS: validate session

    alt Authorized
        F-->>TS: allow request
        TS->>UDAO: getUserId(username)
        UDAO->>DS: getConnection()
        DS->>DB: SELECT user_id
        DB-->>DS: user_id
        DS-->>UDAO: user_id
        UDAO-->>TS: user_id

        TS->>TDAO: addTransaction(userId, amount, type)
        TDAO->>DS: getConnection()
        DS->>DB: INSERT transaction
        DB-->>DS: success
        DS-->>TDAO: result
        TDAO-->>TS: true
        TS-->>C: transaction added
    else Unauthorized
        F-->>C: 401 Unauthorized
    end
    end

    %% ===============================
    %% GET TRANSACTIONS
    %% ===============================
    rect rgb(245, 235, 255)
    C->>F: GET /api/transactions
    F->>SESS: validate session

    alt Authorized
        F-->>GTS: allow request
        GTS->>UDAO: getUserId(username)
        UDAO->>DS: getConnection()
        DS->>DB: SELECT user_id
        DB-->>DS: user_id
        DS-->>UDAO: user_id
        UDAO-->>GTS: user_id

        GTS->>TDAO: getTransactionsByUser(userId)
        TDAO->>DS: getConnection()
        DS->>DB: SELECT transactions
        DB-->>DS: result set
        DS-->>TDAO: transactions
        TDAO-->>GTS: List<Transaction>
        GTS-->>C: JSON transactions
    else Unauthorized
        F-->>C: 401 Unauthorized
    end
    end

    %% ===============================
    %% GET WALLET BALANCE
    %% ===============================
    rect rgb(255, 235, 245)
    C->>F: GET /api/balance
    F->>SESS: validate session

    alt Authorized
        F-->>BS: allow request
        BS->>UDAO: getUserId(username)
        UDAO->>DS: getConnection()
        DS->>DB: SELECT user_id
        DB-->>DS: user_id
        DS-->>UDAO: user_id
        UDAO-->>BS: user_id

        BS->>TDAO: getWalletBalance(userId)
        TDAO->>DS: getConnection()
        DS->>DB: SELECT transactions
        DB-->>DS: credit/debit rows
        DS-->>TDAO: rows
        TDAO->>TDAO: calculate balance
        TDAO-->>BS: balance
        BS-->>C: balance JSON
    else Unauthorized
        F-->>C: 401 Unauthorized
    end
    end
