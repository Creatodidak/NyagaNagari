package id.creatodidak.nyaganagari.Models;

import com.google.gson.annotations.SerializedName;

import id.creatodidak.nyaganagari.dumas.Model.Data;

public class Mlogin {

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