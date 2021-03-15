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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.System.exit;

public class TheftRegisterActivity extends AppCompatActivity {
    private String compinfo="";
    private static EditText strmobno;
    private static EditText strtheftdet;
    private int chkresponse=1;
    private String AuthURL=null;
    private String pipeDelBillInfo="";
    final Context context = this;
    private String StrUrl="";
    private String strmobnoval = "";
    private String strtheftdetval ="";
    private String strDate="";
    private ProgressBar spinner;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theft_register);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        iv_back=findViewById(R.id.iv_backs);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/IncomingSMS/CESU_SMS.jsp?";
        try{
        Bundle compType = getIntent().getExtras();
        compinfo = compType.getString("compinfo");
        }catch(Exception e){compinfo="0";}
        strmobno=(EditText)findViewById(R.id.mobno);
        strtheftdet=(EditText)findViewById(R.id.theftdet);
        Button btnsubmit=(Button)findViewById(R.id.submit);
        SharedPreferences sessiondata = getApplicationContext().getSharedPreferences("sessionval", 0);

        String mobval =sessiondata.getString("mobval", null); // getting String
        strmobno.setText(mobval);
       // Button btnback=(Button)findViewById(R.id.back);
        /*
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(compinfo.equals("reg")){
                    Intent UserDashboard = new Intent(getApplicationContext(), ComplaintDashboardActivity.class);
                    startActivity(UserDashboard);
                    finish();
                }else{
                    Intent QDashboard = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                    startActivity(QDashboard);
                    finish();
                }

            }
        });*/
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy^HH:mm:ss");
                    // SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
                    strDate = sdf.format(c.getTime());
                }catch (Exception e){e.printStackTrace();}
                String Message = "CESU THF DTL ";
                strmobnoval = strmobno.getText().toString();
                strtheftdetval = strtheftdet.getText().toString();
                strtheftdetval = Message +"^"+strtheftdetval ;
                strtheftdetval = strtheftdetval.replaceAll(" ", "^");
                strtheftdetval=strtheftdetval.replaceAll("\n", "");//added on 16.12.2017
                strtheftdetval=strtheftdetval.replaceAll("\t", "");//added on 16.12.2017
                Log.d("DemoApp", "consIDval" + strmobnoval);

                if (strmobnoval.length()==0){
                    strmobno.setError(getString(R.string.enter_phoner_number));
                    strmobno.requestFocus();
                }

               else if(strmobnoval.length()>0 && strmobnoval.length()<10 ) {
                    strmobno.setError(getString(R.string.invalid_mobile));
                    strmobno.requestFocus();
                }
                else if(strtheftdet.getText().toString().length()==0) {
                    strtheftdet.setError("Please enter  theft Description");
                    strtheftdet.requestFocus();
                } else{
                    //Added on 040520 by Santi
                    if (strmobnoval.length() == 10) {
                        if (CommonMethods.isValidMobile(strmobnoval)) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                changeTextStatus(true);
                            } else {
                                changeTextStatus(false);
                            }
                        } else {
                            strmobno.setError("Mobile digit start from 6/7/8/9");
                            strmobno.requestFocus();
                        }
                    }
                    ///end
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
                            TheftRegisterActivity.this.finish();
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
           // String strURLFullPath = "http://portal1.tpcentralodisha.com:8080/IncomingSMS/CESU_SMS.jsp?un=CESU_APPS&pw=CESU_THF&sender="+smsaddress.trim()+"&message=" + msgbody + "&stime=" + strDate;
            AuthURL =StrUrl+"un=CESU_APPS&pw=CESU_THF&sender="+strmobnoval+"&message="+strtheftdetval+"&stime="+strDate;

            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL =StrUrl+"un=CESU_APPS&pw=CESU_THF&sender="+strmobnoval+"&message="+strtheftdetval+"&stime="+strDate;
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
                                TheftRegisterActivity.this.finish();
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
               // progressDialog = ProgressDialog.show(TheftRegisterActivity.this, " ", " ");
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
                                TheftRegisterActivity.this.finish();
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
            pipeDelBillInfo = str;

            //  pipeDelBillInfo="1|1|Resolved|sdfsdfsdffsdfsd"; //Complain register

            // String[] BillInfo = pipeDelBillInfo.split("[|]");
            // Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check
          //if(BillInfo[1].equals("1")){//Theft sucess
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Theft incident submitted successfully");
            alertDialogBuilder.setMessage("Thank You")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onBackPressed();
                        /*    if (compinfo.equals("reg")) {
                                Intent curbill = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(curbill);
                                finish();
                            } else {
                                Intent curbill = new Intent(getApplicationContext(), QuicklinksDashboard.class);
                                startActivity(curbill);
                                finish();
                            }*/
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        //  }
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
            if(compinfo.equals("reg")) {
                Intent i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                finish();
            }else{
                Intent i = new Intent(this,QuicklinksDashboard.class);
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
