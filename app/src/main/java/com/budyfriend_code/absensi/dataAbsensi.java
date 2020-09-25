package com.budyfriend_code.absensi;

public class dataAbsensi {
    private String key;

    private String key_mahasiswa;
    private long tanggal;
    private String kehadiaran;

    public dataAbsensi() {
    }

    public dataAbsensi(String key_mahasiswa, long tanggal, String kehadiaran) {
        this.key_mahasiswa = key_mahasiswa;
        this.tanggal = tanggal;
        this.kehadiaran = kehadiaran;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getKey_mahasiswa() {
        return key_mahasiswa;
    }

    public long getTanggal() {
        return tanggal;
    }

    public String getKehadiaran() {
        return kehadiaran;
    }
}
