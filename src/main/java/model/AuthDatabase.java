package model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class AuthDatabase {

    private static final String URL;

    static {
        Path dbPath = Paths.get(System.getProperty("user.dir"), "app.db");
        URL = "jdbc:sqlite:" + dbPath.toAbsolutePath();
        System.out.println("DB FILE = " + dbPath.toAbsolutePath());

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL
                )
            """);

        } catch (Exception e) {
            System.err.println("Ошибка инициализации БД: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static boolean register(String username, String password) {
        String sql = "INSERT INTO accounts(username, password) VALUES(?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            ps.setString(2, password);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Не удалось зарегистрировать: " + e.getMessage());
            return false;
        }
    }

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT password FROM accounts WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String real = rs.getString("password");
                return real.equals(password);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при проверке логина: " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE accounts SET password = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, username.trim());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Не удалось обновить пароль: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUser(String username) {
        String sql = "DELETE FROM accounts WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Не удалось удалить пользователя: " + e.getMessage());
            return false;
        }
    }
}
