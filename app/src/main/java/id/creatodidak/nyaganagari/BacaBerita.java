package id.creatodidak.nyaganagari;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import id.creatodidak.nyaganagari.API.ApiClient;
import id.creatodidak.nyaganagari.API.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BRIPDA ANGGI PERIANTO on 24,June,2022 CREATODIDAK anggiperianto41ays@gmail.com
 **/
public class BacaBerita extends AppCompatActivity {
    TextView view, tanggal, judul, publish, isi;
    ImageView thm;
    ApiInterface apiInterface;
    String url;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_baca_berita);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<id.creatodidak.nyaganagari.Models.BacaBerita> Sm = apiInterface.baca(url);
        Sm.enqueue(new Callback<id.creatodidak.nyaganagari.Models.BacaBerita>() {
            @Override
            public void onResponse(Call<id.creatodidak.nyaganagari.Models.BacaBerita> call, Response<id.creatodidak.nyaganagari.Models.BacaBerita> response) {

                view = findViewById(R.id.bbView);
                tanggal = findViewById(R.id.bbTanggal);
                judul = findViewById(R.id.bbJudul);
                isi = findViewById(R.id.bbisi);
                publish = findViewById(R.id.bbpublish);

                String gbr = response.body().getGambar();
                view.setText(response.body().getView());
                tanggal.setText(response.body().getWaktu());
                judul.setText(response.body().getJudul());
                isi.setText(response.body().getIsi());
                publish.setText("Dipublish Oleh "+response.body().getPenulis());
                glides(gbr);

            }

            @Override
            public void onFailure(Call<id.creatodidak.nyaganagari.Models.BacaBerita> call, Throwable t) {

            }
        });
    }

    private void glides(String gbr) {
        thm = findViewById(R.id.bbThumb);
        Glide.with(BacaBerita.this)
                .load("https://polreslandak.id/media/fotoblog/"+gbr)
                .placeholder(R.drawable.nf)
                .into(thm);
    }
}
