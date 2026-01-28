package org.wallet.dao;

import org.wallet.model.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private final DataSource dataSource;

    public TransactionDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addTransaction(int userId, double amount, String type) {

        String sql =
                "INSERT INTO transactions (user_id, amount, type) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, type);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public List<Transaction> getTransactionsByUser(int userId) {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try (Connection con = dataSource.getConnection();
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

        } catch (SQLException e) {
            return list;
        }

        return list;
    }

    public double getWalletBalance(int userId) {

        String sql =
                "SELECT amount, type FROM transactions WHERE user_id = ?";

        double balance = 0.0;

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");

                if ("credit".equalsIgnoreCase(type)) {
                    balance += amount;
                } else if ("debit".equalsIgnoreCase(type)) {
                    balance -= amount;
                }
            }

        } catch (SQLException e) {
            return 0.0;
        }

        return balance;
    }
}
