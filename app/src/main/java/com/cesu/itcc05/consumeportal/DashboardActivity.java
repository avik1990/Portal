package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class DashboardActivity extends AppCompatActivity {
    final Context context = this;
    private int chkresponse=1;
    private int reqsttype=0;
    private String AuthURL=null;
    private int procType=0;
    private String mobval="";
    private String ConsID="";
    private String imeinum="";
    private String StrUrl="";
    private String CompanyID="";
    private   DatabaseAccess databaseAccess=null;
    private String versionID="";
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private ProgressBar spinner;
    private ImageButton btncbill,btnprofile,btnpmt,btncomplt,btnoutage;
    private LinearLayout dd_emplyoee,ll_safetyy;
    private LinearLayout ll_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_BillInfo.jsp?";
        btncbill = (ImageButton) findViewById(R.id.cbill);
        btnprofile = (ImageButton) findViewById(R.id.profile);
        btnpmt = (ImageButton) findViewById(R.id.pmt);
        btncomplt = (ImageButton) findViewById(R.id.complt);
        btnoutage = (ImageButton) findViewById(R.id.outage);
        ll_feedback=findViewById(R.id.ll_feedback);

        dd_emplyoee=findViewById(R.id.dd_emplyoee);
        ll_safetyy=findViewById(R.id.ll_safetyy);

        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        ConsID =sessiondata.getString("ConsID", null); // getting String
        mobval =sessiondata.getString("mobval", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT VERSION_NO,VERSION_NAME,COMPANYID "+
                "FROM SWVERSION WHERE RECORD_STS=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String swversion = "";
        versionID="";
        while (cursor.moveToNext()) {
            swversion="Version:"+cursor.getString(0)+"-"+cursor.getString(1);
            Log.d("DemoApp", "in Loop conid" + swversion);
            CompanyID=cursor.getString(2);
        }
        btncbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,"cbill");
                } else {
                    changeTextStatus(false,"cbill");
                }
              //  Intent curbill = new Intent(getApplicationContext(), CurrentBillActivity.class);
             //   startActivity(curbill);
             //   finish();
            }
        });
        /*
        btnconhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent curbill = new Intent(getApplicationContext(), HistoryDetActivity.class);
                   startActivity(curbill);
                   finish();
            }
        });
        */
        btncomplt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent compintent = new Intent(getApplicationContext(), ComplaintDashboardActivity.class);
                Bundle compDet = new Bundle();
                compDet.putString("compinfo", "reg");
                compDet.putString("ConsID1",ConsID );
                compintent.putExtras(compDet);
                startActivity(compintent);
                finish();

            }
        });

        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), FeedBackActivity.class);
                startActivity(compintent);
            }
        });

        dd_emplyoee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), EmployeeVerificationActivity.class);
                startActivity(compintent);

            }
        });

        ll_safetyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compintent = new Intent(getApplicationContext(), SafetyActivity.class);
                startActivity(compintent);

            }
        });

        btnpmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Typeinfo="reg";
                Intent intPayment = new Intent(getApplicationContext(), CollectionRequestActivity.class);
                Bundle paytypDet = new Bundle();
                paytypDet.putString("Typeinfo", Typeinfo);
                paytypDet.putString("ColTyp", "0");
                intPayment.putExtras(paytypDet);
                startActivity(intPayment);
                finish();

            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent curbill = new Intent(getApplicationContext(), UserDashboardActivity.class);
                startActivity(curbill);
                finish();
            }
        });
        /*
        btnbilhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,"bilhis");
                } else {
                    changeTextStatus(false,"bilhis");
                }
                //  Intent curbill = new Intent(getApplicationContext(), CurrentBillActivity.class);
                //   startActivity(curbill);
                //   finish();
            }
        });
        */

        btnoutage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent curblintent = new Intent(getApplicationContext(), OutageInfoActivity.class);
                Bundle reqDet = new Bundle();
                reqDet.putString("Typeinfo", "reg");
                curblintent.putExtras(reqDet);
                startActivity(curblintent);
              //  finish();

            }
        });

    }
    public void changeTextStatus(boolean isConnected,String reqtype) {
        if(reqtype.equals("cbill")){reqsttype=1;}
        if(reqtype.equals("bilhis")){reqsttype=2;}

        // Change status according to boolean value
        if (isConnected) {
            funcUrlCheck(chkresponse,reqsttype);
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
                            DashboardActivity.this.finish();
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
    private String funcUrlCheck(int resCode,int rtype){
        procType=rtype;

        divcode=ConsID.substring(0,3);
        dbtype=ConsID.substring(3,4);
        consacc=ConsID.substring(4);
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
        if(rtype==1){ //current bill
            if(resCode==1){
               AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ConsumerID="+ConsID+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
               new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL = StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ConsumerID="+ConsID+"&imei="+imeinum+"&mosarkar=0&mobile_no="+mobval;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        }
        if(rtype==2){//history
            if(resCode==1){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        }

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
                                DashboardActivity.this.finish();
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
            btncbill.setEnabled(false);
            btncbill.setClickable(false);
            btnprofile.setEnabled(false);
            btnprofile.setClickable(false);
            btnpmt.setEnabled(false);
            btnpmt.setClickable(false);
            btncomplt.setEnabled(false);
            btncomplt.setClickable(false);
            btnoutage.setEnabled(false);
            btnoutage.setClickable(false);
            spinner.setVisibility(View.VISIBLE);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
              //  progressDialog = ProgressDialog.show(DashboardActivity.this, " ", " ");
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
                                DashboardActivity.this.finish();
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
           // progressDialog.dismiss();
            spinner.setVisibility(View.GONE);
            String pipeDelBillInfo =str;
            if(procType==1){
                Intent curblintent = new Intent(getApplicationContext(), CurrentBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("fetchAcnt", "0");
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            }else if(procType==2){
                Intent curblintent = new Intent(getApplicationContext(), HistoryBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else{
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Error!! Contact IT HQ");
                alertDialogBuilder.setMessage("Error")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DashboardActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tolbarlogout, menu);
        return true;
    }
    // Activity's overrided method used to perform click events on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
// Display menu item's title by using a Toast.
        if (id == R.id.action_settings1) { //logout
            Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            Intent i = new Intent(this,MainActivity.class);
            this.startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
