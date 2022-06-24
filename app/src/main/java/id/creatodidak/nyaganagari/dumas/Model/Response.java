package id.creatodidak.nyaganagari.dumas.Model;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("pesan")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}