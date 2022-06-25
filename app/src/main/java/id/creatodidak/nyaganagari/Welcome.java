package id.creatodidak.nyaganagari;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import id.creatodidak.nyaganagari.API.ApiClient;
import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Models.Update;
import id.creatodidak.nyaganagari.UpdateApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Welcome extends AppCompatActivity {

    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    ApiInterface apiInterface;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<Update> updateCall = apiInterface.updates();

                updateCall.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (versionName.equals(response.body().getVersion())){

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            TextView tv = findViewById(R.id.txtUpdate);
                            tv.setText("Membuka Aplikasi");
                            openLogin();
                        }
                    }, 1500);

                }else{

                    Toast.makeText(Welcome.this, "PEMBARUAN TERSEDIA!", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.update);
                    if (checkPermission()) {
                        id.creatodidak.nyaganagari.dumas.UpdateApp atualizaApp = new id.creatodidak.nyaganagari.dumas.UpdateApp();
                        atualizaApp.setContext(Welcome.this);
                        atualizaApp.execute("https://polreslandak.id/media/app-debug.apk");
                    } else {
                        requestPermission();
                    }
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {
                ProgressBar pb;
                TextView tv = findViewById(R.id.txtUpdate);
                tv.setText("Tidak Ada Koneksi Internet! \n Silahkan Aktifkan Konektivitas Internet Anda");
                pb = findViewById(R.id.progressBar);
                pb.setVisibility(View.GONE);

                Button bt = findViewById(R.id.button2);
                bt.setVisibility(View.VISIBLE);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishAndRemoveTask();
                    }
                });
            }
        });
    }

    private void openLogin() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Intent i = new Intent(Welcome.this, Beranda.class);
                startActivity(i);
                finish();
            }
        }, 1500);
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
                    updateApp.setContext(Welcome.this);
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
