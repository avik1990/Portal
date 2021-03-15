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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static java.lang.System.exit;

public class DeleteUserActivity extends AppCompatActivity {
    private   DatabaseAccess databaseAccess=null;
    final Context context = this;
    private Button deleteBtn;
    private static   RadioGroup ll;
    private static RadioButton rdbtn;
    private static String selconid="";
    private int chkresponse=1;
    private String StrUrl="";
    private String CompanyID="";
    private String AuthURL=null;
    private ProgressBar spinner;
    private ImageView iv_backs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);


        spinner=(ProgressBar)findViewById(R.id.progressBar);
        iv_backs=findViewById(R.id.iv_backs);

        iv_backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner.setVisibility(View.GONE);
        //Added by Santi on 040520
        SharedPreferences sessionlinkurl = getApplicationContext().getSharedPreferences("seslinkval", 0);
        String strurlval =sessionlinkurl.getString("strurladdr", null); // getting String
        //End///
        StrUrl="http://portal.tpcentralodisha.com:8070"+"/ePortalAPP/ePortal_App.jsp?";
       // Button btndeluser = (Button) findViewById(R.id.deluser);
        Button btnback = (Button) findViewById(R.id.back);
        deleteBtn=(Button)findViewById(R.id.delete);
        databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String strSelectSQL_02 = "SELECT CONSUMER_ID,CONSUMER_NAME "+
                "FROM CONSUMERDTLS WHERE VALIDATE_FLG=1 ";
        Log.d("DemoApp", "strSelectSQL_02" + strSelectSQL_02);
        Cursor rs1 = DatabaseAccess.database.rawQuery(strSelectSQL_02, null);

        LinearLayout layout = (LinearLayout) findViewById(R.id.rootContainer);
        ll = new RadioGroup(this);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addView(ll, p);
        String consid="";
        String consname="";
        while (rs1.moveToNext()) {
            consid = rs1.getString(0);
            consname = rs1.getString(1);
            rdbtn = new RadioButton(this);
            rdbtn.setText("ID:" + consid +"\n"+ "NAME:" + consname);
            rdbtn.setTextSize(15);
           // rdbtn.setClickable(false);
           // rdbtn.setFocusable(false);
           // rdbtn.setOnClickListener(mThisButtonListener);
            ll.addView(rdbtn, p);
        }
        databaseAccess.close();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll.getCheckedRadioButtonId() == -1) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("No Cosnumer ID selected");
                    alertDialogBuilder.setMessage("Please Select One Consumer ID")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   onBackPressed();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    // internetStatus.setText("Internet Disconnected.");
                    //  internetStatus.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = ll.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    rdbtn = (RadioButton) findViewById(selectedId);
                    String coniddd = rdbtn.getText().toString();
                    String[] rbinfo = coniddd.split("[:]");
                    selconid = rbinfo[1];
                    Log.d("DemoApp", " coniddd   " + coniddd);


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
                            DeleteUserActivity.this.finish();
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
        String mobval =sessiondata.getString("mobval", null); // getting String
        if(resCode==1){
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=6&ConsumerID="+selconid+"&Mobile_No="+mobval;
            new UserAuthOnline().execute(AuthURL);
        }else if(resCode==2){
            AuthURL = StrUrl+"CompanyID="+CompanyID+"&RequestType=6&ConsumerID="+selconid+"&Mobile_No="+mobval;
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
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteUserActivity.this.finish();
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
               // progressDialog = ProgressDialog.show(DeleteUserActivity.this, " ", " ");
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
                                DeleteUserActivity.this.finish();
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
            String[] BillInfo = pipeDelBillInfo.split("[|]");
            Log.d("DemoApp", " BillInfo[0]   " +pipeDelBillInfo);//authoriztion check

            if(BillInfo[0].equals("0")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Mobile No Not found");//Modify on 040520 by Santi
                alertDialogBuilder.setMessage("Plz check Mobile Number")//Modify on 040520 by Santi
                        .setCancelable(false)
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteUserActivity.this.finish();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else {
                if(BillInfo[1].equals("8")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Consumer ID not found");
                    alertDialogBuilder.setMessage("Enter Correct Number")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DeleteUserActivity.this.finish();
                                }
                            });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }else{
                    databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    String strSelectSQL_01 = "UPDATE CONSUMERDTLS SET VALIDATE_FLG=0" ;
                    Log.d("DemoApp", "strSelectSQL_01" + strSelectSQL_01);
                    DatabaseAccess.database.execSQL(strSelectSQL_01);
                    databaseAccess.close();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Delete Successfully ");
                    alertDialogBuilder.setMessage("User Delete Successfully ")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   Intent intent=new Intent(DeleteUserActivity.this,MainActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);
                                   finish();
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
