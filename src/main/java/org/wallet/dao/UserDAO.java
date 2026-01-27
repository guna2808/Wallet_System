package org.wallet.dao;

import org.wallet.util.DBConnection;
import java.sql.*;
import org.wallet.util.PasswordUtil;

public class UserDAO {

    public boolean login(String username, String password) {

        String sql = "SELECT password FROM users WHERE username=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return PasswordUtil.verifyPassword(password, hashedPassword);
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Registration with Password Hased
    public boolean register(String username, String password) {

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, PasswordUtil.hashPassword(password));

            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserIdByUsername(String username) {

        String sql = "SELECT id FROM users WHERE username=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            return -1;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
