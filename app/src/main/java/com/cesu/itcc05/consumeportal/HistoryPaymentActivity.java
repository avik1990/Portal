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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class HistoryPaymentActivity extends AppCompatActivity {
    private static TextView strmon2;
    private static TextView strbill2;
    private static TextView strpmt2;
    private static TextView strdate2;
    private static TextView strmon3;
    private static TextView strbill3;
    private static TextView strpmt3;
    private static TextView strdate3;
    private static TextView strmon4;
    private static TextView strbill4;
    private static TextView strpmt4;
    private static TextView strdate4;
    private static TextView strmon5;
    private static TextView strbill5;
    private static TextView strpmt5;
    private static TextView strdate5;
    private static TextView strmon6;
    private static TextView strbill6;
    private static TextView strpmt6;
    private static TextView strdate6;
    private static TextView strmon1;
    private static TextView strbill1;
    private static TextView strpmt1;
    private static TextView strdate1;
    private static TextView strconName;
    private String pipeDelBillInfo="";
    final Context context = this;
    private int chkresponse=1;
    private int reqsttype=0;
    private String AuthURL=null;
    private int procType=0;
    private String consacc="";
    private String divcode="";
    private String dbtype="";
    private String mobval="";
    private String ConsID="";
    private String imeinum="";
    private String CompanyID="";
    private  String consIDval="";
    private  String conname="";
    private ProgressBar spinner;
    private String StrUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070";
        strconName =(TextView)findViewById(R.id.conName);
        strmon2 =(TextView)findViewById(R.id.mon2);
        strbill2=(TextView)findViewById(R.id.bill2);
        strpmt2=(TextView)findViewById(R.id.pmt2);
        strdate2=(TextView)findViewById(R.id.date2);
        strmon3 =(TextView)findViewById(R.id.mon3);
        strbill3=(TextView)findViewById(R.id.bill3);
        strpmt3=(TextView)findViewById(R.id.pmt3);
        strdate3=(TextView)findViewById(R.id.date3);
        strmon4=(TextView)findViewById(R.id.mon4);
        strbill4=(TextView)findViewById(R.id.bill4);
        strpmt4=(TextView)findViewById(R.id.pmt4);
        strdate4=(TextView)findViewById(R.id.date4);
        strmon5=(TextView)findViewById(R.id.mon5);
        strbill5=(TextView)findViewById(R.id.bill5);
        strpmt5=(TextView)findViewById(R.id.pmt5);
        strdate5=(TextView)findViewById(R.id.date5);
        strmon6=(TextView)findViewById(R.id.mon6);
        strbill6=(TextView)findViewById(R.id.bill6);
        strpmt6=(TextView)findViewById(R.id.pmt6);
        strdate6=(TextView)findViewById(R.id.date6);

        strmon1=(TextView)findViewById(R.id.mon1);
        strbill1=(TextView)findViewById(R.id.bill1);
        strpmt1=(TextView)findViewById(R.id.pmt1);
        strdate1=(TextView)findViewById(R.id.date1);
       // Button btnConHis = (Button) findViewById(R.id.ConHis);
       // Button btnpaynlhis= (Button) findViewById(R.id.paynlhis);
        Bundle parmtrDet = getIntent().getExtras();
        pipeDelBillInfo = parmtrDet.getString("pipeDelBillInfo");
        consIDval = parmtrDet.getString("consIDval");
        conname = parmtrDet.getString("conname");
        String[] BillInfo = pipeDelBillInfo.split("[|]");
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
                            HistoryPaymentActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[1].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Report ID Not Found");
            alertDialogBuilder.setMessage("Report IDNot Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryPaymentActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else if(BillInfo[2].equals("0")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Record Not Found");
            alertDialogBuilder.setMessage("Record Not Found")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HistoryPaymentActivity.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            Log.d("DemoApp", "[4]   ") ;
        }else{
            SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);
            String ConsID =sessiondata.getString("ConsID", null); // getting String
            //strconName.setText("Consumer ID: " + ConsID);
            strconName.setText(conname +"\n"+"("+consIDval+")");
            int arrayparam=70;
            String strData=BillInfo[3];
            String[] strData1=strData.split("[;]");
            String[] ContentWrap = new String[arrayparam];
            // 1|1|1|10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0;09-2019,186,.35,09-2019,00074-79,799,24-09-2019,0;08-2019,130,-.24,08-2019,00552272,555,19-08-2019,0;07-2019,351,0,07-2019,00326691,1678,22-07-2019,0;06-2019,259,-.01,06-2019,00056002,1178,20-06-2019,0;05-2019,132,-2.12,05-2019,00194076,800,22-05-2019,0;04-2019,231,234.38,04-2019,00184406,800,27-04-2019,0;03-2019,177,-17.29,03-2019,0074-152,760,23-03-2019,0;02-2019,138,.17,02-2019,00139625,590,23-02-2019,0;
            String monData="";
            String[] MonthData = null;
            Log.d("DemoApp", " strData1.length   " + strData1.length);
            int j=0;
            //k=, data
            //i=month data
            // j= param data
            int moncnt=strData1.length;
            for(int i=0;i<strData1.length;i++){
                try{

                    monData=strData1[i];
                    // 10-2019,155,.41,10-2019,00074-44,664,25-10-2019,0
                    MonthData = null;
                    MonthData=monData.split("[,]");
                    Log.d("DemoApp", " MonthData.length   " + MonthData.length);
                    for(int k=0;k<MonthData.length;k++){
                        ContentWrap[j]=MonthData[k];
                        j++;
                    }

                    //  Log.d("DemoApp", " strData1.length   " + strData1.length);
                }catch (Exception e){e.printStackTrace();}
            }
            Log.d("DemoApp", " ContentWrap.length   " + ContentWrap.length);
            String[] finContentWrap = new String[arrayparam];
            for(int i=0;i<arrayparam;i++){
                finContentWrap[i]=ContentWrap[i];
                if(ContentWrap[i]==null){
                    finContentWrap[i]="-";
                }
            }

            strmon1.setText(finContentWrap[0]);
            strmon2.setText(finContentWrap[10]);
            strmon3.setText(finContentWrap[20]);
            strmon4.setText(finContentWrap[30]);
            strmon5.setText(finContentWrap[40]);
            strmon6.setText(finContentWrap[50]);

            strbill1.setText(finContentWrap[6]);
            strbill2.setText(finContentWrap[16]);
            strbill3.setText(finContentWrap[26]);
            strbill4.setText(finContentWrap[36]);
            strbill5.setText(finContentWrap[46]);
            strbill6.setText(finContentWrap[56]);

            strpmt1.setText(finContentWrap[4]);
            strpmt2.setText(finContentWrap[14]);
            strpmt3.setText(finContentWrap[24]);
            strpmt4.setText(finContentWrap[34]);
            strpmt5.setText(finContentWrap[44]);
            strpmt6.setText(finContentWrap[54]);

            strdate1.setText(finContentWrap[5]);
            strdate2.setText(finContentWrap[15]);
            strdate3.setText(finContentWrap[25]);
            strdate4.setText(finContentWrap[35]);
            strdate5.setText(finContentWrap[45]);
            strdate6.setText(finContentWrap[55]);

        }
        /*
        btnConHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,"conhis");
                } else {
                    changeTextStatus(false,"conhis");
                }
            }
        });

        btnpaynlhis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    changeTextStatus(true,"dethis");
                } else {
                    changeTextStatus(false,"dethis");
                }
            }
        });
        */
    }
    public void changeTextStatus(boolean isConnected,String reqtype) {
        if(reqtype.equals("conhis")){reqsttype=1;}
        if(reqtype.equals("payhis")){reqsttype=2;}
        if(reqtype.equals("dethis")){reqsttype=3;}
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
                            HistoryPaymentActivity.this.finish();
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
        ConsID =sessiondata.getString("ConsID", null); // getting String
        mobval =sessiondata.getString("mobval", null); // getting String
        imeinum =sessiondata.getString("imeinum", null); // getting String
        CompanyID =sessiondata.getString("CompanyID", null); // getting String
        divcode=ConsID.substring(0,3);
        dbtype=ConsID.substring(3,4);
        consacc=ConsID.substring(4);
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
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;new UserAuthOnline().execute(AuthURL);
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
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;new UserAuthOnline().execute(AuthURL);
            }else if(resCode==2){
                AuthURL="";
                AuthURL=StrUrl+"/IncomingSMS/CESU_DynamicReport.jsp?un=TEST&pw=TEST&CompanyID="+CompanyID+"&ReportID=1074&strDivCode="+divcode+"&strCons_Acc="+consacc;new UserAuthOnline().execute(AuthURL);
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
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HistoryPaymentActivity.this.finish();
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
                //progressDialog = ProgressDialog.show(HistoryPaymentActivity.this, " ", " ");
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
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HistoryPaymentActivity.this.finish();
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
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else if (procType == 2) {//pmt history
                Intent curblintent = new Intent(getApplicationContext(), HistoryPaymentActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            } else if (procType == 3) {//graph history
                Intent curblintent = new Intent(getApplicationContext(), HistoryPaymentBillActivity.class);
                Bundle parmtrDet = new Bundle();
                parmtrDet.putString("pipeDelBillInfo", pipeDelBillInfo);
                curblintent.putExtras(parmtrDet);
                startActivity(curblintent);
                finish();
            }

        }
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
        if (id == R.id.action_settings1) { //logout
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
