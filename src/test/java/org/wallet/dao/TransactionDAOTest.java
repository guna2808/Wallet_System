package org.wallet.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOTest {

    private TransactionDAO transactionDAO;
    private int userId;

    @BeforeEach
    void setup() {

        UserDAO userDAO = new UserDAO();
        transactionDAO = new TransactionDAO();

        String username = "testuser_" + System.currentTimeMillis();

        userDAO.register(username, "password123");

        userId = userDAO.getUserIdByUsername(username);

        assertTrue(userId > 0);
    }

    @Test
    void addTransaction_credit() {

        try {
            transactionDAO.addTransaction(userId, 100.0, "credit");
        } catch (Exception e) {
            fail("Credit transaction failed");
        }
    }

    @Test
    void addTransaction_debit() {

        try {
            transactionDAO.addTransaction(userId, 50.0, "debit");
        } catch (Exception e) {
            fail("Debit transaction failed");
        }
    }

    @Test
    void getTransactionsByUser() {

        transactionDAO.addTransaction(userId, 100.0, "credit");

        List<?> transactions = transactionDAO.getTransactionsByUser(userId);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
    }

    @Test
    void getWalletBalance_onlyCredit() {

        transactionDAO.addTransaction(userId, 200.0, "credit");

        double balance = transactionDAO.getWalletBalance(userId);

        assertEquals(200.0, balance);
    }

    @Test
    void getWalletBalance_creditAndDebit() {

        transactionDAO.addTransaction(userId, 200.0, "credit");
        transactionDAO.addTransaction(userId, 50.0, "debit");

        double balance = transactionDAO.getWalletBalance(userId);

        assertEquals(150.0, balance);
    }

    @Test
    void getWalletBalance_noTransactions() {

        double balance = transactionDAO.getWalletBalance(userId);

        assertEquals(0.0, balance);
    }
}
