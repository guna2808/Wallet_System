package org.wallet.dao;

import org.wallet.model.Transaction;
import org.wallet.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean addTransaction(int userId, double amount, String type) {

        String sql =
                "INSERT INTO transactions (user_id, amount, type) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, type);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactionsByUser(int userId) {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("type"),
                        rs.getTimestamp("created_at")
                );
                list.add(t);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public double getWalletBalance(int userId) {

        String sql =
                "SELECT " +
                        "SUM(CASE WHEN type='credit' THEN amount ELSE 0 END) - " +
                        "SUM(CASE WHEN type='debit' THEN amount ELSE 0 END) AS balance " +
                        "FROM transactions WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

            return 0.0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
