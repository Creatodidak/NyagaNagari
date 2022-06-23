package id.creatodidak.dumaspresisi;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import id.creatodidak.dumaspresisi.Model.Data;


public class Session {

    public static final String NAMA = "nama";
    public static final String NIK = "jabatan";
    public static final String ALAMAT = "alamat";
    public static final String HP = "hp";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private final Context _context;
    private final SharedPreferences sharedPreference;
    private final SharedPreferences.Editor editor;

    public Session(Context context) {
        this._context = context;
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreference.edit();
    }

    public void createLoginSession(Data data) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(NAMA, data.getNama());
        editor.putString(NIK, data.getNik());
        editor.putString(ALAMAT, data.getAlamat());
        editor.putString(HP, data.getHp());

        editor.commit();
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreference.getString(NAMA, null));
        user.put(NIK, sharedPreference.getString(NIK, null));
        user.put(ALAMAT, sharedPreference.getString(ALAMAT, null));
        user.put(HP, sharedPreference.getString(HP, null));

        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreference.getBoolean(IS_LOGGED_IN, false);
    }


}
