package id.creatodidak.nyaganagari.dumas.Model.prog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Progress {

    @SerializedName("progress")
    private List<ProgressItem> progress;

    public List<ProgressItem> getProgress() {
        return progress;
    }
}