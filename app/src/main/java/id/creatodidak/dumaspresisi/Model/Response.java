package id.creatodidak.dumaspresisi.Model;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("pesan")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}