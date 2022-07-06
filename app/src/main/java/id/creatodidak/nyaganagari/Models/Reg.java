package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

public class Reg {

    @SerializedName("Pesan")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}