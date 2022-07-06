package id.creatodidak.nyaganagari.wviews;

import androidx.appcompat.app.AppCompatActivity;
import id.creatodidak.nyaganagari.R;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class wvSIM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wv_sim);
        WebView wv;
        String yan = "sim";

        wv = findViewById(R.id.wvs);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        wv.loadUrl("https://polreslandak.id/pelayanan" + yan);
    }
}