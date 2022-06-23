package id.creatodidak.dumaspresisi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;

import java.util.Locale;

import id.creatodidak.dumaspresisi.API.ApiClient;
import id.creatodidak.dumaspresisi.API.ApiInterface;
import id.creatodidak.dumaspresisi.Model.Reg;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    Button reg;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText Nik = findViewById(R.id.nik);
        EditText Nama = findViewById(R.id.username);
        EditText Alamat = findViewById(R.id.alamat);
        EditText Password = findViewById(R.id.password);
        EditText Hp = findViewById(R.id.nohp);


        reg = findViewById(R.id.regBtn);


        reg.setOnClickListener(v -> {
            String Snik = Nik.getText().toString();
            String Snama = Nama.getText().toString().toUpperCase(Locale.ROOT);
            String Salamat = Alamat.getText().toString().toUpperCase(Locale.ROOT);
            String Spassword = Password.getText().toString();
            String Shp = Hp.getText().toString();

            daftar(Snik, Snama, Salamat, Spassword, Shp);


        });

    }

    private void daftar(String nik, String nama, String alamat, String password, String hp) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Reg> registerCall = apiInterface.reg(nik, nama, alamat, password, hp);
        registerCall.enqueue(new Callback<Reg>() {

            @Override
            public void onResponse(Call<Reg> call, Response<Reg> response) {

                if (response.isSuccessful() && response.body().getPesan().equals("YES")) {

                    new KAlertDialog(Register.this, KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Mendaftar")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();


                } else {
                    new KAlertDialog(Register.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText(response.body().getPesan())
                            .setConfirmText("OK")
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Reg> call, Throwable t) {
                // warning = findViewById(R.id.warning);
                // warning.setText("");
                //new Handler().postDelayed(() -> warning.setVisibility(View.GONE), 2000);
                Toast.makeText(Register.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}