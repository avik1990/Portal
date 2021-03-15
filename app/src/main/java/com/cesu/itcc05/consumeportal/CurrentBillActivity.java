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
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class CurrentBillActivity extends AppCompatActivity {
    private String pipeDelBillInfo="";
    private static TextView strbtot;
    private static TextView strName;
    private static TextView strcbill;
    private static TextView strdudt;
    private static TextView strpmt;
    private static TextView strpmtdt,strarrear,strbdate;
    private static TextView strrbt;
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

    private String consid1="";
    private Spinner spinner2;
    // private static TextView strConName;
    private String consIDval="";
    private String conname="";
    private static TableLayout strtableLayout;
    private String cons_acc="";
    private  String tempConsID="";
    private String sesconid="";
    private String nameTemp="";
    private ProgressBar spinner;
    private Button btnpaynow,btnviewHis;
    private String StrAcnt="";
    private  List<String> list =null;
    private  String stroffcode="";
    private  String strurlval="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bill);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_BillInfo.jsp?";
        btnviewHis = (Button) findViewById(R.id.viewHis);
        btnpaynow = (Button) findViewById(R.id.paynow);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //   strConName=(TextView)findViewById(R.id.conNm);
        strtableLayout=(TableLayout)findViewById(R.id.tableLayout);
        //    strconsIDval=(EditText) findViewById(R.id.consIDval);
        //     strconsIDval.setText(conid);
        //  strconsIDval.setVisibility(View.GONE);
        spinner2.setVisibility(View.VISIBLE);
        //    strConName.setVisibility(View.VISIBLE);
        //strtableLayout.setVisibility(View.GONE);
        // strConName.setEnabled(false);
        addItemsOnSpinner2();

        Bundle parmtrDet = getIntent().getExtras();
        pipeDelBillInfo = parmtrDet.getString("pipeDelBillInfo");
        StrAcnt = parmtrDet.getString("fetchAcnt");
        Log.d("DemoApp", "StrAcnt  " + StrAcnt) ;
        String[] BillInfo = pipeDelBillInfo.split("[|]");
        // 1|1|1|1|BCDD-2BHUBANESHWAR|KHANDAGIRI|75 -KALINGANAGA|03127108|BASANTA KUMAR TRIPATHY|PLOT NO-LIG-950,K4|KALINGA NAGAR|1|2442|3146379|0|191119111102S03127108|21-11-2019|30-11-2019|9437217960|BASANTKUMAR_TRIPATHY@YAHOO.CO.IN|1|102|1|0|null

        Log.d("DemoApp", " BillInfo[2]   " + BillInfo[2]);//authoriztion check
        strName=(TextView)findViewById(R.id.conName);
        strbtot=(TextView)findViewById(R.id.btot);//
        strcbill=(TextView)findViewById(R.id.cbill);
        strarrear=(TextView)findViewById(R.id.arrear);//
        strbdate=(TextView)findViewById(R.id.bdate);//
        strdudt=(TextView)findViewById(R.id.dudt);
        strrbt=(TextView)findViewById(R.id.rbt1);
        strpmt=(TextView)findViewById(R.id.pmt);
        strpmtdt=(TextView)findViewById(R.id.pmtdt);
        modifiedCode(BillInfo);


        btnpaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DemoApp", "Collec consIDval " + consIDval) ;
                Log.d("DemoApp", "Collec cons_acc" + cons_acc);
                String Typeinfo="reg";
                Intent intPayment = new Intent(getApplicationContext(), CollectionRequestActivity.class);
                Bundle paytypDet = new Bundle();
                paytypDet.putString("Typeinfo", Typeinfo);
                paytypDet.putString("ColTyp", "1");
                paytypDet.putString("consIDvalB", consIDval);
                intPayment.putExtras(paytypDet);
                startActivity(intPayment);
                finish();

            }
        });
        btnviewHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,"bilhis",consIDval);
                } else {
                    changeTextStatus(false,"bilhis",consIDval);
                }


                //  Intent UserDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                //startActivity(UserDashboard);
                // finish();
            }
        });
    }

    private void modifiedCode(String [] BillInfo){
        if(BillInfo[0].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("User Not Found");
            alertDialogBuilder.setMessage("User Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CurrentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[1].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("IP Address Not Found");
            alertDialogBuilder.setMessage("IP Address Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CurrentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[2].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Consumer Not Found");
            alertDialogBuilder.setMessage("Consumer Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CurrentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }/*else if(BillInfo[3].equals("0")){
            try {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Current Bill Not Found");
                alertDialogBuilder.setMessage("Current Bill Not Found")
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CurrentBillActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                setBillInformation(BillInfo);
            }catch(Exception e){

            }
            //spinner.setVisibility(View.GONE);
            Log.d("DemoApp", "[4]   ") ;
        }*/else{
            setBillInformation(BillInfo);
        }
    }
    private void setBillInformation(String[] BillInfo){
        try {
            SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
            tempConsID =sessiondata.getString("ConsID", null); // getting String
            sesconid=sessiondata.getString("ConsID", null); // getting String
            stroffcode=BillInfo[21];
            Log.d("DemoApp", "[4]   ") ;
            if(BillInfo[22].equals("1")){
                stroffcode=stroffcode+"S";
            }else{
                stroffcode=stroffcode+"I";
            }
            Log.d("DemoApp", "BillInfo[22]  " + BillInfo[22]) ;
            nameTemp="";
            nameTemp=BillInfo[8] + "\n" + "(" + stroffcode + BillInfo[7] + ")";
            strName.setText(BillInfo[8] + "\n" + "(" + stroffcode + BillInfo[7] + ")");
            cons_acc=BillInfo[7];
            Log.d("DemoApp", "BillInfo[22]  1 " ) ;
            //strconid.setText(BillInfo[7]);
            strbtot.setText(BillInfo[12]);//
            strcbill.setText(BillInfo[27]);//current bill
            strarrear.setText(BillInfo[28]);
            strbdate.setText(BillInfo[16]);
            strdudt.setText(BillInfo[17]);
            strrbt.setText(BillInfo[26]);
           // strpmt.setText(BillInfo[23]);
            strpmt.setText(BillInfo[29]);//Added on 040520 by santi
            Log.d("DemoApp", "BillInfo[22]  2 ") ;
            try {
                if (BillInfo[24] == null || BillInfo[24].equals("") || BillInfo[24].equals(" ") || BillInfo[24].equals("-")) {
                    strpmtdt.setText("-");
                } else {
                    strpmtdt.setText(BillInfo[24].substring(0, 11));//.substring(0,10)
                }
            }catch (Exception e){ strpmtdt.setText("-");}
            Log.d("DemoApp", "BillInfo[22]  3 " ) ;

        }catch (Exception e)  {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Data Not Found");
            alertDialogBuilder.setMessage("Data Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CurrentBillActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
    public void changeTextStatus(boolean isConnected,String reqtype,String txtconid) {
        if(reqtype.equals("cbill")){reqsttype=1;}
        if(reqtype.equals("bilhis")){reqsttype=2;}

        // Change status according to boolean value
        if (isConnected) {
            funcUrlCheck(chkresponse,reqsttype,txtconid);
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
                            CurrentBillActivity.this.finish();
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
    private String funcUrlCheck(int resCode,int rtype,String xtrconid){
        procType=rtype;
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
        mobval =sessiondata.getString("mobval", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        CompanyID =sessiondata.getString("CompanyID", null); // getting String
        ConsID =xtrconid;
        divcode=ConsID.substring(0,3);
        String dbtype=ConsID.substring(3,4);
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

                AuthURL="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
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
                                CurrentBillActivity.this.finish();
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
            btnpaynow.setEnabled(false);
            btnpaynow.setClickable(false);
            btnviewHis.setEnabled(false);
            btnpaynow.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                // progressDialog = ProgressDialog.show(CurrentBillActivity.this, " ", " ");
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
                                CurrentBillActivity.this.finish();
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
                parmtrDet.putString("fetchAcnt", ConsID);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            }else if(procType==2){
                Intent curblintent = new Intent(getApplicationContext(), HistoryBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("nameTemp", nameTemp);
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
                                CurrentBillActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        }


    }
    public void  addItemsOnSpinner2() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        list = new ArrayList<String>();
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD "+
                "FROM CONSUMERDTLS WHERE (VALIDATE_FLG=1 OR VALIDATE_FLG=9) ORDER BY VALIDATE_FLG, CONSUMER_ID ";
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
                String value1 = String.valueOf(item.toString());
                //String value = String.valueOf(position);
                //Log.d("DemoApp", "value" + value);
                Log.d("DemoApp", "value1 " + value1);
                // consIDval=String.valueOf(position);

                String valchk="0";
                tempConsID=stroffcode+cons_acc;
                Log.d("DemoApp", "tempConsID" + tempConsID);
                Log.d("DemoApp", "valchk" + valchk);
                //  Log.d("DemoApp", "chkflg" + chkflg);

                if(!value1.equals(tempConsID)) {
                    int index = 0;
                    for (int i = 0; i < spinner2.getCount(); i++) {
                        if (spinner2.getItemAtPosition(i).equals(tempConsID)) {
                            index = i;
                        }
                        Log.d("DemoApp", "spinner2.getItemAtPosition(i)" + spinner2.getItemAtPosition(i));
                    }
                    Log.d("DemoApp", "index" + index);
                    Log.d("DemoApp", "spinner2.getCount()" + spinner2.getCount());
                    Log.d("DemoApp", "index" + index);
                    spinner2.setSelection(index);
                    valchk=value1;
                    //    chkflg=value1;
                }else{
                    valchk= tempConsID;
                    //   chkflg=tempConsID;
                }
                consIDval = String.valueOf(spinner2.getSelectedItem());
                if(!valchk.equals(tempConsID)){
                    Log.d("DemoApp", "tempConsID 1 " + tempConsID);
                    Log.d("DemoApp", "valchk 1 " + valchk);
                    Log.d("DemoApp", "consIDval1 1 " + consIDval);
                    Log.d("DemoApp", "sesconid 1 " + sesconid);
                    if(!sesconid.equals(valchk)) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            changeTextStatus(true, "cbill", value1); //changed on 17/04/20  by santi
                        } else {
                            changeTextStatus(false, "cbill", value1);//changed on 17/04/20  by santi
                        }
                    }
                }

                //  spinner2.setSelection(position);


                //    Log.d("DemoApp", "tempConsID" + tempConsID);

                //   Log.d("DemoApp", "stroffcode+cons_acc" + stroffcode+cons_acc);
                /*
                if(!consIDval.equals(tempConsID)){
                //    strPosition=position;
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        changeTextStatus(true,"cbill",consIDval);
                    } else {
                        changeTextStatus(false,"cbill",consIDval);
                    }
                   // tempConsID=consIDval;
                }
                */


                //

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tolbarbillhis, menu);
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
        if (id == R.id.action_settings) { //View history
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                changeTextStatus(true,"bilhis",consIDval);
            } else {
                changeTextStatus(false,"bilhis",consIDval);
            }
            return true;
        } else if (id == R.id.action_settings1) { //logout
            // Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            // Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,MainActivity.class);
            this.startActivity(i);

            //Intent ConDashboard = new Intent(getApplicationContext(), DashboardActivity.class);
            // startActivity(ConDashboard);
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
