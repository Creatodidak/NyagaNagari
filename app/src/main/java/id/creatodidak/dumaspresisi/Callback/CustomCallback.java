package id.creatodidak.dumaspresisi.Callback;

import com.google.gson.JsonObject;

public interface CustomCallback {

    String onSucess(JsonObject value);

    void onFailure(String s);

}