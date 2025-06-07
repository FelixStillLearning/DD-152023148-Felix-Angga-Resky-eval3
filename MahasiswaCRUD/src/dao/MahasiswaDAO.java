package dao;

import database.DatabaseConnection;
import model.Mahasiswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object untuk operasi CRUD pada tabel mahasiswa
 * Mengelola semua operasi database untuk entitas Mahasiswa
 */
public class MahasiswaDAO {
    
    /**
     * Method untuk menambahkan mahasiswa baru ke database
     * @param mahasiswa Object mahasiswa yang akan ditambahkan
     * @return true jika berhasil, false jika gagal
     */
    public boolean insertMahasiswa(Mahasiswa mahasiswa) {
        String sql = "INSERT INTO mahasiswa (nim, nama, kelas, no_wa) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mahasiswa.getNim());
            pstmt.setString(2, mahasiswa.getNama());
            pstmt.setString(3, mahasiswa.getKelas());
            pstmt.setString(4, mahasiswa.getNoWa());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error inserting mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Method untuk mengambil semua data mahasiswa dari database
     * @return List berisi semua data mahasiswa
     */
    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String sql = "SELECT nim, nama, kelas, no_wa FROM mahasiswa ORDER BY nim";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setNim(rs.getString("nim"));
                mahasiswa.setNama(rs.getString("nama"));
                mahasiswa.setKelas(rs.getString("kelas"));
                mahasiswa.setNoWa(rs.getString("no_wa"));
                
                mahasiswaList.add(mahasiswa);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all mahasiswa: " + e.getMessage());
        }
        
        return mahasiswaList;
    }
    
    /**
     * Method untuk mengupdate data mahasiswa berdasarkan NIM
     * @param mahasiswa Object mahasiswa dengan data baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateMahasiswa(Mahasiswa mahasiswa) {
        String sql = "UPDATE mahasiswa SET nama = ?, kelas = ?, no_wa = ? WHERE nim = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mahasiswa.getNama());
            pstmt.setString(2, mahasiswa.getKelas());
            pstmt.setString(3, mahasiswa.getNoWa());
            pstmt.setString(4, mahasiswa.getNim());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Method untuk menghapus mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean deleteMahasiswa(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nim);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting mahasiswa: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Method untuk mengambil data mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang dicari
     * @return Object Mahasiswa atau null jika tidak ditemukan
     */
    public Mahasiswa getMahasiswaByNim(String nim) {
        String sql = "SELECT nim, nama, kelas, no_wa FROM mahasiswa WHERE nim = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nim);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Mahasiswa mahasiswa = new Mahasiswa();
                    mahasiswa.setNim(rs.getString("nim"));
                    mahasiswa.setNama(rs.getString("nama"));
                    mahasiswa.setKelas(rs.getString("kelas"));
                    mahasiswa.setNoWa(rs.getString("no_wa"));
                    
                    return mahasiswa;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting mahasiswa by NIM: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Method untuk mengecek apakah NIM sudah ada di database
     * @param nim NIM yang akan dicek
     * @return true jika NIM sudah ada, false jika belum
     */
    public boolean isNimExists(String nim) {
        return getMahasiswaByNim(nim) != null;
    }
}
