```mermaid
---
config:
  theme: neo
  look: classic
---
classDiagram

class LoginServlet {
  - UserDAO userDAO
  - Gson gson
  + LoginServlet()
  + LoginServlet(UserDAO)
  + doPost(HttpServletRequest, HttpServletResponse)
}

class RegisterServlet {
  - UserDAO userDAO
  - Gson gson
  + RegisterServlet()
  + RegisterServlet(UserDAO)
  + doPost(HttpServletRequest, HttpServletResponse)
}

class TransactionServlet {
  - TransactionDAO transactionDAO
  - UserDAO userDAO
  + TransactionServlet()
  + TransactionServlet(TransactionDAO, UserDAO)
  + doPost(HttpServletRequest, HttpServletResponse)
}

class GetTransactionsServlet {
  - TransactionDAO transactionDAO
  - UserDAO userDAO
  + GetTransactionsServlet()
  + GetTransactionsServlet(TransactionDAO, UserDAO)
  + doGet(HttpServletRequest, HttpServletResponse)
}

class WalletBalanceServlet {
  - TransactionDAO transactionDAO
  - UserDAO userDAO
  + WalletBalanceServlet()
  + WalletBalanceServlet(TransactionDAO, UserDAO)
  + doGet(HttpServletRequest, HttpServletResponse)
}

class AuthFilter {
  + doFilter(ServletRequest, ServletResponse, FilterChain)
}

class UserDAO {
  - DataSource dataSource
  + UserDAO(DataSource)
  + register(String, String) boolean
  + login(String, String) boolean
  + getUserIdByUsername(String) int
}

class TransactionDAO {
  - DataSource dataSource
  + TransactionDAO(DataSource)
  + addTransaction(int, double, String) boolean
  + getTransactionsByUser(int) List~Transaction~
  + getWalletBalance(int) double
}

class User {
  - int id
  - String username
  - String password
}

class Transaction {
  - int id
  - int userId
  - double amount
  - String type
  - Timestamp createdAt
}

class TransactionResponseDTO {
  - int id
  - double amount
  - String type
  - Timestamp createdAt
}

class DataSourceUtil {
  - static HikariDataSource dataSource
  + static DataSource getDataSource()
}

class AppProperties {
  + static String get(String)
}

class PasswordUtil {
  + static String hashPassword(String)
  + static boolean verifyPassword(String, String)
}

LoginServlet --> UserDAO
RegisterServlet --> UserDAO
TransactionServlet --> TransactionDAO
TransactionServlet --> UserDAO
GetTransactionsServlet --> TransactionDAO
GetTransactionsServlet --> UserDAO
WalletBalanceServlet --> TransactionDAO
WalletBalanceServlet --> UserDAO

UserDAO --> User
TransactionDAO --> Transaction

UserDAO --> DataSourceUtil
TransactionDAO --> DataSourceUtil

PasswordUtil --> UserDAO
AuthFilter --> LoginServlet
AuthFilter --> TransactionServlet
AuthFilter --> GetTransactionsServlet
AuthFilter --> WalletBalanceServlet
