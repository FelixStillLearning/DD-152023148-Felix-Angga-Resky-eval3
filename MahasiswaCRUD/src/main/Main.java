package main;

import gui.MainFrame;
import database.DatabaseConnection;

import javax.swing.*;

/**
 * Main class untuk menjalankan aplikasi CRUD Mahasiswa
 * Entry point aplikasi
 */
public class Main {
    
    public static void main(String[] args) {        // Set Look and Feel untuk tampilan yang lebih baik
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting Look and Feel: " + e.getMessage());
        }
        
        // Test koneksi database sebelum menjalankan aplikasi
        if (!DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Gagal terhubung ke database!\n" +
                "Pastikan:\n" +
                "1. MySQL server sudah berjalan\n" +
                "2. Database 'db_eval3' sudah dibuat\n" +
                "3. Username: root, Password: oop\n" +
                "4. MySQL JDBC driver sudah ditambahkan ke classpath", 
                "Database Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    
                    // Tampilkan pesan selamat datang
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Selamat datang di Sistem Manajemen Data Mahasiswa!\n" +
                        "Koneksi database berhasil.", 
                        "Aplikasi Siap", 
                        JOptionPane.INFORMATION_MESSAGE);
                        
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, 
                        "Error menjalankan aplikasi: " + e.getMessage(), 
                        "Application Error", 
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
