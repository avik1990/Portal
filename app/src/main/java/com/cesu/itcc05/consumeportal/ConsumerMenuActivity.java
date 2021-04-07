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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ConsumerMenuActivity extends AppCompatActivity {
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
/////////////
    private int reqsttype=0;
    private int procType=0;
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private String mobval="";
   // private String ConsID="";
    private String imeinum="";
    private  String reqtype="0";
    private ProgressBar spinner;
    private Button btnsubmit;
    private ImageView iv_backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_menu);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070";
        try {
            Bundle hisDet = getIntent().getExtras();
            reqtype = hisDet.getString("reqtype");
        }catch(Exception e){reqtype="0";}
        Log.d("DemoApp", "reqtype " + reqtype);
        String StrMenu="";
        if(reqtype.equals("conhis")){
            StrMenu="Consumption History";
        }else if(reqtype.equals("payhis")){
            StrMenu="Payment History";
        }else if(reqtype.equals("dethis")){
            StrMenu="Bill & Pay History";
        }else if(reqtype.equals("bilhis")){
            StrMenu="Bill History";
        }

        strmsgcom=(TextView)findViewById(R.id.msgcom);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        strConName=(TextView)findViewById(R.id.conNm);
        iv_backs=findViewById(R.id.iv_backs);

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    //    strconsIDval=(EditText) findViewById(R.id.consIDval);
   //     strconsIDval.setText(conid);
      //  strconsIDval.setVisibility(View.GONE);
        spinner2.setVisibility(View.VISIBLE);
        strConName.setVisibility(View.VISIBLE);
        // strConName.setEnabled(false);
        addItemsOnSpinner2();
        btnsubmit=(Button)findViewById(R.id.submit);
        //   Button btnback=(Button)findViewById(R.id.back);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // strconsIDval.setAllCaps(true);
                consIDval = String.valueOf(spinner2.getSelectedItem()).trim();
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,reqtype);
                } else {
                    changeTextStatus(false,reqtype);
                }
            }
        });
    }
    public void changeTextStatus(boolean isConnected,String reqtype) {
        if(reqtype.equals("conhis")){reqsttype=1;}
        if(reqtype.equals("payhis")){reqsttype=2;}
        if(reqtype.equals("dethis")){reqsttype=3;}
        if(reqtype.equals("bilhis")){reqsttype=4;}
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
                            ConsumerMenuActivity.this.finish();
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
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
       // ConsID =sessiondata.getString("ConsID", null); // getting String
      //  mobval =sessiondata.getString("mobval", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        CompanyID =sessiondata.getString("CompanyID", null); // getting String

        if (CompanyID==null){
            CompanyID="10";
        }
        divcode=consIDval.substring(0,3);
        dbtype=consIDval.substring(3,4);
        consacc=consIDval.substring(4);
        if(dbtype.equals("S")){
            dbtype="1";
        }else{
            dbtype="2";
        }
        if(rtype==1){
            if(resCode==1){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        }
        if(rtype==2){
            if(resCode==1){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        }
        if(rtype==3){
            if(resCode==1){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;
                new UserAuthOnline().execute(AuthURL);
            }else{
                AuthURL="ServerOut";
                //  int code=AlertErrorCall.ErrorMsgType(6);
                //   if(code==1){MainActivity.this.finish();}
            }
            Log.d("DemoApp", "in Loop AuthURL" + AuthURL);
        }
        if(rtype==4){
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
                                ConsumerMenuActivity.this.finish();
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
            btnsubmit.setEnabled(false);
            btnsubmit.setClickable(false);
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
              //  progressDialog = ProgressDialog.show(ConsumerMenuActivity.this, " ", " ");
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
                                ConsumerMenuActivity.this.finish();
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
            String pipeDelBillInfo = str;
            if (procType == 1) {
                //  pipeDelBillInfo = "1|10|santirnja|102S34343|jun-19|60|500|03-10-2019|200|jul-19|80|500|03-10-2019|200|aug-19|70|500|03-10-2019|200|sep-19|100|500|03-10-2019|200|oct-19|20|500|03-10-2019|200|nov-19|44|500|03-10-2019|200|dec-19|68|500|03-10-2019|200|jan-19|95|500|03-10-2019|200"; //consumption
            } else if (procType == 2) {
                //  pipeDelBillInfo = "1|11|santirnja|102S34343|DEC-19|03-10-2019|25895263|580|NOV-19|03-11-2019|25895263|580|OCT-19|03-10-2019|25895263|580|SEP-19|03-09-2019|25895263|580|AUG-19|03-08-2019|25895263|580|JULY-19|03-07-2019|25895263|580|JUN-19|03-06-2019|25895263|580"; //pmthist
            } else if (procType == 3) {
                // pipeDelBillInfo = "1|12|santirnja|102S34343|jun-19|500|03-10-2019|480|03-10-2019|July-19|900|03-10-2019|500|03-10-2019|aug-19|1500|03-10-2019|300|03-10-2019|sep-19|1600|03-10-2019|1600|03-10-2019|oct-19|500|03-10-2019|900|03-10-2019|nov-19|500|03-10-2019|20|03-10-2019|dec-19|200|03-10-2019|200|03-10-2019"; //blpay
            }
            if (procType == 1) {//conhistory
                Intent curblintent = new Intent(getApplicationContext(), HistoryConsumptionActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("consIDval", consIDval);
                parmtrDet.putString("conname", conname);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else if (procType == 2) {//pmt history
                Intent curblintent = new Intent(getApplicationContext(), HistoryPaymentActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("consIDval", consIDval);
                parmtrDet.putString("conname", conname);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else if (procType == 3) {//graph history
                Intent curblintent = new Intent(getApplicationContext(), HistoryPaymentBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("consIDval", consIDval);
                parmtrDet.putString("conname", conname);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else if (procType == 4) {//Bill history
                Intent curblintent = new Intent(getApplicationContext(), HistoryBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                parmtrDet.putString("consIDval", consIDval);
                parmtrDet.putString("conname", conname);
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
                                ConsumerMenuActivity.this.finish();
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

        if (list.size() > 0) {
            if (!CommonMethods.getSelectedPosition(context).isEmpty()) {
                spinner2.setSelection(Integer.parseInt(CommonMethods.getSelectedPosition(context)));
            }
        }


        /////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CommonMethods.saveSelectedPosition(context, "" + position);

                Object item = parent.getItemAtPosition(position);
                // String value = String.valueOf(item.toString());
                String value = String.valueOf(position);
                // consIDval=String.valueOf(position);
                consIDval = String.valueOf(spinner2.getSelectedItem());
                databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME,CONSUMER_ADD " +
                        "FROM CONSUMERDTLS WHERE CONSUMER_ID='" + consIDval + "'  ";
                Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
                Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);
                while (rs1.moveToNext()) {
                    //   spinpos1=rs1.getString(2);
                    conname = rs1.getString(1);
                }
                strConName.setText("" + conname);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //////////////
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tolbarhisdet, menu);
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
        if (id == R.id.consumption) { //View history
            Intent hisintent = new Intent(this,ConsumerMenuActivity.class);
            Bundle hisDet = new Bundle();
            hisDet.putString("reqtype", "conhis");
            hisDet.putString("consIDval", consIDval);
            hisDet.putString("conname", conname);
            hisintent.putExtras(hisDet);
            this.startActivity(hisintent);
            finish();
            return true;
        } else
        if (id == R.id.PmtBill) { //View history
            Intent hisintent = new Intent(this,ConsumerMenuActivity.class);
            Bundle hisDet = new Bundle();
            hisDet.putString("reqtype", "dethis");
            hisDet.putString("consIDval", consIDval);
            hisDet.putString("conname", conname);
            hisintent.putExtras(hisDet);
            this.startActivity(hisintent);
            finish();
            return true;
        } else if (id == R.id.PmtHistory) { //View history
            Intent hisintent = new Intent(this,ConsumerMenuActivity.class);
            Bundle hisDet = new Bundle();
            hisDet.putString("reqtype", "payhis");
            hisDet.putString("consIDval", consIDval);
            hisDet.putString("conname", conname);
            hisintent.putExtras(hisDet);
            this.startActivity(hisintent);
            finish();
            return true;
        }  else if (id == R.id.BlHistory) { //View history
            Intent hisintent = new Intent(this,ConsumerMenuActivity.class);
            Bundle hisDet = new Bundle();
            hisDet.putString("reqtype", "bilhis");
            hisDet.putString("consIDval", consIDval);
            hisDet.putString("conname", conname);
            hisintent.putExtras(hisDet);
            this.startActivity(hisintent);
            finish();
            return true;
        }
        else if (id == R.id.action_settings1) { //logout
            Toast.makeText(getApplicationContext(), "User Menu1", Toast.LENGTH_SHORT).show();
            finish();
            exit(0);
            return true;
        } else if (id == R.id.action_user) { //back
            Intent i = new Intent(this,UserDashboardActivity.class);
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
