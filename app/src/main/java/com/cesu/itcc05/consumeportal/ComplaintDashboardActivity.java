package com.cesu.itcc05.consumeportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class ComplaintDashboardActivity extends AppCompatActivity {
    private String compinfo="";
    private ProgressBar spinner;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private String StrUrl="";
    private String CompanyID="";
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private String consIDval="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_dashboard);
        androidx.appcompat.widget.Toolbar toolbarback = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complaint and Suggestions");
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
        ImageButton btncomplain = (ImageButton) findViewById(R.id.complain);
        //   ImageButton btncomplainsts= (ImageButton) findViewById(R.id.complainsts);
        // ImageButton btnoutage = (ImageButton) findViewById(R.id.outage);
        ImageButton btntheft= (ImageButton) findViewById(R.id.theft);
        //  Button btnback = (Button) findViewById(R.id.back);
        // ImageButton btnoutage = (ImageButton) findViewById(R.id.outage);
        // ImageButton btnquickinfo= (ImageButton) findViewById(R.id.quickinfo);
        try{
            Bundle compDet = getIntent().getExtras();
            compinfo = compDet.getString("compinfo");
        }catch(Exception e){compinfo="0";}
        btncomplain.setOnClickListener(new View.OnClickListener() {
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
        /*
        btncomplainsts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compintent = new Intent(getApplicationContext(), ComplaintStatusActivity.class);
                Bundle compType = new Bundle();
                compType.putString("compinfo", "reg");
                compintent.putExtras(compType);
                startActivity(compintent);
                finish();
            }
        });

        btnoutage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent curblintent = new Intent(getApplicationContext(), OutageInfoActivity.class);
                Bundle reqDet = new Bundle();
                reqDet.putString("Typeinfo", "reg");
                curblintent.putExtras(reqDet);
                startActivity(curblintent);
                finish();

            }
        });
*/
        btntheft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compintent = new Intent(getApplicationContext(), TheftRegisterActivity.class);
                Bundle compType = new Bundle();
                compType.putString("compinfo", "reg");
                compintent.putExtras(compType);
                startActivity(compintent);
                finish();
            }
        });
/*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compinfo.equals("reg")){
                    Intent QDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(QDashboard);
                }else{
                    Intent QDashboard = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(QDashboard);
                }

            }
        });*/

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
                            ComplaintDashboardActivity.this.finish();
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
        AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=7";
        new UserAuthOnline().execute(AuthURL);


        Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        return AuthURL;
    }
    private  class UserAuthOnline extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            //activity = (MainActivity)params[0];
            String strURL = params[0];

            URLConnection conn = null;
            InputStream inputStreamer = null;
            String bodycontent = null;
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
                int start = html.indexOf("<body>") + "<body>".length();
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
                                ComplaintDashboardActivity.this.finish();
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
            //  btnsubmit.setEnabled(false);
            // btnsubmit.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(ComplaintLoginActivity.this, "  ", " ");
                spinner.setVisibility(View.VISIBLE);
            } else {
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
                                ComplaintDashboardActivity.this.finish();
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
            String pipeDelBillInfo = str;
            String[] BillInfo = pipeDelBillInfo.split("[|]");

            Log.d("DemoApp", " BillInfo[0]   " + pipeDelBillInfo);//authoriztion check
            Log.d("DemoApp", " BillInfo[1]  " + BillInfo[1]);//authoriztion check

            Intent compintent = new Intent(getApplicationContext(), ComplaintLoginActivity.class);
            Bundle compType = new Bundle();
            compType.putString("compinfo", "reg");
            compType.putString("comsubcat",BillInfo[1]);
            compintent.putExtras(compType);
            startActivity(compintent);
            finish();


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
            //    Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back

            if(compinfo.equals("reg")){
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }else{
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
