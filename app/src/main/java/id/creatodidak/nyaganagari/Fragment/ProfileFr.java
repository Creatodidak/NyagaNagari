package id.creatodidak.nyaganagari.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Adapter.BeritaAdapter;
import id.creatodidak.nyaganagari.Adapter.PjuAdapter;
import id.creatodidak.nyaganagari.Models.BeritaItem;
import id.creatodidak.nyaganagari.Models.PejabatItem;
import id.creatodidak.nyaganagari.R;
import id.creatodidak.nyaganagari.API.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFr extends Fragment {

    PjuAdapter pjuAdapter;
    List<PejabatItem> PejabatList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFr.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFr newInstance(String param1, String param2) {
        ProfileFr fragment = new ProfileFr();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager;
        RecyclerView rv = view.findViewById(R.id.pjuRv);
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        pjuAdapter = new PjuAdapter(PejabatList);
        rv.setAdapter(pjuAdapter);



        WebView youtubeWebView; //todo find or bind web view
        String myVideoYoutubeId = "94k5t2mJU4E";


        youtubeWebView = view.findViewById(R.id.prWv);

        youtubeWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        youtubeWebView.loadUrl("https://www.youtube.com/embed/" + myVideoYoutubeId);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<PejabatItem>> Sm = apiInterface.getPejabat();

        Sm.enqueue(new Callback<List<PejabatItem>>() {

            @Override
            public void onResponse(Call<List<PejabatItem>> call, Response<List<PejabatItem>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    PejabatList.clear();
                    PejabatList.addAll(response.body());
                    pjuAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<PejabatItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}