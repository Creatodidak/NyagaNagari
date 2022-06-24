package id.creatodidak.nyaganagari.dumas.Model;

import com.google.gson.annotations.SerializedName;

public class Update {

    @SerializedName("version")
    private String version;

    public String getVersion() {
        return version;
    }
}