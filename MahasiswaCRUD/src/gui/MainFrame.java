package gui;

import dao.MahasiswaDAO;
import model.Mahasiswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * GUI utama untuk aplikasi CRUD Mahasiswa
 * Menggunakan Java Swing untuk antarmuka pengguna
 */
public class MainFrame extends JFrame {
    private MahasiswaDAO mahasiswaDAO;
    private DefaultTableModel tableModel;
    
    // Komponen GUI
    private JTextField txtNim, txtNama, txtKelas, txtNoWa;
    private JButton btnTambah, btnUpdate, btnHapus, btnClear, btnRefresh;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel lblStatus;
    
    /**
     * Constructor untuk MainFrame
     */
    public MainFrame() {
        mahasiswaDAO = new MahasiswaDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshTable();
        
        // Konfigurasi window
        setTitle("Sistem Manajemen Data Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    /**
     * Method untuk inisialisasi komponen GUI
     */
    private void initializeComponents() {
        // Text fields
        txtNim = new JTextField(15);
        txtNama = new JTextField(15);
        txtKelas = new JTextField(15);
        txtNoWa = new JTextField(15);
        
        // Buttons
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");
        
        // Table
        String[] columnNames = {"NIM", "Nama", "Kelas", "No WhatsApp"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat tabel tidak dapat diedit langsung
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(table);
        
        // Status label
        lblStatus = new JLabel("Siap");
        lblStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Set font untuk komponen
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font fieldFont = new Font("Arial", Font.PLAIN, 12);
        
        txtNim.setFont(fieldFont);
        txtNama.setFont(fieldFont);
        txtKelas.setFont(fieldFont);
        txtNoWa.setFont(fieldFont);
    }
    
    /**
     * Method untuk mengatur layout GUI
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel input (bagian atas)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data Mahasiswa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNim, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtNama, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Kelas:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtKelas, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("No WhatsApp:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtNoWa, gbc);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Panel tabel (bagian tengah)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data Mahasiswa"));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
        
        // Panel button (bagian bawah)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(lblStatus, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Method untuk mengatur event handlers
     */
    private void setupEventHandlers() {
        // Event handler untuk button Tambah
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahMahasiswa();
            }
        });
        
        // Event handler untuk button Update
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMahasiswa();
            }
        });
        
        // Event handler untuk button Hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusMahasiswa();
            }
        });
        
        // Event handler untuk button Clear
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        // Event handler untuk button Refresh
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        
        // Event handler untuk klik pada tabel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    populateFormFromTable();
                }
            }
        });
    }
    
    /**
     * Method untuk menambah mahasiswa baru
     */
    private void tambahMahasiswa() {
        if (!validateInput()) {
            return;
        }
        
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String kelas = txtKelas.getText().trim();
        String noWa = txtNoWa.getText().trim();
        
        // Cek apakah NIM sudah ada
        if (mahasiswaDAO.isNimExists(nim)) {
            JOptionPane.showMessageDialog(this, 
                "NIM " + nim + " sudah ada dalam database!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, kelas, noWa);
        
        if (mahasiswaDAO.insertMahasiswa(mahasiswa)) {
            JOptionPane.showMessageDialog(this, 
                "Data mahasiswa berhasil ditambahkan!", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
            setStatus("Data mahasiswa berhasil ditambahkan");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Gagal menambahkan data mahasiswa!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            setStatus("Gagal menambahkan data");
        }
    }
    
    /**
     * Method untuk update data mahasiswa
     */
    private void updateMahasiswa() {
        if (!validateInput()) {
            return;
        }
        
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String kelas = txtKelas.getText().trim();
        String noWa = txtNoWa.getText().trim();
        
        // Cek apakah mahasiswa dengan NIM tersebut ada
        if (!mahasiswaDAO.isNimExists(nim)) {
            JOptionPane.showMessageDialog(this, 
                "Mahasiswa dengan NIM " + nim + " tidak ditemukan!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin mengupdate data mahasiswa dengan NIM " + nim + "?", 
            "Konfirmasi Update", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Mahasiswa mahasiswa = new Mahasiswa(nim, nama, kelas, noWa);
            
            if (mahasiswaDAO.updateMahasiswa(mahasiswa)) {
                JOptionPane.showMessageDialog(this, 
                    "Data mahasiswa berhasil diupdate!", 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTable();
                setStatus("Data mahasiswa berhasil diupdate");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Gagal mengupdate data mahasiswa!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                setStatus("Gagal mengupdate data");
            }
        }
    }
    
    /**
     * Method untuk menghapus mahasiswa
     */
    private void hapusMahasiswa() {
        String nim = txtNim.getText().trim();
        
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Pilih mahasiswa yang akan dihapus terlebih dahulu!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus mahasiswa dengan NIM " + nim + "?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (mahasiswaDAO.deleteMahasiswa(nim)) {
                JOptionPane.showMessageDialog(this, 
                    "Data mahasiswa berhasil dihapus!", 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTable();
                setStatus("Data mahasiswa berhasil dihapus");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Gagal menghapus data mahasiswa!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                setStatus("Gagal menghapus data");
            }
        }
    }
    
    /**
     * Method untuk mengosongkan form input
     */
    private void clearForm() {
        txtNim.setText("");
        txtNama.setText("");
        txtKelas.setText("");
        txtNoWa.setText("");
        txtNim.requestFocus();
        setStatus("Form dikosongkan");
    }
    
    /**
     * Method untuk refresh data tabel
     */
    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing data
        
        List<Mahasiswa> mahasiswaList = mahasiswaDAO.getAllMahasiswa();
        for (Mahasiswa mahasiswa : mahasiswaList) {
            Object[] row = {
                mahasiswa.getNim(),
                mahasiswa.getNama(),
                mahasiswa.getKelas(),
                mahasiswa.getNoWa()
            };
            tableModel.addRow(row);
        }
        
        setStatus("Data berhasil direfresh. Total: " + mahasiswaList.size() + " mahasiswa");
    }
    
    /**
     * Method untuk mengisi form dari data tabel yang dipilih
     */
    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtNim.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtKelas.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtNoWa.setText(tableModel.getValueAt(selectedRow, 3).toString());
            setStatus("Data mahasiswa dipilih untuk diedit");
        }
    }
    
    /**
     * Method untuk validasi input
     * @return true jika valid, false jika tidak valid
     */
    private boolean validateInput() {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String kelas = txtKelas.getText().trim();
        String noWa = txtNoWa.getText().trim();
        
        // Cek field kosong
        if (nim.isEmpty() || nama.isEmpty() || kelas.isEmpty() || noWa.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Semua field harus diisi!", 
                "Validasi Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validasi format NIM (contoh: minimal 5 karakter)
        if (nim.length() < 5) {
            JOptionPane.showMessageDialog(this, 
                "NIM minimal harus 5 karakter!", 
                "Validasi Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNim.requestFocus();
            return false;
        }
        
        // Validasi nomor WA (hanya angka dan minimal 10 digit)
        if (!noWa.matches("\\d+") || noWa.length() < 10) {
            JOptionPane.showMessageDialog(this, 
                "Nomor WhatsApp harus berupa angka minimal 10 digit!", 
                "Validasi Error", 
                JOptionPane.ERROR_MESSAGE);
            txtNoWa.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Method untuk mengatur status message
     * @param message pesan status yang akan ditampilkan
     */
    private void setStatus(String message) {
        lblStatus.setText(" " + message);
    }
}
