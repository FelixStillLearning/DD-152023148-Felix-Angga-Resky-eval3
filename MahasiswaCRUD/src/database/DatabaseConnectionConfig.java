package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class untuk mengelola koneksi database MySQL
 * Versi alternatif untuk berbagai environment (Laragon/XAMPP/MySQL Standalone)
 */
public class DatabaseConnectionConfig {
    
    // =============================================================================
    // KONFIGURASI UNTUK BERBAGAI ENVIRONMENT
    // =============================================================================
    
    // 1. LARAGON Configuration (Default)
    private static final String LARAGON_URL = "jdbc:mysql://localhost:3306/db_eval3";
    private static final String LARAGON_USERNAME = "root";
    private static final String LARAGON_PASSWORD = ""; // Kosong untuk Laragon
    
    // 2. XAMPP Configuration
    private static final String XAMPP_URL = "jdbc:mysql://localhost:3306/db_eval3";
    private static final String XAMPP_USERNAME = "root";
    private static final String XAMPP_PASSWORD = ""; // Kosong untuk XAMPP
    
    // 3. MySQL Standalone Configuration
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/db_eval3";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "oop"; // Sesuai asli
    
    // Driver MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Environment yang sedang digunakan (ganti sesuai kebutuhan)
    private static final String CURRENT_ENV = "LARAGON"; // Opsi: LARAGON, XAMPP, MYSQL
    
    /**
     * Method untuk mendapatkan koneksi sesuai environment
     * @return Connection object ke database MySQL
     * @throws SQLException jika terjadi error koneksi
     */
    public static Connection getConnection() throws SQLException {
        String url, username, password;
        
        // Pilih konfigurasi berdasarkan environment
        switch (CURRENT_ENV.toUpperCase()) {
            case "LARAGON":
                url = LARAGON_URL;
                username = LARAGON_USERNAME;
                password = LARAGON_PASSWORD;
                break;
            case "XAMPP":
                url = XAMPP_URL;
                username = XAMPP_USERNAME;
                password = XAMPP_PASSWORD;
                break;
            case "MYSQL":
                url = MYSQL_URL;
                username = MYSQL_USERNAME;
                password = MYSQL_PASSWORD;
                break;
            default:
                throw new SQLException("Environment tidak dikenal: " + CURRENT_ENV);
        }
        
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            return DriverManager.getConnection(url, username, password);
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
            boolean isConnected = conn != null && !conn.isClosed();
            if (isConnected) {
                System.out.println("✓ Koneksi database berhasil!");
                System.out.println("  Environment: " + CURRENT_ENV);
                System.out.println("  Database: db_eval3");
            }
            return isConnected;
        } catch (SQLException e) {
            System.err.println("✗ Error koneksi database: " + e.getMessage());
            System.err.println("  Environment: " + CURRENT_ENV);
            System.err.println("  Pastikan:");
            System.err.println("  - " + CURRENT_ENV + " server sudah berjalan");
            System.err.println("  - Database 'db_eval3' sudah dibuat");
            return false;
        }
    }
    
    /**
     * Method untuk menampilkan konfigurasi saat ini
     */
    public static void showCurrentConfig() {
        System.out.println("================================================================================");
        System.out.println("                    KONFIGURASI DATABASE SAAT INI");
        System.out.println("================================================================================");
        System.out.println("Environment: " + CURRENT_ENV);
        
        switch (CURRENT_ENV.toUpperCase()) {
            case "LARAGON":
                System.out.println("URL: " + LARAGON_URL);
                System.out.println("Username: " + LARAGON_USERNAME);
                System.out.println("Password: [kosong]");
                break;
            case "XAMPP":
                System.out.println("URL: " + XAMPP_URL);
                System.out.println("Username: " + XAMPP_USERNAME);
                System.out.println("Password: [kosong]");
                break;
            case "MYSQL":
                System.out.println("URL: " + MYSQL_URL);
                System.out.println("Username: " + MYSQL_USERNAME);
                System.out.println("Password: " + MYSQL_PASSWORD);
                break;
        }
        System.out.println("================================================================================");
    }
}
