package com.example.e_pesegu_app;

public class DataSewaGuna {
    private String Jabatan;
    private String Email;
    private String Kontrak;
    private String State;
    private String PrimaryKey;
    private String Nama_Kontrak;
    private String Tamat_tempoh;
    private String pembekal;

    public DataSewaGuna(String jabatan, String email, String kontrak, String state, String primaryKey, String nama_Kontrak, String tamat_tempoh, String pembekal) {
        Jabatan = jabatan;
        Email = email;
        Kontrak = kontrak;
        State = state;
        PrimaryKey = primaryKey;
        Nama_Kontrak = nama_Kontrak;
        Tamat_tempoh = tamat_tempoh;
        this.pembekal = pembekal;
    }

    public String getNama_Kontrak() {
        return Nama_Kontrak;
    }

    public void setNama_Kontrak(String nama_Kontrak) {
        Nama_Kontrak = nama_Kontrak;
    }

    public String getTamat_tempoh() {
        return Tamat_tempoh;
    }

    public void setTamat_tempoh(String tamat_tempoh) {
        Tamat_tempoh = tamat_tempoh;
    }

    public String getPembekal() {
        return pembekal;
    }

    public void setPembekal(String pembekal) {
        this.pembekal = pembekal;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        PrimaryKey = primaryKey;
    }



    public String getJabatan() {return Jabatan;}

    public void setJabatan(String jabatan) {
        this.Jabatan = jabatan;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getKontrak() {
        return Kontrak;
    }

    public void setKontrak(String kontrak) {
        this.Kontrak = kontrak;
    }



    public DataSewaGuna() {
    }


}
