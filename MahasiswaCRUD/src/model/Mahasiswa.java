package model;

/**
 * Model class untuk data mahasiswa
 * Berisi atribut dan method untuk mengelola data mahasiswa
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String kelas;
    private String noWa;
    
    /**
     * Constructor tanpa parameter
     */
    public Mahasiswa() {
    }
    
    /**
     * Constructor dengan parameter
     * @param nim NIM mahasiswa
     * @param nama Nama mahasiswa
     * @param kelas Kelas mahasiswa
     * @param noWa Nomor WhatsApp mahasiswa
     */
    public Mahasiswa(String nim, String nama, String kelas, String noWa) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.noWa = noWa;
    }
    
    // Getter methods
    public String getNim() {
        return nim;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getKelas() {
        return kelas;
    }
    
    public String getNoWa() {
        return noWa;
    }
    
    // Setter methods
    public void setNim(String nim) {
        this.nim = nim;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    
    public void setNoWa(String noWa) {
        this.noWa = noWa;
    }
    
    /**
     * Override toString method untuk representasi string object
     */
    @Override
    public String toString() {
        return "Mahasiswa{" +
                "nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", kelas='" + kelas + '\'' +
                ", noWa='" + noWa + '\'' +
                '}';
    }
}
