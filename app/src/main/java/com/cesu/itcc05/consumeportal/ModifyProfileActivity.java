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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class ModifyProfileActivity extends AppCompatActivity {
    private  TextView strmobmsg;
    private  EditText newmail,newmob,newmob1,conid,email,mob;
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private int chkresponse=1;
    private String AuthURL=null;
    private CheckBox chkemail;
    private CheckBox chkmobno;
    private String StrUrl="";
    private String CompanyID="";
    private String versionID="";
    private String  snewmob = "";
    private String  snewmail = "";
    private String consid="";
    private String mobNo="";
    private String eMail="";
    private ProgressBar spinner;
    private ImageView iv_backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        iv_backs=findViewById(R.id.iv_backs);

        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
        conid=(EditText) findViewById(R.id.consIDval);
        email=(EditText) findViewById(R.id.emailval);
        mob=(EditText) findViewById(R.id.mobval);
        newmob=(EditText) findViewById(R.id.mobvalnew);
        newmob1=(EditText) findViewById(R.id.mobvalnew1);
        newmail=(EditText) findViewById(R.id.emailvalnew);
       // Button btnback = (Button) findViewById(R.id.exit);
        Button btnsubmit = (Button) findViewById(R.id.submit);

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_01 = "SELECT VERSION_NO,VERSION_NAME,COMPANYID "+
                "FROM SWVERSION WHERE RECORD_STS=1";
        Cursor cursor1 = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);
        String swversion = "";
        while (cursor1.moveToNext()) {
            swversion="Version:"+cursor1.getString(0)+"-"+cursor1.getString(1);
            Log.d("DemoApp", "in Loop conid" + swversion);
            CompanyID=cursor1.getString(2);
        }
        cursor1.close();
        databaseAccess.close();
        databaseAccess.open();
        strSelectSQL_01 ="";
        strSelectSQL_01 = "SELECT CONSUMER_ID,MOBILENO,EMAIL "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1 ";
        Cursor cursor = DatabaseAccess.database.rawQuery(strSelectSQL_01, null);
        Log.d("DemoApp", "Query SQL " + strSelectSQL_01);

        while (cursor.moveToNext()) {
            consid=cursor.getString(0);
            mobNo=cursor.getString(1);
            eMail=cursor.getString(2);
        }
        cursor.close();
        databaseAccess.close();
        conid.setText(consid);
        conid.setFocusable(false);
        conid.setEnabled(false);
        if (mobNo!=null){
            mob.setText(mobNo);
        }
        email.setFocusable(false);
        email.setEnabled(false);
        if (!(email.getText().toString().trim().equalsIgnoreCase(""))||(!(eMail.trim().equalsIgnoreCase("0")))){
            email.setText(eMail);
        }
        else {
            email.setHint(ModifyProfileActivity.this.getString(R.string.enter_email_id));
        }

        mob.setFocusable(false);
        mob.setEnabled(false);
        //terms and condition agree
        chkemail = (CheckBox)findViewById(R.id.emailchk);
        chkmobno = (CheckBox)findViewById(R.id.mobchk);
        chkemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    // System.out.println("Checked");
                    newmail.setEnabled(true);
                    newmail.setClickable(true);
                } else {
                    newmail.setEnabled(false);
                    newmail.setClickable(false);
                }
            }
        });
        chkmobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    // System.out.println("Checked");
                    newmob.setEnabled(true);
                    newmob.setClickable(true);
                } else {
                    newmob.setEnabled(false);
                    newmob.setClickable(false);
                }
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snewmob = newmob.getText().toString();
                String snewmob1 = newmob1.getText().toString();
                snewmail = newmail.getText().toString();
                Log.d("DemoApp", "consIDval" + snewmob);
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                if (!snewmail.matches(emailPattern) && snewmail.length() > 0) {
                    newmail.setError("Enter Valid e-Mail ID");
                    newmail.requestFocus();
                }

                else if(TextUtils.isEmpty(snewmob) ) {
                    newmob.setError("Please enter mobile number");
                    newmob.requestFocus();
                }else if(snewmob.length()>0 && snewmob.length()<10){
                    newmob.setError("Enter Valid Mobile No of 10 digit");
                    newmob.requestFocus();
                }else if(!snewmob.equals(snewmob1)){
                    newmob.setError("Enter Mobile No. Mismatch");
                    newmob.requestFocus();
                }else{
                    //Added on 040520 by Santi
                    if (snewmob.length() == 10) {
                        if (CommonMethods.isValidMobile(snewmob)) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                changeTextStatus(true);
                            } else {
                                changeTextStatus(false);
                            }
                        } else {
                            newmob.setError("Mobile digit start from 6/7/8/9");
                            newmob.requestFocus();
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
                    .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ModifyProfileActivity.this.finish();
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
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=4&ConsumerID="+consid+"&Mobile_No="+mobNo+"&e_Mail="+snewmail+"&NewMob="+snewmob;
            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=4&ConsumerID="+consid+"&Mobile_No="+mobNo+"&e_Mail="+snewmail+"&NewMob="+snewmob;
            new UserAuthOnline().execute(AuthURL);
        }else{
            AuthURL="ServerOut";
            //  int code=AlertErrorCall.ErrorMsgType(6);
            //   if(code==1){MainActivity.this.finish();}


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
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ModifyProfileActivity.this.finish();
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
                //progressDialog = ProgressDialog.show(ModifyProfileActivity.this, " ", " ");
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
                                ModifyProfileActivity.this.finish();
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
          //  pipeDelBillInfo="2|1111111111|102S03127108|dghghgsdghgsd@jhjhh.com";

            String[] BillInfo = pipeDelBillInfo.split("[|]");
          //  String NewMobileNo=BillInfo[1];
         //   String ConsID=BillInfo[2];
         //   String email=BillInfo[3];


            //Modify user
            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Mobile No Not found");//Modify on 040520 by Santi
                alertDialogBuilder.setMessage("Enter Correct Mobile number")//Modify on 040520 by Santi
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ModifyProfileActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else {
                if(BillInfo[1].equals("9")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Consumer ID not found");
                    alertDialogBuilder.setMessage("Enter Correct Number")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ModifyProfileActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else if(BillInfo[1].equals("1")){
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "";
                    if (email.length() > 5) {
                        strSelectSQL_01 = "UPDATE CONSUMERDTLS SET MOBILENO='" + snewmob + "',EMAIL='" + snewmail + "' WHERE CONSUMER_ID='" + consid + "' AND VALIDATE_FLG=1";
                    } else {
                        strSelectSQL_01 = "UPDATE CONSUMERDTLS SET MOBILENO='" + snewmob + "' WHERE CONSUMER_ID='" + consid + "' AND VALIDATE_FLG=1";
                    }

                    Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                    DatabaseAccess.database.execSQL(strSelectSQL_01);
                    databaseAccess.close();
                    Intent usrdahbrd = new Intent(getApplicationContext(), UserDashboardActivity.class);//Modify on 040520 by Santi
                    startActivity(usrdahbrd);//Modify on 040520 by Santi
                    finish();//Modify on 040520 by Santi
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Data Error");
                    alertDialogBuilder.setMessage("Data Error")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ModifyProfileActivity.this.finish();
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
