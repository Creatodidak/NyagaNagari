package id.creatodidak.nyaganagari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.gson.JsonObject;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.io.File;
import java.util.ArrayList;

import id.creatodidak.nyaganagari.dumas.API.ApiClient;
import id.creatodidak.nyaganagari.dumas.API.ApiInterface;
import id.creatodidak.nyaganagari.dumas.Callback.CustomCallback;
import id.creatodidak.nyaganagari.dumas.Config;
import id.creatodidak.nyaganagari.dumas.progressLapor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DumasPolres extends AppCompatActivity {

    private static final String ARG_PARAM2 = "param2";
    private final String SERVER_URL = "https://polreslandak.id/upload.php";
    private final int STORAGE_PERMISSION_CODE = 1;
    private final int PICK_FILE_PDF = 2;
    private String selectedFilePath;
    private File FILE_UPLOAD = null;
    private PickiT pickiT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dumas_polres);

        Session session;
        String[] jenis = {"Jenis Laporan", "Hukum / HAM", "Pelayanan Masyarakat", "Penyalahgunaan Wewenang", "Kewaspadaan Nasional", "Personel", "Pungutan Liar / Korupsi", "Penyidikan Tindak Pidana", "Tanah / Rumah", "Lain-lain"};
        String[] pelayanan = {"Pilih Pelayanan", "SPKT", "SKCK", "SIM", "Identifikasi Sidik Jari", "Penyidikan Sat Reskrim", "Penyidikan Sat Res Narkoba"};
        session = new Session(DumasPolres.this);
        ScrollView sv = findViewById(R.id.mainS);

        Button btn = findViewById(R.id.button);
        String SSnama = session.getUserDetail().get(Session.NAMA);
        String SSalamat = session.getUserDetail().get(Session.ALAMAT);
        String SSnik = session.getUserDetail().get(Session.NIK);

        ApiInterface apiInterfaces = ApiClient.getClient().create(ApiInterface.class);

        String SShp = session.getUserDetail().get(Session.HP);


        CheckBox cb = findViewById(R.id.checkBox);

        EditText Etnik = findViewById(R.id.Etnamax);
        EditText Etnama = findViewById(R.id.Etnama);
        EditText Etalamat = findViewById(R.id.Etalamat);
        EditText Ethp = findViewById(R.id.Ethp);
        EditText Etterlapor = findViewById(R.id.etNamaterlapor);
        EditText Etpenjelasan = findViewById(R.id.Etsaran);
        Spinner Spjenislaporan = findViewById(R.id.spJenislaporan);
        Spinner Spjenispelayanan = findViewById(R.id.spPelayanan);
        TextView tvpick = findViewById(R.id.tvPick);

        Etnik.setText(SSnik);
        Etnama.setText(SSnama);
        Etalamat.setText(SSalamat);
        Ethp.setText(SShp);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(DumasPolres.this, android.R.layout.simple_spinner_item, jenis);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spjenislaporan.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(DumasPolres.this, android.R.layout.simple_spinner_item, pelayanan);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spjenispelayanan.setAdapter(dataAdapter2);

        btn.setEnabled(cb.isChecked());

        Spjenislaporan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            final Spinner spj = findViewById(R.id.spPelayanan);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pelayanan Masyarakat")) {
                    spj.setVisibility(View.VISIBLE);
                } else {
                    spj.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btn.setEnabled(true);
                }
                if (!isChecked) {
                    btn.setEnabled(false);
                }
            }
        });

        tvpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPDF.setType("image/*");
                    startActivityForResult(intentPDF, PICK_FILE_PDF);

                } else {
                    requestStoragePermission();
                }


            }


        });

        pickiT = new PickiT(DumasPolres.this, new PickiTCallbacks() {
            @Override
            public void PickiTonUriReturned() {

            }

            @Override
            public void PickiTonStartListener() {

            }

            @Override
            public void PickiTonProgressUpdate(int progress) {

            }

            @Override
            public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {

                FILE_UPLOAD = new File(path);
                tvpick.setText(FILE_UPLOAD.getName());

            }

            @Override
            public void PickiTonMultipleCompleteListener(ArrayList<String> paths, boolean wasSuccessful, String Reason) {

            }
        }, this);


        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String nik = Etnik.getText().toString();
                final String nama = Etnama.getText().toString();
                final String alamat = Etalamat.getText().toString();
                final String hp = Ethp.getText().toString();
                final String terlapor = Etterlapor.getText().toString();
                final String penjelasan = Etpenjelasan.getText().toString();
                final String jenislaporan = Spjenislaporan.getSelectedItem().toString();
                final int jenispelayanan = Spjenispelayanan.getSelectedItemPosition();
                if (FILE_UPLOAD != null) {

                    KAlertDialog pDialog = new KAlertDialog(v.getContext(), KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Memproses..");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    upload_Pdf(FILE_UPLOAD, FILE_UPLOAD.getName(), nik, nama, alamat, hp, terlapor, penjelasan, jenislaporan, jenispelayanan, new CustomCallback() {
                        @Override
                        public String onSucess(JsonObject value) {

                            pDialog.dismiss();

                            KAlertDialog pDialog = new KAlertDialog(v.getContext(), KAlertDialog.SUCCESS_TYPE);
                            pDialog.setTitleText("OK");
                            pDialog.setContentText("Berhasil Membuat Pengaduan");
                            pDialog.setCancelable(false);
                            pDialog.setConfirmText("OK");
                            pDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Fragment fragment = new progressLapor();
                                    FragmentManager fragmentManager = DumasPolres.this.getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            });
                            pDialog.show();
                            tvpick.setText("KETUK DISINI UNTUK UPLOAD FILE");

                            FILE_UPLOAD = null; //reset

                            return null;
                        }

                        @Override
                        public void onFailure(String s) {
                            pDialog.dismiss();

                            KAlertDialog pDialog = new KAlertDialog(v.getContext(), KAlertDialog.WARNING_TYPE);
                            pDialog.setTitleText("Maaf");
                            pDialog.setContentText("Gagal Input Laporan " + s);
                            pDialog.setConfirmText("OK");
                            pDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            });
                            pDialog.setCancelable(false);
                            pDialog.show();

                        }
                    });

                } else {
                    Toast.makeText(v.getContext(), "Silahkan pilih file dahulu", Toast.LENGTH_SHORT).show();

                }
            }

            public void upload_Pdf(File fl, String filename,
                                   String nik, String nama, String alamat, String hp, String terlapor, String penjelasan, String jenislaporan, int jenispelayanan, CustomCallback customCallback) {

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fl);
                RequestBody submit = RequestBody.create(MediaType.parse("text/plain"), filename);
                RequestBody xnik = RequestBody.create(MediaType.parse("text/plain"), nik);
                RequestBody xnama = RequestBody.create(MediaType.parse("text/plain"), nama);
                RequestBody xalamat = RequestBody.create(MediaType.parse("text/plain"), alamat);
                RequestBody xhp = RequestBody.create(MediaType.parse("text/plain"), hp);
                RequestBody xterlapor = RequestBody.create(MediaType.parse("text/plain"), terlapor);
                RequestBody xpenjelasan = RequestBody.create(MediaType.parse("text/plain"), penjelasan);
                RequestBody xjenislaporan = RequestBody.create(MediaType.parse("text/plain"), jenislaporan);
                RequestBody xjenispelayanan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jenispelayanan));

                //RequestBody asalSURAT = RequestBody.create(MediaType.parse("text/plain"), asal_Surat);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Config.WEB + "/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface api = retrofit.create(ApiInterface.class);
                Call<JsonObject> call = api.uploadPdf(requestFile, submit, xnik, xnama, xalamat, xhp, xterlapor, xpenjelasan, xjenislaporan, xjenispelayanan);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        if (response.body().get("error") != null) {
                            if ((response.body().get("error").toString().replace("\"", "")).equals("1")) {

                                if (response.body().get("mes") != null) {
                                    customCallback.onFailure((response.body().get("mes").toString().replace("\"", "")));
                                } else {
                                    customCallback.onFailure("Error Kode #51");
                                }

                            } else {
                                customCallback.onSucess(response.body());
                            }
                        } else {
                            customCallback.onSucess(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        customCallback.onFailure("Error Koneksi.. " + t.getMessage());
                    }
                });
            }

        });

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(DumasPolres.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DumasPolres.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FILE_PDF) {
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            }
        }
    }
}