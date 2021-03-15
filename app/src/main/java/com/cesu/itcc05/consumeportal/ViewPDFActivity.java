package com.cesu.itcc05.consumeportal;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewPDFActivity extends AppCompatActivity {
    private Context mContext;
    private String docName="";
    private String docPath="";
    private TextView doc_name;
    private PDFView pdfView;
    private ImageView iv_download;
    private int totalsize;
    private int downloadedSize;
    private ProgressDialog pDialog;
    long downloadId;
    ArrayList<Long> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_view_pdf);
        mContext=this;
        pDialog=new ProgressDialog(mContext);


        doc_name=findViewById(R.id.doc_name);
        pdfView=findViewById(R.id.pdfView);
        iv_download=findViewById(R.id.iv_download);

        Intent intent=getIntent();
        docName=intent.getStringExtra("name");
        docPath=intent.getStringExtra("url");

        doc_name.setText(docName);

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // new DownloadFile().execute(docPath,docName);

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(docPath));
                request.setMimeType("application/pdf");
                String cookies = CookieManager.getInstance().getCookie(docPath);

                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(docPath, docName,
                        "application/pdf"));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                docPath, docName, "application/pdf"));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                 downloadId=    dm.enqueue(request);
                list.add(downloadId);


            }
        });

        if (CommonMethods.isNetworkAvailable(mContext)){

            try{

                new RetrievePdfStream().execute(docPath);
            }
            catch (Exception e){
                Toast.makeText(this, "Failed to load Url :" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(mContext,"No internet connection, please connect to internet",Toast.LENGTH_SHORT).show();
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            // get the refid from the download manager
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

// remove it from our list
            list.remove(referenceId);

// if list is empty means all downloads completed
            if (list.isEmpty())
            {

                Toast.makeText(ViewPDFActivity.this,"Download completed!",Toast.LENGTH_SHORT).show();

// show a notification
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(ViewPDFActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                               // .setContentTitle("")
                                .setContentText("All Download completed");


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());

            }
        }
    };


    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;

            }
            return inputStream;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showpDialog();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            hidepDialog();
        }
    }




    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);



    }


}

