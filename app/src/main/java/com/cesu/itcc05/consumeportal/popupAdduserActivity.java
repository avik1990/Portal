package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class popupAdduserActivity extends AppCompatActivity {
    private static EditText straddusr;
    private String consIDval = "";
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private ProgressBar spinner;
    private String StrUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_adduser);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070";
        straddusr=(EditText) findViewById(R.id.addUsr);
        consIDval = straddusr.getText().toString();
        Button btnAdduser = (Button) findViewById(R.id.Adduser);
        btnAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true);
                } else {
                    changeTextStatus(false);
                }
            }
        });
    }
    public void changeTextStatus(boolean isConnected) {

        // Change status according to boolean value
        if (isConnected) {
            funcUrlCheck(chkresponse);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("You are not connected to internet");
            alertDialogBuilder.setMessage("Enable data and Click Retry for Login !!")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            popupAdduserActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            // internetStatus.setText("Internet Disconnected.");
            //  internetStatus.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        ChkConnectivity.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        ChkConnectivity.activityResumed();// On Resume notify the Application
    }

    private String funcUrlCheck(int resCode){

        if(resCode==1){
            // send imei number to get mobile activation key if new one then activation key 1111 if different imei no then 1111
            AuthURL=StrUrl+"/IncomingSMS/CESU_mCollection1.jsp?strCompanyID=8&un=3&pw=a&imei=358461097642898";
            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL=StrUrl+"/IncomingSMS/CESU_mCollection1.jsp?strCompanyID=8&un=3&pw=a&imei=358461097642898";
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
        }
        Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        return AuthURL;
    }
    private  class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL=params[0];
            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent=null;
            Log.d("DemoApp", " strURL   " + strURL);


            try {

                URL url = new URL(strURL);
                URLConnection uc = url.openConnection();
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
                e.printStackTrace();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Not Connectd to Server");
                alertDialogBuilder.setMessage("Please Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                popupAdduserActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }

            return bodycontent;
        }
        @Override

        protected void onPreExecute() {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
               // progressDialog = ProgressDialog.show(popupAdduserActivity.this, " ", " ");
                spinner.setVisibility(View.VISIBLE);
            }else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Enable Data");
                alertDialogBuilder.setMessage("Enable Data & Retry")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                popupAdduserActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
        @Override
        protected void onPostExecute(String str) {
            Log.d("DemoApp", " str   " + str);
          //  progressDialog.dismiss();
            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            pipeDelBillInfo="0|0|1|1|0|102S23569832|0|0|0";

            String[] BillInfo = pipeDelBillInfo.split("[|]");
            String MobileNo=BillInfo[4];
            String ConsID=BillInfo[5];
            String ConsName=BillInfo[6];
            String ConsAdd=BillInfo[7];

            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[0]);
            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[1]);
            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[2]);
            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[3]);
            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[4]);
            Log.d("DemoApp", " BillInfo[1]   " + BillInfo[5]);
           //new user
             if(BillInfo[0].equals("9")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(getString(R.string.error_title));
                alertDialogBuilder.setMessage(getString(R.string.ca_not_found))
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                popupAdduserActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                Log.d("DemoApp", "[4]   ") ;
            }else{
                 databaseAccess = DatabaseAccess.getInstance(context);
                 databaseAccess.open();
                 String strSelectSQL_01 = "INSERT INTO CONSUMERDTLS  " +
                         " (CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD,VALIDATE_FLG)" +
                         " VALUES('" + ConsID + "','" + ConsName + "','" + ConsAdd + "',2) ";
                 Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                 DatabaseAccess.database.execSQL(strSelectSQL_01);
                 databaseAccess.close();

                 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                 alertDialogBuilder.setTitle("Consumer Added");
                 alertDialogBuilder.setMessage("Consumer Added Sucessfully")
                         .setCancelable(false)
                         .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                             }
                         })
                         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 popupAdduserActivity.this.finish();
                             }
                         });
                 // create alert dialog
                 AlertDialog alertDialog = alertDialogBuilder.create();
                 // show it
                 alertDialog.show();
                 Log.d("DemoApp", "[4]   ") ;
             }

        }


    }
}
