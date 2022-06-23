package id.creatodidak.dumaspresisi.Model;

import com.google.gson.annotations.SerializedName;

public class Logins {

    @SerializedName("next")
    private boolean next;

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private int status;

    public boolean isNext() {
        return next;
    }

    public Data getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}