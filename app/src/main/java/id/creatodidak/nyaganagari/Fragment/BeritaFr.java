package id.creatodidak.nyaganagari.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Adapter.BeritaAdapter;
import id.creatodidak.nyaganagari.Models.BeritaItem;
import id.creatodidak.nyaganagari.R;
import id.creatodidak.nyaganagari.dumas.API.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeritaFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeritaFr extends Fragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BeritaAdapter beritaAdapter;
    List<BeritaItem> BeritaList = new ArrayList<>();
    SliderLayout mDemoSlider;


    public BeritaFr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeritaFr.
     */
    // TODO: Rename and change types and number of parameters
    public static BeritaFr newInstance(String param1, String param2) {
        BeritaFr fragment = new BeritaFr();
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
        return inflater.inflate(R.layout.fragment_berita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager;
        RecyclerView rv = view.findViewById(R.id.rvBerita);
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        beritaAdapter = new BeritaAdapter(BeritaList);
        rv.setAdapter(beritaAdapter);

        berita(view);

        mDemoSlider = view.findViewById(R.id.slider);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("https://polreslandak.id/media/1.JPG");
        listName.add("Anjangsana Kapolres Landak Dalam Rangka Hari Bhayangkara Ke-76");

        listUrl.add("https://polreslandak.id/media/2.JPG");
        listName.add("Wakapolres Landak Memberikan Arahan apel Pagi");

        listUrl.add("https://polreslandak.id/media/3.JPG");
        listName.add("Giat STIK Lemdiklat Polri");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopCyclingWhenTouch(false);

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void berita(View view) {
        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<BeritaItem>> Sm = apiInterface.getBerita();

        WebView youtubeWebView; //todo find or bind web view
        String myVideoYoutubeId = "r3kkzzjQ6O8";

        youtubeWebView = view.findViewById(R.id.youtube_web_view);

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

        Sm.enqueue(new Callback<List<BeritaItem>>() {
            @Override
            public void onResponse(Call<List<BeritaItem>> call, Response<List<BeritaItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BeritaList.addAll(response.body());
                    beritaAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<BeritaItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}