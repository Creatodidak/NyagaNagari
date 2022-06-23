package id.creatodidak.dumaspresisi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.creatodidak.dumaspresisi.API.ApiClient;
import id.creatodidak.dumaspresisi.API.ApiInterface;
import id.creatodidak.dumaspresisi.Model.Saran;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link saran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class saran extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public saran() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment saran.
     */
    // TODO: Rename and change types and number of parameters
    public static saran newInstance(String param1, String param2) {
        saran fragment = new saran();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frsaran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etName = view.findViewById(R.id.Etnamax);
        Button btn = view.findViewById(R.id.button);
        EditText saranEtz = view.findViewById(R.id.Etsaran);
        Session sessionz = new Session(getContext());
        String namaz = sessionz.getUserDetail().get(Session.NAMA);
        etName.setText(namaz);
        etName.setEnabled(false);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(saranEtz.getText().toString())) {
                    Toast.makeText(v.getContext(), "Ketikkan Saran Anda Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    upload();
                }
            }

            private void upload() {
                EditText saranEt = view.findViewById(R.id.Etsaran);
                Session session = new Session(getContext());
                String nama = session.getUserDetail().get(Session.NAMA);
                String nik = session.getUserDetail().get(Session.NIK);
                ApiInterface apiInterface;
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                String saran = saranEt.getText().toString();

                Call<Saran> Sm = apiInterface.inputSaran(nama, nik, saran);

                Sm.enqueue(new Callback<Saran>() {
                    @Override
                    public void onResponse(Call<Saran> call, Response<Saran> response) {
                        if (response.isSuccessful() && response.body().getPesan().equals("YES")) {
                            saranEt.getText().clear();
                            Toast.makeText(getContext(), "Berhasil Memberikan Saran", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Gagal Memberikan Saran", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Saran> call, Throwable t) {
                        Toast.makeText(getContext(), "PERIKSA KONEKTIVITAS ANDA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}