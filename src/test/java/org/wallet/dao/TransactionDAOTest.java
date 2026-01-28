package org.wallet.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionDAOTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TransactionDAO transactionDAO;

    @BeforeEach
    void setup() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    // -------------------------
    // addTransaction()
    // -------------------------

    @Test
    void addTransaction_success() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = transactionDAO.addTransaction(
                1,
                500.0,
                "CREDIT"
        );

        assertTrue(result);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setDouble(2, 500.0);
        verify(preparedStatement).setString(3, "CREDIT");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void addTransaction_failure() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = transactionDAO.addTransaction(
                1,
                500.0,
                "DEBIT"
        );

        assertFalse(result);
    }

    // -------------------------
    // getWalletBalance()
    // -------------------------

    @Test
    void getWalletBalance_onlyCredit() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("type")).thenReturn("CREDIT");
        when(resultSet.getDouble("amount")).thenReturn(200.0);

        double balance = transactionDAO.getWalletBalance(1);

        assertEquals(200.0, balance);
    }

    @Test
    void getWalletBalance_creditAndDebit() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("type"))
                .thenReturn("CREDIT")
                .thenReturn("DEBIT");
        when(resultSet.getDouble("amount"))
                .thenReturn(500.0)
                .thenReturn(200.0);

        double balance = transactionDAO.getWalletBalance(1);

        assertEquals(300.0, balance);
    }

    @Test
    void getWalletBalance_noTransactions() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        double balance = transactionDAO.getWalletBalance(1);

        assertEquals(0.0, balance);
    }
}
