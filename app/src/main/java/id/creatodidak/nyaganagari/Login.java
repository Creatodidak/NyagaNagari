package id.creatodidak.nyaganagari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;

import id.creatodidak.nyaganagari.API.ApiClient;
import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Models.Data;
import id.creatodidak.nyaganagari.Models.Mlogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BRIPDA ANGGI PERIANTO on 10,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class Login extends AppCompatActivity {

    Button loginBtn;
    TextView register;
    ApiInterface apiInterface;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        register = findViewById(R.id.tvReg);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        loginBtn = findViewById(R.id.loginBtn);
        EditText etNik = findViewById(R.id.nik);
        EditText etPass = findViewById(R.id.password);


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String nik = etNik.getText().toString();
                final String password = etPass.getText().toString();

                login(nik, password);
            }
        });


    }

    private void login(String nik, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Mlogin> loginCall = apiInterface.login(nik, password);
        loginCall.enqueue(new Callback<Mlogin>() {

            @Override
            public void onResponse(Call<Mlogin> call, Response<Mlogin> response) {

                assert response.body() != null;
                if (response.body().isNext()) {

                    session = new Session(Login.this);
                    Data data = response.body().getData();

                    session.createLoginSession(data);

                    Intent intent = new Intent(Login.this, Beranda.class);
                    startActivity(intent);

                }

                if (response.body().isNext()) {
                    new KAlertDialog(Login.this, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal Masuk")
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
            public void onFailure(Call<Mlogin> call, Throwable t) {
                // warning = findViewById(R.id.warning);
                // warning.setText("");
                //new Handler().postDelayed(() -> warning.setVisibility(View.GONE), 2000);
                Toast.makeText(Login.this, "Gagal Masuk", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
