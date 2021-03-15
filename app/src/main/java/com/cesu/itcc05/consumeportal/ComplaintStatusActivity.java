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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class ComplaintStatusActivity extends AppCompatActivity {
    private static EditText strCompNo;
    private String strCompNoval="";
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private String pipeDelBillInfo="";
    private   DatabaseAccess databaseAccess=null;
    private static TextView strmsgcom;
    private String compinfo="";
    private Spinner spinner2;
    private String StrUrl="";
    private String CompanyID="";
    private String conname="";
    private static TextView strmobmsg,strConName;
    private static EditText strconsIDval;
    private String consIDval="";
    private String spinpos1="";
    private String conid = "";
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        androidx.appcompat.widget.Toolbar toolbarback = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complaint Status ");
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/CESU_API_Report/CESU_DynamicReport.jsp?";
        strmsgcom=(TextView)findViewById(R.id.msgcom);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        strConName=(TextView)findViewById(R.id.conNm);
        strconsIDval=(EditText) findViewById(R.id.consIDval);
        try {
            Bundle compType = getIntent().getExtras();
            compinfo = compType.getString("compinfo");
        }catch(Exception e){compinfo="0";}
        Log.d("DemoApp", "compinfo " + compinfo);
        ////////checking user registration...////////////////
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL,CONSUMER_ADD,CONSUMER_NAME "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);

        conname="";
        while (cursor.moveToNext()) {
            conid=cursor.getString(0);
        }

        cursor.close();
        databaseAccess.close();
        strconsIDval.setText("");

        if(compinfo.equals("reg")){

            strconsIDval.setVisibility(View.GONE);
            spinner2.setVisibility(View.VISIBLE);
            strConName.setVisibility(View.VISIBLE);
            // strConName.setEnabled(false);
            addItemsOnSpinner2();
        }else{

            strconsIDval.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.GONE);
            strConName.setVisibility(View.GONE);
        }
        Button btnsubmit=(Button)findViewById(R.id.submit);
        //   Button btnback=(Button)findViewById(R.id.back);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strconsIDval.setAllCaps(true);
                if(compinfo.equals("reg")){
                    consIDval=  String.valueOf(spinner2.getSelectedItem()).trim();
                }else{
                    consIDval = strconsIDval.getText().toString().trim().toUpperCase();
                }
                if(TextUtils.isEmpty(consIDval) ) {
                    strconsIDval.setError("Please enter consumer/CA number");
                    strconsIDval.requestFocus();
                    Log.d("DemoApp", "Query SQL " + consIDval);
                }else if(consIDval.length()>0 && consIDval.length()<12){
                    strconsIDval.setError("Enter Valid Consumer ID of 12 digit");
                }else{
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        changeTextStatus(true);
                    } else {
                        changeTextStatus(false);
                    }
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
                            ComplaintStatusActivity.this.finish();
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
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        CompanyID =sessiondata.getString("CompanyID", null); // getting String

        if (CompanyID==null){
            CompanyID="10";
        }

        String divcode=consIDval.substring(0,3);
        String dbtype=consIDval.substring(3,4);
        String consacc=consIDval.substring(4);
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
        if(resCode==1){
            AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1076&strDivCode="+divcode+"&strCons_Acc="+consacc;
            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL =StrUrl+"un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1076&strDivCode="+divcode+"&strCons_Acc="+consacc;
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
            //  int code=AlertErrorCall.ErrorMsgType(6);
            //   if(code==1){MainActivity.this.finish();}
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
                                ComplaintStatusActivity.this.finish();
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
                // progressDialog = ProgressDialog.show(ComplaintStatusActivity.this, " ", " ");
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
                                ComplaintStatusActivity.this.finish();
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
            pipeDelBillInfo =str;

            // pipeDelBillInfo="1|1|Resolved|sdfsdfsdffsdfsd"; //Complain register

            String[] BillInfo = pipeDelBillInfo.split("[|]");

            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("User Not found");
                alertDialogBuilder.setMessage("User Not found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComplaintStatusActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            } if(BillInfo[1].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Report ID not found");
                alertDialogBuilder.setMessage("Report ID not found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ComplaintStatusActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else {
                if(BillInfo[2].equals("1")){//complaint sucess
                    Intent comstsintent = new Intent(getApplicationContext(), ComplaintStsDetailActivity.class);
                    Bundle comstsDet = new Bundle();
                    comstsDet.putString("compSts", BillInfo[3]);
                    comstsDet.putString("strtype", compinfo);
                    comstsDet.putString("StrConID", consIDval);
                    comstsDet.putString("conname", conname);
                    comstsintent.putExtras(comstsDet);
                    startActivity(comstsintent);

                }else {//complaint sucess
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("No Complaint "); //change by santi 17/04/20
                    alertDialogBuilder.setMessage("No Registered Complaint found")//change by santi 17/04/20
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ComplaintStatusActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();

                }

            }
        }
    }
    public void  addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                "FROM CONSUMERDTLS WHERE (VALIDATE_FLG=1 OR VALIDATE_FLG=9)  ";
        Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
        Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
        while (rs1.moveToNext()) {
            list.add(rs1.getString(0));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        /////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                // consIDval=String.valueOf(position);
                consIDval=String.valueOf(spinner2.getSelectedItem());
                databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                        "FROM CONSUMERDTLS WHERE CONSUMER_ID='" + consIDval + "'  ";
                Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
                Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
                while (rs1.moveToNext()) {
                    //   spinpos1=rs1.getString(2);
                    conname=rs1.getString(1);
                }
                strConName.setText(conname+"\n"+"("+conid+")");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
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
            if(compinfo.equals("new")) {
                Intent i = new Intent(this,QuicklinksDashboard.class);
                this.startActivity(i);
                finish();
            }else{
                Intent i = new Intent(this,ComplaintDashboardActivity.class);
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
