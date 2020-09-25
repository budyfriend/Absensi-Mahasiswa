package com.budyfriend_code.absensi;

public class mahasiswa {
    private String key;

    private String nama;
    private String fakultas;
    private String jurusan;
    private String semester;

    public mahasiswa() {
    }

    public mahasiswa(String nama, String fakultas, String jurusan, String semester) {
        this.nama = nama;
        this.fakultas = fakultas;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getNama() {
        return nama;
    }

    public String getFakultas() {
        return fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getSemester() {
        return semester;
    }
}
