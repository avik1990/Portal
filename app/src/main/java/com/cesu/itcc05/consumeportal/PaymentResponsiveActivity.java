package com.cesu.itcc05.consumeportal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import im.delight.android.webview.AdvancedWebView;

public class PaymentResponsiveActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    AdvancedWebView web_view;
    private ProgressDialog progressdialog;
    private Context mContext;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_responsive);
        web_view = findViewById(R.id.wvPaymentGateway);
        mContext = this;
        progressdialog = new ProgressDialog(mContext);
        web_view.setListener(this, this);

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setDomStorageEnabled(true);
        web_view.getSettings().setSaveFormData(true);
        web_view.getSettings().setAllowContentAccess(true);
        web_view.getSettings().setAllowFileAccess(true);
        web_view.getSettings().setAllowFileAccessFromFileURLs(true);
        web_view.getSettings().setAllowUniversalAccessFromFileURLs(true);
        web_view.getSettings().setSupportZoom(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.setClickable(true);
        web_view.setMixedContentAllowed(false);
        web_view.getSettings().setAppCacheEnabled(true);
        web_view.getSettings().setSupportMultipleWindows(false);
        web_view.clearCache(true);

        web_view.clearHistory();

        web_view.getSettings().setMediaPlaybackRequiresUserGesture(false);

        String urlToLoad = getIntent().getStringExtra("paymentUrl");
        Log.d("EncryptedURL:",urlToLoad);

        web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                AdvancedWebView newWebView = new AdvancedWebView(mContext);
                // myParentLayout.addView(newWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                return true;
            }

        });


        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        web_view.loadUrl(urlToLoad);
    }


    @Override
    public boolean onSupportNavigateUp() {
        showConfirmationScreen();
        return true;
    }

    private void showConfirmationScreen(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Are you sure you want to leave this page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            dialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(PaymentResponsiveActivity.this, "", Toast.LENGTH_SHORT).show();

                        }
                        dialog.dismiss();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }
    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        web_view.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        web_view.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        web_view.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        web_view.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!web_view.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {

        progressdialog.dismiss();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        progressdialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

        if ( urlIsPDF(url)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), "application/pdf");
            try{
               startActivity(intent);
            } catch (ActivityNotFoundException e) {
                //user does not have a pdf viewer installed
            }
        } else {
            web_view.loadUrl(url);
        }


        // request.setDestinationUri(uri);
    }

    public boolean urlIsPDF(String url) {
        boolean isPdf=false;
        String filenameArray[] = url.split("\\.");
        String extension = filenameArray[filenameArray.length-1];

        if(extension.equals("jpg")){
           isPdf=false;
        }
        else if(extension.equals("pdf")){
            isPdf=true;
        }

        return isPdf;
    }

    @Override
    public void onExternalPageRequest(String url) {
        Log.d("asdfg", "onDownloadRequested: " + url);
    }

}
