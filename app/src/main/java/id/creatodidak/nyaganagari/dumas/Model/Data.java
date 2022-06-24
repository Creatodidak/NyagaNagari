package id.creatodidak.nyaganagari.dumas.Model;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("nik")
    private String nik;

    @SerializedName("nama")
    private String nama;

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

    public String getHp() {
        return hp;
    }

    public String getAlamat() {
        return alamat;
    }
}