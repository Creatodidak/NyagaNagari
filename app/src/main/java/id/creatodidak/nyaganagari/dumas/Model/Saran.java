package id.creatodidak.nyaganagari.dumas.Model;

import com.google.gson.annotations.SerializedName;

public class Saran {

    @SerializedName("Pesan")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}