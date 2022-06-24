package id.creatodidak.nyaganagari.dumas;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import id.creatodidak.nyaganagari.BuildConfig;
import id.creatodidak.nyaganagari.R;
import id.creatodidak.nyaganagari.dumas.API.ApiClient;
import id.creatodidak.nyaganagari.dumas.API.ApiInterface;
import id.creatodidak.nyaganagari.dumas.Model.Update;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BRIPDA ANGGI PERIANTO on 10,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class Splash extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    ApiInterface apiInterface;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Update> updateCall = apiInterface.update();

        updateCall.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (versionName.equals(response.body().getVersion())){

                }else{
                    setContentView(R.layout.update);
                    if (checkPermission()) {
                        UpdateApp atualizaApp = new UpdateApp();
                        atualizaApp.setContext(Splash.this);
                        atualizaApp.execute("https://polreslandak.id/media/app-debug.apk");
                    } else {
                        requestPermission();
                    }
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {
                Toast.makeText(Splash.this, "Periksa Konektivitas Jaringan Anda!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted && cameraAccepted) {
                    UpdateApp updateApp = new UpdateApp();
                    updateApp.setContext(Splash.this);
                    updateApp.execute("https://polreslandak.id/media/app-debug.apk");
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

}
