package com.cesu.itcc05.consumeportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class OfferSchemeWebAcivity extends AppCompatActivity {
    private WebView web_view;
    private Context mContext;
    private String url="";
    private ProgressDialog progress_bar;
    private ImageView iv_backs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_scheme_web);
        web_view=findViewById(R.id.web_view);
        iv_backs=findViewById(R.id.iv_backs);

        progress_bar = ProgressDialog.show(OfferSchemeWebAcivity.this, "Please wait", "We are opening the page");
        mContext=this;

        Intent intent=getIntent();
        url=intent.getStringExtra("url");

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (CommonMethods.isNetworkAvailable(mContext)){

            WebSettings webSettings = web_view.getSettings();
            webSettings.setJavaScriptEnabled(true);

            web_view.loadUrl(url);
        }
        web_view.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progress_bar.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
