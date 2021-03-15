package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ServCheckLiveActivity extends AppCompatActivity {
    private static String strURL01="";
    private static String strURL02="";
    private static String Primarylink="";
    private static String Secondarylink="";
    private static String strURLPart1="";
    private static String retvalidUrl="";
    private ProgressBar spinner;
    private Context context = this;
    private static TextView strchkcon;
    private Animation topAnim,bottomAnim;
    ImageView tv_employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_serv_check_live);
        strchkcon=(TextView) findViewById(R.id.chkcon);
        tv_employee=findViewById(R.id.tv_employee);
        topAnim= AnimationUtils.loadAnimation(ServCheckLiveActivity.this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(ServCheckLiveActivity.this,R.anim.bottom_animation);

        tv_employee.setAnimation(topAnim);

        Bundle BundleIpset = getIntent().getExtras();
//        String StrvalidIpget = BundleIpset.getString("StrChkFlg");
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        setProgressBarIndeterminateVisibility(true);
       // spinner.setVisibility(View.VISIBLE);
        strURL01="http://portal.tpcentralodisha.com:8070";
        strURL02="http://portal.tpcentralodisha.com:8070";
        strURLPart1="/ChkServerCon/ChkServerCon.jsp?ChkServer=1";
        Primarylink ="";
        Secondarylink ="";
        String strSiteMainProv="";
        Date strcurdate= new Date();
        long strcurtime = strcurdate.getTime();
        Log.d("DemoApp", " strcurtime " + strcurtime);
        if((strcurtime%2) == 0){
            strSiteMainProv = strURL01;
        }else{
            strSiteMainProv = strURL02;
        }
        if(strSiteMainProv.equals(strURL01)){
            Primarylink=strURL01;
            Secondarylink=strURL02;
        }else{
            Primarylink=strURL02;
            Secondarylink=strURL01;
        }
        Log.d("DemoApp", "in Loop AuthURL" + strSiteMainProv);
      //  new strCheckSiteAvailability().execute(Primarylink);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                Bundle BundleIpset = new Bundle();
                BundleIpset.putString("StrChkFlg", "1");
                BundleIpset.putString("retLiveUrl", Secondarylink);
                ChkServerLiveCon.putExtras(BundleIpset);
                startActivity(ChkServerLiveCon);
                finish();
            }
        }, 3000);
    }
    public class strCheckSiteAvailability extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String strURL=params[0];
            strURL=strURL+strURLPart1;
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
            Log.d("DemoApp", " strURL   " + strURL);
            try {
                URL url = new URL(strURL);
                URLConnection uc = url.openConnection();
                uc.setConnectTimeout(2000);
                uc.setDoInput(true);
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine;
                StringBuilder a = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    a.append(inputLine);
                in.close();
                Log.d("DemoApp", " fullString   " + a.toString());
                String html = a.toString();
                int start = html.indexOf("<body>")+"<body>".length();
                int end = html.indexOf("</body>", start);
                bodycontent = html.substring(start, end);
                Log.d("DemoApp", " start   " + start);
                Log.d("DemoApp", " end   " + end);
                Log.d("DemoApp", " body   " + bodycontent);
            } catch (Exception e) {

            }
            return bodycontent;
        }
        @Override
        protected void onPreExecute() {
          //  spinner.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String str) {
            try {
              //  spinner.setVisibility(View.VISIBLE);
                if (str == null || str.equalsIgnoreCase(null) || str.isEmpty() || str.length()==0) {
                    str = "0";
                }
            }catch (Exception e)  {
                str = "4";
            }
            String chkValidUrl =str;
            if(!chkValidUrl.equals("2")){//if 2 the link and db ok
              //  spinner.setVisibility(View.VISIBLE);
                new strCheckSiteAvailability1().execute(Secondarylink);
            }else{
                //retvalidUrl= Primarylink;
               // spinner.setVisibility(View.VISIBLE);
                Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                Bundle BundleIpset = new Bundle();
                BundleIpset.putString("StrChkFlg", "1");
                BundleIpset.putString("retLiveUrl", Primarylink);
                ChkServerLiveCon.putExtras(BundleIpset);
                startActivity(ChkServerLiveCon);
                finish();
            }
            Log.d("DemoApp", " chkValidUrl1   " + retvalidUrl);
        }
    }

    public class strCheckSiteAvailability1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL=params[0];
            strURL=strURL+strURLPart1;
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
            Log.d("DemoApp", " strURL   " + strURL);
            try {

                URL url = new URL(strURL);
                URLConnection uc = url.openConnection();
                uc.setConnectTimeout(2000);
                uc.setDoInput(true);
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String inputLine;
                StringBuilder a = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    a.append(inputLine);
                in.close();
                Log.d("DemoApp", " fullString   " + a.toString());
                String html = a.toString();
                int start = html.indexOf("<body>")+"<body>".length();
                int end = html.indexOf("</body>", start);
                bodycontent = html.substring(start, end);
                Log.d("DemoApp", " start   " + start);
                Log.d("DemoApp", " end   " + end);
                Log.d("DemoApp", " body   " + bodycontent);
            } catch (Exception e) {

            }
            return bodycontent;
        }
        @Override
        protected void onPreExecute() {
         //   spinner.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
            // progressDialog.dismiss();
            try {
           //     spinner.setVisibility(View.VISIBLE);
                if (str == null || str.equalsIgnoreCase(null) || str.isEmpty() || str.length()==0) {
                    str = "0";
                }
            }catch (Exception e)  {
                str = "4";
            }
            String chkValidUrl =str;
            if(!chkValidUrl.equals("2")){
              //  spinner.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle BundleIpset = new Bundle();
                        BundleIpset.putString("StrChkFlg", "1");
                        BundleIpset.putString("retLiveUrl", Secondarylink);
                        ChkServerLiveCon.putExtras(BundleIpset);
                        startActivity(ChkServerLiveCon);
                        finish();
                    }
                }, 3000);
                Log.d("DemoApp", " Server BUsy   " + chkValidUrl);
                // Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                // Bundle BundleIpset = new Bundle();
                // BundleIpset.putString("StrChkFlg", "2");
                // ChkServerLiveCon.putExtras(BundleIpset);
                // startActivity(ChkServerLiveCon);
                //  finish();

             /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Server Busy");
                alertDialogBuilder.setMessage("Try after  Some Time !!")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(ChkServerLiveCon);
                                finish();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ServCheckLiveActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();*/
                // internetStatus.setText("Internet Disconnected.");
                //  internetStatus.setTextColor(Color.parseColor("#ff0000"));
            }else{
             //   spinner.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent ChkServerLiveCon = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle BundleIpset = new Bundle();
                        BundleIpset.putString("StrChkFlg", "1");
                        BundleIpset.putString("retLiveUrl", Secondarylink);
                        ChkServerLiveCon.putExtras(BundleIpset);
                        startActivity(ChkServerLiveCon);
                        finish();
                    }
                }, 5000);
               //  new Splashy(ServCheckLiveActivity.this).

            }
            Log.d("DemoApp", " chkValidUrl2   " + retvalidUrl);
        }
    }

}


