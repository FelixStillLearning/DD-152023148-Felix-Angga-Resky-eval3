package main;

import database.DatabaseConnection;
import dao.MahasiswaDAO;
import model.Mahasiswa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Class untuk testing koneksi database dan operasi CRUD
 * Dapat dijalankan secara terpisah untuk mengecek fungsi backend
 */
public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("                    TEST KONEKSI DATABASE DAN OPERASI CRUD");
        System.out.println("================================================================================");
        
        // Test 1: Koneksi Database
        System.out.println("\n1. Testing koneksi database...");
        testDatabaseConnection();
        
        // Test 2: CRUD Operations
        System.out.println("\n2. Testing operasi CRUD...");
        testCRUDOperations();
        
        System.out.println("\n================================================================================");
        System.out.println("Testing selesai!");
        System.out.println("================================================================================");
    }
    
    /**
     * Test koneksi ke database
     */
    private static void testDatabaseConnection() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Koneksi database BERHASIL!");
                System.out.println("  - Database: db_eval3");
                System.out.println("  - Host: localhost:3306");
                System.out.println("  - User: root");
                conn.close();
            } else {
                System.out.println("✗ Koneksi database GAGAL!");
            }
        } catch (SQLException e) {
            System.out.println("✗ Error koneksi database: " + e.getMessage());
            System.out.println("  Pastikan:");
            System.out.println("  - MySQL server sudah berjalan");
            System.out.println("  - Database 'db_eval3' sudah dibuat");
            System.out.println("  - Username/password benar (root/oop)");
        }
    }
    
    /**
     * Test operasi CRUD
     */
    private static void testCRUDOperations() {
        MahasiswaDAO dao = new MahasiswaDAO();
        
        try {
            // Test READ - Ambil semua data
            System.out.println("\n2.1 Testing READ operation...");
            List<Mahasiswa> semuaMahasiswa = dao.getAllMahasiswa();
            System.out.println("✓ Jumlah data mahasiswa saat ini: " + semuaMahasiswa.size());
            
            if (!semuaMahasiswa.isEmpty()) {
                System.out.println("  Data mahasiswa:");
                for (Mahasiswa m : semuaMahasiswa) {
                    System.out.println("    - " + m.getNim() + " | " + m.getNama() + 
                                     " | " + m.getKelas() + " | " + m.getNoWa());
                }
            }
            
            // Test CREATE - Tambah data test
            System.out.println("\n2.2 Testing CREATE operation...");
            String testNim = "TEST-001";
            Mahasiswa testMahasiswa = new Mahasiswa(testNim, "Test User", "TEST-CLASS", "081234567890");
            
            // Hapus data test jika sudah ada
            if (dao.isNimExists(testNim)) {
                dao.deleteMahasiswa(testNim);
            }
            
            boolean insertResult = dao.insertMahasiswa(testMahasiswa);
            if (insertResult) {
                System.out.println("✓ INSERT data test BERHASIL!");
            } else {
                System.out.println("✗ INSERT data test GAGAL!");
                return;
            }
            
            // Test READ by NIM
            System.out.println("\n2.3 Testing READ by NIM...");
            Mahasiswa foundMahasiswa = dao.getMahasiswaByNim(testNim);
            if (foundMahasiswa != null) {
                System.out.println("✓ Data ditemukan: " + foundMahasiswa.toString());
            } else {
                System.out.println("✗ Data tidak ditemukan!");
            }
            
            // Test UPDATE
            System.out.println("\n2.4 Testing UPDATE operation...");
            testMahasiswa.setNama("Test User Updated");
            testMahasiswa.setKelas("UPDATED-CLASS");
            boolean updateResult = dao.updateMahasiswa(testMahasiswa);
            if (updateResult) {
                System.out.println("✓ UPDATE data test BERHASIL!");
                
                // Verify update
                Mahasiswa updatedMahasiswa = dao.getMahasiswaByNim(testNim);
                if (updatedMahasiswa != null) {
                    System.out.println("  Data setelah update: " + updatedMahasiswa.toString());
                }
            } else {
                System.out.println("✗ UPDATE data test GAGAL!");
            }
            
            // Test DELETE
            System.out.println("\n2.5 Testing DELETE operation...");
            boolean deleteResult = dao.deleteMahasiswa(testNim);
            if (deleteResult) {
                System.out.println("✓ DELETE data test BERHASIL!");
                
                // Verify delete
                Mahasiswa deletedMahasiswa = dao.getMahasiswaByNim(testNim);
                if (deletedMahasiswa == null) {
                    System.out.println("  Data berhasil dihapus dari database");
                } else {
                    System.out.println("  WARNING: Data masih ada setelah delete!");
                }
            } else {
                System.out.println("✗ DELETE data test GAGAL!");
            }
            
            System.out.println("\n✓ Semua operasi CRUD berhasil ditest!");
            
        } catch (Exception e) {
            System.out.println("✗ Error saat testing CRUD: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
