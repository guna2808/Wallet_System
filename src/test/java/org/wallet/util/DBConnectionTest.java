package org.wallet.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testDatabaseConnection() {

        try {
            Connection con = DBConnection.getConnection();
            assertNotNull(con);
            con.close();
        } catch (Exception e) {
            fail("Database connection failed");
        }
    }
}
