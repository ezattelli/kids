package ir.etelli.kids.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import ir.etelli.kids.R;

public class ActivityHelp extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Bundle b = getIntent().getExtras();


        WebView webView = findViewById(R.id.wvHelp);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextZoom(300);
        webView.getSettings().setBuiltInZoomControls(true);

//        String st = "<html>" +
//                        "<head>" +
//                            "<style>" +
////                                "img{display: inline;height: auto;max-width: 100%;}" +
//                            "</style>" +
//                        "</head>" +
//
//                        "<body>" +
//                            b.getString("html") +
//                        "</body>" +
//                "</html>";
//
//        webView.loadDataWithBaseURL(null, st, "text/html", "utf-8", null);
//
//
////        webView.loadDataWithBaseURL(null, html , "text/html", "UTF-8", null);
////
////        //        webView.loadData(html, "text/html", null);
//

        webView.loadUrl(b.getString("html"));


    }
}