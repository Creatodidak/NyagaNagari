package id.creatodidak.nyaganagari.dumas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.nyaganagari.R;
import id.creatodidak.nyaganagari.dumas.API.ApiClient;
import id.creatodidak.nyaganagari.dumas.API.ApiInterface;
import id.creatodidak.nyaganagari.dumas.Adapter.Adapter;
import id.creatodidak.nyaganagari.dumas.Model.prog.ProgressItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link progressLapor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class progressLapor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Adapter adapter;
    List<ProgressItem> SMasukList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public progressLapor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment progressLapor.
     */
    // TODO: Rename and change types and number of parameters
    public static progressLapor newInstance(String param1, String param2) {
        progressLapor fragment = new progressLapor();
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
        return inflater.inflate(R.layout.frprogresslapor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager;
        RecyclerView rv = view.findViewById(R.id.rvBerita);
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        adapter = new Adapter(SMasukList);
        rv.setAdapter(adapter);

        fetchprog();
    }

    private void fetchprog() {
        Session session = new Session(getContext());
        String nama = session.getUserDetail().get(Session.NAMA);
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProgressItem>> Sm = apiInterface.getProgress(nama);

        Sm.enqueue(new Callback<List<ProgressItem>>() {
            @Override
            public void onResponse(Call<List<ProgressItem>> call, Response<List<ProgressItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SMasukList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<ProgressItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}