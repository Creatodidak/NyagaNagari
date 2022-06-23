package id.creatodidak.dumaspresisi.Model;

import com.google.gson.annotations.SerializedName;

public class Upload {

    @SerializedName("Pesan")
    private String pesan;

    public String getPesan() {
        return pesan;
    }
}