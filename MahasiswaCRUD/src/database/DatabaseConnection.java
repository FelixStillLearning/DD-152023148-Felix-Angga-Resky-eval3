package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class untuk mengelola koneksi database MySQL
 * Menggunakan pattern Singleton untuk efisiensi koneksi
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_eval3";
    private static final String USERNAME = "root";
    // Untuk Laragon gunakan password kosong "", untuk MySQL biasa gunakan "oop"
    private static final String PASSWORD = "oop";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Method untuk mendapatkan koneksi ke database
     * @return Connection object ke database MySQL
     * @throws SQLException jika terjadi error koneksi
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver tidak ditemukan: " + e.getMessage());
        }
    }
    
    /**
     * Method untuk test koneksi database
     * @return true jika koneksi berhasil, false jika gagal
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error testing connection: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Method untuk menutup koneksi dengan aman
     * @param conn Connection yang akan ditutup
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
