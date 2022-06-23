package id.creatodidak.dumaspresisi.Model;

import com.google.gson.annotations.SerializedName;

public class Masuk {

    @SerializedName("nik")
    private String nik;

    @SerializedName("nama")
    private String nama;

    @SerializedName("auth")
    private String auth;

    @SerializedName("hp")
    private String hp;

    @SerializedName("alamat")
    private String alamat;

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getAuth() {
        return auth;
    }

    public String getHp() {
        return hp;
    }

    public String getAlamat() {
        return alamat;
    }
}